package com.example.rishabh.curotest.activities;

import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.example.rishabh.curotest.DBO.BgDBO;
import com.example.rishabh.curotest.Interfaces.LogScheduleCallback;
import com.example.rishabh.curotest.Network.ConnectionDetector;
import com.example.rishabh.curotest.R;
import com.example.rishabh.curotest.Utils.AppDateHelper;
import com.example.rishabh.curotest.Utils.Constants;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class BgQuickLog extends AppCompatActivity {
  @Bind(R.id.custom_value) EditText customValue;
  @Bind(R.id.done) TextView done;
  @Bind(R.id.current_time) TextView currentTime;
  @Bind(R.id.bg_unit) TextView bgUnit;
  @Bind(R.id.spinner) Spinner spinner;
  ArrayList<Integer> timeSlot = new ArrayList<>();
  ArrayList<String> timeSlotName = new ArrayList<>();
  int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
  int minute = Calendar.getInstance().get(Calendar.MINUTE);
  TimePickerDialog mTimePicker;
  HashMap<String, Integer> hashMap = new HashMap<>();
  ConnectionDetector connectionDetector;
  @Bind(R.id.toolbar) Toolbar toolbar;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_bg_quick_log);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      getWindow().setStatusBarColor(Color.parseColor("#3595B9"));
    }
    ButterKnife.bind(this);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeButtonEnabled(true);
    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        finish();
      }
    });
    toolbar.setTitle("Quick Log");
    toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
    connectionDetector = new ConnectionDetector(this);
    currentTime.setText(getTime(hour, minute) + ":00");
    currentTime.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        mTimePicker =
            new TimePickerDialog(BgQuickLog.this, new TimePickerDialog.OnTimeSetListener() {
              @Override
              public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                currentTime.setText(getTime(selectedHour, selectedMinute) + ":00");
              }
            }, hour, minute, true);//Yes 24 hour time
        mTimePicker.show();
      }
    });
    timeSlot = getIntent().getExtras().getIntegerArrayList("id_array");
    getTimeSlotForQuickLog(timeSlot);

    done.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (spinner.getSelectedItem().equals("Select time slot")) {
          Toast.makeText(BgQuickLog.this, "Please select time slot", Toast.LENGTH_SHORT).show();
        } else if (customValue.getText().toString().equalsIgnoreCase("") || customValue.getText()
            .toString()
            .equalsIgnoreCase("0")) {
          Toast.makeText(BgQuickLog.this, "Please enter valid value", Toast.LENGTH_SHORT).show();
        } else {
          BgDBO.saveBgLog(Integer.parseInt(customValue.getText().toString()),
              AppDateHelper.getInstance().getDateInMillisWithSwipeCount(0),
              hashMap.get(spinner.getSelectedItem()), getDateTimeValue(currentTime),
              getLoggedTimeValue(currentTime), connectionDetector.isNetworkAvailable(),
              new LogScheduleCallback() {
                @Override public void onSuccess(boolean check) {
                  finish();
                }
              }, 0);
        }
      }
    });
  }

  private void setSpinnerData(ArrayList<String> arrayList) {
    ArrayAdapter arrayAdapter =
        new ArrayAdapter(this, R.layout.simple_spinner_item, arrayList);
    arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
    spinner.setAdapter(arrayAdapter);
  }

  private void getTimeSlotForQuickLog(ArrayList<Integer> timeSlot) {
    timeSlotName = BgDBO.getBgQuickLogTimeSlot(timeSlot);
    hashMap = BgDBO.quickLogHashMap;
    timeSlotName.add(0, "Select time slot");
    setSpinnerData(timeSlotName);
  }

  private StringBuilder getTime(int hour, int minute) {
    if (hour < 10 && minute < 10) {
      return new StringBuilder().append("0").append(hour).append(":").append("0").append(minute);
    } else if (minute < 10) {
      return new StringBuilder().append(hour).append(":").append("0").append(minute);
    } else if (hour < 10) {
      return new StringBuilder().append("0").append(hour).append(":").append(minute);
    } else {
      return new StringBuilder().append(hour).append(":").append(minute);
    }
  }

  private String getDateTimeValue(TextView textView) {
    String dateTime = AppDateHelper.getInstance().getDateWithWeekDays(Constants.DATEFORMAT, 0)
        + " "
        + textView.getText().toString()
        + " +0530";
    return dateTime;
  }

  private String getLoggedTimeValue(TextView textView) {
    String loggedTime = AppDateHelper.getInstance().getDateWithWeekDays(Constants.DATEFORMAT, 0)
        + " "
        + textView.getText().toString()
        + " +0530";
    return loggedTime;
  }
}
