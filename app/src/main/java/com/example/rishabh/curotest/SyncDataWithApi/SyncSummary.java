package com.example.rishabh.curotest.SyncDataWithApi;

import com.example.rishabh.curotest.API.RestClient;
import com.example.rishabh.curotest.helpers.AppSettings;
import com.example.rishabh.curotest.POJO.SummaryResponse;
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

      }
    });
  }

  private static void saveSummary(SummaryResponse response) {
    if (response != null) {
      if (response.getActivity() != null) {
        AppSettings.setActivitySummary(String.valueOf(response.getActivity().getTotalCaloriesBurned()));
      }
      if (response.getBloodGlucose() != null) {
        AppSettings.setBgSummary(String.valueOf(response.getBloodGlucose().getLatestValue()) + response.getBloodGlucose()
            .getUnit());
      }
      if (response.getGoals() != null) {
        AppSettings.setGoalSummary(String.valueOf(response.getGoals().getNumberOfGoalsSet()));
      }
      if (response.getMeal() != null) {
        AppSettings.setMealSummary(String.valueOf(response.getMeal().getTotalCaloriesConsumed()));
      }
      if (response.getMedication() != null) {
        AppSettings.setMedicationSummary(String.valueOf(response.getMedication().getMedicineTaken())
            + "/"
            + response.getMedication().getMedicineTarget());
      }
    }
  }
}
