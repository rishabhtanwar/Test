package com.example.rishabh.curotest.Model;

/**
 * Created by rishabh on 27/03/2017.
 */

public class BgLogScreenInfo {
  String mainTitle;
  String subTitle;
  long date;
int timeSlotId;

  public int getTimeSlotId() {
    return timeSlotId;
  }

  public void setTimeSlotId(int timeSlotId) {
    this.timeSlotId = timeSlotId;
  }

  public long getDate() {
    return date;
  }

  public void setDate(long date) {
    this.date = date;
  }

  public String getMainTitle() {
    return mainTitle;
  }

  public void setMainTitle(String mainTitle) {
    this.mainTitle = mainTitle;
  }

  public String getSubTitle() {
    return subTitle;
  }

  public void setSubTitle(String subTitle) {
    this.subTitle = subTitle;
  }

  public long getValue() {
    return value;
  }

  public void setValue(long value) {
    this.value = value;
  }

  long value;
}
