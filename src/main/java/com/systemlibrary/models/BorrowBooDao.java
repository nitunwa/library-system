package com.systemlibrary.models;
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
	
	 
}
