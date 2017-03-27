package com.example.rishabh.curotest.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.realm.RealmObject;

/**
 * Created by rishabh on 27/03/2017.
 */

public class TimeSlots extends RealmObject {
  @JsonProperty("id") int id;
 @JsonProperty("name") String name;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  public int getSlotId() {
    return slotId;
  }

  public void setSlotId(int slotId) {
    this.slotId = slotId;
  }

  @JsonProperty("bg") boolean bg;
  @JsonProperty("description") String description;
  int slotId;
}
