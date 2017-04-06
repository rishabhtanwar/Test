package com.example.rishabh.curotest.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.example.rishabh.curotest.DBO.BgDBO;
import com.example.rishabh.curotest.Model.BgLoggingSettingInfo;
import com.example.rishabh.curotest.Model.BgSchedule;
import com.example.rishabh.curotest.R;
import com.example.rishabh.curotest.Utils.AppDateHelper;
import io.realm.Realm;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by rishabh on 27/03/2017.
 */

public class BgLogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
  Context context;
  ArrayList<BgLoggingSettingInfo> arrayList = new ArrayList<>();
  ArrayList<Integer> timeSlotIdListTicked = new ArrayList<>();
  ArrayList<Integer> timeSlotIdListUnTicked = new ArrayList<>();
  HashMap<Integer, String> hashMap = new HashMap<>();

  public BgLogAdapter(Context context, ArrayList<BgLoggingSettingInfo> arrayList) {
    this.context = context;
    this.arrayList = arrayList;
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(context).inflate(R.layout.bg_logging_setting_layout, parent, false);
    ViewHolder viewHolder = new ViewHolder(view);
    return viewHolder;
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
    final BgLoggingSettingInfo bgLoggingSettingInfo = arrayList.get(position);
    ViewHolder viewHolder = (ViewHolder) holder;
    if (bgLoggingSettingInfo.isCheck()) {
      viewHolder.checkBox.setChecked(true);
      if (!hashMap.containsKey(bgLoggingSettingInfo.getSlotid())) {
        //timeSlotIdListTicked.add(bgLoggingSettingInfo.getSlotid());
        hashMap.put(bgLoggingSettingInfo.getSlotid(), "ticked");
      }
    } else {
      viewHolder.checkBox.setChecked(false);
    }
    viewHolder.mainTitle.setText(bgLoggingSettingInfo.getMainTitle());
    viewHolder.subTitle.setText(bgLoggingSettingInfo.getSubTitle());
    viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
          arrayList.get(position).setCheck(true);
          //if (!timeSlotIdList.contains(bgLoggingSettingInfo.getSlotid())) {
          //  timeSlotIdList.add(bgLoggingSettingInfo.getSlotid());
          //}
          hashMap.put(bgLoggingSettingInfo.getSlotid(), "ticked");
          //BgDBO.saveBgSchedule(bgLoggingSettingInfo.getSlotid(),
          //    AppDateHelper.getInstance().getDateInMillisWithSwipeCount(0));
        } else {
          arrayList.get(position).setCheck(false);
          //if (checkBgValueInDb(bgLoggingSettingInfo.getSlotid())){
          hashMap.put(bgLoggingSettingInfo.getSlotid(), "unticked");
          //}
          //timeSlotIdList.remove(removePosition(bgLoggingSettingInfo.getSlotid()));
          //BgDBO.deleteBgSchedule(bgLoggingSettingInfo.getSlotid());
        }
      }
    });
  }

  @Override public int getItemCount() {
    return arrayList.size();
  }

  class ViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.main_title) TextView mainTitle;
    @Bind(R.id.sub_title) TextView subTitle;
    @Bind(R.id.check_box) CheckBox checkBox;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }

  public HashMap<Integer,String> getTimeSlotIdList() {
    return hashMap;
  }

  private int removePosition(int slotId) {
    for (int i = 0; i < timeSlotIdListTicked.size(); i++) {
      if (timeSlotIdListTicked.get(i) == slotId) {
        return i;
      }
    }
    return 0; // this should never occur
  }

  private boolean checkBgValueInDb(int id) {
    Realm realm = Realm.getDefaultInstance();
    try {
      BgSchedule bgSchedule = realm.where(BgSchedule.class)
          .equalTo("slotTypeId", id)
          .equalTo("isDeleted", false)
          .findFirst();
      if (bgSchedule != null) {
        return true;
      }
    } finally {
      realm.close();
    }
    return false;
  }
}
