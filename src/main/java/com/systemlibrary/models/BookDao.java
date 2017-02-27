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
	
	public void create(Book book)
	{
		entityManager.persist(book);
		return;
	}
	
	 /**
	   * Delete the book from the database.
	   */
	
	public void delete(Book book)
	{
	 if(entityManager.contains( book))
		 entityManager.remove(book);
	 entityManager.remove(entityManager.merge(book));
	    return;
		 
	}
	
	 /**
	   * Return all the book stored in the database.
	   */
	
	@SuppressWarnings("unchecked")
	public List<Book> getAll() {
	    return entityManager.createQuery("from Book").getResultList();
	  }
	
	/**
	   * Return the book having the passed user name.
	   */
	public Book getByEmail(String ISDN) {
	    return (Book) entityManager.createQuery(
	        "from   Book where ISDN= :ISDN")
	        .setParameter("Email",ISDN)
	        .getSingleResult();
	  }
	
	public void login(Book book) {
	    entityManager.merge(book);
	    return;
}
}
