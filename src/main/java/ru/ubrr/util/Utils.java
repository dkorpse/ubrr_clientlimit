package ru.ubrr.util;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Utils {
	public static final String DEFAULT_DATE_FORMAT = "MM.dd.yyyy";

	public static Date convertDate(String date, String pattern) {
		if (pattern == null) {
			pattern = DEFAULT_DATE_FORMAT;
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		LocalDate localDate = LocalDate.parse(date, formatter);
		return Date.valueOf(localDate);
	}
}
