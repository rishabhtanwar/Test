package com.example.rishabh.curotest.DBO;

import com.example.rishabh.curotest.BodyRequest.LogValuesData;
import com.example.rishabh.curotest.Helpers.AppSettings;
import com.example.rishabh.curotest.Interfaces.LogScheduleCallback;
import com.example.rishabh.curotest.Model.BgLogScreenInfo;
import com.example.rishabh.curotest.Model.BgLoggingSettingInfo;
import com.example.rishabh.curotest.Model.BgLogs;
import com.example.rishabh.curotest.Model.BgSchedule;
import com.example.rishabh.curotest.Model.TimeSlots;
import com.example.rishabh.curotest.SyncDataWithApi.SyncBgLogging;
import com.example.rishabh.curotest.Utils.AppDateHelper;
import com.example.rishabh.curotest.Utils.IncrementClientId;
import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmResults;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by rishabh on 27/03/2017.
 */

public class BgDBO {
  public static ArrayList<BgLoggingSettingInfo> setLoggingTimeSlot(Realm realm, long date) {
    ArrayList<BgLoggingSettingInfo> arrayList = new ArrayList<>();
    BgLoggingSettingInfo bgLoggingSettingInfo;
    try {
      RealmResults<TimeSlots> timeSlot = realm.where(TimeSlots.class).equalTo("bg", true).findAll();
      for (TimeSlots timeSlots : timeSlot) {
        bgLoggingSettingInfo = new BgLoggingSettingInfo();
        if (checkBgSchedule(realm, timeSlots.getId(), date) == true) {
          bgLoggingSettingInfo.setCheck(true);
        } else {
          bgLoggingSettingInfo.setCheck(false);
        }
        bgLoggingSettingInfo.setSubTitle(timeSlots.getDescription());
        bgLoggingSettingInfo.setMainTitle(timeSlots.getName());
        bgLoggingSettingInfo.setSlotid(timeSlots.getId());
        arrayList.add(bgLoggingSettingInfo);
      }
    } finally {
      realm.close();
    }
    return arrayList;
  }

