package com.example.rishabh.curotest.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import com.example.rishabh.curotest.R;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class BgLoggingScreen extends AppCompatActivity {
  @Bind(R.id.right_arrow) ImageView mRightArrow;
  @Bind(R.id.date_indicator) TextView mDateIndicator;
  @Bind(R.id.glucose_quick_log) TextView quickLog;
  @Bind(R.id.glucose_setup_log) TextView logSetup;
  @Bind(R.id.logs_floating_layout) LinearLayout logs_fab_layout;
  @Bind(R.id.settings_floating_layout) LinearLayout settings_fab_layout;
  @Bind(R.id.fab_button) FloatingActionButton fab_button;
  @Bind(R.id.settings_floating_button) FloatingActionButton settings_fab_button;
  @Bind(R.id.logs_floating_button) FloatingActionButton logs_fab_button;
  @Bind(R.id.click_blocker) View mClickBlocker;
  double conversionFactorBG;
  private Boolean isFabOpen = false;
  @Bind(R.id.bg_rv) RecyclerView recyclerView;
  private Animation fab_open, fab_close, rotate_forward, rotate_backward;
  Context mContext;
  LinearLayoutManager linearLayoutManager;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_bg_logging_screen);
    mContext = this;
    setUpAnimation();
    linearLayoutManager=new LinearLayoutManager(this);
    settings_fab_button.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        // Click action
        Intent intent = new Intent(mContext, BloodSugarLoggingSettings.class);
        startActivity(intent);
      }
    });
    mClickBlocker.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        closeFAB();
      }
    });

    fab_button.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        // Click action
        animateFAB();
      }
    });
  }

  private void setUpAnimation() {
    fab_open = AnimationUtils.loadAnimation(mContext.getApplicationContext(), R.anim.fab_open);
    fab_close = AnimationUtils.loadAnimation(mContext.getApplicationContext(), R.anim.fab_close);
    rotate_forward =
        AnimationUtils.loadAnimation(mContext.getApplicationContext(), R.anim.rotate_forward);
    rotate_backward =
        AnimationUtils.loadAnimation(mContext.getApplicationContext(), R.anim.rotate_backward);
  }

  private void animateFAB() {

    if (isFabOpen) {
      mClickBlocker.setVisibility(View.GONE);
      settings_fab_layout.setVisibility(View.GONE);
      logs_fab_layout.setVisibility(View.GONE);
      fab_button.startAnimation(rotate_backward);
      settings_fab_button.setClickable(false);
      logs_fab_button.setClickable(false);
      isFabOpen = false;
    } else {
      mClickBlocker.setVisibility(View.VISIBLE);
      settings_fab_layout.setVisibility(View.VISIBLE);
      logs_fab_layout.setVisibility(View.VISIBLE);
      fab_button.startAnimation(rotate_forward);
      settings_fab_button.setClickable(true);
      logs_fab_button.setClickable(true);
      isFabOpen = true;
    }
  }

  private void closeFAB() {
    if (isFabOpen) {
      mClickBlocker.setVisibility(View.GONE);
      settings_fab_layout.setVisibility(View.GONE);
      logs_fab_layout.setVisibility(View.GONE);
      fab_button.startAnimation(rotate_backward);
      settings_fab_button.setClickable(false);
      logs_fab_button.setClickable(false);
      isFabOpen = false;
    }
  }
}

