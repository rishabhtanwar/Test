package com.example.rishabh.curotest.Model;

import io.realm.RealmObject;

/**
 * Created by rishabh on 29/03/2017.
 */

public class BgSchedule extends RealmObject {
  int id;
  long startDate;
  long endDate;
  int slotTypeId;
  int setById;
  String notes;
  int clientId;

  public int getClientId() {
    return clientId;
  }

  public void setClientId(int clientId) {
    this.clientId = clientId;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public long getStartDate() {
    return startDate;
  }

  public void setStartDate(long startDate) {
    this.startDate = startDate;
  }

  public long getEndDate() {
    return endDate;
  }

  public void setEndDate(long endDate) {
    this.endDate = endDate;
  }

  public int getTimeSlotId() {
    return slotTypeId;
  }

  public void setTimeSlotId(int slotTypeId) {
    this.slotTypeId = slotTypeId;
  }

  public int getSetById() {
    return setById;
  }

  public void setSetById(int setById) {
    this.setById = setById;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
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

  public long getServerId() {
    return serverId;
  }

  public void setServerId(long serverId) {
    this.serverId = serverId;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  boolean isDeleted;
  boolean isSynced;
  long serverId;
  int userId;
}
