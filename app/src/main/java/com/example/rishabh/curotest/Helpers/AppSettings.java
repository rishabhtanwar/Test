package com.example.rishabh.curotest.Helpers;

import com.example.rishabh.curotest.Utils.Application;
import com.example.rishabh.curotest.Utils.Constants;
import android.content.SharedPreferences;

/**
 * Created by rishabh on 27/03/2017.
 */

public class AppSettings {
  public static final String SHARED_PREFS_NAME = "CuroPreFile";
  public static void setAuthToken(String authToken) {
    SharedPreferences settings = Application.getAppContext().getSharedPreferences(SHARED_PREFS_NAME, 0);
    SharedPreferences.Editor editor = settings.edit();
    editor.putString(Constants.AUTH_TOKEN, authToken);
    editor.commit();
  }

  public static String getAuthToken() {
    SharedPreferences settings = Application.getAppContext().getSharedPreferences(SHARED_PREFS_NAME, 0);
    String authToken = settings.getString(Constants.AUTH_TOKEN, "");
    return authToken;
  }
  public static int getCurrentUser() {
    SharedPreferences settings =Application.getAppContext().getSharedPreferences(SHARED_PREFS_NAME, 0);
    int userId = settings.getInt(Constants.APP_CURRENT_USER, 0);
    return userId;
  }
  public static void setCurrentUser(int id) {
    SharedPreferences settings = Application.getAppContext().getSharedPreferences(SHARED_PREFS_NAME, 0);
    SharedPreferences.Editor editor = settings.edit();
    editor.putInt(Constants.APP_CURRENT_USER, id);
    editor.commit();
  }
}
