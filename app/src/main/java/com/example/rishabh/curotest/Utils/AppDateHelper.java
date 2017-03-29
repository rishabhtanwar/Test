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
    return getMillisFromDate(getDateWithWeekDays(Constants.DATEFORMAT, swipeCount), Constants.DATEFORMAT);
  }


}
