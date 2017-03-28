package com.example.rishabh.curotest.API;

/**
 * Created by rishabh on 28/03/2017.
 */

public class ApiRequest {
  public static final String LOG_STREAK_LATEST = "api/v3/diary/log_streak";
  public static final String SUMMARY = "api/v3/diary/summary";

  public static class RequestParams {
    public static final String USER = "user";
    public static final String USER_ID = "user_id";
    public static final String INVITE_CODE = "invite_code";
    public static final String PHONE = "phone";
    public static final String OTP = "otp";
    public static final String TYPE = "type";
    public static final String EMAIL = "email";
    public static final String PAGE = "page";
    public static final String PER = "per";
    public static final String ID = "id";
    public static final String LAST_SYNC = "last_sync";
    public static final String COUNTRY_CODE = "country_code";
    public static final String START_DATE = "start_date";
    public static final String END_DATE = "end_date";
  }
}
