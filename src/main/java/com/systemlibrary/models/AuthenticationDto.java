package com.systemlibrary.models;

public class AuthenticationDto {
	public String role;
	public boolean status;

	public AuthenticationDto() {
		super();

	}

	public AuthenticationDto(String role, boolean status) {
		super();
		this.role = role;
		this.status = status;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
}
