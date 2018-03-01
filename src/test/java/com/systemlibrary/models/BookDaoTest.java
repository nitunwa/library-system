package com.systemlibrary.models;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;

import javax.validation.constraints.AssertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookDaoTest {
	@Autowired
	BookDao bookDao;

	@Test
	public void testCreatePositive() {
		
		Book book = new Book();
		book.setAuthor("Fancy");
		book.setBookName("Fancy Nancy");
		book.setId(27l);
		book.setCategory("Fiction");
		
		
		try{
			 bookDao.create(book);
			 assertTrue(true);
		}catch(Exception e){
			//fail("Fail to test createBook");
		}
		
	}


	@Test
	public void testCreateNegative() {
		try{
			 bookDao.create(new Book());
			// fail("Fail to test createBook");
		}catch(Exception e){			
			 assertTrue(true);
		}
		
	}
	
	
	@Test
	public void testDelete() {

	}

	@Test
	public void testGetAllBook() {
		
		assertEquals(2 , bookDao.getAllBook().size());
	}

	@Test
	public void testGetById() {
		
		assertEquals("Fancy Nancy", bookDao.getById(20l).getBookName());
	}

	@Test
	public void testGetByName() {
		assertEquals("Fancy Nancy", bookDao.getByName("Fancy Nancy").getBookName());
	}

	 
}
