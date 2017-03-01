package com.systemlibrary.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "book")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	public String getISDN() {
		return ISDN;
	}

	public void setISDN(String iSDN) {
		ISDN = iSDN;
	}

	@NotNull
	private String ISDN;

	@NotNull
	private String bookName;

	@NotNull
	private String category;

	@NotNull
	private String author;

	@NotNull
	private int edition;

	public Date getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}

	@NotNull
	private Date currentDate = new Date();

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

	public Book(String ISDN, String bookName, String category, String author, int edition) {

		this.ISDN = ISDN;
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
		return "Book [id=" + id + ", ISDN=" + ISDN + ", bookName=" + bookName + ", category=" + category + ", author="
				+ author + ", edition=" + edition + ", currentDate=" + currentDate + "]";
	}
	
	
}
