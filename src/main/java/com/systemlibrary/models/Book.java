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
	private long id;
	
	public String getISDN() {
		return ISDN;
	}

	public void setISDN(String iSDN) {
		ISDN = iSDN;
	}

	@NotNull
	private String ISDN;
	
	@NotNull
	private String name;
	
	@NotNull
	private String category;
	
	@NotNull
	private String  author;
	
	@NotNull
	private int edition; 
	 
	public Date getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}

	@NotNull
	private  Date currentDate = new Date();

	public int getEdition() {
		return edition;
	}

	public void setEdition(int edition) {
		this.edition = edition;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	
public Book(){ }
	
	public Book(long id){
		this.id=id;
	}
	
	public Book(String ISDN, String name, String category, String author, int edition) {
		
		this.ISDN = ISDN;
		this.name = name;
		this.category = category;
		this.author = author;
		this.edition = edition;
	}

	public Book(String name,String category,String author ){
		
		this.name= name;
		this.category=category;
		this.author=author;
	}
}
