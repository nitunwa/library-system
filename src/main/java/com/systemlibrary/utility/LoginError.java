package com.systemlibrary.utility;

public class LoginError {

	private String errorMessage;

    public LoginError(String errorMessage){
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
