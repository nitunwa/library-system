package com.systemlibrary.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional(readOnly=true)
public class BookDao {

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Save the book in the database.
	 */

	public void create(Book book) {
		entityManager.persist(book);
		System.out.println("BookDao.create()" +book.getId());
		
	}
	@SuppressWarnings("unused")
	public Boolean isBookExist(Book newBook) {
		try {
			String sql = "from   Book where bookName= :bookName";
			
			Book existingBook = entityManager.createQuery(sql, Book.class)
					           .setParameter("bookName", newBook.getBookName()).getSingleResult();
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * Delete the book from the database.
	 */

	public void delete(Book book) {
		if (entityManager.contains(book))
			entityManager.remove(book);
		entityManager.remove(entityManager.merge(book));
		return;

	}

	/**
	 * Return all the book stored in the database.
	 */

	@SuppressWarnings("unchecked")
	public List<Book> getAllBook() {
		return entityManager.createQuery("from Book").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Book> getBookName(String category) {
		return entityManager.createQuery("from Book where category= :type ")
				              .setParameter("type", category)
				              .getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Book> getBookName(Book book) {
		if(book.getCategory()!=null &&  ! "".equals(book.getCategory()) ){ // is user set category
			return entityManager.createQuery("from Book where category= :category ")
		              .setParameter("category", book.getCategory())
		              .getResultList();
			
		}else if(book.getAuthor()!=null &&  ! "".equals(book.getAuthor()) ){ // is user set category
			return entityManager.createQuery("from Book where author= :author ")
		              .setParameter("author", book.getAuthor())
		              .getResultList();
			
		}else{
			return new ArrayList<Book>();
		}
		
		
	}

	/**
	 * Return the book having the passed user name.
	 */
	public Book getById(Long bookId) {
		return (Book) entityManager.createQuery("from   Book where id= :bookId").setParameter("bookId", bookId)
				.getSingleResult();
	}

	public Book getByName(String bookname) {
		return (Book) entityManager.createQuery("from   Book where bookName= :bookName")
				.setParameter("bookName", bookname).getSingleResult();
	}

}
