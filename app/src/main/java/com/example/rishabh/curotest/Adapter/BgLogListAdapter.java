package com.example.rishabh.curotest.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.example.rishabh.curotest.Model.BgLoggingSettingInfo;
import com.example.rishabh.curotest.R;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by rishabh on 07/04/2017.
 */

public class BgLogListAdapter extends BaseAdapter {
  Context context;
  ArrayList<BgLoggingSettingInfo> arrayList = new ArrayList<>();
  ArrayList<Integer> timeSlotIdListTicked = new ArrayList<>();
  ArrayList<Integer> timeSlotIdListUnTicked = new ArrayList<>();
  HashMap<Integer, String> hashMap = new HashMap<>();

  public BgLogListAdapter(Context context, ArrayList<BgLoggingSettingInfo> arrayList) {
    this.context = context;
    this.arrayList = arrayList;
  }

  @Override public int getCount() {
    return arrayList.size();
  }

  @Override public Object getItem(int position) {
    return arrayList.get(position);
  }

  @Override public long getItemId(int position) {
    return position;
  }

  @Override public View getView(final int position, View convertView, ViewGroup parent) {
    View view = convertView;
    ViewHolder viewHolder;
    if (convertView == null) {
      LayoutInflater layoutInflater =
          (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      view = layoutInflater.inflate(R.layout.bg_logging_setting_layout, null);
      viewHolder = new ViewHolder(view);
      view.setTag(viewHolder);
    } else {
      viewHolder = (ViewHolder) view.getTag();
    }
    final BgLoggingSettingInfo bgLoggingSettingInfo = arrayList.get(position);

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
        //notifyDataSetChanged();
      }
    });
    if (bgLoggingSettingInfo.isCheck()) {
      viewHolder.checkBox.setChecked(true);
      if (!hashMap.containsKey(bgLoggingSettingInfo.getSlotid())) {
        //timeSlotIdListTicked.add(bgLoggingSettingInfo.getSlotid());
        hashMap.put(bgLoggingSettingInfo.getSlotid(), "ticked");
      }
    } else {
      viewHolder.checkBox.setChecked(false);
    }

    return view;
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
}
