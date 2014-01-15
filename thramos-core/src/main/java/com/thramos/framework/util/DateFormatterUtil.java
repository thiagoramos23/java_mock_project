package com.thramos.framework.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatterUtil {

	public static Date formatarStringParaData(String stringToDate) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return dateFormat.parse(stringToDate);
		} catch (ParseException e) {
			throw new IllegalArgumentException("A data enviada para o método de formatação de data está em um formato "
					+ "não esperado. O formato deve ser: yyyy-MM-dd HH:mm:ss");
		}
	}
}
