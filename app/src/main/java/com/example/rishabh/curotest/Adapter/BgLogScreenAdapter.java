package com.example.rishabh.curotest.Adapter;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.example.rishabh.curotest.activities.BgLoggingScreen;
import com.example.rishabh.curotest.DBO.BgDBO;
import com.example.rishabh.curotest.Interfaces.LogScheduleCallback;
import com.example.rishabh.curotest.Model.BgLogScreenInfo;
import com.example.rishabh.curotest.Network.ConnectionDetector;
import com.example.rishabh.curotest.R;
import com.example.rishabh.curotest.Utils.AppDateHelper;
import com.example.rishabh.curotest.Utils.Constants;
import java.util.ArrayList;

/**
 * Created by rishabh on 27/03/2017.
 */

public class BgLogScreenAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
  Context context;
  ArrayList<BgLogScreenInfo> arrayList = new ArrayList<>();
  int swipeCount;
  ConnectionDetector connectionDetector;

  public BgLogScreenAdapter(ArrayList<BgLogScreenInfo> arrayList, Context context) {
    this.arrayList = arrayList;
    this.context = context;
    connectionDetector = new ConnectionDetector(context);
  }

  public void notifyData(ArrayList<BgLogScreenInfo> arrayList) {
    this.arrayList = arrayList;
    notifyDataSetChanged();
  }

  public void swipeCount(int swipeCount) {
    this.swipeCount = swipeCount;
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.bg_schedule_screen, parent, false);
    ViewHolder viewHolder = new ViewHolder(view);
    return viewHolder;
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    final ViewHolder viewHolder = (ViewHolder) holder;
    final BgLogScreenInfo bgLogScreenInfo = arrayList.get(position);
    viewHolder.mainTitle.setText(bgLogScreenInfo.getMainTitle());
    viewHolder.subTitle.setText(bgLogScreenInfo.getSubTitle());
    if (bgLogScreenInfo.getValue() == 0) {
      viewHolder.log.setText("Log Value");
    } else {
      viewHolder.log.setText("" + bgLogScreenInfo.getValue());
    }
    viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (viewHolder.childLayout.getVisibility() == View.VISIBLE) {
          viewHolder.childLayout.setVisibility(View.GONE);
          viewHolder.log.setVisibility(View.VISIBLE);
        } else {
          viewHolder.childLayout.setVisibility(View.VISIBLE);
          viewHolder.log.setVisibility(View.GONE);
        }
      }
    });

    viewHolder.time.setText(bgLogScreenInfo.getLogTime());

    viewHolder.time.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        String[] minHour = viewHolder.time.getText().toString().split(":");
        int min = Integer.parseInt(minHour[1]);
        int hour = Integer.parseInt(minHour[0]);
        setTimer(viewHolder.time, hour, min);
      }
    });
    viewHolder.btnCancel.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        viewHolder.childLayout.setVisibility(View.GONE);
        viewHolder.log.setVisibility(View.VISIBLE);
      }
    });
    viewHolder.btnDone.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (!viewHolder.value.getText().toString().equalsIgnoreCase("")
            && !viewHolder.value.getText().toString().equalsIgnoreCase("0")) {
          viewHolder.childLayout.setVisibility(View.GONE);
          viewHolder.log.setVisibility(View.VISIBLE);
          viewHolder.log.setText(viewHolder.value.getText().toString());

          BgDBO.saveBgLog(Integer.parseInt(viewHolder.value.getText().toString()),
              bgLogScreenInfo.getDate(), bgLogScreenInfo.getTimeSlotId(),
              getDateTimeValue(viewHolder.time), getLoggedTimeValue(viewHolder.time),
              connectionDetector.isNetworkAvailable(), new LogScheduleCallback() {
                @Override public void onSuccess(boolean check) {
                  if (check) {
                    ((BgLoggingScreen) context).checkUnSyncData();
                    ((BgLoggingScreen) context).getBgGraphData();
                  }else {
                    ((BgLoggingScreen) context).setBloodGlucoseData();
                  }

                }
              }, swipeCount);
        } else {
          Toast.makeText(context, "Please enter valid value", Toast.LENGTH_SHORT).show();
        }
      }
    });
    viewHolder.parentLayout.setOnLongClickListener(new View.OnLongClickListener() {
      @Override public boolean onLongClick(View v) {
        if (!viewHolder.log.getText().toString().equalsIgnoreCase("log value")) {
          new AlertDialog.Builder(context).setTitle("Delete entry")
              .setMessage("Are you sure you want to delete this entry?")
              .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                  // continue with delete

                  BgDBO.deleteBgLog(bgLogScreenInfo.getTimeSlotId(), bgLogScreenInfo.getDate(),
                      context, new LogScheduleCallback() {
                        @Override public void onSuccess(boolean check) {
                          notifyData(BgDBO.getBgLogScreenListByDate(AppDateHelper.getInstance()
                              .getDateInMillisWithSwipeCount(swipeCount)));
                          ((BgLoggingScreen) context).getBgGraphData();
                        }
                      });
                }
              })
              .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                  // do nothing
                }
              })
              .setIcon(android.R.drawable.ic_dialog_alert)
              .show();
        }
        return false;
      }
    });
  }

  @Override public int getItemCount() {
    return arrayList.size();
  }

  class ViewHolder extends RecyclerView.ViewHolder {
    public TextView mainTitle, noteText;
    public TextView subTitle;
    public ImageView arrow, stethoscope;
    public int position;
    public TextView log;
    public View lineView;
    public CardView mainLayout;
    @Bind(R.id.value) EditText value;
    @Bind(R.id.time) EditText time;
    @Bind(R.id.btn_cancel) TextView btnCancel;
    @Bind(R.id.btn_done) TextView btnDone;
    @Bind(R.id.unit) TextView unit;
    @Bind(R.id.parent_layout) RelativeLayout parentLayout;
    @Bind(R.id.child_layout) LinearLayout childLayout;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      mainTitle = (TextView) itemView.findViewById(R.id.main_title);
      mainLayout = (CardView) itemView.findViewById(R.id.main_layout);
      subTitle = (TextView) itemView.findViewById(R.id.sub_title);
      //    arrow = (ImageView) itemView.findViewById(R.id.arrow);
      stethoscope = (ImageView) itemView.findViewById(R.id.stethoscope);
      noteText = (TextView) itemView.findViewById(R.id.note_text);
      log = (TextView) itemView.findViewById(R.id.log);
      lineView = (View) itemView.findViewById(R.id.line_view);
    }
  }

  private void setTimer(final EditText editText, int hour, int min) {
    TimePickerDialog mTimePicker =
        new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
          @Override
          public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
            String minuteFormate;
            if (selectedMinute < 10) {
              minuteFormate = String.format("%02d", selectedMinute);
            } else {
              minuteFormate = String.valueOf(selectedMinute);
            }
            int notificationMin = Integer.parseInt(minuteFormate);
            int notificationHour = selectedHour;
            editText.setText("" + notificationHour + ":" + minuteFormate + ":" + "00");
          }
        }, hour, min, true);//Yes 24 hour time
    mTimePicker.show();
  }

  private String getDateTimeValue(EditText editText) {
    String dateTime =
        AppDateHelper.getInstance().getDateWithWeekDays(Constants.DATEFORMAT, swipeCount)
            + " "
            + editText.getText().toString()
            + " +0530";
    return dateTime;
  }

  private String getLoggedTimeValue(EditText editText) {
    String loggedTime = AppDateHelper.getInstance().getDateWithWeekDays(Constants.DATEFORMAT, 0)
        + " "
        + editText.getText().toString()
        + " +0530";
    return loggedTime;
  }

  public ArrayList<BgLogScreenInfo> getBgLoggingScreen() {
    return arrayList;
  }
}
