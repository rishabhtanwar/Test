package com.example.rishabh.curotest.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.realm.RealmObject;

/**
 * Created by rishabh on 27/03/2017.
 */

public class TimeSlots extends RealmObject {
 public long id;

 public String name;

public int slotTypeId;

 public int sortOrder;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getSlotTypeId() {
    return slotTypeId;
  }

  public void setSlotTypeId(int slotTypeId) {
    this.slotTypeId = slotTypeId;
  }

  public int getSortOrder() {
    return sortOrder;
  }

  public void setSortOrder(int sortOrder) {
    this.sortOrder = sortOrder;
  }

  public boolean isMealSlot() {
    return mealSlot;
  }

  public void setMealSlot(boolean mealSlot) {
    this.mealSlot = mealSlot;
  }

  public String getDefaultTime() {
    return defaultTime;
  }

  public void setDefaultTime(String defaultTime) {
    this.defaultTime = defaultTime;
  }

  public boolean isBg() {
    return bg;
  }

  public void setBg(boolean bg) {
    this.bg = bg;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public boolean mealSlot;

 public String defaultTime;

 public boolean bg;
 public String description;
}
