package com.systemlibrary.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "user")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@NotNull
	private String name;

	@NotNull
	private String email;

	@NotNull
	private String password;

	@NotNull
	private String role;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "borrrowUser", fetch = FetchType.LAZY)
	@JsonIgnore
	List<BorrowBook> borrowList = new ArrayList<BorrowBook>();

	public User() {

	}

	public User(long id) {
		this.id = id;
	}

	public List<BorrowBook> getBorrowList() {
		return borrowList;
	}

	public void setBorrowList(List<BorrowBook> borrowList) {
		this.borrowList = borrowList;
	}

	public User(String email, String password, String role) {

		this.email = email;
		this.password = password;
		this.role = role;
	}

	public User(String name, String email, String password, String role) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", role=" + role
				+ "]";
	}

}
