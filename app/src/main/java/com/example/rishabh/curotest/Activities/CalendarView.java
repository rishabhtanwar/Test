package com.example.rishabh.curotest.activities;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.rishabh.curotest.R;
import com.example.rishabh.curotest.Utils.AppDateHelper;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarView extends LinearLayout {
  // for logging
  private static final String LOGTAG = "Calendar View";

  // how many days to show, defaults to six weeks, 42 days
  private static final int DAYS_COUNT = 35;

  // default date format
  private static final String DATE_FORMAT = "MMM yyyy";
  public static List<Integer> markingList = new ArrayList<>();

  // date format
  private String dateFormat;

  // current displayed month
  private Calendar currentDate = Calendar.getInstance();
  boolean isFirstTime = true, isFirstDate = false;
  private final int releaseMonth = 9, releaseYear = 2016;
  //event handling
  private EventHandler eventHandler = null;
  private PreviousButtonClick previousButtonClick;
  // internal components
  private LinearLayout header;
  private ImageView btnPrev;
  private ImageView btnNext;
  private TextView txtDate, perfectDays, longestStreakDays;
  private GridView grid;
  Context context;
  int swipeCount;
  public static Calendar currentMonthDate = Calendar.getInstance();
  public static int todayInMonth = currentMonthDate.get(Calendar.DAY_OF_MONTH), daysInCurrentMonth =
      currentMonthDate.getActualMaximum(Calendar.DAY_OF_MONTH);
  public static int differenceInDays, skipIndex = 1 - todayInMonth;
  private long longestStreakNoOfDays = 0, perfectNoOfDays = 0;

  // month-season association (northern hemisphere, sorry australia :)
  int[] monthSeason = new int[] { 2, 2, 3, 3, 3, 0, 0, 0, 1, 1, 1, 2 };

  public CalendarView(Context context) {
    super(context);
  }

  public CalendarView(Context context, AttributeSet attrs) {
    super(context, attrs);
    this.context = context;
    initControl(context, attrs);
  }

  public CalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    this.context = context;
    initControl(context, attrs);
  }

  /**
   * Load control xml layout
   */
  private void initControl(Context context, AttributeSet attrs) {
    LayoutInflater inflater =
        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    inflater.inflate(R.layout.control_calendar, this);
    this.context = context;
    loadDateFormat(attrs);
    assignUiElements();
    assignClickHandlers();
  }

  private void loadDateFormat(AttributeSet attrs) {
    TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.CalendarView);

    try {
      // try to load provided date format, and fallback to default otherwise
      dateFormat = ta.getString(R.styleable.CalendarView_dateFormat);
      if (dateFormat == null) dateFormat = DATE_FORMAT;
    } finally {
      ta.recycle();
    }
  }

  private void assignUiElements() {
    // layout is inflated, assign local variables to components
    header = (LinearLayout) findViewById(R.id.calendar_header);
    btnPrev = (ImageView) findViewById(R.id.calendar_prev_button);
    btnNext = (ImageView) findViewById(R.id.calendar_next_button);
    txtDate = (TextView) findViewById(R.id.calendar_date_display);
    longestStreakDays = (TextView) findViewById(R.id.longest_streak_days);
    perfectDays = (TextView) findViewById(R.id.perfect_days);
    grid = (GridView) findViewById(R.id.calendar_grid);
  }

  private void assignClickHandlers() {
    // add one month and refresh UI
    btnNext.setOnClickListener(new OnClickListener() {
      @Override public void onClick(View v) {
        //isFirstDate = false;
        //daysInCurrentMonth = currentDate.getActualMaximum(Calendar.DAY_OF_MONTH);
        //currentDate.add(Calendar.MONTH, 1);
        //int currentMonth = currentDate.get(Calendar.MONTH), currentYear =
        //    currentDate.get(Calendar.YEAR);
        //int latestMonth = currentMonthDate.get(Calendar.MONTH), latestYear =
        //    currentMonthDate.get(Calendar.YEAR);
        //if (latestYear > currentYear || (latestYear == currentYear
        //    && latestMonth >= currentMonth)) {
        //  int daysInMonth = currentDate.getActualMaximum(Calendar.DAY_OF_MONTH);
        //  skipIndex += daysInCurrentMonth;
        //  //markingList.clear();
        //  int futureIndex = skipIndex + daysInMonth - 1;
        //  //markingList = TaskDBO.getStreakListBtwRange(skipIndex, futureIndex > 0 ? 0 : futureIndex);
        //  //for (int i = markingList.size(); i < daysInMonth; i++) {
        //  //  markingList.add(-1);
        //  //}
        //  updateCalendar(markingList);
        //} else {
        //  isFirstDate = true;
        //  currentDate.add(Calendar.MONTH, -1);
        //  btnNext.setImageResource((R.drawable.chevron_right));
        //}
        if (swipeCount < 0) {
          previousButtonClick.swipeCount(swipeCount++);
        }
      }
    });

    // subtract one month and refresh UI
    btnPrev.setOnClickListener(new OnClickListener() {
      @Override public void onClick(View v) {
        //int latestMonth = currentDate.get(Calendar.MONTH), latestYear =
        //    currentDate.get(Calendar.YEAR);
        //if (releaseYear < latestYear || (releaseYear == latestYear && releaseMonth < latestMonth)) {
        //  FetchMonthDataTask task = new FetchMonthDataTask();
        //  task.execute(new String[] { "" });
        //}
        if (swipeCount > -3 && swipeCount <= 0) {
          previousButtonClick.swipeCount(swipeCount-1);
        }
      }
    });

    // long-pressing a day
    grid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

      @Override
      public boolean onItemLongClick(AdapterView<?> view, View cell, int position, long id) {
        // handle long-press
        if (eventHandler == null) return false;

        eventHandler.onDayLongPress((Date) view.getItemAtPosition(position));
        return true;
      }
    });
  }

  private class FetchMonthDataTask extends AsyncTask<String, Void, String> {

    @Override protected void onPreExecute() {
      //showProgress("Please wait...");
    }

    @Override protected String doInBackground(String... urls) {
      // we use the OkHttp library from https://github.com/square/okhttp
      isFirstDate = false;
      currentDate.add(Calendar.MONTH, -1);
      daysInCurrentMonth = currentDate.getActualMaximum(Calendar.DAY_OF_MONTH);
      skipIndex -= daysInCurrentMonth;
      //markingList.clear();
      int startDate = skipIndex, endDate = skipIndex + daysInCurrentMonth - 1;
      //markingList = TaskDBO.getStreakListBtwRange(startDate, endDate);
      return "";
    }

    @Override protected void onPostExecute(String result) {
      //updateCalendar(markingList);
      //hideProgress();
    }
  }

  public void updateCalendar(List<Integer> events, int swipeCount) {
    ArrayList<Date> cells = new ArrayList<>();
    this.swipeCount = swipeCount;
    Calendar calendar = (Calendar) currentDate.clone();
    //perfectNoOfDays = TaskDBO.perfectStreakDays();
    //longestStreakNoOfDays = TaskDBO.longestStreakValue();

    calendar.set(Calendar.DAY_OF_MONTH, 1);
    int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 1;
    calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);
    while (cells.size() < DAYS_COUNT) {
      cells.add(calendar.getTime());
      calendar.add(Calendar.DAY_OF_MONTH, 1);
    }

    grid.setAdapter(new CalendarAdapter(getContext(), cells, events));
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
    txtDate.setText(AppDateHelper.monthYearNameBySwipeIndex(swipeCount));
    perfectDays.setText(perfectNoOfDays + "");
    longestStreakDays.setText(longestStreakNoOfDays <= 1 ? longestStreakNoOfDays + " day"
        : longestStreakNoOfDays + " days");

    int tempMonth = currentDate.get(Calendar.MONTH), tempYear = currentDate.get(Calendar.YEAR);
    int latestMonth = currentMonthDate.get(Calendar.MONTH), latestYear =
        currentMonthDate.get(Calendar.YEAR);
    if ((releaseYear == latestYear && releaseMonth < tempMonth) || tempYear > releaseYear || (
        tempYear == releaseYear
            && tempMonth > releaseMonth)) {
      btnPrev.setImageResource((R.drawable.chevron_left));
    } else {
      btnPrev.setImageResource((R.drawable.chevron_left));
    }
    if ((tempYear < latestYear) || (tempMonth < latestMonth && tempYear == latestYear)) {
      btnNext.setImageResource((R.drawable.chevron_right));
    } else {
      btnNext.setImageResource((R.drawable.chevron_right));
    }
  }

  private class CalendarAdapter extends ArrayAdapter<Date> {
    private List<Integer> eventDays;
    ImageView textDateImage;
    private LayoutInflater inflater;
    int day, month, year, numDays;

    public CalendarAdapter(Context context, ArrayList<Date> days, List<Integer> eventDays) {
      super(context, R.layout.control_calendar_day, days);
      this.eventDays = eventDays;
      inflater = LayoutInflater.from(context);
      Date date = getItem(0);
      day = date.getDate();
      month = date.getMonth();
      year = date.getYear();
      Calendar calendar = Calendar.getInstance();
      calendar.set(Calendar.YEAR, year);
      calendar.set(Calendar.MONTH, month);
      numDays = calendar.getActualMaximum(Calendar.DATE);
    }

    @Override public View getView(int position, View view, ViewGroup parent) {

      if (!isFirstDate && day > 1) {
        differenceInDays = numDays - day + 1;
      } else {
        differenceInDays = 0;
        isFirstDate = true;
      }

      if (view == null) view = inflater.inflate(R.layout.control_calendar_day, parent, false);
      textDateImage = (ImageView) view.findViewById(R.id.text);
      if (eventDays != null
          && eventDays.size() > position - differenceInDays
          && (position - differenceInDays) >= 0) {
        int checkValue = eventDays.get(position - differenceInDays);
        switch (checkValue) {
          case 0:
            textDateImage.setImageResource((R.drawable.starred_circle));
            break;
          case 1:
            textDateImage.setImageResource((R.drawable.tick_diary));
            break;
          case 2:
            textDateImage.setImageResource(R.drawable.empty_circle);
            break;
          default:
            break;
        }
      } else {
        textDateImage.setVisibility(INVISIBLE);
      }
      return view;
    }
  }

  public void prevButtonClick(PreviousButtonClick previousButtonClick) {
    this.previousButtonClick = previousButtonClick;
  }

  public interface PreviousButtonClick {
    void swipeCount(int swipeIndex);
  }

  /**
   * Assign event handler to be passed needed events
   */
  public void setEventHandler(EventHandler eventHandler) {
    this.eventHandler = eventHandler;
  }

  /**
   * This interface defines what events to be reported to
   * the outside world
   */
  public interface EventHandler {
    void onDayLongPress(Date date);
  }
}

