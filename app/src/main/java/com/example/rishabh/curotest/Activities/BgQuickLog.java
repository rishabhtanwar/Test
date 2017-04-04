package com.example.rishabh.curotest.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.example.rishabh.curotest.R;
import java.util.ArrayList;

public class BgQuickLog extends AppCompatActivity {
  @Bind(R.id.custom_value) EditText customValue;
  @Bind(R.id.done) TextView done;
  @Bind(R.id.current_time) TextView currentTime;
  @Bind(R.id.bg_unit) TextView bgUnit;
  @Bind(R.id.spinner) Spinner spinner;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_bg_quick_log);
    ButterKnife.bind(this);
  }

  private void setSpinnerData(ArrayList<String> arrayList) {
    ArrayAdapter arrayAdapter =
        new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, arrayList);
    spinner.setAdapter(arrayAdapter);
  }
}
