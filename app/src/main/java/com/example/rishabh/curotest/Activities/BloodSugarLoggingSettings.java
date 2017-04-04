package com.example.rishabh.curotest.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.example.rishabh.curotest.Adapter.BgLogAdapter;
import com.example.rishabh.curotest.DBO.BgDBO;
import com.example.rishabh.curotest.helpers.AppSettings;
import com.example.rishabh.curotest.Interfaces.LogScheduleCallback;
import com.example.rishabh.curotest.Model.BgLoggingSettingInfo;
import com.example.rishabh.curotest.Model.BgSchedule;
import com.example.rishabh.curotest.Network.ConnectionDetector;
import com.example.rishabh.curotest.R;
import com.example.rishabh.curotest.Utils.AppDateHelper;
import io.realm.Realm;
import io.realm.RealmResults;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BloodSugarLoggingSettings extends AppCompatActivity {
  @Bind(R.id.bg_log_rv) RecyclerView recyclerView;
  @Bind(R.id.progress) ProgressBar progressBar;
  LinearLayoutManager linearLayoutManager;
  ArrayList<BgLoggingSettingInfo> arrayList = new ArrayList<>();
  BgLogAdapter bgLogAdapter;
  Realm realm;
  HashMap<Integer, String> timeSlotIdList = new HashMap<>();
  long todayDateInMillis;
  @Bind(R.id.done) ImageView done;
  ConnectionDetector connectionDetector;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_blood_sugar_logging_settings);
    ButterKnife.bind(this);
    connectionDetector = new ConnectionDetector(this);
    linearLayoutManager = new LinearLayoutManager(this);
    realm = Realm.getDefaultInstance();
    todayDateInMillis = AppDateHelper.getInstance().getDateInMillisWithSwipeCount(0);
    setData();
    AppSettings.setBgApiStatus(true);
    progressBar.setVisibility(View.GONE);
    done.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        timeSlotIdList = bgLogAdapter.getTimeSlotIdList();
        for (Map.Entry<Integer, String> entry : timeSlotIdList.entrySet()) {
          if (entry.getValue().equalsIgnoreCase("ticked")) {
            BgDBO.saveBgSchedule(entry.getKey(), todayDateInMillis);
          } else {
            BgDBO.deleteBgSchedule(entry.getKey());
          }
        }
        if (connectionDetector.isConnectingToInternet()) {
          progressBar.setVisibility(View.VISIBLE);
          BgDBO.syncBgSchedule(new LogScheduleCallback() {
            @Override public void onSuccess(boolean check) {
              progressBar.setVisibility(View.GONE);
              if (realm.isClosed()) {
                realm = Realm.getDefaultInstance();
              }
              RealmResults<BgSchedule> realmResults = realm.where(BgSchedule.class).findAll();
              realm.close();
              finish();
            }
          });
        }
      }
    });
  }

  private void setData() {
    arrayList = BgDBO.setLoggingTimeSlot(realm, todayDateInMillis);
    setAdapter();
  }

  private void setAdapter() {
    recyclerView.setLayoutManager(linearLayoutManager);
    bgLogAdapter = new BgLogAdapter(BloodSugarLoggingSettings.this, arrayList);
    recyclerView.setAdapter(bgLogAdapter);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.done) {

    }
    return super.onOptionsItemSelected(item);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    super.onCreateOptionsMenu(menu);
    MenuInflater menuInflater = getMenuInflater();
    menuInflater.inflate(R.menu.done_menu, menu);
    return true;
  }

  @Override public void onBackPressed() {

    super.onBackPressed();
  }
}
