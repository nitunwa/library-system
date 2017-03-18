package com.systemlibrary.models;

import org.springframework.stereotype.Service;

@Service
public class BookFineCalService {
	double bFine=1.3 ;
	public Double getFine(Book book, int totalExpireDay) {
		return bFine * totalExpireDay;
	}
}
