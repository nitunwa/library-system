package com.systemlibrary.models;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class BorrowBooDao {

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Save the book in the database.
	 */

	public void create(BorrowBook borrowBook) {
		entityManager.persist(borrowBook);
		return;
	}
	
	/**
	 *  update return book list
	 */
	
	public Boolean updateReturnBorrowBookList(BorrowBook borrowBook) {
		
		BorrowBook	borrowBookDb=	entityManager.find(BorrowBook.class, borrowBook.getId());
		borrowBookDb.setReturnDate(new Date());
	    entityManager.merge(borrowBookDb);
	    return true;
	  }
	 
	public List<BorrowBook> getBorrowBookListByUserId(User user) {
		String sql = "SELECT bb FROM BorrowBook bb "
				+ "JOIN FETCH bb.borrrowUser u JOIN FETCH bb.borrowBook "
				+ " b where bb.borrrowUser.id=:id and  bb.returnDate = null";
		List<BorrowBook> borrowBookList = entityManager.createQuery(sql, BorrowBook.class)
				.setParameter("id", user.getId()).getResultList();
		System.out.println("BorrowBooDao.getBorrowBookListByUserId()" + borrowBookList.size());
		return borrowBookList;
	}
	
	

	public Long totalBorrowBook(User user) {

		// select count(*) from BorrowBook bb where bb.borrrowUserId=3;(user how
		// many book got)

		// String sql="select count(bb.id) from BorrowBook bb where
		// bb.borrrowUser.id=:id";(how many book(id=20) left)[member]

		// String sql="select count(bb.id) from BorrowBook bb where
		// bb.borrowBook.id=20";

		String sql = "select count(bb.id) from  BorrowBook bb where  bb.borrrowUser.id=:id "
				+ " and  bb.returnDate = null";
		Long bookTotal = ((Number) entityManager.createQuery(sql, Number.class).setParameter("id", user.getId())
				.getSingleResult()).longValue();
		return bookTotal;
	}
	
	
	public Long totalExpairBorrowBook(User user,Date expairDate){
		String sql= "select count(bb.id) from  BorrowBook bb where  bb.borrrowUser.id=:id  and bb.checkIn<= :expairDate "
				+ " and  bb.returnDate = null";
		Long exTotalBorrowBook =((Number) entityManager.createQuery(sql, Number.class).
				setParameter("id", user.getId()).setParameter("expairDate", expairDate)
				.getSingleResult()).longValue();
		
		return exTotalBorrowBook;
	}
	public Long totalLaterExpairBorrowBook(User user,Date laterDueDate){
		String sql= "select count(bb.id) from  BorrowBook bb where  bb.borrrowUser.id=:id  and bb.checkIn>= :laterDueDate "
				+ " and  bb.returnDate = null";
		Long laterExTotalBorrowBook =((Number) entityManager.createQuery(sql, Number.class).
				setParameter("id", user.getId()).setParameter("laterDueDate", laterDueDate)
				.getSingleResult()).longValue();
		
		return laterExTotalBorrowBook;
	}
	public List<BorrowBook> getlaterDueBorrowBookList(User user,Date laterDueDate){
		String sql= "select bb from  BorrowBook bb where  bb.borrrowUser.id=:id  and bb.checkIn>= :laterDueDate "
				+ " and  bb.returnDate = null";
		List<BorrowBook> laterDueBookList = entityManager.createQuery(sql, BorrowBook.class)
				.setParameter("id", user.getId()).setParameter("laterDueDate", laterDueDate).getResultList();
		
		return laterDueBookList;
	}

	public Boolean hasExpairBooks(User user, Date remainderDate) {
		String sql = "select count(bb.id) from  BorrowBook bb where  bb.borrrowUser.id=:id and bb.checkIn<= :remainderDate "
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

}
