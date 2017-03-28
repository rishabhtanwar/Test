package com.example.rishabh.curotest.Helpers;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.json.JSONObject;

/**
 * Created by rishabh on 28/03/2017.
 */

public class LogStreakResponse {
  @JsonProperty("log_data") JSONObject log_data;

  public JSONObject getLog_data() {
    return log_data;
  }

  public void setLog_data(JSONObject log_data) {
    this.log_data = log_data;
  }
  //@JsonProperty("message") String message;


  //public String getMessage() {
  //  return message;
  //}
  //
  //public void setMessage(String message) {
  //  this.message = message;
  //}
}
