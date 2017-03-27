package com.example.rishabh.curotest.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.rishabh.curotest.DBO.LogStreakDBO;
import com.example.rishabh.curotest.Helpers.SetTimeSlots;
import com.example.rishabh.curotest.Model.LogStreakPerDay;
import com.example.rishabh.curotest.Model.TimeSlots;
import com.example.rishabh.curotest.R;
import io.realm.Realm;
import io.realm.RealmResults;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
  private static final String BUNDLE_RECYCLER_LAYOUT = "classname.recycler.layout";
  Dialog mDialog;
  private RelativeLayout coachMarksLayout1, doctorAdviceLayout;
  LinearLayout mealLayout, bgLayout, vitalsLayout, medicationLayout, activityLayout, goalsLayout;
  TextView mealText, vitalsText, activityText, medicationText, quickTipText, bgText,
      docNameTextView, textGoals;
  RelativeLayout oneArrow, twoArrow, threeArrow, fourArrow, fiveArrow, sixArrow, sevenArrow;
  Toolbar toolbar;
  private SwipeRefreshLayout swipeRefreshLayout;
  Context mContext;
  ImageView img1, img2, img3, img4, img5, img6, img7;
  Realm realm;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    realm = Realm.getDefaultInstance();
    setUpUI();
    checkTimeSlot();
    setStreakData();
  }

  private void setUpUI() {

    mDialog = new Dialog(mContext, R.style.ProgressBarTheme);
    toolbar = (Toolbar) findViewById(R.id.toolbar);
    toolbar.setTitle("Lifeincontrol");
    toolbar.setNavigationIcon(R.drawable.menu);

    swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
    swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.goals_ui_red,
        R.color.trends_ui_band_fill_color, R.color.spinner_floatinglabel_color);

    mealLayout = (LinearLayout) findViewById(R.id.meal_layout);
    bgLayout = (LinearLayout) findViewById(R.id.bg_layout);
    vitalsLayout = (LinearLayout) findViewById(R.id.vitals_layout);
    medicationLayout = (LinearLayout) findViewById(R.id.medication_layout);
    activityLayout = (LinearLayout) findViewById(R.id.activity_layout);
    goalsLayout = (LinearLayout) findViewById(R.id.goals_layout);
    textGoals = (TextView) findViewById(R.id.goals_text);
    ;
    coachMarksLayout1 = (RelativeLayout) findViewById(R.id.quick_tip_layout_1);
    doctorAdviceLayout = (RelativeLayout) findViewById(R.id.doctor_advice_layout);
    docNameTextView = (TextView) findViewById(R.id.doctorname);
    mealText = (TextView) findViewById(R.id.meal_text);
    vitalsText = (TextView) findViewById(R.id.vitals_text);
    activityText = (TextView) findViewById(R.id.activity_text);
    bgText = (TextView) findViewById(R.id.bg_text);
    quickTipText = (TextView) findViewById(R.id.quick_tip_text);
    medicationText = (TextView) findViewById(R.id.medication_text);
    oneArrow = (RelativeLayout) findViewById(R.id.one_arrow);
    twoArrow = (RelativeLayout) findViewById(R.id.two_arrow);
    threeArrow = (RelativeLayout) findViewById(R.id.three_arrow);
    fourArrow = (RelativeLayout) findViewById(R.id.four_arrow);
    fiveArrow = (RelativeLayout) findViewById(R.id.five_arrow);
    sixArrow = (RelativeLayout) findViewById(R.id.six_arrow);
    sevenArrow = (RelativeLayout) findViewById(R.id.seven_arrow);
    img1 = (ImageView) findViewById(R.id.image_one);
    img2 = (ImageView) findViewById(R.id.image_two);
    img3 = (ImageView) findViewById(R.id.image_three);
    img4 = (ImageView) findViewById(R.id.image_four);
    img5 = (ImageView) findViewById(R.id.image_five);
    img6 = (ImageView) findViewById(R.id.image_six);
    img7 = (ImageView) findViewById(R.id.image_seven);

    mealLayout.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        //freeLogging(AppConstants.FOOD_TASKTYPE_ID);
      }
    });
    coachMarksLayout1.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        coachMarksLayout1.setVisibility(View.GONE);
      }
    });
    doctorAdviceLayout.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        //Intent intent = new Intent(getActivity(), DoctorAdviceActivity.class);
        //startActivity(intent);
      }
    });
    activityLayout.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        //freeLogging(AppConstants.ACTIVITY_TASKTYPE_ID);
      }
    });
    bgLayout.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        //freeLogging(AppConstants.BG_TASKTYPE_ID);
        Intent intent = new Intent(MainActivity.this, BgLoggingScreen.class);
        startActivity(intent);
      }
    });
    medicationLayout.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        //freeLogging(AppConstants.MEDICATION_TASKTYPE_ID);
      }
    });
    vitalsLayout.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        //freeLogging(AppConstants.VITALS_TASKTYPE_ID);
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

  private void setStreakData() {
    Calendar calendar = Calendar.getInstance();
    int dayOfWeek = (calendar.get(Calendar.DAY_OF_WEEK) * -1) + 1;
    int[] statusArray = new int[7];
    int count = 0;
    long currentMiilis = System.currentTimeMillis();
    currentMiilis = currentMiilis - (86400000 * (calendar.get(Calendar.DAY_OF_WEEK) - 1));
    LogStreakDBO logStreakDBO = new LogStreakDBO();
    for (int i = dayOfWeek; i >= 0; i++) {
      statusArray[count] = logStreakDBO.getLogStreakStatus(currentMiilis, realm);
      currentMiilis = currentMiilis + 86400000;
      count++;
    }
    for (int j = count + 1; j < 6; j++) {
      statusArray[j] = 3;
    }
    for (int k = 0; k < statusArray.length; k++) {
      switch (k) {
        case 0:
          setSunImage(statusArray[k]);
          break;
        case 1:
          setMonImage(statusArray[k]);
          break;
        case 2:
          setTueImage(statusArray[k]);
          break;
        case 3:
          setWedImage(statusArray[k]);
          break;
        case 4:
          setThuImage(statusArray[k]);
          break;
        case 5:
          setFriImage(statusArray[k]);
          break;
        case 6:
          setSatImage(statusArray[k]);
          break;
      }
    }
  }

  private void setSunImage(int status) {
    switch (status) {
      case 0:
        img1.setImageResource(R.drawable.empty_circle);
        break;
      case 1:
        img1.setImageResource(R.drawable.checked_circle);
        break;
      case 2:
        img1.setImageResource(R.drawable.starred_circle);
        break;
      case 3:
        img1.setImageResource(R.drawable.locked_circle);
        break;
    }
  }

  private void setMonImage(int status) {
    switch (status) {
      case 0:
        img2.setImageResource(R.drawable.empty_circle);
        break;
      case 1:
        img2.setImageResource(R.drawable.checked_circle);
        break;
      case 2:
        img2.setImageResource(R.drawable.starred_circle);
        break;
      case 3:
        img2.setImageResource(R.drawable.locked_circle);
        break;
    }
  }

  private void setTueImage(int status) {
    switch (status) {
      case 0:
        img3.setImageResource(R.drawable.empty_circle);
        break;
      case 1:
        img3.setImageResource(R.drawable.checked_circle);
        break;
      case 2:
        img3.setImageResource(R.drawable.starred_circle);
        break;
      case 3:
        img3.setImageResource(R.drawable.locked_circle);
        break;
    }
  }

  private void setWedImage(int status) {
    switch (status) {
      case 0:
        img4.setImageResource(R.drawable.empty_circle);
        break;
      case 1:
        img4.setImageResource(R.drawable.checked_circle);
        break;
      case 2:
        img4.setImageResource(R.drawable.starred_circle);
        break;
      case 3:
        img4.setImageResource(R.drawable.locked_circle);
        break;
    }
  }

  private void setThuImage(int status) {
    switch (status) {
      case 0:
        img5.setImageResource(R.drawable.empty_circle);
        break;
      case 1:
        img5.setImageResource(R.drawable.checked_circle);
        break;
      case 2:
        img5.setImageResource(R.drawable.starred_circle);
        break;
      case 3:
        img5.setImageResource(R.drawable.locked_circle);
        break;
    }
  }

  private void setFriImage(int status) {
    switch (status) {
      case 0:
        img6.setImageResource(R.drawable.empty_circle);
        break;
      case 1:
        img6.setImageResource(R.drawable.checked_circle);
        break;
      case 2:
        img6.setImageResource(R.drawable.starred_circle);
        break;
      case 3:
        img6.setImageResource(R.drawable.locked_circle);
        break;
    }
  }

  private void setSatImage(int status) {
    switch (status) {
      case 0:
        img7.setImageResource(R.drawable.empty_circle);
        break;
      case 1:
        img7.setImageResource(R.drawable.checked_circle);
        break;
      case 2:
        img7.setImageResource(R.drawable.starred_circle);
        break;
      case 3:
        img7.setImageResource(R.drawable.locked_circle);
        break;
    }
  }
}
