package com.systemlibrary;

import org.joda.time.Days;
import org.joda.time.LocalDate;

public class BrRmichelJodaTest {

	public static void main(String[] args) {

		LocalDate dateStart = new LocalDate(2013, 9, 1);
		LocalDate dateEnd = new LocalDate(2013, 9, 6);

		int days = Days.daysBetween(dateStart, dateEnd).getDays();
		System.out.println("days between: " + days);
	}
}