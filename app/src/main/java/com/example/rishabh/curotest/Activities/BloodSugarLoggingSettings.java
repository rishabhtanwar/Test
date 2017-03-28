package com.example.rishabh.curotest.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.example.rishabh.curotest.Adapter.BgLogAdapter;
import com.example.rishabh.curotest.DBO.TimeSlotDBO;
import com.example.rishabh.curotest.Model.BgLoggingSettingInfo;
import com.example.rishabh.curotest.R;
import io.realm.Realm;
import java.util.ArrayList;

public class BloodSugarLoggingSettings extends AppCompatActivity {
  @Bind(R.id.bg_log_rv) RecyclerView recyclerView;
  LinearLayoutManager linearLayoutManager;
  ArrayList<BgLoggingSettingInfo> arrayList = new ArrayList<>();
  BgLogAdapter bgLogAdapter;
  Realm realm;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_blood_sugar_logging_settings);
    ButterKnife.bind(this);
    linearLayoutManager = new LinearLayoutManager(this);
    realm = Realm.getDefaultInstance();
    setData();
  }

  private void setData() {
    arrayList = TimeSlotDBO.setLoggingTimeSlot(realm);
    setAdapter();
  }

  private void setAdapter(){
    recyclerView.setLayoutManager(linearLayoutManager);
    bgLogAdapter=new BgLogAdapter(BloodSugarLoggingSettings.this,arrayList);
  }
}
