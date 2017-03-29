package com.example.rishabh.curotest.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.example.rishabh.curotest.Adapter.BgLogAdapter;
import com.example.rishabh.curotest.DBO.BgDBO;
import com.example.rishabh.curotest.DBO.TimeSlotDBO;
import com.example.rishabh.curotest.Model.BgLoggingSettingInfo;
import com.example.rishabh.curotest.Model.BgSchedule;
import com.example.rishabh.curotest.R;
import com.example.rishabh.curotest.Utils.AppDateHelper;
import io.realm.Realm;
import io.realm.RealmResults;
import java.util.ArrayList;

public class BloodSugarLoggingSettings extends AppCompatActivity {
  @Bind(R.id.bg_log_rv) RecyclerView recyclerView;
  LinearLayoutManager linearLayoutManager;
  ArrayList<BgLoggingSettingInfo> arrayList = new ArrayList<>();
  BgLogAdapter bgLogAdapter;
  Realm realm;
  ArrayList<Integer> timeSlotIdList = new ArrayList<>();
  long todayDateInMillis;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_blood_sugar_logging_settings);
    ButterKnife.bind(this);
    linearLayoutManager = new LinearLayoutManager(this);
    realm = Realm.getDefaultInstance();
    setData();
    todayDateInMillis = AppDateHelper.getInstance().getDateInMillisWithSwipeCount(0);
  }

  private void setData() {
    arrayList = BgDBO.setLoggingTimeSlot(realm);
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
    timeSlotIdList = bgLogAdapter.getTimeSlotIdList();
    BgDBO.saveBgScheduleFromLogging(timeSlotIdList, todayDateInMillis);
    if (realm == null) {
      realm = Realm.getDefaultInstance();
    }
    RealmResults<BgSchedule> realmResults = realm.where(BgSchedule.class).findAll();
    realm.close();
    super.onBackPressed();

  }
}
