package com.example.rishabh.curotest.helpers;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import com.example.rishabh.curotest.Utils.Application;
import com.example.rishabh.curotest.Utils.Constants;
import android.content.SharedPreferences;

/**
 * Created by rishabh on 27/03/2017.
 */

public class AppSettings {
  public static final String SHARED_PREFS_NAME = "CuroPreFile";
  public static Account account;
  public static AppSettings ourInstance = new AppSettings();

  public static AppSettings getInstance() {
    return ourInstance;
  }

  public static void setAuthToken(String authToken) {
    SharedPreferences settings =
        Application.getAppContext().getSharedPreferences(SHARED_PREFS_NAME, 0);
    SharedPreferences.Editor editor = settings.edit();
    editor.putString(Constants.AUTH_TOKEN, authToken);
    editor.commit();
  }

  public static String getAuthToken() {
    SharedPreferences settings =
        Application.getAppContext().getSharedPreferences(SHARED_PREFS_NAME, 0);
    String authToken = settings.getString(Constants.AUTH_TOKEN, "");
    return authToken;
  }

  public static int getCurrentUserId() {
    SharedPreferences settings =
        Application.getAppContext().getSharedPreferences(SHARED_PREFS_NAME, 0);
    int userId = settings.getInt(Constants.APP_CURRENT_USER, 0);
    return userId;
  }

  public static void setCurrentUserId(int id) {
    SharedPreferences settings =
        Application.getAppContext().getSharedPreferences(SHARED_PREFS_NAME, 0);
    SharedPreferences.Editor editor = settings.edit();
    editor.putInt(Constants.APP_CURRENT_USER, id);
    editor.commit();
  }

  public static void setBgSummary(String value) {
    SharedPreferences settings =
        Application.getAppContext().getSharedPreferences(SHARED_PREFS_NAME, 0);
    SharedPreferences.Editor editor = settings.edit();
    editor.putString(Constants.BG_SUMMARY, value);
    editor.commit();
  }

  public static String getBgSummary() {
    SharedPreferences settings =
        Application.getAppContext().getSharedPreferences(SHARED_PREFS_NAME, 0);
    String authToken = settings.getString(Constants.BG_SUMMARY, "");
    return authToken;
  }

  public static void setActivitySummary(String value) {
    SharedPreferences settings =
        Application.getAppContext().getSharedPreferences(SHARED_PREFS_NAME, 0);
    SharedPreferences.Editor editor = settings.edit();
    editor.putString(Constants.ACTIVITY_SUMMARY, value);
    editor.commit();
  }

  public static String getActivitySummary() {
    SharedPreferences settings =
        Application.getAppContext().getSharedPreferences(SHARED_PREFS_NAME, 0);
    String authToken = settings.getString(Constants.ACTIVITY_SUMMARY, "");
    return authToken;
  }

  public static void setMealSummary(String value) {
    SharedPreferences settings =
        Application.getAppContext().getSharedPreferences(SHARED_PREFS_NAME, 0);
    SharedPreferences.Editor editor = settings.edit();
    editor.putString(Constants.MEAL_SUMMARY, value);
    editor.commit();
  }

  public static String getMealSummary() {
    SharedPreferences settings =
        Application.getAppContext().getSharedPreferences(SHARED_PREFS_NAME, 0);
    String authToken = settings.getString(Constants.MEAL_SUMMARY, "");
    return authToken;
  }

  public static void setMedicationSummary(String value) {
    SharedPreferences settings =
        Application.getAppContext().getSharedPreferences(SHARED_PREFS_NAME, 0);
    SharedPreferences.Editor editor = settings.edit();
    editor.putString(Constants.MEDICATION_SUMMARY, value);
    editor.commit();
  }

  public static String getMedicationSummary() {
    SharedPreferences settings =
        Application.getAppContext().getSharedPreferences(SHARED_PREFS_NAME, 0);
    String authToken = settings.getString(Constants.MEDICATION_SUMMARY, "");
    return authToken;
  }

  public static void setVitalSummary(String value) {
    SharedPreferences settings =
        Application.getAppContext().getSharedPreferences(SHARED_PREFS_NAME, 0);
    SharedPreferences.Editor editor = settings.edit();
    editor.putString(Constants.VITAL_SUMMARY, value);
    editor.commit();
  }

  public static String getVitalSummary() {
    SharedPreferences settings =
        Application.getAppContext().getSharedPreferences(SHARED_PREFS_NAME, 0);
    String authToken = settings.getString(Constants.VITAL_SUMMARY, "");
    return authToken;
  }

  public static void setGoalSummary(String value) {
    SharedPreferences settings =
        Application.getAppContext().getSharedPreferences(SHARED_PREFS_NAME, 0);
    SharedPreferences.Editor editor = settings.edit();
    editor.putString(Constants.GOAL_SUMMARY, value);
    editor.commit();
  }

  public static String getGoalSummary() {
    SharedPreferences settings =
        Application.getAppContext().getSharedPreferences(SHARED_PREFS_NAME, 0);
    String authToken = settings.getString(Constants.GOAL_SUMMARY, "");
    return authToken;
  }

  public static boolean getBgApiStatus() {
    SharedPreferences settings =
        Application.getAppContext().getSharedPreferences(SHARED_PREFS_NAME, 0);
    boolean status = settings.getBoolean(Constants.BG_API_STATUS, false);
    return status;
  }

  public static void setBgApiStatus(boolean status) {
    SharedPreferences settings =
        Application.getAppContext().getSharedPreferences(SHARED_PREFS_NAME, 0);
    SharedPreferences.Editor editor = settings.edit();
    editor.putBoolean(Constants.BG_API_STATUS, status);
    editor.commit();
  }

  public Account CreateSyncAccount() {
    if (account == null) {
      account = new Account(Constants.ACCOUNT, Constants.ACCOUNT_TYPE);
      AccountManager accountManager =
          (AccountManager) Application.getAppContext().getSystemService(Context.ACCOUNT_SERVICE);
      accountManager.addAccountExplicitly(account, null, null);
      ContentResolver.setIsSyncable(account, Constants.AUTHORITY, 1);
      ContentResolver.setSyncAutomatically(account, Constants.AUTHORITY, true);
      //    ContentResolver.addPeriodicSync(newAccount, AUTHORITY, new Bundle(), 8640);
    }
    return account;
  }
}
