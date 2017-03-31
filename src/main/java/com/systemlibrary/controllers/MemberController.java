package com.systemlibrary.controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.joda.time.Days;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.joda.time.Days;
import org.joda.time.LocalDate;
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

	@RequestMapping(value = "/memberDashboard", method = RequestMethod.GET)
	public String showDashboard(Model model) {
		return "member/memberDashboard";
	}

	/* Book list for single user */

	public List<BorrowBook> showBookReportByUser(User user) {
		List<BorrowBook> borrowBookList = borrowBooDao.getBorrowBookListByUserId(user);
		logger.info("Total borrow list size: " + borrowBookList.size());
		return borrowBookList;
	}


	/*book list for expair, due soon, later due    */
	@RequestMapping(value = "/borrowBookReport", method = RequestMethod.GET)
	public String showborrowBookReport(Model model, HttpSession httpSession) {
		

		User user = (User) httpSession.getAttribute("loginUser");
		List<BorrowBook> borrowBookList = showBookReportByUser(user);
		List<BorrowBook> borrowBookListLaterDue = new ArrayList<BorrowBook>();
		List<BorrowBook> borrowBookListDueSoon = new ArrayList<BorrowBook>();
		List<BorrowBook> borrowBookListExpire = new ArrayList<BorrowBook>();
		Date today = new Date();
		for (BorrowBook borrowBook : borrowBookList) {
			Date checkInDate = borrowBook.getCheckIn();
		
			LocalDate dateStart = new LocalDate(today.getYear(), today.getMonth() + 1, today.getDate());
			LocalDate dateEnd = new LocalDate(checkInDate.getYear(), checkInDate.getMonth() + 1, checkInDate.getDate());
			int totalDays = Days.daysBetween(dateStart, dateEnd).getDays();
			if (totalDays < 0) {
				borrowBookListExpire.add(borrowBook);
			} else if ((totalDays >= 0) && (totalDays <= 2)) {
				borrowBookListDueSoon.add(borrowBook);
			} else if (totalDays > 2) {
				borrowBookListLaterDue.add(borrowBook);
			}

		}
		model.addAttribute("borrowBookListExpire", borrowBookListExpire);
		model.addAttribute("borrowBookListLaterDue", borrowBookListLaterDue);
		model.addAttribute("borrowBookListDueSoon", borrowBookListDueSoon);
		return "member/borrowBookReport";
	}

	@RequestMapping(value = "/borrowBook", method = RequestMethod.GET)
	public String borrowBook(Model model, HttpSession httpSession) {
		/*  */
		User loginUser = (User) httpSession.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/auth/singin";
		}

		List<Book> bookList = bookDao.getAllBook();
		logger.info("book size: " + bookList.size());
		model.addAttribute("bookList", bookList);
		model.addAttribute("loginUser", loginUser);

		/*  */

		/* show the total borrow book */
		User user = (User) httpSession.getAttribute("loginUser");
		Long Total = borrowBooDao.totalBorrowBook(user);
		logger.info("Total borrow book : " + Total);
		model.addAttribute("bookTotal", Total);

		/* show later due book */

		Date laterDueDate = addDays(new Date(), 2);
		Long laterDue = borrowBooDao.totalLaterExpairBorrowBook(user, laterDueDate);
		model.addAttribute("laterExTotalBorrowBook", laterDue);
		logger.info("Total later due book : " + laterDue);

		/* show the due soon borrow book number */
		Date everyday = subDays( new Date(),-1);
		Date reminderDate = addDays(new Date(), 2);
		Long totalDueSoonExBook = borrowBooDao.dueSoonExpairBorrowBookNumber(user, everyday,reminderDate );
		logger.info("Total expair borrow book : " + totalDueSoonExBook);
		model.addAttribute("exTotalBorrowBook", totalDueSoonExBook);

		/* check the expiration date */
		Date today = new Date();
		Date twoDay = addDays(today, 2);
		Boolean hasExpairBook = borrowBooDao.hasExpairBooks(user);
		model.addAttribute("hasExpairBook", hasExpairBook);
		logger.info("Total borrow book : " + hasExpairBook);

	/* show the expiration date */
		
		Date expairDate = addDays(today, 2);
		model.addAttribute("expairDate", expairDate);
		logger.info("Due Date : " + twoDay);

		return "member/borrowBook";
	}

	public Date subDays(Date date, int days) {
		logger.info("new date function");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days); // minus number would decrement the days
		
		return cal.getTime();
	}

	@RequestMapping(value = "/returnBorrowBook", method = RequestMethod.POST)
	public String returnBorrowBook(Model model, @RequestParam(value = "borrowBookId") Long[] returnBorrowIds,
			@RequestParam(value = "borrowBookReportType") String borrowBookReportType, HttpSession httpSession) {
		logger.info("return book list: " + returnBorrowIds.length);
		logger.info("report type :" + borrowBookReportType);
		try {
			for (Long borrowBookId : returnBorrowIds) {
				logger.info("borrowBook Id : " + borrowBookId);
				BorrowBook borrowBook = new BorrowBook();
				borrowBook.setId(borrowBookId);
				borrowBooDao.updateReturnBorrowBookList(borrowBook);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if ("laterBorrowBook".equals(borrowBookReportType)) {
			return "redirect:/member/laterDueBookReportByUser";
		} else if ("expireBorrowBook".equals(borrowBookReportType)) {
			return "redirect:/member/expairBookReportByUser";
		} else if ("normalBorrowBook".equals(borrowBookReportType)) {
			return "redirect:/member/borrowBookReport";
		}

		return "redirect:/member/borrowBookReport";

	}

	public void returnBorrowBook(Long[] returnBorrowIds) {
		try {
			for (Long borrowBookId : returnBorrowIds) {
				logger.info("borrowBook Id : " + borrowBookId);
				BorrowBook borrowBook = new BorrowBook();
				borrowBook.setId(borrowBookId);
				borrowBooDao.updateReturnBorrowBookList(borrowBook);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/borrowBook", method = RequestMethod.POST)
	public String borrowBook(Model model, @RequestParam(value = "bookId") Long bookId, HttpSession httpSession) {
		Book book = new Book();
		BorrowBook borrowBook = new BorrowBook();
		User user = new User();
		// user.setId(3);
		// user.setEmail("siiam@d,c"); //hardcoded /static

		// user.setName("nitun");
		// book.setBookName("Nancy");

		try {
			book = bookDao.getById(bookId);
			User loginUser = (User) httpSession.getAttribute("loginUser");
			if (loginUser == null) {
				return "redirect:/auth/singin";
			}

			user = userDao.getByEmail(loginUser.getEmail());
			borrowBook.setCheckOut(new Date());
			Date checkOutDate = addDays(new Date(), 7);
			borrowBook.setCheckIn(checkOutDate);
			borrowBook.setBorrowBook(book);
			borrowBook.setBorrrowUser(user);

			borrowBooDao.create(borrowBook);
			logger.info("Book: " + book.toString());
			logger.info("borrowBook: " + borrowBook.toString());

			// List<BorrowBook> borrowBookList =showBookReportByUser(user);
			// logger.info("Total borrow list size: " + borrowBookList.size());
			model.addAttribute("borrowBookList", showBookReportByUser(user));

		} catch (Exception ex) {
			return "Book not found: " + ex.toString();
		}

		logger.info("Borrow book successfully!!!!!1: id: " + bookId);

		// Book newBook= new Book(book.getBookName());

		// return "Book succesfully Check Out !" +" "+"borrowBook Name:"
		// +borrowBook.toString();
		return "member/borrowBookReport";
	}

	// renew book
	@RequestMapping(value = "/renewBorrowBook", method = RequestMethod.POST)
	public String renewBorrowBook(Model model, @RequestParam(value = "borrowBookId") Long[] renewBorrowId,
			HttpSession httpSession) {

		logger.info("return book list: " + renewBorrowId.length);
		BorrowBook borrowBook = new BorrowBook();
		User user = new User();

		try {
			for (Long borrowBookId : renewBorrowId) {
				logger.info(" Renew borrowBook Id : " + borrowBookId);
				borrowBook.setId(borrowBookId);
				borrowBooDao.renewBorrowBook(borrowBook);
			}

			model.addAttribute("borrowBookList", showBookReportByUser(user));

		} catch (Exception ex) {
			return "Book not found: " + ex.toString();
		}
		return "redirect:/member/borrowBookReport";
	}

	// renew the book

	public Date addDays(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days); // minus number would decrement the days
		return cal.getTime();
	}

	/* show expair book list */

	@RequestMapping(value = "/expairBookReportByUser", method = RequestMethod.GET)
	public String getExpairBooksList(Model model, HttpSession httpSession) {
		User user = (User) httpSession.getAttribute("loginUser");
		Date date = new Date();
		Date epairdate = addDays(date, 2);
		List<BorrowBook> expairBookList = borrowBooDao.getExpairBooksList(user, epairdate);
		model.addAttribute("expairBookList", expairBookList);
		logger.info("Total Expair Book list size: " + expairBookList.size());
		return "member/expairBookList";
	}

	/* show later due book list */

	@RequestMapping(value = "/laterDueBookReportByUser", method = RequestMethod.GET)
	public String totalLaterExpairBorrowBook(Model model, HttpSession httpSession) {
		User user = (User) httpSession.getAttribute("loginUser");
		Date date = new Date();
		Date laterDueDate = addDays(date, 2);
		List<BorrowBook> laterDueBookList = borrowBooDao.getlaterDueBorrowBookList(user, laterDueDate);
		model.addAttribute("laterDueBookList", laterDueBookList);
		logger.info("Total Later Due Book list size: " + laterDueBookList.size());
		return "member/laterDueBookList";
	}

}
