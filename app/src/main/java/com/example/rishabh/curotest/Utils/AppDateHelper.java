package com.example.rishabh.curotest.Utils;

import java.util.Calendar;

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
}
