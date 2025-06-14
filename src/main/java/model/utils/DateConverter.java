package model.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {
	private static final String STANDART_DATE_FORMAT = "dd-MM-yyyy";
	
 	private String dateFormat;

    public DateConverter(String dateFormat) {
        this.dateFormat = dateFormat;
    }
    
    public DateConverter() {
    	this.dateFormat = STANDART_DATE_FORMAT;
    }

    public String dateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(date);
    }

    public Date stringToDate(String dateString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.parse(dateString);
    }
    
    public String teste() {
    	return "league of legends";
    }
}
