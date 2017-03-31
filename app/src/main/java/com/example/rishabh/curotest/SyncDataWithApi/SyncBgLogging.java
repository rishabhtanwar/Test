package com.example.rishabh.curotest.SyncDataWithApi;

import android.util.Log;
import com.example.rishabh.curotest.API.RestClient;
import com.example.rishabh.curotest.BodyRequest.LogScheduleJson;
import com.example.rishabh.curotest.BodyRequest.LogScheduleTimeSlotList;
import com.example.rishabh.curotest.Model.BgSchedule;
import com.example.rishabh.curotest.POJO.LogScheduleData;
import com.example.rishabh.curotest.Utils.AppDateHelper;
import com.example.rishabh.curotest.BodyRequest.LogSchedulePostData;
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
    logSchedulePostData.start_date = AppDateHelper.getStrigDateFromMillis(startDate);
    logSchedulePostData.log_type = logType;
    LogScheduleJson log = new LogScheduleJson();
    log.timeslots = new ArrayList<>();
    for (int i = 0; i < jsonArray.length(); i++) {
      LogScheduleTimeSlotList logs = new LogScheduleTimeSlotList();
      try {
        JSONObject json = jsonArray.getJSONObject(i);
        logs.client_id = json.getString("client_id");
        logs.timeslot_id = json.getString("timeslot_id");
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

  public static void getBgSchedule(String startDate, String endDate) {
    Call<ResponseBody> call =
        RestClient.getApiService().getBgSchedule(startDate, endDate, "blood_glucose");
    call.enqueue(new Callback<ResponseBody>() {
      @Override public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        try {
          saveBgscheduleBWDates(response.body().string());
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

      @Override public void onFailure(Call<ResponseBody> call, Throwable t) {

      }
    });
  }

  private static void saveBgscheduleBWDates(String json) {
    try {
      Realm realm=Realm.getDefaultInstance();
      JSONObject jsonObject = new JSONObject(json);
      JSONArray jsonArray = jsonObject.getJSONArray("log_values");
      realm.beginTransaction();
      for (int i = 0; i < jsonArray.length(); i++) {

      }
      realm.commitTransaction();
    } catch (JSONException e) {
      e.printStackTrace();
    } finally {

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
