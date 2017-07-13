package com.systemlibrary.controllers;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.systemlibrary.models.Book;
import com.systemlibrary.models.BookDao;
import com.systemlibrary.utility.BookErrorType;


@Controller

public class BookController {
	@Autowired
	BookDao bookdao;
	
	public static final Logger logger = LoggerFactory.getLogger(BookController.class);
	
	@RequestMapping(value = "/bookInfo/{edition}" , method = RequestMethod.GET)

	public ResponseEntity<Book> getBookInfo(@PathVariable(value = "edition") Integer edition){
		Book book = null;
		if(edition == 2002){
			book = new Book("Fancy Nancy",  "Fiction",  "Fancy");
		}
		else{
		book = new Book();
		}
		
		return new ResponseEntity<Book>(book, HttpStatus.OK);
	}
	
	@RequestMapping( value = "/book" , method = RequestMethod.POST)
	public ResponseEntity<?>  createBook(@RequestBody Book book){
		logger.info("Create book {}" , book);
		
		if(bookdao.isBookExist(book)){
			logger.error("Unable to create. A Book with name {} already exist",book.getBookName()); 
			BookErrorType error=	new BookErrorType("Unable to create. A User with name " + 
					book.getBookName() + " already exist.");
			
			return new ResponseEntity (error,HttpStatus.CONFLICT);
			
		}
		bookdao.create(book);
		return new ResponseEntity<String>( HttpStatus.CREATED);
		
		
	}
	
	
}



