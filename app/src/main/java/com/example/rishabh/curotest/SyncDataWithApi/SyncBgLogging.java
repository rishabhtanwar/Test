package com.example.rishabh.curotest.SyncDataWithApi;

import android.content.ContentResolver;
import android.os.Bundle;
import com.example.rishabh.curotest.API.RestClient;
import com.example.rishabh.curotest.BodyRequest.LogScheduleJson;
import com.example.rishabh.curotest.BodyRequest.LogScheduleTimeSlotList;
import com.example.rishabh.curotest.BodyRequest.LogValuePostData;
import com.example.rishabh.curotest.BodyRequest.LogValuesData;
import com.example.rishabh.curotest.helpers.AppSettings;
import com.example.rishabh.curotest.Interfaces.LogScheduleCallback;
import com.example.rishabh.curotest.Model.BgAverageGraph;
import com.example.rishabh.curotest.Model.BgLogs;
import com.example.rishabh.curotest.Model.BgSchedule;
import com.example.rishabh.curotest.POJO.BgGraphResponse;
import com.example.rishabh.curotest.POJO.BgLogValueGetResponse;
import com.example.rishabh.curotest.POJO.BgLogValuePostResponse;
import com.example.rishabh.curotest.POJO.LogScheduleData;
import com.example.rishabh.curotest.Utils.AppDateHelper;
import com.example.rishabh.curotest.BodyRequest.LogSchedulePostData;
import com.example.rishabh.curotest.Utils.Constants;
import io.realm.Realm;
import java.util.ArrayList;
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
  static SyncListener syncListener;

  public static void postBgSchedule(long startDate, JSONArray jsonArray, String logType,
      final LogScheduleCallback logScheduleCallback) {
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
        if (json.has("end_date")) {
          logs.end_date = json.getString("end_date");
        }
        if (json.has("server_id")) {
          logs.server_id = json.getString("server_id");
        }
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

        //Log.e(TAG, "onResponse: " + response.body().getLogSchedules().get(0).getId());
        saveBgSchedule(response, logScheduleCallback);
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
        t.printStackTrace();
      }
    });
  }

  private static void saveBgscheduleBWDates(Response<LogScheduleData> response,
      LogScheduleCallback logScheduleCallback) {
    Realm realm = Realm.getDefaultInstance();
    int count = 0;
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
        count++;
      }
      if (count == response.body().getLogSchedules().size()) {
        logScheduleCallback.onSuccess(true);
      }
      realm.commitTransaction();
    } finally {
      realm.close();
    }
  }

  private static void saveBgSchedule(Response<LogScheduleData> response,
      LogScheduleCallback logScheduleCallback) {
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
      logScheduleCallback.onSuccess(true);
      realm.commitTransaction();
    } finally {
      realm.close();
    }
  }

  public static void postBgValues(ArrayList<LogValuesData> logValuesDatas,
      final String uploadType) {
    LogValuePostData logValuePostData = new LogValuePostData();
    logValuePostData.sync_data = logValuesDatas;
    Call<BgLogValuePostResponse> call = RestClient.getApiService().postBgLog(logValuePostData);
    call.enqueue(new Callback<BgLogValuePostResponse>() {
      @Override public void onResponse(Call<BgLogValuePostResponse> call,
          Response<BgLogValuePostResponse> response) {
        saveBgPostResponse(response);
        if (response.body().getResult().size() != 0 && uploadType.equalsIgnoreCase("bulk")) {
          Bundle bundle = new Bundle();
          bundle.putString(Constants.SYNC_DATA, Constants.BG_LOG_SYNC);
          ContentResolver.requestSync(AppSettings.getInstance().CreateSyncAccount(),
              Constants.AUTHORITY, bundle);
        }
        if (response.body().getResult().size() == 0 && uploadType.equalsIgnoreCase("bulk")) {
          syncListener.onSucess(true);
        }
      }

      @Override public void onFailure(Call<BgLogValuePostResponse> call, Throwable t) {

      }
    });
  }

  private static void saveBgPostResponse(Response<BgLogValuePostResponse> response) {
    Realm realm = Realm.getDefaultInstance();
    try {
      realm.beginTransaction();
      for (BgLogValuePostResponse.Result logValue : response.body().getResult()) {
        BgLogs bgLogs =
            realm.where(BgLogs.class).equalTo("clientId", logValue.getClientId()).findFirst();
        bgLogs.setValue(Double.parseDouble(logValue.getValue()));
        bgLogs.setTimeSlotId(logValue.getTimeslotId());
        bgLogs.setLoggedTime(logValue.getLoggedTime());
        bgLogs.setDateTime(logValue.getDateTime());
        bgLogs.setDate(AppDateHelper.getInstance()
            .getMillisFromDate(logValue.getDate(), Constants.DATEFORMAT));
        bgLogs.setServerId(logValue.getId());
        bgLogs.setDeleted(logValue.getDeleted());
        bgLogs.setSynced(true);
        realm.copyToRealm(bgLogs);
      }
      realm.commitTransaction();
    } finally {
      realm.close();
    }
  }

  public static void getBgLogValues(String startDate, String endDate,
      final LogScheduleCallback logScheduleCallback) {
    Call<BgLogValueGetResponse> call =
        RestClient.getApiService().getBgLogs(startDate, endDate, "blood_glucose");
    call.enqueue(new Callback<BgLogValueGetResponse>() {
      @Override public void onResponse(Call<BgLogValueGetResponse> call,
          Response<BgLogValueGetResponse> response) {
        saveBgLogs(response, logScheduleCallback);
      }

      @Override public void onFailure(Call<BgLogValueGetResponse> call, Throwable t) {
        t.printStackTrace();
      }
    });
  }

  private static void saveBgLogs(Response<BgLogValueGetResponse> response,
      LogScheduleCallback logScheduleCallback) {
    Realm realm = Realm.getDefaultInstance();
    try {
      realm.beginTransaction();
      for (BgLogValueGetResponse.LogValue logValue : response.body().getLogValues()) {
        BgLogs bgLogs = realm.where(BgLogs.class).equalTo("serverId", logValue.getId()).findFirst();
        if (bgLogs == null) {
          bgLogs = new BgLogs();
        }
        bgLogs.setServerId(logValue.getId());
        bgLogs.setValue(Double.parseDouble(logValue.getValue()));
        bgLogs.setTimeSlotId(logValue.getTimeslotId());
        bgLogs.setLoggedTime(logValue.getLoggedTime());
        bgLogs.setDateTime(logValue.getDateTime());
        bgLogs.setDate(AppDateHelper.getInstance()
            .getMillisFromDate(logValue.getDate(), Constants.DATEFORMAT));
        realm.copyToRealm(bgLogs);
      }
      realm.commitTransaction();
      logScheduleCallback.onSuccess(true);
    } finally {
      realm.close();
    }
  }

  public static void getBgGraph(String startDate, String endDate,
      final LogScheduleCallback logScheduleCallback) {
    Call<BgGraphResponse> call = RestClient.getApiService().getBgGraph(startDate, endDate);
    call.enqueue(new Callback<BgGraphResponse>() {
      @Override
      public void onResponse(Call<BgGraphResponse> call, Response<BgGraphResponse> response) {
        saveBgGraph(logScheduleCallback, response);
      }

      @Override public void onFailure(Call<BgGraphResponse> call, Throwable t) {
        t.printStackTrace();
      }
    });
  }

  private static void saveBgGraph(LogScheduleCallback logScheduleCallback,
      Response<BgGraphResponse> response) {
    Realm realm = Realm.getDefaultInstance();
    try {
      realm.beginTransaction();
      for (BgGraphResponse.Datum datum : response.body().getData()) {
        long date =
            AppDateHelper.getInstance().getMillisFromDate(datum.getDate(), Constants.DATEFORMAT);
        BgAverageGraph bgAverageGraph =
            realm.where(BgAverageGraph.class).equalTo("date", date).findFirst();
        if (bgAverageGraph == null) {
          bgAverageGraph = new BgAverageGraph();
        }
        bgAverageGraph.setDate(date);
        if (datum.getAvgValue() == null) {
          bgAverageGraph.setAverage(0);
        } else {
          bgAverageGraph.setAverage(Double.parseDouble(datum.getAvgValue()));
        }
        realm.copyToRealm(bgAverageGraph);
      }

      realm.commitTransaction();
      logScheduleCallback.onSuccess(true);
    } finally {
      realm.close();
    }
  }

  public void setSyncListener(SyncListener syncListener) {
    this.syncListener = syncListener;
  }

  public interface SyncListener {
    void onSucess(boolean check);
  }
}
