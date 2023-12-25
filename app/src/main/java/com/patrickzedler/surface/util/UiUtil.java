package com.patrickzedler.surface.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.view.View;
import android.view.WindowInsetsController;
import androidx.annotation.NonNull;

public class UiUtil {

  public static void setLightNavigationBar(@NonNull View view, boolean isLight) {
    view.getWindowInsetsController().setSystemBarsAppearance(
        isLight ? WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS : 0,
        WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
    );
  }

  public static void setLightStatusBar(@NonNull View view, boolean isLight) {
    view.getWindowInsetsController().setSystemBarsAppearance(
        isLight ? WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS : 0,
        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
    );
  }

  public static boolean isDarkModeActive(Context context) {
    int uiMode = context.getResources().getConfiguration().uiMode;
    return (uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES;
  }

  public static boolean isNavigationModeGesture(Context context) {
    final int NAV_GESTURE = 2;
    Resources resources = context.getResources();
    @SuppressLint("DiscouragedApi")
    int resourceId = resources.getIdentifier(
        "config_navBarInteractionMode", "integer", "android"
    );
    int mode = resourceId > 0 ? resources.getInteger(resourceId) : 0;
    return mode == NAV_GESTURE;
  }
}
