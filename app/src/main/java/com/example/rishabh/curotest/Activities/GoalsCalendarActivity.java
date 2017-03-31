package com.example.rishabh.curotest.Activities;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import butterknife.ButterKnife;
import com.example.rishabh.curotest.Interfaces.LogStreakResponse;
import com.example.rishabh.curotest.Model.LogStreakForMonth;
import com.example.rishabh.curotest.R;
import com.example.rishabh.curotest.SyncDataWithApi.SyncLogStreakData;
import com.example.rishabh.curotest.Utils.AppDateHelper;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GoalsCalendarActivity extends AppCompatActivity {
  String month;
  List<Integer> markingList = new ArrayList<>();
  int swipeCount = 0;
  CalendarView cv;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.daily_goals_layout);
    ButterKnife.bind(this);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    toolbar.setBackgroundColor(getResources().getColor(R.color.theme_color));
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeButtonEnabled(true);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      getWindow().setStatusBarColor(Color.parseColor("#3595B9"));
    }
    month = AppDateHelper.monthYearNameBySwipeIndex(swipeCount);
    cv = ((CalendarView) findViewById(R.id.calendar_view));
    SyncLogStreakData.syncData(AppDateHelper.getFirstDayOfTheMonth(swipeCount),
        AppDateHelper.getLastDayOfTheMonth(swipeCount), "history", new LogStreakResponse() {
          @Override public void onSuccess(boolean status) {
            if (status) {
              setMarkingList(month);
            }
          }
        }, AppDateHelper.monthYearNameBySwipeIndex(swipeCount));

    // assign event handler
    cv.setEventHandler(new CalendarView.EventHandler() {
      @Override public void onDayLongPress(Date date) {
        // show returned day
        DateFormat df = SimpleDateFormat.getDateInstance();
        //AppUtils.showToast(df.format(date),GoalsCalendarActivity.this,null);
      }
    });
    cv.prevButtonClick(new CalendarView.PreviousButtonClick() {
      @Override public void swipeCount(int swipeIndex) {
        swipeCount = swipeIndex;
        month = AppDateHelper.monthYearNameBySwipeIndex(swipeCount);
        SyncLogStreakData.syncData(AppDateHelper.getFirstDayOfTheMonth(swipeCount),
            AppDateHelper.getLastDayOfTheMonth(swipeCount), "history", new LogStreakResponse() {
              @Override public void onSuccess(boolean status) {
                if (status) {
                  setMarkingList(month);
                }
              }
            }, AppDateHelper.monthYearNameBySwipeIndex(swipeCount));
      }
    });
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {

    return super.onOptionsItemSelected(item);
  }

  @Override public void onBackPressed() {
    super.onBackPressed();
    CalendarView.skipIndex = 1 - Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    finish();
  }

  private void setMarkingList(String month) {
    Realm realm = Realm.getDefaultInstance();
    RealmResults<LogStreakForMonth> realmResults = realm.where(LogStreakForMonth.class)
        .equalTo("month", month)
        .findAllSorted("date", Sort.ASCENDING);
    if (realmResults.size() > 0) {
      for (LogStreakForMonth logStreakForMonth : realmResults) {
        markingList.add(logStreakForMonth.getStatusFlag());
      }
    } else {
      for (int i = 0; i < AppDateHelper.totalNumberOfDays(swipeCount); i++) {
        markingList.add(2);
      }
    }
    cv.updateCalendar(markingList, swipeCount);
  }
}
