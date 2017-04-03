package com.example.rishabh.curotest.SyncDataWithApi;

import android.util.Log;
import com.example.rishabh.curotest.API.RestClient;
import com.example.rishabh.curotest.BodyRequest.LogScheduleJson;
import com.example.rishabh.curotest.BodyRequest.LogScheduleTimeSlotList;
import com.example.rishabh.curotest.Interfaces.LogScheduleCallback;
import com.example.rishabh.curotest.Model.BgSchedule;
import com.example.rishabh.curotest.POJO.LogScheduleData;
import com.example.rishabh.curotest.Utils.AppDateHelper;
import com.example.rishabh.curotest.BodyRequest.LogSchedulePostData;
import com.example.rishabh.curotest.Utils.Constants;
import io.realm.Realm;
import java.io.IOException;
import java.util.ArrayList;
import okhttp3.ResponseBody;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rishabh on 30/03/2017.
 */

public class SyncBgLogging {
  static String TAG = "SyncBgLogging";

  public static void postBgSchedule(long startDate, JSONArray jsonArray, String logType) {
    LogSchedulePostData logSchedulePostData = new LogSchedulePostData();
    //logSchedulePostData.start_date = AppDateHelper.getStrigDateFromMillis(startDate);
    logSchedulePostData.log_type = logType;
    LogScheduleJson log = new LogScheduleJson();
    log.timeslots = new ArrayList<>();
    for (int i = 0; i < jsonArray.length(); i++) {
      LogScheduleTimeSlotList logs = new LogScheduleTimeSlotList();
      try {
        JSONObject json = jsonArray.getJSONObject(i);
        logs.client_id = json.getInt("client_id");
        logs.timeslot_id = json.getInt("timeslot_id");
        logs.is_deleted = json.getBoolean("is_deleted");
        logs.end_date = json.getString("end_date");
        logs.server_id = json.getString("server_id");
        logs.start_date = json.getString("start_date");
        log.timeslots.add(logs);
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }
    logSchedulePostData.log_schedule_data = log;
    Call<LogScheduleData> call = RestClient.getApiService().postBgSchedule(logSchedulePostData);
    call.enqueue(new Callback<LogScheduleData>() {
      @Override
      public void onResponse(Call<LogScheduleData> call, Response<LogScheduleData> response) {

        Log.e(TAG, "onResponse: " + response.body().getLogSchedules().get(0).getId());
        saveBgSchedule(response);
      }

      @Override public void onFailure(Call<LogScheduleData> call, Throwable t) {
        t.printStackTrace();
      }
    });
  }

  public static void getBgSchedule(String startDate, String endDate,
      final LogScheduleCallback logScheduleCallback) {
    Call<LogScheduleData> call =
        RestClient.getApiService().getBgSchedule(startDate, endDate, "blood_glucose");
    call.enqueue(new Callback<LogScheduleData>() {
      @Override
      public void onResponse(Call<LogScheduleData> call, Response<LogScheduleData> response) {
        saveBgscheduleBWDates(response, logScheduleCallback);
      }

      @Override public void onFailure(Call<LogScheduleData> call, Throwable t) {

      }
    });
  }

  private static void saveBgscheduleBWDates(Response<LogScheduleData> response,
      LogScheduleCallback logScheduleCallback) {
    Realm realm = Realm.getDefaultInstance();
    try {
      realm.beginTransaction();
      for (LogScheduleData.LogSchedule logSchedule : response.body().getLogSchedules()) {
        BgSchedule bgSchedule = realm.where(BgSchedule.class)
            .equalTo("serverId", logSchedule.getId())
            .equalTo("slotTypeId", logSchedule.getTimeslotId())
            .findFirst();
        if (bgSchedule == null) {
          bgSchedule = new BgSchedule();
        }
        if (logSchedule.getEnddate() == null) {
          bgSchedule.setEndDate(0);
        } else {
          bgSchedule.setEndDate(AppDateHelper.getInstance()
              .getMillisFromDate(logSchedule.getEnddate(), Constants.DATEFORMAT));
        }
        bgSchedule.setStartDate(AppDateHelper.getInstance()
            .getMillisFromDate(logSchedule.getStartdate(), Constants.DATEFORMAT));
        bgSchedule.setTimeSlotId(logSchedule.getTimeslotId());
        bgSchedule.setSetById(logSchedule.getSetById());
        bgSchedule.setServerId(logSchedule.getId());
        bgSchedule.setSynced(true);
        realm.copyToRealm(bgSchedule);
      }
      logScheduleCallback.onSuccess(true);
      realm.commitTransaction();
    } finally {
      realm.close();
    }
  }

  private static void saveBgSchedule(Response<LogScheduleData> response) {
    Realm realm = Realm.getDefaultInstance();
    try {
      realm.beginTransaction();
      for (LogScheduleData.LogSchedule logSchedule : response.body().getLogSchedules()) {
        BgSchedule bgSchedule = realm.where(BgSchedule.class)
            .equalTo("clientId", logSchedule.getClientId())
            .equalTo("slotTypeId", logSchedule.getTimeslotId())
            .findFirst();
        bgSchedule.setSetById(logSchedule.getSetById());
        bgSchedule.setServerId(logSchedule.getId());
        bgSchedule.setSynced(true);
        realm.copyToRealm(bgSchedule);
      }
      realm.commitTransaction();
    } finally {
      realm.close();
    }
  }
}
