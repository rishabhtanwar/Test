package com.example.rishabh.curotest.DBO;

import com.example.rishabh.curotest.Model.BgLoggingSettingInfo;
import com.example.rishabh.curotest.Model.BgLogs;
import com.example.rishabh.curotest.Model.TimeSlots;
import io.realm.Realm;
import io.realm.RealmResults;
import java.util.ArrayList;

/**
 * Created by rishabh on 27/03/2017.
 */

public class TimeSlotDBO {

  public static ArrayList<BgLoggingSettingInfo> setLoggingTimeSlot(Realm realm) {
    ArrayList<BgLoggingSettingInfo> arrayList = new ArrayList<>();
    BgLoggingSettingInfo bgLoggingSettingInfo;
    RealmResults<TimeSlots> timeSlot = realm.where(TimeSlots.class).equalTo("bg", true).findAll();
    for (TimeSlots timeSlots : timeSlot) {
      bgLoggingSettingInfo = new BgLoggingSettingInfo();
      bgLoggingSettingInfo.setCheck(checkBgLog(realm, timeSlots.getSlotId()));
      bgLoggingSettingInfo.setSubTitle(timeSlots.getDescription());
      bgLoggingSettingInfo.setTimeSlot(timeSlots.getName());
      arrayList.add(bgLoggingSettingInfo);
    }
    return arrayList;
  }

  private static boolean checkBgLog(Realm realm, int timeSlot) {
    RealmResults<BgLogs> realmResults = realm.where(BgLogs.class)
        .equalTo("timeSlot", timeSlot)
        .equalTo("isDeleted", false)
        .findAll();
    if (realmResults.size() > 0) {
      return true;
    } else {
      return false;
    }
  }
}
