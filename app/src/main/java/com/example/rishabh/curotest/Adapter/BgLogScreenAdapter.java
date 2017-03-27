package com.example.rishabh.curotest.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.example.rishabh.curotest.Model.BgLogScreenInfo;
import java.util.ArrayList;

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
    return null;
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

  }

  @Override public int getItemCount() {
    return arrayList.size();
  }


}
