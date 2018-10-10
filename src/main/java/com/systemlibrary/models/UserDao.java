package com.systemlibrary.models;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.systemlibrary.models.User;

@Repository
@Transactional
public class UserDao {

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Save the book in the database.
	 */

	public void create(User user) {
		entityManager.persist(user);
		return;
	}

	/**
	 * Delete the book from the database.
	 */

	public void delete(User user) {

		User u = entityManager.find(User.class, user.getId());
		entityManager.remove(u);

	}

	/**
	 * Return all the book stored in the database.
	 */

	@SuppressWarnings("unchecked")
	public List<User> getAllUser() {
		return entityManager.createQuery("from User").getResultList();
	}

	/**
	 * Return the user to pass the user email.
	 */
	public User getByEmail(String email) {
		return (User) entityManager.createQuery("from   User where email= :Email").setParameter("Email", email)
				.getSingleResult();
	}

	public void login(User user) {

		entityManager.merge(user);
		return;
	}
}
