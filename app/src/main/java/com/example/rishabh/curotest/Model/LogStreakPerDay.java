package com.example.rishabh.curotest.Model;

import io.realm.RealmObject;

/**
 * Created by rishabh on 27/03/2017.
 */

public class LogStreakPerDay extends RealmObject {
  long Date;
  int statusFlag;
  int id;
  int userId;

  public int getStatusFlag() {
    return statusFlag;
  }

  public void setStatusFlag(int statusFlag) {
    this.statusFlag = statusFlag;
  }

  public long getDate() {
    return Date;
  }

  public void setDate(long date) {
    Date = date;
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
