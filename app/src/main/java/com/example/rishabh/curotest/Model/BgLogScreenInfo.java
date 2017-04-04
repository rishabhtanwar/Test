package com.example.rishabh.curotest.Model;

/**
 * Created by rishabh on 27/03/2017.
 */

public class BgLogScreenInfo {
  String mainTitle;
  String subTitle;
  long date;
  int timeSlotId;

  public String getLogTime() {
    return logTime;
  }

  public void setLogTime(String logTime) {
    this.logTime = logTime;
  }

  String logTime;

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

  public double getValue() {
    return value;
  }

  public void setValue(double value) {
    this.value = value;
  }

  double value;
}
