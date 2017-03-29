package com.example.rishabh.curotest.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.example.rishabh.curotest.Model.BgLogScreenInfo;
import com.example.rishabh.curotest.R;
import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by rishabh on 27/03/2017.
 */

public class BgLogScreenAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
  Context context;
  ArrayList<BgLogScreenInfo> arrayList = new ArrayList<>();

  public BgLogScreenAdapter(ArrayList<BgLogScreenInfo> arrayList, Context context) {
    this.arrayList = arrayList;
    this.context = context;
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.bg_schedule_screen, parent, false);
    ViewHolder viewHolder = new ViewHolder(view);
    return viewHolder;
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

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
}
