package com.example.rishabh.curotest.SyncDataWithApi;

import com.example.rishabh.curotest.API.RestClient;
import com.example.rishabh.curotest.POJO.SummaryResponse;
import com.example.rishabh.curotest.helpers.AppSettings;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rishabh on 28/03/2017.
 */

public class SyncSummary {

  public static void syncSummary() {
    Call<SummaryResponse> call = RestClient.getApiService().getSummary();
    call.enqueue(new Callback<SummaryResponse>() {
      @Override
      public void onResponse(Call<SummaryResponse> call, Response<SummaryResponse> response) {
        saveSummary(response.body());
      }

      @Override public void onFailure(Call<SummaryResponse> call, Throwable t) {
        t.printStackTrace();
      }
    });
  }

  private static void saveSummary(SummaryResponse response) {
    if (response.getSummary().getActivity() != null) {
      AppSettings.setActivitySummary(
          String.valueOf(response.getSummary().getActivity().getTotalCaloriesBurned()));
    }
    if (response.getSummary().getBloodGlucose() != null) {
      com.example.rishabh.curotest.helpers.AppSettings.setBgSummary(
          String.valueOf(response.getSummary().getBloodGlucose().getLatestValue())
              + response.getSummary().getBloodGlucose().getUnit());
    }
    if (response.getSummary().getGoals() != null) {
      AppSettings.setGoalSummary(
          String.valueOf(response.getSummary().getGoals().getNumberOfGoalsSet()));
    }
    if (response.getSummary().getMeal() != null) {
      AppSettings.setMealSummary(
          String.valueOf(response.getSummary().getMeal().getTotalCaloriesConsumed()));
    }
    if (response.getSummary().getMedication() != null) {
      AppSettings.setMedicationSummary(
          String.valueOf(response.getSummary().getMedication().getMedicineTaken())
              + "/"
              + response.getSummary().getMedication().getMedicineTarget());
    }
  }
}
