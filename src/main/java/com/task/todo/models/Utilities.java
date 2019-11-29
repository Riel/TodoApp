package com.task.todo.models;

import javax.persistence.Transient;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Utilities {

  private final static String DATE_FORMAT = "yyyy-MM-dd";

  public static String dateToString(LocalDate date){
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
    return formatter.format(date);
  }

  public static String dateToString(Date date){
    DateFormat outputFormatter = new SimpleDateFormat(DATE_FORMAT);
    return outputFormatter.format(date);
  }

  public static LocalDate stringToDate(String text) throws ParseException {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
    return LocalDate.parse(text, formatter);
  }
}
