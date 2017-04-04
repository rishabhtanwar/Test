package com.example.rishabh.curotest.helpers;

import com.github.mikephil.charting.formatter.FillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

/**
 * Created by rishabh on 04/04/2017.
 */

public class CustomizedFillFormatter implements FillFormatter {

  Float value;

  public CustomizedFillFormatter(Float value) {
    this.value = value;
  }

  @Override public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
    return value;
  }
}
