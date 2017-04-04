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
  double value;
  int serverId;
  int clientId;

  public int getClientId() {
    return clientId;
  }

  public void setClientId(int clientId) {
    this.clientId = clientId;
  }

  public String getDateTime() {
    return dateTime;
  }

  public void setDateTime(String dateTime) {
    this.dateTime = dateTime;
  }

  public String getLoggedTime() {
    return loggedTime;
  }

  public void setLoggedTime(String loggedTime) {
    this.loggedTime = loggedTime;
  }

  String  dateTime;
  String  loggedTime;

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

  public double getValue() {
    return value;
  }

  public void setValue(double value) {
    this.value = value;
  }

  public int getServerId() {
    return serverId;
  }

  public void setServerId(int serverId) {
    this.serverId = serverId;
  }


}
