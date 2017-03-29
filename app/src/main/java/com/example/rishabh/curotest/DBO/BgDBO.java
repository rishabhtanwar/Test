package com.example.rishabh.curotest.DBO;

import com.example.rishabh.curotest.Helpers.AppSettings;
import com.example.rishabh.curotest.Model.BgLogScreenInfo;
import com.example.rishabh.curotest.Model.BgLoggingSettingInfo;
import com.example.rishabh.curotest.Model.BgLogs;
import com.example.rishabh.curotest.Model.BgSchedule;
import com.example.rishabh.curotest.Model.TimeSlots;
import io.realm.Realm;
import io.realm.RealmResults;
import java.util.ArrayList;

/**
 * Created by rishabh on 27/03/2017.
 */

public class BgDBO {
  public static ArrayList<BgLoggingSettingInfo> setLoggingTimeSlot(Realm realm) {
    ArrayList<BgLoggingSettingInfo> arrayList = new ArrayList<>();
    BgLoggingSettingInfo bgLoggingSettingInfo;
    try {
      RealmResults<TimeSlots> timeSlot = realm.where(TimeSlots.class).equalTo("bg", true).findAll();
      for (TimeSlots timeSlots : timeSlot) {
        bgLoggingSettingInfo = new BgLoggingSettingInfo();
        bgLoggingSettingInfo.setCheck(checkBgSchedule(realm, timeSlots.getSlotTypeId()));
        bgLoggingSettingInfo.setSubTitle(timeSlots.getDescription());
        bgLoggingSettingInfo.setMainTitle(timeSlots.getName());
        bgLoggingSettingInfo.setSlotid(timeSlots.getSlotTypeId());
        arrayList.add(bgLoggingSettingInfo);
      }
    } finally {
      realm.close();
    }
    return arrayList;
  }

  private static boolean checkBgSchedule(Realm realm, int timeSlot) {
    RealmResults<BgSchedule> realmResults = realm.where(BgSchedule.class)
        .equalTo("slotTypeId", timeSlot)
        .equalTo("isDeleted", false)
        .findAll();
    if (realmResults.size() > 0) {
      return true;
    }
    return false;
  }

  public static void saveBgScheduleFromLogging(ArrayList<Integer> timeSlotId, long date) {
    Realm realm = Realm.getDefaultInstance();
    try {
      realm.beginTransaction();
      for (int i = 0; i < timeSlotId.size(); i++) {
        BgSchedule bgSchedule = checkBgScheduleForDate(timeSlotId.get(i), realm, date);
        if (bgSchedule == null) {
          bgSchedule = new BgSchedule();
        }
        bgSchedule.setDeleted(false);
        bgSchedule.setEndDate(0);
        bgSchedule.setSetById(0);
        bgSchedule.setStartDate(date);
        bgSchedule.setSynced(false);
        bgSchedule.setTimeSlotId(timeSlotId.get(i));
        bgSchedule.setUserId(AppSettings.getCurrentUserId());
        realm.copyToRealm(bgSchedule);
      }
      realm.commitTransaction();
    } finally {
      realm.close();
    }
  }

  private static BgSchedule checkBgScheduleForDate(int timeSlotId, Realm realm, long date) {
    BgSchedule bgSchedule = realm.where(BgSchedule.class)
        .equalTo("slotTypeId", timeSlotId)
        .equalTo("startDate", date)
        .equalTo("isDeleted", false)
        .findFirst();
    return bgSchedule;
  }

  public static void deleteBgSchedule(int timeSlotId, long date) {
    Realm realm = Realm.getDefaultInstance();
    BgSchedule bgSchedule = realm.where(BgSchedule.class)
        .equalTo("slotTypeId", timeSlotId)
        .equalTo("date", date)
        .findFirst();
    if (bgSchedule != null) {
      bgSchedule.setDeleted(true);
    }
    realm.close();
  }

  public static ArrayList<BgLogScreenInfo> getBgLogScreenListByDate(long date) {
    ArrayList<BgLogScreenInfo> arrayList = new ArrayList<>();
    Realm realm = Realm.getDefaultInstance();
    try {
      RealmResults<BgLogs> realmResults = realm.where(BgLogs.class).equalTo("date", date).findAll();

      for (BgLogs bgLogs : realmResults) {
        BgLogScreenInfo bgLogScreenInfo = new BgLogScreenInfo();
        bgLogScreenInfo.setMainTitle(getTimeSlotTitleById(bgLogs.getTimeSlotId(), realm));
        bgLogScreenInfo.setSubTitle(getTimeSlotDescriptionById(bgLogs.getId(), realm));
        bgLogScreenInfo.setValue(bgLogs.getValue());
        arrayList.add(bgLogScreenInfo);
      }
      arrayList = getBgLogListBySchedule(arrayList, realm, date);
    } finally {
      realm.close();
    }
    return arrayList;
  }

  private static ArrayList<BgLogScreenInfo> getBgLogListBySchedule(
      ArrayList<BgLogScreenInfo> arrayList, Realm realm, long date) {
    RealmResults<BgSchedule> realmResults =
        realm.where(BgSchedule.class).equalTo("startDate", date).findAll();
    for (BgSchedule bgSchedule : realmResults) {
      if (!arrayList.contains(bgSchedule.getTimeSlotId())) {
        BgLogScreenInfo bgLogScreenInfo = new BgLogScreenInfo();
        bgLogScreenInfo.setMainTitle(getTimeSlotTitleById(bgSchedule.getTimeSlotId(), realm));
        bgLogScreenInfo.setSubTitle(getTimeSlotDescriptionById(bgSchedule.getTimeSlotId(), realm));
        arrayList.add(bgLogScreenInfo);
      }
    }
    return arrayList;
  }

  private static String getTimeSlotTitleById(int id, Realm realm) {
    return realm.where(TimeSlots.class).equalTo("slotTypeId", id).findFirst().getName();
  }

  private static String getTimeSlotDescriptionById(int id, Realm realm) {
    return realm.where(TimeSlots.class).equalTo("slotTypeId", id).findFirst().getDescription();
  }
}
