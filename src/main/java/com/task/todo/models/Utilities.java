package com.task.todo.models;

import javax.persistence.Transient;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utilities {

  private final static String DATE_FORMAT = "yyyy/MM/dd";

  public static String dateToString(Date date){
    DateFormat outputFormatter = new SimpleDateFormat(DATE_FORMAT);
    return outputFormatter.format(date);
  }

  public static Date stringToDate(String text) throws ParseException {
    DateFormat outputFormatter = new SimpleDateFormat(DATE_FORMAT);
    return outputFormatter.parse(text);
  }
}
