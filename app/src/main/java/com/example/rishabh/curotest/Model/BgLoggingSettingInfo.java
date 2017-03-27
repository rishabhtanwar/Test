package com.example.rishabh.curotest.Model;

/**
 * Created by rishabh on 27/03/2017.
 */

public class BgLoggingSettingInfo {
  boolean check;
  String mainTitle;
  String subTitle;

  public String getSubTitle() {
    return subTitle;
  }

  public void setSubTitle(String subTitle) {
    this.subTitle = subTitle;
  }

  public boolean isCheck() {
    return check;
  }

  public void setCheck(boolean check) {
    this.check = check;
  }

  public String getTimeSlot() {
    return mainTitle;
  }

  public void setTimeSlot(String mainTitle) {
    this.mainTitle = mainTitle;
  }
}
