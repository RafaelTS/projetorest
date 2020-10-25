package br.sc.rafael.rest.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	
	public static String getDataDiferencaDias(Integer qtDias) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, qtDias);
		return getDataFormatada(cal.getTime());
	}
	//formatar em String
	public static String getDataFormatada(Date data) {
		DateFormat format = new SimpleDateFormat("dd/mm/aaaa");
		return format.format(data);
	}
}
