package com.example.rishabh.curotest.Model;

import io.realm.RealmObject;

/**
 * Created by rishabh on 27/03/2017.
 */

public class LogStreakPerDay extends RealmObject {
  long date;
  int statusFlag;
  int id;
  int userId;

  public String getDateString() {
    return dateString;
  }

  public void setDateString(String dateString) {
    this.dateString = dateString;
  }

  String dateString;
  public String getMonth() {
    return month;
  }

  public void setMonth(String month) {
    this.month = month;
  }

  String month;

  public int getStatusFlag() {
    return statusFlag;
  }

  public void setStatusFlag(int statusFlag) {
    this.statusFlag = statusFlag;
  }

  public long getDate() {
    return date;
  }

  public void setDate(long date) {
    this.date = date;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }
}
