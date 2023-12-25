package com.patrickzedler.surface.util;

import android.content.Context;
import android.util.TypedValue;
import androidx.annotation.AttrRes;

public class ResUtil {

  public static int getColorAttr(Context context, @AttrRes int resId) {
    TypedValue typedValue = new TypedValue();
    context.getTheme().resolveAttribute(resId, typedValue, true);
    return typedValue.data;
  }
}