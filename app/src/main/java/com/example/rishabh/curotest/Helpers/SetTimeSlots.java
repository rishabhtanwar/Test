package com.example.rishabh.curotest.Helpers;

import com.example.rishabh.curotest.Model.TimeSlots;
import io.realm.Realm;
import retrofit2.Response;

/**
 * Created by rishabh on 27/03/2017.
 */

public class SetTimeSlots {
  Realm realm;
  TimeSlots timeSlots;

  public SetTimeSlots(Realm realm) {
    this.realm = realm;
    setTimeSlot();
  }

  private void setTimeSlot() {
    try {
      realm.beginTransaction();
      timeSlots = new TimeSlots();
      timeSlots.setId(0);
      timeSlots.setBg(true);
      timeSlots.setDescription("xyz");
      timeSlots.setName("fasting");
      realm.commitTransaction();

      realm.beginTransaction();
      timeSlots = new TimeSlots();
      timeSlots.setId(1);
      timeSlots.setBg(true);
      timeSlots.setDescription("xyz1");
      timeSlots.setName("fasting");
      realm.commitTransaction();

      realm.beginTransaction();
      timeSlots = new TimeSlots();
      timeSlots.setId(2);
      timeSlots.setBg(true);
      timeSlots.setDescription("xyz2");
      timeSlots.setName("fasting");
      realm.commitTransaction();

      realm.beginTransaction();
      timeSlots = new TimeSlots();
      timeSlots.setId(3);
      timeSlots.setBg(true);
      timeSlots.setDescription("xyz3");
      timeSlots.setName("fasting");
      realm.commitTransaction();

      realm.beginTransaction();
      timeSlots = new TimeSlots();
      timeSlots.setId(4);
      timeSlots.setBg(true);
      timeSlots.setDescription("xyz4");
      timeSlots.setName("fasting");
      realm.commitTransaction();
    } finally {
      realm.close();
    }
  }
}
