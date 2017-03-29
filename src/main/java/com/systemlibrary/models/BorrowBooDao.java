package com.systemlibrary.models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.systemlibrary.utility.DateUtility;

@Repository
@Transactional
public class BorrowBooDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private DateUtility dateUtility;

	/**
	 * Save the book in the database.
	 */

	public void create(BorrowBook borrowBook) {
		entityManager.persist(borrowBook);
		return;
	}

	/**
	 * update return book list
	 */

	public Boolean updateReturnBorrowBookList(BorrowBook borrowBook) {

		BorrowBook borrowBookDb = entityManager.find(BorrowBook.class, borrowBook.getId());
		borrowBookDb.setReturnDate(new Date());
		entityManager.merge(borrowBookDb);
		return true;
	}

	/** renew book */

	public Boolean renewBorrowBook(BorrowBook borrowBook) {

		BorrowBook borrowBookDb = entityManager.find(BorrowBook.class, borrowBook.getId());// pull
																							// the
																							// book
																							// id
																							// from
																							// database
		Date renew = dateUtility.addDays(borrowBookDb.getCheckIn(), 5);
		borrowBookDb.setCheckIn(renew); // renew the checkin date
		entityManager.merge(borrowBookDb);
		return true;
	}

	/** end renew book */

	public List<BorrowBook> getBorrowBookListByUserId(User user) {
		String sql = "SELECT bb FROM BorrowBook bb " + "JOIN FETCH bb.borrrowUser u JOIN FETCH bb.borrowBook "
				+ " b where bb.borrrowUser.id=:id and  bb.returnDate = null";
		List<BorrowBook> borrowBookList = entityManager.createQuery(sql, BorrowBook.class)
				.setParameter("id", user.getId()).getResultList();
		System.out.println("BorrowBooDao.getBorrowBookListByUserId()" + borrowBookList.size());
		return borrowBookList;
	}

	// total borrow book number (like-checked out --->5 url==member/borrowBook)
	public Long totalBorrowBook(User user) {

		String sql = "select count(bb.id) from  BorrowBook bb where  bb.borrrowUser.id=:id "
				+ " and  bb.returnDate = null";
		Long bookTotal = ((Number) entityManager.createQuery(sql, Number.class).setParameter("id", user.getId())
				.getSingleResult()).longValue();
		return bookTotal;

	} // end total borrow book number

	// total reminder(due soon) book number
	public Long dueSoonExpairBorrowBookNumber(User user, Date reminderDate) {
		String sql = "select count(bb.id) from  BorrowBook bb where  bb.borrrowUser.id=:id  and bb.checkIn >= :dueSoonExpairDate "
				+ " and  bb.returnDate = null";
		Long exTotalBorrowBook = ((Number) entityManager.createQuery(sql, Number.class).setParameter("id", user.getId())
				.setParameter("dueSoonExpairDate", reminderDate).getSingleResult()).longValue();

		return exTotalBorrowBook;
	}  // total reminder(due soon) book number

	// total next due book number
	public Long totalLaterExpairBorrowBook(User user, Date laterDueDate) {
		String sql = "select count(bb.id) from  BorrowBook bb where  bb.borrrowUser.id=:id  and bb.checkIn>= :laterDueDate "
				+ " and  bb.returnDate = null";
		Long laterExTotalBorrowBook = ((Number) entityManager.createQuery(sql, Number.class)
				.setParameter("id", user.getId()).setParameter("laterDueDate", laterDueDate).getSingleResult())
						.longValue();

		return laterExTotalBorrowBook;
	}

	public List<BorrowBook> getlaterDueBorrowBookList(User user, Date laterDueDate) {
		String sql = "select bb from  BorrowBook bb where  bb.borrrowUser.id=:id  and bb.checkIn>= :laterDueDate "
				+ " and  bb.returnDate = null";
		List<BorrowBook> laterDueBookList = entityManager.createQuery(sql, BorrowBook.class)
				.setParameter("id", user.getId()).setParameter("laterDueDate", laterDueDate).getResultList();

		return laterDueBookList;
	}

	public Boolean hasExpairBooks(User user) {
		Date remainderDate = new Date();
		String sql = "select count(bb.id) from  BorrowBook bb where  bb.borrrowUser.id=:id and bb.checkIn< :remainderDate "
				+ " and  bb.returnDate = null";
		Long totalExpairBook = ((Number) entityManager.createQuery(sql, Number.class).setParameter("id", user.getId())
				.setParameter("remainderDate", remainderDate).getSingleResult()).longValue();
		if (totalExpairBook == 0) {
			return false;
		} else {
			return true;
		}
	}

	public List<BorrowBook> getExpairBooksList(User user, Date remainderDate) {
		String sql = "select bb from  BorrowBook bb where  bb.borrrowUser.id=:id and bb.checkIn<= :remainderDate "
				+ "and  bb.returnDate = null ";
		List<BorrowBook> expairBookList = entityManager.createQuery(sql, BorrowBook.class)
				.setParameter("id", user.getId()).setParameter("remainderDate", remainderDate).getResultList();

		return expairBookList;

	}

	// show all member's name & email with total number of book
	@SuppressWarnings("unchecked")
	public List<BorrowBookDto> getAllMemberBorrowBookList() {
		String sql = "SELECT  bb.borrrowUserId, u.email, count(*) AS 'Total Book' " + " FROM systemlibraryv1.user u "
				+ " join borrowbook bb on u.id = bb.borrrowUserId " + " join book b on bb.borrrowBookId = b.id "
				+ " GROUP BY   bb.borrrowUserId ";
		List<Object[]> memberBookObjList = entityManager.createNativeQuery(sql).getResultList();

		List<BorrowBookDto> memberBookDtoList = new ArrayList<BorrowBookDto>(memberBookObjList.size());
		for (Object[] objArry : memberBookObjList) {
			BorrowBookDto bdto = new BorrowBookDto();
			User user = new User();
			Book book = new Book();
			user.setId(new Long(objArry[0].toString()));
			user.setEmail(objArry[1].toString());

			bdto.setTotalnumbook(new Integer(objArry[2].toString()));
			bdto.setBook(book);
			bdto.setUser(user);

			memberBookDtoList.add(bdto);

		}

		return memberBookDtoList;

	}

	// show single member's name & email with total number of book
	@SuppressWarnings("unchecked")
	public List<BorrowBookDto> getSingleMemberBorrowBookList(User member) {
		String sql = "SELECT  bb.borrrowUserId, u.email, b.bookname, count(*) AS 'Total Book' "
				+ " FROM systemlibraryv1.user u " + " join borrowbook bb on u.id = bb.borrrowUserId "
				+ " join book b on bb.borrrowBookId = b.id " + " where u.email = :email "
				+ " GROUP BY   bb.borrrowUserId,b.bookname; ";
		List<Object[]> singleMemBookObjList = entityManager.createNativeQuery(sql)
				.setParameter("email", member.getEmail()).getResultList();

		List<BorrowBookDto> singleMemBookDtoList = new ArrayList<BorrowBookDto>(singleMemBookObjList.size());
		for (Object[] objArry : singleMemBookObjList) {
			BorrowBookDto bdto = new BorrowBookDto();
			User user = new User();
			Book book = new Book();  
			user.setId(new Long(objArry[0].toString()));
			user.setEmail(objArry[1].toString());
            book.setBookName(objArry[2].toString());
			bdto.setTotalnumbook(new Integer(objArry[3].toString()));
			bdto.setBook(book);
			bdto.setUser(user);

			singleMemBookDtoList.add(bdto);

		}

		return singleMemBookDtoList;

	}

}
