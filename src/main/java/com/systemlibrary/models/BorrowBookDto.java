package com.systemlibrary.models;

import java.util.List;

import javax.servlet.http.HttpSession;

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
import com.systemlibrary.models.BorrowBook;
import com.systemlibrary.models.User;

public class BorrowBookDto {

	BorrowBook borrowBook = new BorrowBook();
	User user = new User();
	Book book = new Book();

	private int totalnumbook;

	public BorrowBook getBorrowBook() {
		return borrowBook;
	}

	public void setBorrowBook(BorrowBook borrowBook) {
		this.borrowBook = borrowBook;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public int getTotalnumbook() {
		return totalnumbook;
	}

	public void setTotalnumbook(int totalnumbook) {
		this.totalnumbook = totalnumbook;
	}
}
