package nl.ctac.verbeeten.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DateUtil {

	// TODO fix me

	/** 
	 * Return date format.
	 * 
	 * @return dd-MM-yyyy
	 */
	public static DateFormat getDateFormat() {
		return new SimpleDateFormat("dd-MM-yyyy");
	}

	
	/**
	 * Return date time format.
	 * 
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static DateFormat getDateTimeFormat() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}
}
