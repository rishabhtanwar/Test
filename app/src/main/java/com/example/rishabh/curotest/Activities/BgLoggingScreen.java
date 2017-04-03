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
import butterknife.ButterKnife;
import com.example.rishabh.curotest.Adapter.BgLogScreenAdapter;
import com.example.rishabh.curotest.DBO.BgDBO;
import com.example.rishabh.curotest.Helpers.AppSettings;
import com.example.rishabh.curotest.Interfaces.LogScheduleCallback;
import com.example.rishabh.curotest.Model.BgLogScreenInfo;
import com.example.rishabh.curotest.R;
import com.example.rishabh.curotest.SyncDataWithApi.SyncBgLogging;
import com.example.rishabh.curotest.Utils.AppDateHelper;
import com.example.rishabh.curotest.Utils.Constants;
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
  @Bind(R.id.left_arrow) ImageView leftArrow;
  @Bind(R.id.right_arrow) ImageView rightArrow;
  double conversionFactorBG;
  private Boolean isFabOpen = false;
  @Bind(R.id.bg_rv) RecyclerView recyclerView;
  private Animation fab_open, fab_close, rotate_forward, rotate_backward;
  Context mContext;
  LinearLayoutManager linearLayoutManager;
  ArrayList<BgLogScreenInfo> arrayList = new ArrayList<>();
  int swipeCount = 0;
  BgLogScreenAdapter bgLogScreenAdapter;
  String indicatordate;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_bg_logging_screen);
    ButterKnife.bind(this);
    mContext = this;
    setUpAnimation();
    linearLayoutManager = new LinearLayoutManager(this);
    settings_fab_button.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        // Click action
        Intent intent = new Intent(mContext, BloodSugarLoggingSettings.class);
        startActivity(intent);
        closeFAB();
      }
    });
    mClickBlocker.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        closeFAB();
      }
    });
    indicatordate = AppDateHelper.getInstance()
        .getDateWithWeekDays(Constants.DATEFORMAT_LOGGING_SCREEN, swipeCount);
    mDateIndicator.setText("Today " + indicatordate);
    bgLogScreenAdapter = new BgLogScreenAdapter(arrayList, BgLoggingScreen.this);

    fab_button.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        // Click action
        animateFAB();
      }
    });
    leftArrow.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        swipeCount = swipeCount - 1;
        indicatordate = AppDateHelper.getInstance()
            .getDateWithWeekDays(Constants.DATEFORMAT_LOGGING_SCREEN, swipeCount);
        mDateIndicator.setText(indicatordate);
        arrayList = BgDBO.getBgLogScreenListByDate(
            AppDateHelper.getInstance().getDateInMillisWithSwipeCount(swipeCount));
        bgLogScreenAdapter.notifyDataSetChanged();
      }
    });

    rightArrow.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (swipeCount < 0) {
          swipeCount = swipeCount + 1;
          if (swipeCount == 0) {
            indicatordate = AppDateHelper.getInstance()
                .getDateWithWeekDays(Constants.DATEFORMAT_LOGGING_SCREEN, swipeCount);
            mDateIndicator.setText("Today " + indicatordate);
          } else {
            indicatordate = AppDateHelper.getInstance()
                .getDateWithWeekDays(Constants.DATEFORMAT_LOGGING_SCREEN, swipeCount);
            mDateIndicator.setText(indicatordate);
          }
          arrayList = BgDBO.getBgLogScreenListByDate(
              AppDateHelper.getInstance().getDateInMillisWithSwipeCount(swipeCount));
          bgLogScreenAdapter.notifyDataSetChanged();
        }
      }
    });
  }

  @Override protected void onResume() {
    swipeCount = 0;
    indicatordate = AppDateHelper.getInstance()
        .getDateWithWeekDays(Constants.DATEFORMAT_LOGGING_SCREEN, swipeCount);
    mDateIndicator.setText("Today " + indicatordate);
    if (AppSettings.getBgApiStatus()) {
      AppSettings.setBgApiStatus(false);
      SyncBgLogging.getBgSchedule(
          AppDateHelper.getInstance().getDateWithWeekDays(Constants.DATEFORMAT, swipeCount),
          AppDateHelper.getInstance().getDateWithWeekDays(Constants.DATEFORMAT, swipeCount - 30),
          new LogScheduleCallback() {
            @Override public void onSuccess(boolean check) {
              setAdapter();
            }
          });
    } else {
      setAdapter();
    }

    super.onResume();
  }

  private void setAdapter() {
    arrayList = BgDBO.getBgLogScreenListByDate(
        AppDateHelper.getInstance().getDateInMillisWithSwipeCount(swipeCount));
    linearLayoutManager = new LinearLayoutManager(this);
    recyclerView.setLayoutManager(linearLayoutManager);
    bgLogScreenAdapter = new BgLogScreenAdapter(arrayList, BgLoggingScreen.this);
    recyclerView.setAdapter(bgLogScreenAdapter);
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

