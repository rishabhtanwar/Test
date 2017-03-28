package com.example.rishabh.curotest.API;

import com.example.rishabh.curotest.Helpers.LogStreakResponse;
import com.example.rishabh.curotest.Helpers.SummaryResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by rishabh on 27/03/2017.
 */

public interface AuthorizedApi {
  @GET(ApiRequest.LOG_STREAK_LATEST) Call<ResponseBody> getLogStreak(
      @Query("start_date") String startDate, @Query("end_date") String endDate,
      @Query("type") String requestType);

  @GET(ApiRequest.SUMMARY) Call<SummaryResponse> getSummary();
}
