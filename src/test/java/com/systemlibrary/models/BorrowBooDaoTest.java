package com.systemlibrary.models;

import static org.junit.Assert.*;

import org.junit.Test;

public class BorrowBooDaoTest {
	BorrowBooDao borrowBooDao = new BorrowBooDao();
	User user = new User();

	@Test
	public void testTotalBorrowBook() throws Exception {

		assertEquals("failure - total borrowBook  are not equal", new Long(4), borrowBooDao.totalBorrowBook(user));

	}

	@Test
	public void testTotalLaterExpairBorrowBook() {
		assertEquals("failure - total borrowBook  are not equal", new Long(4), borrowBooDao.totalBorrowBook(user));
	}

	@Test
	public void testHasExpairBooks() {
		//fail("Not yet implemented");
	}

	@Test
	public void testGetAllMemberBorrowBookList() {
		fail("Not yet implemented");
	}

}
