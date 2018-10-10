package com.systemlibrary.controllers.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.systemlibrary.controllers.Exception.IllegalException;
import com.systemlibrary.dao.BookDao;
import com.systemlibrary.models.Book;
import com.systemlibrary.models.BookCategoryCount;

@RestController
@RequestMapping("/rest-service")
public class TestRestController {
	Logger logger = LoggerFactory.getLogger(TestRestController.class);
	Book b = new Book();
	Map<Long, Book> bookMap = new HashMap<Long, Book>();
	List<String> categoryList = Arrays.asList("Math", "English");
	BookDao bookDao = new BookDao();

	TestRestController() {
		List<Book> bookList = bookDao.getBookList();
		for (Book book : bookList) {
			bookMap.put(book.getId(), book);
		}

	}

	@GetMapping(value = "/getBookByName", produces = "application/json")
	public List<Book> getBookByName(@RequestParam(value = "bookName") String bookName) {
		logger.info("Book Name:" + bookName);
		List<Book> b = findBookByNameV2(bookName);
		return b;
	}

	private List<Book> findBookByNameV2(String name) {
		List<Book> list = new ArrayList<Book>();
		Set<Entry<Long, Book>> entrySet = bookMap.entrySet();
		for (Entry<Long, Book> entry : entrySet) {
			Book b = entry.getValue();
			if (name.equals(b.getBookName())) {
				list.add(b);
			}

		}

		return list;
	}

	@GetMapping(value = "/getByNameV1", produces = "application/json")
	public Book getByNameV1(@RequestParam(value = "bookName") String bookName) {
		logger.info("No duplication");
		Book b = findBookName(bookName);
		return b;
	}

	private Book findBookName(String name) {
		Set<Long> set = bookMap.keySet();
		for (Long val : set) {
			Book bk = bookMap.get(val);
			if (name.equals(bk.getBookName())) {
				logger.info("find the book");
				return bk;
			}
		}
		return new Book();
	}

	@GetMapping(value = "getBookById/{id}", produces = "application/json")
	public Book getBookById(@PathVariable Long id) {
		Book b = findBook(id);

		logger.info(b.toString());

		/*
		 * if (("Math".equals(b.getCategory()))
		 * ||("English".equals(b.getCategory())) ) { return b; }
		 */

		try {
			if (b.getBookName() == null) {
				throw new IllegalException("book name is null");
			}
		} catch (IllegalException ex) {
			logger.error(ex.getMessage(), ex);
		}

		return b;

	}

	@GetMapping(value = "/countBookByCategory", produces = "application/json")
	public List<BookCategoryCount> countBookByCategory() {
		List<BookCategoryCount> bookCategoryList = getBookCategoryCount();
		return bookCategoryList;
	}

	private List<BookCategoryCount> getBookCategoryCount() {

		BookDao bk = new BookDao();
		List<Book> bookList = bk.getBookList();
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (Book book : bookList) {
			if (map.containsKey(book.getCategory())) {
				int count = map.get(book.getCategory()) + 1;
				map.put(book.getCategory(), count);
			} else {
				map.put(book.getCategory(), 1);
			}
		}
		List<BookCategoryCount> categoryList = new ArrayList<BookCategoryCount>();
		Set<String> set = map.keySet();
		for (String subject : set) {
			int count = map.get(subject);
			BookCategoryCount bc = new BookCategoryCount(subject,count);
			categoryList.add(bc);
		}

		return categoryList;
	}

	private Book findBook(Long id) {
		Book b = bookMap.get(id);
		if (b == null) {
			return new Book();
		}
		return b;
	}

}