  private static boolean checkBgSchedule(Realm realm, int timeSlot, long date) {
    RealmResults<BgSchedule> realmResults1 = realm.where(BgSchedule.class).findAll();
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

  public static void saveBgSchedule(int slotId, long date) {
    Realm realm = Realm.getDefaultInstance();
    try {
      if (checkBgScheduleForId(slotId, realm)) {
        realm.beginTransaction();
        BgSchedule bgSchedule = new BgSchedule();
        bgSchedule.setDeleted(false);
        bgSchedule.setEndDate(0);
        bgSchedule.setSetById(0);
        bgSchedule.setStartDate(date);
        bgSchedule.setSynced(false);
        bgSchedule.setTimeSlotId(slotId);
        bgSchedule.setUserId(AppSettings.getCurrentUserId());
        bgSchedule.setClientId(getBgScheduleLastClientId(realm));
        realm.copyToRealm(bgSchedule);
        realm.commitTransaction();
      }
    } finally {
      realm.close();
    }
  }

  private static boolean checkBgScheduleForId(int id, Realm realm) {
    RealmResults<BgSchedule> realmResults =
        realm.where(BgSchedule.class).equalTo("slotTypeId", id).findAll();
    for (BgSchedule bgSchedule : realmResults) {
      if (bgSchedule.isDeleted() == false) {
        return false;
      }
    }
    return true;
  }

  public static void syncBgSchedule(LogScheduleCallback logScheduleCallback) {
    Realm realm = Realm.getDefaultInstance();
    try {
      JSONArray jsonArray = new JSONArray();
      JSONObject jsonObject = null;
      RealmResults<BgSchedule> realmResults =
          realm.where(BgSchedule.class).equalTo("isSynced", false).findAll();
      for (BgSchedule bgSchedule : realmResults) {
        jsonObject = new JSONObject();
        try {
          jsonObject.put("client_id", bgSchedule.getClientId());
          jsonObject.put("timeslot_id", bgSchedule.getTimeSlotId());
          jsonObject.put("start_date",
              AppDateHelper.getStrigDateFromMillis(bgSchedule.getStartDate()));
          if (bgSchedule.getEndDate() == 0) {
            jsonObject.put("end_date", null);
            jsonObject.put("is_deleted", false);
          } else {
            jsonObject.put("end_date",
                AppDateHelper.getStrigDateFromMillis(bgSchedule.getEndDate()));
            jsonObject.put("is_deleted", true);
          }
          if (bgSchedule.getServerId() == 0) {
            jsonObject.put("server_id", null);
          } else {
            jsonObject.put("server_id", bgSchedule.getServerId());
          }
          jsonArray.put(jsonObject);
        } catch (JSONException e) {
          e.printStackTrace();
        }
      }
      SyncBgLogging.postBgSchedule(0, jsonArray, "blood_glucose", logScheduleCallback);
    } finally {
      realm.close();
    }
  }

  private static JSONObject postData(int clientId, int timeSlotId, long serverId) {
    JSONObject jsonObject = new JSONObject();
    JSONArray jsonArray = array(clientId, timeSlotId, serverId);
    try {
      jsonObject.put("timeslots", jsonArray);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return jsonObject;
  }

  private static JSONArray array(int clientId, int timeSlotId, long serverId) {
    JSONArray jsonArray = new JSONArray();
    JSONObject jsonObject = null;
    jsonObject = new JSONObject();
    try {
      jsonObject.put("client_id", clientId);
      jsonObject.put("timeslot_id", timeSlotId);
    } catch (JSONException e) {
      e.printStackTrace();
    }

    jsonArray.put(jsonObject);
    return jsonArray;
  }

  public static void deleteBgSchedule(int timeSlotId) {
    Realm realm = Realm.getDefaultInstance();
    try {
      realm.beginTransaction();
      BgSchedule bgSchedule = realm.where(BgSchedule.class)
          .equalTo("slotTypeId", timeSlotId)
          .equalTo("isDeleted", false)
          .findFirst();
      if (bgSchedule != null) {
        bgSchedule.setDeleted(true);
        bgSchedule.setEndDate(AppDateHelper.getInstance().getDateInMillisWithSwipeCount(-1));
        bgSchedule.setSynced(false);
      }
      realm.commitTransaction();
      //deleteBgLog(timeSlotId, realm);
    } finally {
      realm.close();
    }
  }

  private static void deleteBgLog(int timeSlotId, Realm realm) {
    realm.beginTransaction();
    BgLogs bgLogs = realm.where(BgLogs.class)
        .equalTo("timeSlotId", timeSlotId)
        .equalTo("isDeleted", false)
        .findFirst();
    if (bgLogs != null) {
      bgLogs.setDeleted(true);
      bgLogs.setValue(0);
    }
    realm.commitTransaction();
  }

  public static ArrayList<BgLogScreenInfo> getBgLogScreenListByDate(long date) {
    ArrayList<BgLogScreenInfo> arrayList = new ArrayList<>();
    Realm realm = Realm.getDefaultInstance();
    try {
      RealmResults<BgLogs> realmResults =
          realm.where(BgLogs.class).equalTo("date", date).equalTo("isDeleted", false).findAll();

      for (BgLogs bgLogs : realmResults) {
        BgLogScreenInfo bgLogScreenInfo = new BgLogScreenInfo();
        bgLogScreenInfo.setMainTitle(getTimeSlotTitleById(bgLogs.getTimeSlotId(), realm));
        bgLogScreenInfo.setSubTitle(getTimeSlotDescriptionById(bgLogs.getTimeSlotId(), realm));
        bgLogScreenInfo.setValue(bgLogs.getValue());
        bgLogScreenInfo.setDate(date);
        bgLogScreenInfo.setTimeSlotId(bgLogs.getTimeSlotId());
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
    RealmResults<BgSchedule> realmResults = realm.where(BgSchedule.class)
        .lessThanOrEqualTo("startDate", date)
        .equalTo("endDate", 0)
        .or()
        .greaterThan("endDate", date)
        .equalTo("isDeleted", false)
        .findAll();
    for (BgSchedule bgSchedule : realmResults) {
      if (!checkBgScheduleInLog(arrayList, bgSchedule.getTimeSlotId())) {
        BgLogScreenInfo bgLogScreenInfo = new BgLogScreenInfo();
        bgLogScreenInfo.setMainTitle(getTimeSlotTitleById(bgSchedule.getTimeSlotId(), realm));
        bgLogScreenInfo.setSubTitle(getTimeSlotDescriptionById(bgSchedule.getTimeSlotId(), realm));
        bgLogScreenInfo.setDate(date);
        bgLogScreenInfo.setTimeSlotId(bgSchedule.getTimeSlotId());
        arrayList.add(bgLogScreenInfo);
      }
    }
    return arrayList;
  }

  private static boolean checkBgScheduleInLog(ArrayList<BgLogScreenInfo> arrayList,
      int slotTypeId) {
    for (int i = 0; i < arrayList.size(); i++) {
      if (arrayList.get(i).getTimeSlotId() == slotTypeId) {
        return true;
      }
    }
    return false;
  }

  private static String getTimeSlotTitleById(int id, Realm realm) {
    return realm.where(TimeSlots.class).equalTo("id", id).findFirst().getName();
  }

  private static String getTimeSlotDescriptionById(int id, Realm realm) {
    return realm.where(TimeSlots.class).equalTo("id", id).findFirst().getDescription();
  }

  public static void saveBgLog(int value, long date, int timeSlotId, String dateTime,
      String loggedTime) {
    Realm realm = Realm.getDefaultInstance();
    try {
      realm.beginTransaction();
      BgLogs bgLogs = realm.where(BgLogs.class)
          .equalTo("date", date)
          .equalTo("timeSlotId", timeSlotId)
          .findFirst();
      if (bgLogs == null) {
        bgLogs = new BgLogs();
      }
      bgLogs.setDate(date);
      bgLogs.setTimeSlotId(timeSlotId);
      bgLogs.setUserId(AppSettings.getCurrentUserId());
      bgLogs.setSynced(false);
      bgLogs.setDeleted(false);
      bgLogs.setDateTime(dateTime);
      bgLogs.setLoggedTime(loggedTime);
      bgLogs.setValue(value);
      bgLogs.setClientId(getBgValueLastClientId(realm));
      BgLogs bgLogs1 = realm.copyToRealm(bgLogs);
      SyncBgLogging.postBgValues( arrayList(bgLogs1.getClientId(), bgLogs1.getServerId(), bgLogs1.getValue(),
          bgLogs1.getTimeSlotId(), bgLogs1.getDateTime(), bgLogs1.getLoggedTime()));

      realm.commitTransaction();
    } finally {
      realm.close();
    }
  }

  private static ArrayList<LogValuesData> arrayList(int clientId, int serverId, long value,
      int timeslotId, String dateTime, String loggedTime) {
    ArrayList<LogValuesData> arrayList = new ArrayList<>();
    LogValuesData logValuesData = new LogValuesData();
    logValuesData.client_id = clientId;
    if (serverId != 0) {
      logValuesData.server_id = serverId;
    }
    logValuesData.date_time = dateTime;
    logValuesData.logged_time = loggedTime;
    logValuesData.timeslot_id = timeslotId;
    logValuesData.value = value;
    arrayList.add(logValuesData);
    return arrayList;
  }

  private static int getBgValueLastClientId(Realm realm) {
    RealmResults<BgLogs> realmResults = realm.where(BgLogs.class).findAll();
    if (realmResults.size() > 0) {
      return realmResults.get(realmResults.size() - 1).getClientId() + 1;
    } else {
      return 1;
    }
  }

  private static int getBgScheduleLastClientId(Realm realm) {
    RealmResults<BgSchedule> realmResults = realm.where(BgSchedule.class).findAll();
    if (realmResults.size() > 0) {
      return realmResults.get(realmResults.size() - 1).getClientId() + 1;
    } else {
      return 1;
    }
  }
}
