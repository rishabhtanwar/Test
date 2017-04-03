package com.example.rishabh.curotest.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.example.rishabh.curotest.Helpers.AppSettings;
import com.example.rishabh.curotest.Helpers.SetTimeSlots;
import com.example.rishabh.curotest.Model.TimeSlots;
import com.example.rishabh.curotest.R;
import com.example.rishabh.curotest.SyncDataWithApi.SyncLogStreakData;
import com.example.rishabh.curotest.SyncDataWithApi.SyncSummary;
import com.example.rishabh.curotest.Utils.AppDateHelper;
import com.example.rishabh.curotest.Utils.Constants;
import io.realm.Realm;
import io.realm.RealmResults;
import java.util.Calendar;

public class LauncherActivity extends AppCompatActivity {
  @Bind(R.id.enter_button) Button enterButton;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_launcher);
    ButterKnife.bind(this);
    if (AppSettings.getAuthToken().equalsIgnoreCase("")) {
      AppSettings.setAuthToken("ecf5yhhkvcpu5dxbsbuy");
      AppSettings.setCurrentUserId(111);
    }
    AppSettings.setBgApiStatus(true);
    checkTimeSlot();
    Calendar calendar = Calendar.getInstance();
    int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
    String startDate =
        AppDateHelper.getInstance().getDateWithWeekDays(Constants.DATEFORMAT, -(dayOfWeek - 1));
    String endDate = AppDateHelper.getInstance().getDateWithWeekDays(Constants.DATEFORMAT, 0);
    SyncLogStreakData.syncData(startDate, endDate, "latest", null, "");
    SyncSummary.syncSummary();
    enterButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Intent intent = new Intent(LauncherActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
      }
    });
  }

  private void checkTimeSlot() {
    Realm realm = Realm.getDefaultInstance();
    RealmResults<TimeSlots> realmResults = realm.where(TimeSlots.class).findAll();
    if (realmResults.size() < 1) {
      SetTimeSlots setTimeSlots = new SetTimeSlots(realm);
    }
  }
}
