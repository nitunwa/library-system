package com.systemlibrary.utility;

import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
@Component
public class DateUtility {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	public Date addDays(Date date, int days) {
		logger.info("new date function");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days); // minus number would decrement the days
		return cal.getTime();
	}


}
