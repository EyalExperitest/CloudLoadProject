package utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GetDate {

	public GetDate() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return
	 */
	public static String getDate() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		String date = dtf.format(now);
		return date;
	}

}
