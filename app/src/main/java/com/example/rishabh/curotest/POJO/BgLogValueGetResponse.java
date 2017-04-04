package com.example.rishabh.curotest.POJO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Created by rishabh on 04/04/2017.
 */

public class BgLogValueGetResponse {
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

  public List<LogValue> getLogValues() {
    return logValues;
  }

  public void setLogValues(List<LogValue> logValues) {
    this.logValues = logValues;
  }

  @JsonProperty("success") private Boolean success;
  @JsonProperty("errorcode") private Integer errorcode;
  @JsonProperty("message") private String message;
  @JsonProperty("log_values") private List<LogValue> logValues = null;

  @JsonCreator public BgLogValueGetResponse(@JsonProperty("success") Boolean success,
      @JsonProperty("errorcode") int errorcode, @JsonProperty("message") String message,
      @JsonProperty("log_schedules") List<LogValue> logValues) {
    this.success = success;
    this.errorcode = errorcode;
    this.message = message;
    this.logValues = logValues;
  }

  public static class LogValue {
    @JsonProperty("id") private Integer id;
    @JsonProperty("date") private String date;

    public String getDateTime() {
      return dateTime;
    }

    public void setDateTime(String dateTime) {
      this.dateTime = dateTime;
    }

    public Integer getId() {
      return id;
    }

    public void setId(Integer id) {
      this.id = id;
    }

    public String getDate() {
      return date;
    }

    public void setDate(String date) {
      this.date = date;
    }

    public Integer getTimeslotId() {
      return timeslotId;
    }

    public void setTimeslotId(Integer timeslotId) {
      this.timeslotId = timeslotId;
    }

    public String getValue() {
      return value;
    }

    public void setValue(String value) {
      this.value = value;
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

    public String getLoggedTime() {
      return loggedTime;
    }

    public void setLoggedTime(String loggedTime) {
      this.loggedTime = loggedTime;
    }

    @JsonProperty("date_time")

    private String dateTime;
    @JsonProperty("timeslot_id") private Integer timeslotId;
    @JsonProperty("value") private String value;
    @JsonProperty("unit_id") private Integer unitId;
    @JsonProperty("unit") private String unit;
    @JsonProperty("logged_time") private String loggedTime;
  }
}
