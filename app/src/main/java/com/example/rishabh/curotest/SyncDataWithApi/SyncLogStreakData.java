package com.example.rishabh.curotest.SyncDataWithApi;

import com.example.rishabh.curotest.API.RestClient;
import com.example.rishabh.curotest.Interfaces.LogStreakResponse;
import com.example.rishabh.curotest.Model.LogStreakForMonth;
import com.example.rishabh.curotest.Model.LogStreakPerDay;
import com.example.rishabh.curotest.Utils.AppDateHelper;
import com.example.rishabh.curotest.Utils.Constants;
import io.realm.Realm;
import java.io.IOException;
import java.util.Iterator;
import okhttp3.ResponseBody;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rishabh on 28/03/2017.
 */

public class SyncLogStreakData {
  static Realm realm = Realm.getDefaultInstance();

  public static void syncData(String startDate, String endDate, final String requestType,
      final LogStreakResponse logStreakResponse, final String month) {
    Call<ResponseBody> call =
        RestClient.getApiService().getLogStreak(startDate, endDate, requestType);
    call.enqueue(new Callback<ResponseBody>() {
      @Override public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        try {

          updateLogStreak(response.body().string(), logStreakResponse, requestType, month);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

      @Override public void onFailure(Call<ResponseBody> call, Throwable t) {
        t.printStackTrace();
      }
    });
  }

  private static void updateLogStreak(String response, LogStreakResponse logStreakResponse,
      String requestType, String month) {
    try {
      JSONObject jsonObject1 = new JSONObject(response);
      JSONObject jsonObject = new JSONObject(
          String.valueOf(jsonObject1.getJSONObject("log_data").getJSONObject("streak")));
      Iterator iterator = jsonObject.keys();
      while (iterator.hasNext()) {
        String key = String.valueOf(iterator.next());
        long date = AppDateHelper.getInstance().getMillisFromDate(key, Constants.DATEFORMAT);
        String status = jsonObject.getString(key);
        if (requestType.equalsIgnoreCase("history")) {
          setMonthStreakData(date, status, month);
        } else {
          setLogStreakDB(date, status);
        }
      }
      realm.close();
      if (requestType.equalsIgnoreCase("history")) {
        logStreakResponse.onSuccess(true);
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  private static void setLogStreakDB(long date, String status) {
    if (realm.isClosed()) {
      realm = Realm.getDefaultInstance();
    }
    try {
      LogStreakPerDay logStreakPerDay =
          realm.where(LogStreakPerDay.class).equalTo("date", date).findFirst();
      realm.beginTransaction();
      if (logStreakPerDay == null) {
        logStreakPerDay = new LogStreakPerDay();
      }
      logStreakPerDay.setDate(date);
      logStreakPerDay.setStatusFlag(status);
      realm.copyToRealm(logStreakPerDay);
      realm.commitTransaction();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void setMonthStreakData(long date, String status, String month) {
    if (realm.isClosed()) {
      realm = Realm.getDefaultInstance();
    }
    try {
      LogStreakForMonth logStreakForMonth =
          realm.where(LogStreakForMonth.class).equalTo("date", date).findFirst();
      realm.beginTransaction();
      if (logStreakForMonth == null) {
        logStreakForMonth = new LogStreakForMonth();
      }
      logStreakForMonth.setDate(date);
      switch (status) {
        case Constants.EMPTY_STREAK:
          logStreakForMonth.setStatusFlag(2);
          break;
        case Constants.LOGGED_STREAK:
          logStreakForMonth.setStatusFlag(1);
          break;

        case Constants.STARRED_STREAK:
          logStreakForMonth.setStatusFlag(0);
          break;
      }
      logStreakForMonth.setMonth(month);
      realm.copyToRealm(logStreakForMonth);
      realm.commitTransaction();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
