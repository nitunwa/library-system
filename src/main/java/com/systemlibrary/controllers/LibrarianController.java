package com.systemlibrary.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.joda.time.Days;
import org.joda.time.LocalDate;
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
import com.systemlibrary.models.BorrowBookDto;
import com.systemlibrary.models.User;
import com.systemlibrary.models.UserDao;

@Controller
@RequestMapping("/library")
public class LibrarianController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserDao userDao;

	@Autowired
	private BookDao bookDao;
	@Autowired
	private BorrowBooDao borrowBooDao;

	@RequestMapping(value = "/showDashboard", method = RequestMethod.GET)
	public String showDashboard(Model model, HttpSession httpSession) {
		User loginUser = (User) httpSession.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/auth/singin";
		}

		model.addAttribute("loginUser", loginUser);
		return "library/libraryDashbord";
	}

	@RequestMapping(value = "/createmember", method = RequestMethod.GET)
	public String createUser(Model model) {
		return "library/createmember";
	}

	@RequestMapping(value = "/createmember", method = RequestMethod.POST)
	@ResponseBody
	public String create(Model model, @RequestParam(value = "name") String name,
			@RequestParam(value = "email") String email, @RequestParam(value = "password") String password) {
		try {
			User user = new User(name, email, password, "Member");

			userDao.create(user);
		} catch (Exception ex) {
			return "Error creating the user: " + ex.toString();
		}
		return "User succesfully created!";
	}

	@RequestMapping(value = "/createBook", method = RequestMethod.GET)
	public String createBook(Model model) {
		return "library/addBook";
	}

	// show all member's total number of book
	@RequestMapping(value = "/memberTotalBorrowBook", method = RequestMethod.GET)

	public String getMemberBorrowBookList(Model model, HttpSession httpSession) {
		List<BorrowBookDto> borrowDto;
		try {
			borrowDto = borrowBooDao.getAllMemberBorrowBookList();
			logger.info("total number of book:" + borrowDto.size());
			model.addAttribute("memberBookDtoList", borrowDto);

		} catch (Exception ex) {
			ex.printStackTrace();
			return "Book not found: " + ex.toString();
		}

		return "library/memberBookList";
	}

	// single member book list
	@RequestMapping(value = "/singleMemBorrowBook", method = RequestMethod.GET)

	public String getSingleMemBorrowBookList(Model model, HttpSession httpSession,
			@RequestParam(value = "userEmail") String useremail) {
		List<BorrowBookDto> borrowDtosingle;

		try {
			User user;
			user = userDao.getByEmail(useremail);
			borrowDtosingle = borrowBooDao.getSingleMemberBorrowBookList(user);
			logger.info("One member's number of book:" + borrowDtosingle.size());
			model.addAttribute("singleMemBookDtoList", borrowDtosingle);
			model.addAttribute("useremail", useremail);

		} catch (Exception ex) {
			ex.printStackTrace();
			return "Book not found: " + ex.toString();
		}

		return "library/singleMemberBookList";
	}

	public List<BorrowBook> showBookReportByUser(User user) {
		List<BorrowBook> borrowBookList = borrowBooDao.getBorrowBookListByUserId(user);
		logger.info("Total borrow list size: " + borrowBookList.size());
		return borrowBookList;
	}

	

	@RequestMapping(value = "/borrowBookReport", method = RequestMethod.GET)
	public String showborrowBookReport(Model model, @RequestParam(value = "userEmail") String useremail) {

		User user;
		user = userDao.getByEmail(useremail);
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
		return "library/borrowBookReport";
	}

	@RequestMapping(value = "/createBook", method = RequestMethod.POST)
	@ResponseBody
	public String create(Model model, @RequestParam(value = "ISDN") String ISDN,
			@RequestParam(value = "bookName") String bookName, @RequestParam(value = "category") String category,
			@RequestParam(value = "author") String author, @RequestParam(value = "edition") int edition) {

		logger.info("Verifiing book: " + ISDN);

		try {
			Book book = new Book(ISDN, bookName, category, author, edition);
			bookDao.create(book);
		} catch (Exception ex) {
			return "Error creating the book: " + ex.toString();
		}
		return "Book succesfully created!";
	}

}
