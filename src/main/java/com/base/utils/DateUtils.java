package com.base.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * 获取当前周一
	 * 
	 * @return
	 */
	public static String getCurrentWeekFirstDay(int day) {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
		if (dayofweek == 1) {
			dayofweek += 7;
		}
		cal.add(Calendar.DATE, 2 - dayofweek + day);

		String format = dateFormat.format(cal.getTime());
		return format;
	}

	/**
	 * 本周日
	 * 
	 * @return
	 */
	public static String getCurrentWeekEndDay(int day) {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
		cal.add(Calendar.DATE, 8 - dayofweek + day);

		String format = dateFormat.format(cal.getTime());
		return format;
	}
}
