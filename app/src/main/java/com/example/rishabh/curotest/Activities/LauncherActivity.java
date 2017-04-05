package com.example.rishabh.curotest.activities;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.example.rishabh.curotest.helpers.AppSettings;
import com.example.rishabh.curotest.helpers.SetTimeSlots;
import com.example.rishabh.curotest.Model.TimeSlots;
import com.example.rishabh.curotest.Network.ConnectionDetector;
import com.example.rishabh.curotest.R;
import com.example.rishabh.curotest.SyncDataWithApi.SyncBgLogging;
import com.example.rishabh.curotest.SyncDataWithApi.SyncLogStreakData;
import com.example.rishabh.curotest.SyncDataWithApi.SyncSummary;
import com.example.rishabh.curotest.Utils.AppDateHelper;
import com.example.rishabh.curotest.Utils.Constants;
import io.realm.Realm;
import io.realm.RealmResults;
import java.util.Calendar;

public class LauncherActivity extends AppCompatActivity {
  @Bind(R.id.enter_button) Button enterButton;
  int proceed = 0;
  SyncBgLogging syncBgLogging;
  ConnectionDetector connectionDetector;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_launcher);
    ButterKnife.bind(this);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      getWindow().setStatusBarColor(Color.parseColor("#3595B9"));
    }
    if (AppSettings.getAuthToken().equalsIgnoreCase("")) {
      AppSettings.setAuthToken("upecqc5qyxjgupuqxt2g");
      AppSettings.setCurrentUserId(111);
    }
    connectionDetector = new ConnectionDetector(this);
    AppSettings.setBgApiStatus(true);
    checkTimeSlot();
    Calendar calendar = Calendar.getInstance();
    int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
    String startDate =
        AppDateHelper.getInstance().getDateWithWeekDays(Constants.DATEFORMAT, -(dayOfWeek - 1));
    String endDate = AppDateHelper.getInstance().getDateWithWeekDays(Constants.DATEFORMAT, 0);
    SyncLogStreakData.syncData(startDate, endDate, "latest", null, "");
    SyncSummary.syncSummary();
    syncBgLogging = new SyncBgLogging();
    syncBgLogging.setSyncListener(new SyncBgLogging.SyncListener() {
      @Override public void onSucess(boolean check) {
        if (check == true) {
          proceed = proceed + 1;
        }
      }
    });
    enterButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (proceed == 2) {
          Intent intent = new Intent(LauncherActivity.this, MainActivity.class);
          startActivity(intent);
          finish();
        } else {
          Toast.makeText(LauncherActivity.this, "Sync is going on", Toast.LENGTH_SHORT).show();

        }
      }
    });
    if (connectionDetector.isNetworkAvailable()) {
      Bundle bundle = new Bundle();
      bundle.putString(Constants.SYNC_DATA, Constants.SYNC_ALL);
      ContentResolver.requestSync(AppSettings.getInstance().CreateSyncAccount(),
          Constants.AUTHORITY, bundle);
    } else {
      proceed = 2;
    }
  }

  private void checkTimeSlot() {
    Realm realm = Realm.getDefaultInstance();
    RealmResults<TimeSlots> realmResults = realm.where(TimeSlots.class).findAll();
    if (realmResults.size() < 1) {
      SetTimeSlots setTimeSlots = new SetTimeSlots(realm);
    }
  }

  public void setProceed(int proceed1) {
    proceed = proceed+proceed1;
  }
}
