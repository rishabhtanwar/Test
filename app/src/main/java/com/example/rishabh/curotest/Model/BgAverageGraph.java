package com.example.rishabh.curotest.Model;

import io.realm.RealmObject;

/**
 * Created by rishabh on 27/03/2017.
 */

public class BgAverageGraph extends RealmObject {
  long date;
  double average;
  int userId;

  public long getDate() {
    return date;
  }

  public void setDate(long date) {
    this.date = date;
  }

  public double getAverage() {
    return average;
  }

  public void setAverage(double average) {
    this.average = average;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }
}
