package com.systemlibrary.models;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "borrowBook")
public class BorrowBook {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "borrrowUserId")
	private User borrrowUser;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "borrrowBookId")
	private Book borrowBook;
	
	

	private Date checkOut;
	private Date checkIn;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getBorrrowUser() {
		return borrrowUser;
	}

	public void setBorrrowUser(User borrrowUser) {
		this.borrrowUser = borrrowUser;
	}

	public Book getBorrowBook() {
		return borrowBook;
	}

	public void setBorrowBook(Book borrowBook) {
		this.borrowBook = borrowBook;
	}

	public Date getCheckOut() {
		return checkOut;
	}

	public void setCheckOut(Date checkOut) {
		this.checkOut = checkOut;
	}

	public Date getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(Date checkIn) {
		this.checkIn = checkIn;
	}

	public BorrowBook(long id, User borrrowUser, Book borrowBook, Date checkOut, Date checkIn) {
		super();
		this.id = id;
		this.borrrowUser = borrrowUser;
		this.borrowBook = borrowBook;
		this.checkOut = checkOut;
		this.checkIn = checkIn;
	}
	
	//public BorrowBook(long id, User borrrowUserName, Book borrowBookName, Date checkOut) {
		//super();
		//this.id = id;
		//this.borrrowUserName = borrrowUserName;
		//this.borrowBookName = borrowBookName;
		//this.checkOut = checkOut;
		
	//}

	public BorrowBook() {

	}

	@Override
	public String toString() {
		return "BorrowBook [id=" + id + ", borrrowUser=" + borrrowUser + ", borrowBook=" + borrowBook + ", checkOut="
				+ checkOut + ", checkIn=" + checkIn + "]";
	}
	
	//@Override
	//public String toString() {
		//return "BorrowBook [id=" + id + ", borrrowUserName=" + borrrowUserName + ", borrowBookName=" + borrowBookName + ", checkOut="
				//+ checkOut +  "]";
	//}

}
