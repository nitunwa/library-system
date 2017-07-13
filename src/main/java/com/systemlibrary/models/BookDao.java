package com.systemlibrary.models;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class BookDao {

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Save the book in the database.
	 */

	public void create(Book book) {
		entityManager.persist(book);
		return;
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
