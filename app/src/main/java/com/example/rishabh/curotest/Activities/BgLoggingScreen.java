package com.example.rishabh.curotest.activities;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.example.rishabh.curotest.Adapter.BgLogScreenAdapter;
import com.example.rishabh.curotest.DBO.BgDBO;
import com.example.rishabh.curotest.Model.BgLogs;
import com.example.rishabh.curotest.Network.ConnectionDetector;
import com.example.rishabh.curotest.helpers.AppSettings;
import com.example.rishabh.curotest.Interfaces.LogScheduleCallback;
import com.example.rishabh.curotest.Model.BgLogScreenInfo;
import com.example.rishabh.curotest.R;
import com.example.rishabh.curotest.SyncDataWithApi.SyncBgLogging;
import com.example.rishabh.curotest.Utils.AppDateHelper;
import com.example.rishabh.curotest.Utils.Constants;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;
import io.realm.Realm;
import io.realm.RealmResults;
import java.util.ArrayList;

public class BgLoggingScreen extends AppCompatActivity {
  //@Bind(R.id.right_arrow) ImageView mRightArrow;
  @Bind(R.id.date_indicator) TextView mDateIndicator;
  @Bind(R.id.glucose_quick_log) TextView quickLog;
  @Bind(R.id.glucose_setup_log) TextView logSetup;
  @Bind(R.id.logs_floating_layout) LinearLayout logs_fab_layout;
  @Bind(R.id.settings_floating_layout) LinearLayout settings_fab_layout;
  @Bind(R.id.fab_button) FloatingActionButton fab_button;
  @Bind(R.id.settings_floating_button) FloatingActionButton settings_fab_button;
  @Bind(R.id.logs_floating_button) FloatingActionButton logs_fab_button;
  @Bind(R.id.click_blocker) View mClickBlocker;
  @Bind(R.id.layout_left_arrow) RelativeLayout leftArrow;
  @Bind(R.id.layout_right_arrow) RelativeLayout rightArrow;
  double conversionFactorBG = 1;
  private Boolean isFabOpen = false;
  @Bind(R.id.bg_rv) RecyclerView recyclerView;
  private Animation fab_open, fab_close, rotate_forward, rotate_backward;
  Context mContext;
  LinearLayoutManager linearLayoutManager;
  ArrayList<BgLogScreenInfo> arrayList = new ArrayList<>();
  int swipeCount = 0;
  BgLogScreenAdapter bgLogScreenAdapter;
  String indicatordate;
  //graph id's
  @Bind(R.id.main_layout) RelativeLayout mMainLayout;
  @Bind(R.id.title) TextView mTitle;
  @Bind(R.id.date) TextView mDate;
  @Bind(R.id.value) TextView mValue;
  @Bind(R.id.text_average_value) TextView mTextAverageValue;
  @Bind(R.id.line_chart) LineChart mLineChart;
  @Bind(R.id.no_data_layout) LinearLayout mNoDataLayout;
  @Bind(R.id.no_reading_text) TextView mNoReadingText;
  @Bind(R.id.record_now_image) ImageView mRecordNowImage;
  @Bind(R.id.no_internet_connection) TextView noIntenet;
  @Bind(R.id.bg_screen_coordinator) CoordinatorLayout coordinatorLayout;
  @Bind(R.id.bg_screen_pg) ProgressBar progressBar;
  @Bind(R.id.nested_scroll) NestedScrollView nestedScrollView;
  long startDateLong, endDateLong;
  LineData lineData;
  ArrayList<Integer> idArray=new ArrayList<>();
  ConnectionDetector connectionDetector;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_bg_logging_screen);
    ButterKnife.bind(this);
    mContext = this;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      getWindow().setStatusBarColor(Color.parseColor("#3595B9"));
    }
    setUpAnimation();
    linearLayoutManager = new LinearLayoutManager(this);
    settings_fab_button.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        // Click action
        idArray.clear();
        ArrayList<BgLogScreenInfo> arrayList=bgLogScreenAdapter.getBgLoggingScreen();
        for (int i=0; i<arrayList.size(); i++){
          idArray.add(arrayList.get(i).getTimeSlotId());
        }
        Intent intent = new Intent(mContext, BloodSugarLoggingSettings.class);
        intent.putIntegerArrayListExtra("id_array",idArray);
        startActivity(intent);
        closeFAB();
      }
    });
    connectionDetector = new ConnectionDetector(this);
    mClickBlocker.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        closeFAB();
      }
    });
    indicatordate = AppDateHelper.getInstance()
        .getDateWithWeekDays(Constants.DATEFORMAT_LOGGING_SCREEN, swipeCount);
    mDateIndicator.setText("Today " + indicatordate);
    bgLogScreenAdapter = new BgLogScreenAdapter(arrayList, BgLoggingScreen.this);
    bgLogScreenAdapter.swipeCount(swipeCount);
    fab_button.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        // Click action
        animateFAB();
      }
    });
    recyclerView.setNestedScrollingEnabled(false);
    startDateLong = AppDateHelper.getInstance().getDateInMillisWithSwipeCount(swipeCount - 6);
    endDateLong = AppDateHelper.getInstance().getDateInMillisWithSwipeCount(swipeCount);
    setBloodGlucoseData();
    leftArrow.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        fab_button.setVisibility(View.GONE);
        swipeCount = swipeCount - 1;
        indicatordate = AppDateHelper.getInstance()
            .getDateWithWeekDays(Constants.DATEFORMAT_LOGGING_SCREEN, swipeCount);
        mDateIndicator.setText(indicatordate);
        bgLogScreenAdapter.swipeCount(swipeCount);
        startDateLong = AppDateHelper.getInstance().getDateInMillisWithSwipeCount(swipeCount - 6);
        endDateLong = AppDateHelper.getInstance().getDateInMillisWithSwipeCount(swipeCount);
        setBloodGlucoseData();
        if (connectionDetector.isNetworkAvailable()) {
          //noIntenet.setVisibility(View.GONE);
          getBgLogValueData();
          if (swipeCount % 7 == 0 && swipeCount < 0) {
            getBgGraphData();
          }
        } else {
          //noIntenet.setVisibility(View.VISIBLE);
          arrayList = BgDBO.getBgLogScreenListByDate(
              AppDateHelper.getInstance().getDateInMillisWithSwipeCount(swipeCount));
          if (arrayList.size() == 0) {
            recyclerView.setVisibility(View.GONE);
          } else {
            recyclerView.setVisibility(View.VISIBLE);
            bgLogScreenAdapter.notifyData(arrayList);
          }
        }
      }
    });

    rightArrow.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (swipeCount < 0) {
          swipeCount = swipeCount + 1;
          if (swipeCount == 0) {
            fab_button.setVisibility(View.VISIBLE);
            indicatordate = AppDateHelper.getInstance()
                .getDateWithWeekDays(Constants.DATEFORMAT_LOGGING_SCREEN, swipeCount);
            mDateIndicator.setText("Today " + indicatordate);
          } else {
            indicatordate = AppDateHelper.getInstance()
                .getDateWithWeekDays(Constants.DATEFORMAT_LOGGING_SCREEN, swipeCount);
            mDateIndicator.setText(indicatordate);
          }
          startDateLong = AppDateHelper.getInstance().getDateInMillisWithSwipeCount(swipeCount - 6);
          endDateLong = AppDateHelper.getInstance().getDateInMillisWithSwipeCount(swipeCount);
          bgLogScreenAdapter.swipeCount(swipeCount);
          setBloodGlucoseData();
          if (connectionDetector.isNetworkAvailable()) {
            //noIntenet.setVisibility(View.GONE);
            getBgLogValueData();
            if (swipeCount % 7 == 0 && swipeCount < 0) {
              getBgGraphData();
            }
          } else {
            //noIntenet.setVisibility(View.VISIBLE);
            arrayList = BgDBO.getBgLogScreenListByDate(
                AppDateHelper.getInstance().getDateInMillisWithSwipeCount(swipeCount));
            if (arrayList.size() == 0) {
              recyclerView.setVisibility(View.GONE);
            } else {
              recyclerView.setVisibility(View.VISIBLE);
              bgLogScreenAdapter.notifyData(arrayList);
            }
          }
        }
      }
    });
  }

  @Override protected void onResume() {
    swipeCount = 0;
    indicatordate = AppDateHelper.getInstance()
        .getDateWithWeekDays(Constants.DATEFORMAT_LOGGING_SCREEN, swipeCount);
    mDateIndicator.setText("Today " + indicatordate);
    setAdapter();
    if (AppSettings.getBgApiStatus() && connectionDetector.isNetworkAvailable()) {
      //noIntenet.setVisibility(View.GONE);
      AppSettings.setBgApiStatus(false);
      getBgScheduleData();
      getBgLogValueData();
      getBgGraphData();
    } else {
      //noIntenet.setVisibility(View.VISIBLE);
      setAdapter();
    }

    super.onResume();
  }

  private void getBgScheduleData() {
    SyncBgLogging.getBgSchedule(
        AppDateHelper.getInstance().getDateWithWeekDays(Constants.DATEFORMAT, swipeCount - 30),
        AppDateHelper.getInstance().getDateWithWeekDays(Constants.DATEFORMAT, swipeCount),
        new LogScheduleCallback() {
          @Override public void onSuccess(boolean check) {
            setAdapter();
          }
        });
  }

  private void getBgLogValueData() {
    SyncBgLogging.getBgLogValues(
        AppDateHelper.getInstance().getDateWithWeekDays(Constants.DATEFORMAT, swipeCount),
        AppDateHelper.getInstance().getDateWithWeekDays(Constants.DATEFORMAT, swipeCount),
        new LogScheduleCallback() {
          @Override public void onSuccess(boolean check) {
            setAdapter();
          }
        });
  }

  public void getBgGraphData() {
    SyncBgLogging.getBgGraph(
        AppDateHelper.getInstance().getDateWithWeekDays(Constants.DATEFORMAT, swipeCount - 7),
        AppDateHelper.getInstance().getDateWithWeekDays(Constants.DATEFORMAT, swipeCount),
        new LogScheduleCallback() {
          @Override public void onSuccess(boolean check) {
            setBloodGlucoseData();
          }
        });
  }

  private void setAdapter() {
    arrayList = BgDBO.getBgLogScreenListByDate(
        AppDateHelper.getInstance().getDateInMillisWithSwipeCount(swipeCount));
    linearLayoutManager = new LinearLayoutManager(this);
    recyclerView.setLayoutManager(linearLayoutManager);
    bgLogScreenAdapter = new BgLogScreenAdapter(arrayList, BgLoggingScreen.this);
    bgLogScreenAdapter.swipeCount(swipeCount);
    recyclerView.setAdapter(bgLogScreenAdapter);
    if (arrayList.size() > 0) {
      recyclerView.setVisibility(View.VISIBLE);
    } else {
      recyclerView.setVisibility(View.GONE);
    }
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

  public void setBloodGlucoseData() {
    if (connectionDetector.isNetworkAvailable()){
      noIntenet.setVisibility(View.GONE);
    }else {
      noIntenet.setVisibility(View.VISIBLE);
    }
    if (conversionFactorBG == 1) {
      mTitle.setText(R.string.string_blood_glucose_with_unit);
    } else {
      mTitle.setText(R.string.string_blood_glucose_with_unit_mmol);
    }
    mDate.setText(R.string.string_daily_average);
    BgDBO bgDb = new BgDBO();
    lineData = bgDb.getLineDataForBloodSugar(startDateLong, endDateLong, R.color.colorPrimary, true,
        conversionFactorBG, BgLoggingScreen.this);
    if (lineData == null) {
      mLineChart.setVisibility(View.GONE);
      mNoDataLayout.setVisibility(View.VISIBLE);
    } else if (lineData.getYValCount() == 0) {
      mValue.setVisibility(View.GONE);
      mLineChart.setVisibility(View.VISIBLE);
      mNoDataLayout.setVisibility(View.VISIBLE);
      mRecordNowImage.setImageResource(R.drawable.blood_glucose_empty_graph);
      setLineChart(mLineChart, false);
    } else {
      if (bgDb.avgValue != 0) {
        mValue.setVisibility(View.VISIBLE);
        mValue.setText("" + bgDb.avgValue * conversionFactorBG);
      } else {
        mValue.setVisibility(View.GONE);
        mTextAverageValue.setVisibility(View.GONE);
      }
      mLineChart.setVisibility(View.VISIBLE);
      mNoDataLayout.setVisibility(View.GONE);
      setLineChart(mLineChart, false);
    }
  }

  private void setLineChart(LineChart mLineChart, boolean shouldScroll) {
    mLineChart.invalidate();
    mLineChart.setDescription("");
    mLineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
    mLineChart.getAxisRight().setEnabled(false);
    mLineChart.getLegend().setEnabled(false);
    mLineChart.setDrawGridBackground(false);
    mLineChart.setNoDataText("");
    mLineChart.animateXY(200, 200);
    mLineChart.setScaleEnabled(false);
    mLineChart.setDoubleTapToZoomEnabled(false);

    mLineChart.getAxis(YAxis.AxisDependency.LEFT)
        .setAxisLineColor(mContext.getResources().getColor(R.color.colorPrimary));
    mLineChart.getAxis(YAxis.AxisDependency.LEFT).setDrawAxisLine(false);
    mLineChart.getAxis(YAxis.AxisDependency.LEFT).setDrawLabels(false);
    mLineChart.getAxis(YAxis.AxisDependency.LEFT)
        .setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
    mLineChart.getAxisLeft().setDrawGridLines(false);
    mLineChart.getAxisLeft().setGridColor(mContext.getResources().getColor(R.color.colorPrimary));

    mLineChart.getXAxis().setDrawGridLines(false);
    mLineChart.setData(lineData);
  }

  public void checkUnSyncData() {
    Realm realm = Realm.getDefaultInstance();
    try {
      RealmResults<BgLogs> realmResults =
          realm.where(BgLogs.class).equalTo("isSynced", false).findAll();
      if (realmResults.size() > 0 && connectionDetector.isNetworkAvailable()) {
        final Snackbar snackbar =
            Snackbar.make(coordinatorLayout, "There are some unsync data please sync",
                Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("SYNC", new View.OnClickListener() {
          @Override public void onClick(View view) {
            progressBar.setVisibility(View.VISIBLE);
            SyncBgLogging syncBgLogging = new SyncBgLogging();
            syncBgLogging.setSyncListener(new SyncBgLogging.SyncListener() {
              @Override public void onSucess(boolean check) {
                snackbar.dismiss();
                runOnUiThread(new Runnable() {
                  @Override public void run() {
                    progressBar.setVisibility(View.GONE);
                    nestedScrollView.scrollTo(0, 0);
                    getBgGraphData();
                  }
                });
              }
            });
            Bundle bundle = new Bundle();
            bundle.putString(Constants.SYNC_DATA, Constants.BG_LOG_SYNC);
            ContentResolver.requestSync(AppSettings.getInstance().CreateSyncAccount(),
                Constants.AUTHORITY, bundle);
          }
        });

        snackbar.show();
      }
    } finally {
      realm.close();
    }
  }
}

