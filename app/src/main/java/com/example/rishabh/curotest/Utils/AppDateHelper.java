package com.example.rishabh.curotest.Utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by rishabh on 27/03/2017.
 */

public class AppDateHelper {
  private static AppDateHelper ourInstance = new AppDateHelper();

  private AppDateHelper() {
  }

  public static AppDateHelper getInstance() {
    return ourInstance;
  }

  public long getSerializedDateAccToSwipeIndex(int swipeIndex) {
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DAY_OF_YEAR, swipeIndex);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    return 0;
    //UtilDateSerializer dateSerializer = new UtilDateSerializer();
    //return dateSerializer.serialize(calendar.getTime());
  }

  public long getMillisFromDate(String date, String format) {
    long datemilliseconds = 0;
    SimpleDateFormat dateFormat = new SimpleDateFormat(format);
    Date dateObject;
    try {
      dateObject = dateFormat.parse(date);
      datemilliseconds = dateObject.getTime();
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return datemilliseconds;
  }

  public String getDateWithWeekDays(String format, int weekdays) {
    DateFormat dateFormat = new SimpleDateFormat(format);
    Calendar startCalender = Calendar.getInstance();
    startCalender.add(Calendar.DAY_OF_YEAR, weekdays);
    String currentDate = dateFormat.format(startCalender.getTime());
    return currentDate;
  }

  public long getDateInMillisWithSwipeCount(int swipeCount) {
    return getMillisFromDate(getDateWithWeekDays(Constants.DATEFORMAT, swipeCount),
        Constants.DATEFORMAT);
  }

  public static String getFirstDayOfTheMonth(int swipeCount) {
    Calendar aCalendar = Calendar.getInstance();
    // add -1 month to current month
    aCalendar.add(Calendar.MONTH, swipeCount);
    // set DATE to 1, so first date of previous month
    aCalendar.set(Calendar.DATE, 1);

    Date firstDateOfPreviousMonth = aCalendar.getTime();

    return stringFromDate(firstDateOfPreviousMonth);
  }

  public static String getLastDayOfTheMonth(int swipeCount) {
    Calendar aCalendar = Calendar.getInstance();
    // add  month to current month
    aCalendar.add(Calendar.MONTH, swipeCount);
    // set DATE to 1, so first date of previous month
    aCalendar.set(Calendar.DATE, 1);

    //Date firstDateOfPreviousMonth = aCalendar.getTime();

    // set actual maximum date of previous month
    aCalendar.set(Calendar.DATE, aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
    //read it
    Date lastDateOfPreviousMonth = aCalendar.getTime();
    return stringFromDate(lastDateOfPreviousMonth);
  }

  public static String stringFromDate(Date date) {
    DateFormat dateFormat = new SimpleDateFormat(Constants.DATEFORMAT);
    String _date = null;
    if (date != null) _date = dateFormat.format(date);
    return _date;
  }

  public static String monthYearNameBySwipeIndex(int swipeCount) {
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.MONTH, swipeCount);
    //format it to MMM-yyyy // January-2012
    String previousMonthYear = new SimpleDateFormat("MMM-yyyy").format(cal.getTime());
    return previousMonthYear;
  }

  public static int totalNumberOfDays(int swipeCount) {
    Calendar aCalendar = Calendar.getInstance();
    // add  month to current month
    aCalendar.add(Calendar.MONTH, swipeCount);
    aCalendar.set(Calendar.YEAR, aCalendar.get(Calendar.YEAR));
    return aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
  }

  public static String getStrigDateFromMillis(long date){
    String _date = null;
    try {
      DateFormat dateFormat = new SimpleDateFormat(Constants.DATEFORMAT);
      _date = dateFormat.format(new Date(date));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return _date;
  }

  public String deserializeDateTrends(long time) {
    return (new SimpleDateFormat(Constants.DATE_FORMAT_TRENDS)).format(new Date(time));
  }
}
