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
	
	public void create(BorrowBook borrowBook)
	{
		entityManager.persist(borrowBook);
		return;
	}
	
	public List<BorrowBook> getBorrowBookListByUserId(User user)
	{
		String sql="SELECT bb FROM BorrowBook bb JOIN FETCH bb.borrrowUser u JOIN FETCH bb.borrowBook b where bb.borrrowUser.id=:id";
		List<BorrowBook>  borrowBookList=entityManager.createQuery(sql, BorrowBook.class).setParameter("id", user.getId()).getResultList();
		System.out.println("BorrowBooDao.getBorrowBookListByUserId()" +borrowBookList.size());
		return borrowBookList;
	}
	
	
	
	public Long totalBorrowBook(User user)
	{
	
	
		// select count(*) from BorrowBook bb where bb.borrrowUserId=3;(user how many book got)
		
		//String sql="select count(bb.id) from BorrowBook bb where  bb.borrrowUser.id=:id";(how many book(id=20) left)[member]
		
		//String sql="select count(bb.id) from BorrowBook bb where  bb.borrowBook.id=20";
		
	    String sql="select count(bb.id) from  BorrowBook bb where  bb.borrrowUser.id=:id";
		Long bookTotal= ((Number)entityManager.createQuery(sql,Number.class ).setParameter("id", user.getId())
                .getSingleResult()).longValue();
		return bookTotal;
	}
	
	public Boolean hasExpairBooks(User user,Date remainderDate)
	{
		String sql="select count(bb.id) from  BorrowBook bb where  bb.borrrowUser.id=:id and bb.checkOut<= :remainderDate";
		Long totalExpairBook= ((Number)entityManager.createQuery(sql,Number.class )
							  .setParameter("id", user.getId())
							  .setParameter("remainderDate", remainderDate)
                               .getSingleResult())
				               .longValue();
		if(totalExpairBook == 0){
			return false;
		}
		else{
			return true;
		}
		
	}
	
}
