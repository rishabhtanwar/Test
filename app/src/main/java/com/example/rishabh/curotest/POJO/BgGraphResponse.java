package com.example.rishabh.curotest.POJO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Created by rishabh on 04/04/2017.
 */

public class BgGraphResponse {
  @JsonProperty("success")
  private Boolean success;
  @JsonProperty("errorcode")
  private Integer errorcode;

  public Boolean getSuccess() {
    return success;
  }

  public void setSuccess(Boolean success) {
    this.success = success;
  }

  public Integer getErrorcode() {
    return errorcode;
  }

  public void setErrorcode(Integer errorcode) {
    this.errorcode = errorcode;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public List<Datum> getData() {
    return data;
  }

  public void setData(List<Datum> data) {
    this.data = data;
  }

  @JsonProperty("message")

  private String message;
  @JsonProperty("data")
  private List<Datum> data = null;

  @JsonCreator public BgGraphResponse(@JsonProperty("success") Boolean success,
      @JsonProperty("errorcode") int errorcode, @JsonProperty("message") String message,
      @JsonProperty("log_schedules") List<Datum> data) {
    this.success = success;
    this.errorcode = errorcode;
    this.message = message;
    this.data = data;
  }

  public static class Datum{
    @JsonProperty("date")
    private String date;

    public String getDate() {
      return date;
    }

    public void setDate(String date) {
      this.date = date;
    }

    public String getMaxValue() {
      return maxValue;
    }

    public void setMaxValue(String maxValue) {
      this.maxValue = maxValue;
    }

    public String getAvgValue() {
      return avgValue;
    }

    public void setAvgValue(String avgValue) {
      this.avgValue = avgValue;
    }

    public String getMinValue() {
      return minValue;
    }

    public void setMinValue(String minValue) {
      this.minValue = minValue;
    }

    @JsonProperty("max_value")
    private String maxValue;
    @JsonProperty("avg_value")
    private String avgValue;
    @JsonProperty("min_value")
    private String minValue;
  }
}
