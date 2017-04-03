package com.example.rishabh.curotest.POJO;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Created by rishabh on 03/04/2017.
 */

public class BgLogValueResponse {
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

  public List<Result> getResult() {
    return result;
  }

  public void setResult(List<Result> result) {
    this.result = result;
  }

  @JsonProperty("message")
  private String message;
  @JsonProperty("result")
  private List<Result> result = null;

  public class Result{
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("date")
    private String date;
    @JsonProperty("date_time")
    private String dateTime;
    @JsonProperty("logged_time")
    private String loggedTime;

    public Integer getTasktemplateId() {
      return tasktemplateId;
    }

    public void setTasktemplateId(Integer tasktemplateId) {
      this.tasktemplateId = tasktemplateId;
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

    public String getDateTime() {
      return dateTime;
    }

    public void setDateTime(String dateTime) {
      this.dateTime = dateTime;
    }

    public String getLoggedTime() {
      return loggedTime;
    }

    public void setLoggedTime(String loggedTime) {
      this.loggedTime = loggedTime;
    }

    public Integer getTimeslotId() {
      return timeslotId;
    }

    public void setTimeslotId(Integer timeslotId) {
      this.timeslotId = timeslotId;
    }

    public Integer getClientId() {
      return clientId;
    }

    public void setClientId(Integer clientId) {
      this.clientId = clientId;
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

    public Boolean getStatus() {
      return status;
    }

    public void setStatus(Boolean status) {
      this.status = status;
    }

    public Boolean getDeleted() {
      return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
      isDeleted = deleted;
    }

    @JsonProperty("tasktemplate_id")

    private Integer tasktemplateId;
    @JsonProperty("timeslot_id")
    private Integer timeslotId;
    @JsonProperty("client_id")
    private Integer clientId;
    @JsonProperty("value")
    private String value;
    @JsonProperty("unit_id")
    private Integer unitId;
    @JsonProperty("unit")
    private String unit;
    @JsonProperty("status")
    private Boolean status;
    @JsonProperty("is_deleted")
    private Boolean isDeleted;

  }
}
