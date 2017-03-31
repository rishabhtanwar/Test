package com.example.rishabh.curotest.Model;

import io.realm.RealmObject;

/**
 * Created by rishabh on 27/03/2017.
 */

public class BgLogs extends RealmObject {
  int id;
  int userId;
  long date;
  int timeSlotId;
  boolean isDeleted;
  boolean isSynced;
  long value;
  int serverId;

  public long getDateTime() {
    return dateTime;
  }

  public void setDateTime(long dateTime) {
    this.dateTime = dateTime;
  }

  public long getLoggedTime() {
    return loggedTime;
  }

  public void setLoggedTime(long loggedTime) {
    this.loggedTime = loggedTime;
  }

  long dateTime;
  long loggedTime;

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

  public long getDate() {
    return date;
  }

  public void setDate(long date) {
    this.date = date;
  }

  public int getTimeSlotId() {
    return timeSlotId;
  }

  public void setTimeSlotId(int timeSlotId) {
    this.timeSlotId = timeSlotId;
  }

  public boolean isDeleted() {
    return isDeleted;
  }

  public void setDeleted(boolean deleted) {
    isDeleted = deleted;
  }

  public boolean isSynced() {
    return isSynced;
  }

  public void setSynced(boolean synced) {
    isSynced = synced;
  }

  public long getValue() {
    return value;
  }

  public void setValue(long value) {
    this.value = value;
  }

  public int getServerId() {
    return serverId;
  }

  public void setServerId(int serverId) {
    this.serverId = serverId;
  }


}
