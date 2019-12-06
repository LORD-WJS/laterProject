package com.wjs.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
*@author 诸夏 QQ:1372339756
*
*/
public class DateTools {
	//将制定格式的字符串时间转为时间对象
	public static Date strToDate(String strDate,SimpleDateFormat dateFormat) {
		Date date=null;
		try {
			date = dateFormat.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	//重载
	public static Date strToDate(String strDate,String format) {
		Date date=null;
		SimpleDateFormat dateFormat=new SimpleDateFormat(format);
		try {
			date = dateFormat.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	
	//重载
	public static String dateToStr(Date date,String format) {
		SimpleDateFormat dateFormat=new SimpleDateFormat(format);
		String strDate=dateFormat.format(date);
		return strDate;
	}
	
	
	
	
	
	
	
	
}
