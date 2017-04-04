package com.example.rishabh.curotest.POJO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by rishabh on 28/03/2017.
 */

public class SummaryResponse {

  public Summary getSummary() {
    return summary;
  }

  public void setSummary(Summary summary) {
    this.summary = summary;
  }

  @JsonProperty("summary") private Summary summary;

  @JsonCreator public SummaryResponse(@JsonProperty("summary") Summary summary) {
    this.summary = summary;
  }

  public static class Summary {

    @JsonProperty("blood_glucose") private BloodGlucose bloodGlucose;
    @JsonProperty("activity") private Activity activity;
    @JsonProperty("meal") private Meal meal;

    public Medication getMedication() {
      return medication;
    }

    public void setMedication(Medication medication) {
      this.medication = medication;
    }

    public Goals getGoals() {
      return goals;
    }

    public void setGoals(Goals goals) {
      this.goals = goals;
    }

    public Vitals getVitals() {
      return vitals;
    }

    public void setVitals(Vitals vitals) {
      this.vitals = vitals;
    }

    public Meal getMeal() {
      return meal;
    }

    public void setMeal(Meal meal) {
      this.meal = meal;
    }

    public Activity getActivity() {
      return activity;
    }

    public void setActivity(Activity activity) {
      this.activity = activity;
    }

    public BloodGlucose getBloodGlucose() {
      return bloodGlucose;
    }

    public void setBloodGlucose(BloodGlucose bloodGlucose) {
      this.bloodGlucose = bloodGlucose;
    }

    @JsonProperty("medication") private Medication medication;
    @JsonProperty("vitals") private Vitals vitals;
    @JsonProperty("goals") private Goals goals;

    @JsonCreator public Summary(@JsonProperty("medication") Medication medication,
        @JsonProperty("vitals") Vitals vitals, @JsonProperty("goals") Goals goals,
        @JsonProperty("blood_glucose") BloodGlucose bloodGlucose,
        @JsonProperty("activity") Activity activity, @JsonProperty("meal") Meal meal) {
      this.meal = meal;
      this.activity = activity;
      this.bloodGlucose = bloodGlucose;
      this.goals = goals;
      this.vitals = vitals;
      this.medication = medication;
    }
  }

  public static class Activity {

    public Integer getTotalActivityCount() {
      return totalActivityCount;
    }

    public void setTotalActivityCount(Integer totalActivityCount) {
      this.totalActivityCount = totalActivityCount;
    }

    public Double getTotalCaloriesBurned() {
      return totalCaloriesBurned;
    }

    public void setTotalCaloriesBurned(Double totalCaloriesBurned) {
      this.totalCaloriesBurned = totalCaloriesBurned;
    }

    @JsonProperty("total_activity_count")

    private Integer totalActivityCount;
    @JsonProperty("total_calories_burned") private Double totalCaloriesBurned;
  }

  public static class BloodGlucose {
    @JsonProperty("latest_timeslot") private Integer latestTimeslot;
    @JsonProperty("latest_value") private Double latestValue;
    @JsonProperty("unit_id") private Integer unitId;

    public Integer getLatestTimeslot() {
      return latestTimeslot;
    }

    public void setLatestTimeslot(Integer latestTimeslot) {
      this.latestTimeslot = latestTimeslot;
    }

    public Double getLatestValue() {
      return latestValue;
    }

    public void setLatestValue(Double latestValue) {
      this.latestValue = latestValue;
    }

    public Integer getUnitId() {
      return unitId;
    }

    public void setUnitId(Integer unitId) {
      this.unitId = unitId;
    }

    public String getUnit() {
      return unit;
    }

    public void setUnit(String unit) {
      this.unit = unit;
    }

    public Integer getTimeslotsLogged() {
      return timeslotsLogged;
    }

    public void setTimeslotsLogged(Integer timeslotsLogged) {
      this.timeslotsLogged = timeslotsLogged;
    }

    @JsonProperty("unit") private String unit;
    @JsonProperty("timeslots_logged") private Integer timeslotsLogged;
  }

  public static class Meal {
    public Double getTotalCaloriesConsumed() {
      return totalCaloriesConsumed;
    }

    public void setTotalCaloriesConsumed(Double totalCaloriesConsumed) {
      this.totalCaloriesConsumed = totalCaloriesConsumed;
    }

    public Double getTotalCarbsConsumed() {
      return totalCarbsConsumed;
    }

    public void setTotalCarbsConsumed(Double totalCarbsConsumed) {
      this.totalCarbsConsumed = totalCarbsConsumed;
    }

    public Integer getTimeslotsLogged() {
      return timeslotsLogged;
    }

    public void setTimeslotsLogged(Integer timeslotsLogged) {
      this.timeslotsLogged = timeslotsLogged;
    }

    @JsonProperty("timeslots_logged")

    private Integer timeslotsLogged;
    @JsonProperty("total_calories_consumed") private Double totalCaloriesConsumed;
    @JsonProperty("total_carbs_consumed") private Double totalCarbsConsumed;
  }

  public static class Medication {
    @JsonProperty("medicine_target") private Integer medicineTarget;

    public Integer getMedicineTaken() {
      return medicineTaken;
    }

    public void setMedicineTaken(Integer medicineTaken) {
      this.medicineTaken = medicineTaken;
    }

    public Integer getMedicineTarget() {
      return medicineTarget;
    }

    public void setMedicineTarget(Integer medicineTarget) {
      this.medicineTarget = medicineTarget;
    }

    @JsonProperty("medicine_taken")

    private Integer medicineTaken;
  }

  public static class Vitals {
    @JsonProperty("vitaldataattribute_id") private Integer vitaldataattributeId;

    public String getVitaldataattributeName() {
      return vitaldataattributeName;
    }

    public void setVitaldataattributeName(String vitaldataattributeName) {
      this.vitaldataattributeName = vitaldataattributeName;
    }

    public Integer getVitaldataattributeId() {
      return vitaldataattributeId;
    }

    public void setVitaldataattributeId(Integer vitaldataattributeId) {
      this.vitaldataattributeId = vitaldataattributeId;
    }

    public Double getValue() {
      return value;
    }

    public void setValue(Double value) {
      this.value = value;
    }

    @JsonProperty("vitaldataattribute_name")

    private String vitaldataattributeName;
    @JsonProperty("value") private Double value;
  }

  public  static class Goals {
    @JsonProperty("number_of_goals_set") private Integer numberOfGoalsSet;

    public Integer getNumberOfGoalsSet() {
      return numberOfGoalsSet;
    }

    public void setNumberOfGoalsSet(Integer numberOfGoalsSet) {
      this.numberOfGoalsSet = numberOfGoalsSet;
    }
  }
}
