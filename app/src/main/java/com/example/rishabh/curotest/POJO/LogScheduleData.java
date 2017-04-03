package com.example.rishabh.curotest.POJO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Created by rishabh on 31/03/2017.
 */

public class LogScheduleData {
  @JsonProperty("success") private Boolean success;
  @JsonProperty("errorcode") private int errorcode;

  public Boolean getSuccess() {
    return success;
  }

  public void setSuccess(Boolean success) {
    this.success = success;
  }

  public int getErrorcode() {
    return errorcode;
  }

  public void setErrorcode(int errorcode) {
    this.errorcode = errorcode;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public List<LogSchedule> getLogSchedules() {
    return logSchedules;
  }

  public void setLogSchedules(List<LogSchedule> logSchedules) {
    this.logSchedules = logSchedules;
  }

  @JsonProperty("message") private String message;
  @JsonProperty("log_schedules") private List<LogSchedule> logSchedules = null;

  @JsonCreator public LogScheduleData(@JsonProperty("success") Boolean success,
      @JsonProperty("errorcode") int errorcode, @JsonProperty("message") String message,
      @JsonProperty("log_schedules") List<LogSchedule> logSchedules) {
    this.success = success;
    this.errorcode = errorcode;
    this.message = message;
    this.logSchedules = logSchedules;
  }

  public static class LogSchedule {
    @JsonProperty("id") private int id;
    @JsonProperty("start_date") private String startdate;
    @JsonProperty("end_date") private String enddate;
    @JsonProperty("set_by_id") private int setById;

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public String getStartdate() {
      return startdate;
    }

    public void setStartdate(String startdate) {
      this.startdate = startdate;
    }

    public String getEnddate() {
      return enddate;
    }

    public void setEnddate(String enddate) {
      this.enddate = enddate;
    }

    public Integer getSetById() {
      return setById;
    }

    public void setSetById(Integer setById) {
      this.setById = setById;
    }

    public String getNotes() {
      return notes;
    }

    public void setNotes(String notes) {
      this.notes = notes;
    }

    public int getTimeslotId() {
      return timeslotId;
    }

    public void setTimeslotId(int timeslotId) {
      this.timeslotId = timeslotId;
    }

    public Integer getClientId() {
      return clientId;
    }

    public void setClientId(Integer clientId) {
      this.clientId = clientId;
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

    @JsonProperty("notes") private String notes;
    @JsonProperty("timeslot_id") private int timeslotId;
    @JsonProperty("client_id") private Integer clientId;
    @JsonProperty("status") private Boolean status;
    @JsonProperty("is_deleted") private Boolean isDeleted;

    public LogSchedule() {

    }
  }
}
