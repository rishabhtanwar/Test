package com.example.rishabh.curotest.Helpers;

import com.example.rishabh.curotest.Model.TimeSlots;
import com.example.rishabh.curotest.Utils.Constants;
import io.realm.Realm;
import retrofit2.Response;

/**
 * Created by rishabh on 27/03/2017.
 */

public class SetTimeSlots {
  Realm realm;
  TimeSlots timeSlots;

  public  SetTimeSlots(Realm realm) {
    this.realm = realm;
    setTimeSlot();
  }

  private void setTimeSlot() {
    try {
      realm.beginTransaction();
      timeSlots = new TimeSlots();
      timeSlots.id = 1;
      timeSlots.name = "Fasting";
      timeSlots.slotTypeId = 1;
      timeSlots.sortOrder = 2;
      timeSlots.mealSlot = false;
      timeSlots.defaultTime = "07:00:00";
      timeSlots.description = "After 8 hours of overnight fasting";
      timeSlots.bg = true;
      realm.copyToRealm(timeSlots);

      timeSlots = new TimeSlots();
      timeSlots.id = 2;
      timeSlots.name = "Early Morning Snack";
      timeSlots.slotTypeId = 2;
      timeSlots.sortOrder = 3;
      timeSlots.mealSlot = true;
      timeSlots.defaultTime = "07:00:00";
      timeSlots.bg = false;
      timeSlots.description = "Early Morning Snack";
    /*timeSlots.description = "Early Morning Snack";*/
      realm.copyToRealm(timeSlots);

      timeSlots = new TimeSlots();
      timeSlots.id = 3;
      timeSlots.name = "After Early Morning Snack";
      timeSlots.slotTypeId = 3;
      timeSlots.sortOrder = 4;
      timeSlots.mealSlot = false;
      timeSlots.defaultTime = "07:30:00";
      timeSlots.description = "1.5-2.0 hours after Early Morning Snack";
      timeSlots.bg = true;
      realm.copyToRealm(timeSlots);

      timeSlots = new TimeSlots();
      timeSlots.id = 4;
      timeSlots.name = "Before Breakfast";
      timeSlots.slotTypeId = 1;
      timeSlots.sortOrder = 5;
      timeSlots.mealSlot = false;
      timeSlots.defaultTime = "08:00:00";
      timeSlots.description = "5-15 minutes before Breakfast";
      timeSlots.bg = true;
      realm.copyToRealm(timeSlots);

      timeSlots = new TimeSlots();
      timeSlots.id = 5;
      timeSlots.name = "Breakfast";
      timeSlots.slotTypeId = 2;
      timeSlots.sortOrder = 6;
      timeSlots.mealSlot = true;
      timeSlots.defaultTime = "08:00:00";
      timeSlots.description = "Breakfast";
      timeSlots.bg = false;
      realm.copyToRealm(timeSlots);

      timeSlots = new TimeSlots();
      timeSlots.id = 6;
      timeSlots.name = "After Breakfast";
      timeSlots.slotTypeId = 3;
      timeSlots.sortOrder = 7;
      timeSlots.mealSlot = false;
      timeSlots.defaultTime = "10:00:00";
      timeSlots.description = "1.5-2.0 hours after Breakfast";
      timeSlots.bg = true;
      realm.copyToRealm(timeSlots);

      timeSlots = new TimeSlots();
      timeSlots.id = 7;
      timeSlots.name = "Before Mid-Morning Snack";
      timeSlots.slotTypeId = 1;
      timeSlots.sortOrder = 8;
      timeSlots.mealSlot = false;
      timeSlots.defaultTime = "10:30:00";
      timeSlots.description = "5-15 minutes before Mid-Morning Snack";
      timeSlots.bg = true;
      realm.copyToRealm(timeSlots);

      timeSlots = new TimeSlots();
      timeSlots.id = 8;
      timeSlots.name = "Mid-Morning Snack";
      timeSlots.slotTypeId = 2;
      timeSlots.sortOrder = 9;
      timeSlots.mealSlot = true;
      timeSlots.defaultTime = "11:00:00";
      timeSlots.description = "Mid-Morning Snack";
      timeSlots.bg = false;
      realm.copyToRealm(timeSlots);

      timeSlots = new TimeSlots();
      timeSlots.id = 9;
      timeSlots.name = "After Mid-Morning Snack";
      timeSlots.slotTypeId = 3;
      timeSlots.sortOrder = 10;
      timeSlots.mealSlot = false;
      timeSlots.defaultTime = "11:30:00";
      timeSlots.description = "1.5-2.0 hours after Mid-Morning Snack";
      timeSlots.bg = true;
      realm.copyToRealm(timeSlots);

      timeSlots = new TimeSlots();
      timeSlots.id = 10;
      timeSlots.name = "Before Lunch";
      timeSlots.slotTypeId = 1;
      timeSlots.sortOrder = 11;
      timeSlots.mealSlot = false;
      timeSlots.defaultTime = "12:00:00";
      timeSlots.description = "5-15 minutes before Lunch";
      timeSlots.bg = true;
      realm.copyToRealm(timeSlots);

      timeSlots = new TimeSlots();
      timeSlots.id = 11;
      timeSlots.name = "Lunch";
      timeSlots.slotTypeId = 2;
      timeSlots.sortOrder = 12;
      timeSlots.mealSlot = true;
      timeSlots.defaultTime = "13:00:00";
      timeSlots.description = "Lunch";
      timeSlots.bg = false;
      realm.copyToRealm(timeSlots);

      timeSlots = new TimeSlots();
      timeSlots.id = 12;
      timeSlots.name = "After Lunch";
      timeSlots.slotTypeId = 3;
      timeSlots.sortOrder = 13;
      timeSlots.mealSlot = false;
      timeSlots.defaultTime = "14:00:00";
      timeSlots.description = "1.5-2.0 hours after Lunch";
      timeSlots.bg = true;
      realm.copyToRealm(timeSlots);

      timeSlots = new TimeSlots();
      timeSlots.id = 13;
      timeSlots.name = "Before Evening Snack";
      timeSlots.slotTypeId = 1;
      timeSlots.sortOrder = 14;
      timeSlots.mealSlot = false;
      timeSlots.defaultTime = "16:30:00";
      timeSlots.description = "5-15 minutes before Tea";
      timeSlots.bg = true;
      realm.copyToRealm(timeSlots);

      timeSlots = new TimeSlots();
      timeSlots.id = 14;
      timeSlots.name = "Evening Snack";
      timeSlots.slotTypeId = 2;
      timeSlots.sortOrder = 15;
      timeSlots.mealSlot = true;
      timeSlots.defaultTime = "17:00:00";
      timeSlots.description = "Evening Snack";
      timeSlots.bg = false;
      realm.copyToRealm(timeSlots);

      timeSlots = new TimeSlots();
      timeSlots.id = 15;
      timeSlots.name = "After Evening Snack";
      timeSlots.slotTypeId = 3;
      timeSlots.sortOrder = 16;
      timeSlots.mealSlot = false;
      timeSlots.defaultTime = "18:00:00";
      timeSlots.description = "1.5-2.0 hours after Tea";
      timeSlots.bg = true;
      realm.copyToRealm(timeSlots);

      timeSlots = new TimeSlots();
      timeSlots.id = 16;
      timeSlots.name = "Before Dinner";
      timeSlots.slotTypeId = 1;
      timeSlots.sortOrder = 17;
      timeSlots.mealSlot = false;
      timeSlots.defaultTime = "20:30:00";
      timeSlots.description = "5-15 minutes before Dinner";
      timeSlots.bg = true;
      realm.copyToRealm(timeSlots);

      timeSlots = new TimeSlots();
      timeSlots.id = 17;
      timeSlots.name = "Dinner";
      timeSlots.slotTypeId = 2;
      timeSlots.sortOrder = 18;
      timeSlots.mealSlot = true;
      timeSlots.defaultTime = "21:00:00";
      timeSlots.description = "Dinner";
      timeSlots.bg = false;
      realm.copyToRealm(timeSlots);

      timeSlots = new TimeSlots();
      timeSlots.id = 18;
      timeSlots.name = "After Dinner";
      timeSlots.slotTypeId = 3;
      timeSlots.sortOrder = 19;
      timeSlots.mealSlot = false;
      timeSlots.defaultTime = "22:00:00";
      timeSlots.description = "1.5-2.0 hours after Dinner";
      timeSlots.bg = true;
      realm.copyToRealm(timeSlots);

      timeSlots = new TimeSlots();
      timeSlots.id = 19;
      timeSlots.name = "Before Bedtime Snack";
      timeSlots.slotTypeId = 1;
      timeSlots.sortOrder = 20;
      timeSlots.mealSlot = false;
      timeSlots.defaultTime = "22:30:00";
      timeSlots.description = "5-15 minutes before Bedtime Snack";
      timeSlots.bg = true;
      realm.copyToRealm(timeSlots);

      timeSlots = new TimeSlots();
      timeSlots.id = 20;
      timeSlots.name = "Bedtime Snack";
      timeSlots.slotTypeId = 2;
      timeSlots.sortOrder = 21;
      timeSlots.mealSlot = true;
      timeSlots.defaultTime = "23:00:00";
      timeSlots.description = "Bedtime Snack";
      timeSlots.bg = false;
      realm.copyToRealm(timeSlots);

      timeSlots = new TimeSlots();
      timeSlots.id = 21;
      timeSlots.name = "Bedtime";
      timeSlots.slotTypeId = 3;
      timeSlots.sortOrder = 22;
      timeSlots.mealSlot = false;
      timeSlots.defaultTime = "23:30:00";
      timeSlots.description = "Before Sleep";
      timeSlots.bg = true;
      realm.copyToRealm(timeSlots);

      timeSlots = new TimeSlots();
      timeSlots.id = 22;
      timeSlots.name = "Overnight";
      timeSlots.slotTypeId = 3;
      timeSlots.sortOrder = 22;
      timeSlots.mealSlot = false;
      timeSlots.defaultTime = "03:00:00";
      timeSlots.description = "During Sleep";
      timeSlots.bg = true;
      realm.copyToRealm(timeSlots);

      timeSlots = new TimeSlots();
      timeSlots.id = Constants.APPOINTMENT_SLOTID;
      timeSlots.name = "Appointment";
      timeSlots.slotTypeId = 4;
      timeSlots.sortOrder = 1;
      timeSlots.mealSlot = false;
      timeSlots.bg = false;
      timeSlots.defaultTime = "00:00:00";
      realm.copyToRealm(timeSlots);
      realm.commitTransaction();
    } finally {
      realm.close();
    }
  }
}
