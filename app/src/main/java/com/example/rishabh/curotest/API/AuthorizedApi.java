package com.example.rishabh.curotest.API;

import com.example.rishabh.curotest.BodyRequest.LogValuePostData;
import com.example.rishabh.curotest.POJO.BgGraphResponse;
import com.example.rishabh.curotest.POJO.BgLogValueGetResponse;
import com.example.rishabh.curotest.POJO.BgLogValuePostResponse;
import com.example.rishabh.curotest.POJO.LogScheduleData;
import com.example.rishabh.curotest.POJO.SummaryResponse;
import com.example.rishabh.curotest.BodyRequest.LogSchedulePostData;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by rishabh on 27/03/2017.
 */

public interface AuthorizedApi {
  @GET(ApiRequest.LOG_STREAK_LATEST) Call<ResponseBody> getLogStreak(
      @Query("start_date") String startDate, @Query("end_date") String endDate,
      @Query("type") String requestType);

  @GET(ApiRequest.SUMMARY) Call<SummaryResponse> getSummary();

  @POST(ApiRequest.BGLOGGING) Call<LogScheduleData> postBgSchedule(@Body
      LogSchedulePostData logSchedulePostData);

  @GET(ApiRequest.BGLOGGING) Call<LogScheduleData> getBgSchedule(@Query("start_date") String startDate,
      @Query("end_date") String endDate, @Query("log_type") String logType);

  @POST(ApiRequest.BGLOGGING_VALUE) Call<BgLogValuePostResponse> postBgLog(@Body LogValuePostData logValuePostData);

  @GET(ApiRequest.BGLOGGING_VALUE) Call<BgLogValueGetResponse> getBgLogs(@Query("start_date") String startDate,
      @Query("end_date") String endDate, @Query("log_type") String logType);

  @GET(ApiRequest.BGGRAPH) Call<BgGraphResponse> getBgGraph(@Query("start_date") String startDate,
      @Query("end_date") String endDate);
}
