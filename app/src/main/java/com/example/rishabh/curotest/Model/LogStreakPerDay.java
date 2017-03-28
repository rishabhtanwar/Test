package com.example.rishabh.curotest.Model;

import io.realm.RealmObject;

/**
 * Created by rishabh on 27/03/2017.
 */

public class LogStreakPerDay extends RealmObject {
  long date;
  String statusFlag;
  int id;
  int userId;

  public String  getStatusFlag() {
    return statusFlag;
  }

  public void setStatusFlag(String  statusFlag) {
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
