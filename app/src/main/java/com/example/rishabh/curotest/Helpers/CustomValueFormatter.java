package com.example.rishabh.curotest.Helpers;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.text.DecimalFormat;

/**
 * Created by rishabh on 04/04/2017.
 */
public class CustomValueFormatter implements ValueFormatter {

  private DecimalFormat mFormat;

  public CustomValueFormatter(long attributeId) {
    if (attributeId == 8 || attributeId == 9 || attributeId == 10 || attributeId == 118 || attributeId == 25) {
      mFormat = new DecimalFormat("####.#"); // use one decimal
    } else {
      mFormat = new DecimalFormat("####");
    }
  }

  @Override
  public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {

    return mFormat.format(value); // e.g. append a dollar-sign
  }
}
