package com.systemlibrary.controllers;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.systemlibrary.models.Book;
import com.systemlibrary.models.BookDao;
import com.systemlibrary.models.BorrowBooDao;
import com.systemlibrary.models.BorrowBook;
import com.systemlibrary.models.User;
import com.systemlibrary.models.UserDao;


@Controller
@RequestMapping("/member")
public class MemberController {
	
	@Autowired
	private BookDao bookDao;
	@Autowired
	private BorrowBooDao borrowBooDao;
	@Autowired
	private UserDao userDao;
   
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@RequestMapping(value="/memberDashboard",method=RequestMethod.GET)
	public String showDashboard(Model model) {
		return "member/memberDashboard";
	}
	
	@RequestMapping(value="/borrowBook",method=RequestMethod.GET)
	public String borrowBook(Model model) {
		List<Book> bookList = bookDao.getAllBook();
		logger.info("book size: " +bookList.size());
		model.addAttribute("bookList", bookList);
	
		return "member/borrowBook";
	}
	
	@RequestMapping(value="/borrowBook",method=RequestMethod.POST)
	@ResponseBody
	public String borrowBook(Model model, @RequestParam(value = "bookId") Long  bookId) {
		Book book = new Book();
		BorrowBook borrowBook = new BorrowBook();
		User user = new User();
		//user.setId(3);
		//user.setEmail("siiam@d,c"); //hardcoded /static
		try {
			 book = bookDao.getById(bookId);
			 user= userDao.getByEmail("luna@gm.com");
			 borrowBook.setCheckIn(new Date());
			 Date checkOutDate= addDays(new Date() ,7);
			 borrowBook.setCheckOut(checkOutDate);
			 borrowBook.setBorrowBook(book);
			 borrowBook.setBorrrowUser(user);
			 
			 borrowBooDao.create(borrowBook);
			logger.info("Book: " +book.toString());
			logger.info("borrowBook: " +borrowBook.toString());
			
		} catch (Exception ex) {
			return "Book not found: " + ex.toString();
		}
		
		logger.info("Borrow book successfully!!!!!1: id: " +bookId);
		
      
		Book newBook=  new Book(book.getBookName());
       
		return "Book succesfully Check Out !" +"   "+"borrowBook Name:" +borrowBook.toString();
	}
	
	public  Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }

}

