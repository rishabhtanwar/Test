package com.example.rishabh.curotest.Model;

import io.realm.RealmObject;

/**
 * Created by rishabh on 30/03/2017.
 */

public class LogStreakForMonth extends RealmObject {
  String month;
  int statusFlag;

  public long getDate() {
    return date;
  }

  public void setDate(long date) {
    this.date = date;
  }

  long date;
  public String getMonth() {
    return month;
  }

  public void setMonth(String month) {
    this.month = month;
  }

  public int getStatusFlag() {
    return statusFlag;
  }

  public void setStatusFlag(int statusFlag) {
    this.statusFlag = statusFlag;
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

  int id;
  int userId;

}
