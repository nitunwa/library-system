package com.systemlibrary.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "book")
@XmlRootElement
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	private String isdn;

	@NotNull
	private String bookName;

	@NotNull
	private String category;

	@NotNull
	private String author;

	@NotNull
	private int edition;
	@NotNull
	private Date currentDate = new Date();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "borrowBook")
	List<BorrowBook> borrowList = new ArrayList<BorrowBook>();
	
	

	public String getIsdn() {
		return isdn;
	}


	public void setIsdn(String isdn) {
		this.isdn = isdn;
	}


	public List<BorrowBook> getBorrowList() {
		return borrowList;
	}


	public void setBorrowList(List<BorrowBook> borrowList) {
		this.borrowList = borrowList;
	}


	public Date getCurrentDate() {
		return currentDate;
	}


	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}

	public int getEdition() {
		return edition;
	}

	public void setEdition(int edition) {
		this.edition = edition;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public Book() {
	}

	public Book(Long id) {
		this.id = id;
	}

	public Book(String bookName) {
		super();
		this.bookName = bookName;
	}

	public Book(String isdn, String bookName, String category, String author, int edition) {

		this.isdn = isdn;
		this.bookName = bookName;
		this.category = category;
		this.author = author;
		this.edition = edition;
	}

	public Book(String bookName, String category, String author) {

		this.bookName = bookName;
		this.category = category;
		this.author = author;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", ISDN=" + isdn + ", bookName=" + bookName + ", category=" + category + ", author="
				+ author + ", edition=" + edition + ", currentDate=" + currentDate + "]";
	}

}
