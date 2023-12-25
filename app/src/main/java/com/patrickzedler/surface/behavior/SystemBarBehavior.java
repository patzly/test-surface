package com.patrickzedler.surface.behavior;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat.Type;
import com.google.android.material.appbar.AppBarLayout;
import com.patrickzedler.surface.util.UiUtil;

public class SystemBarBehavior {

  private final Activity activity;
  private final Window window;
  int containerPaddingTop;
  int containerPaddingBottom;
  private AppBarLayout appBarLayout;
  private ViewGroup container;

  public SystemBarBehavior(@NonNull Activity activity) {
    this.activity = activity;
    window = activity.getWindow();

    // GOING EDGE TO EDGE
    window.setDecorFitsSystemWindows(false);
  }

  public void setAppBar(AppBarLayout appBarLayout) {
    this.appBarLayout = appBarLayout;
  }

  public void setContainer(ViewGroup container) {
    this.container = container;
    containerPaddingTop = container.getPaddingTop();
    containerPaddingBottom = container.getPaddingBottom();
  }

  public void setUp() {
    // TOP INSET
    if (appBarLayout != null) {
      ViewCompat.setOnApplyWindowInsetsListener(appBarLayout, (v, insets) -> {
        // STATUS BAR INSET
        appBarLayout.setPadding(
            0, insets.getInsets(Type.systemBars()).top, 0,
            appBarLayout.getPaddingBottom()
        );
        appBarLayout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

        // APP BAR INSET
        ViewGroup.MarginLayoutParams params
            = (ViewGroup.MarginLayoutParams) container.getLayoutParams();
        params.topMargin = appBarLayout.getMeasuredHeight();
        container.setLayoutParams(params);
        return insets;
      });
    } else if (container != null) {
      // if no app bar exists, status bar inset is applied to container
      ViewCompat.setOnApplyWindowInsetsListener(container, (v, insets) -> {
        // STATUS BAR INSET
        container.setPadding(
            container.getPaddingLeft(),
            containerPaddingTop + insets.getInsets(Type.systemBars()).top,
            container.getPaddingRight(),
            container.getPaddingBottom()
        );
        return insets;
      });
    }

    // NAV BAR INSET
    if (hasContainer()) {
      ViewCompat.setOnApplyWindowInsetsListener(container, (v, insets) -> {
        int paddingBottom = containerPaddingBottom;
        int navBarInset = insets.getInsets(Type.systemBars()).bottom;
        container.setPadding(
            container.getPaddingLeft(),
            container.getPaddingTop(),
            container.getPaddingRight(),
            paddingBottom + navBarInset
        );
        return insets;
      });
    }

    updateSystemBars();
  }

  private void updateSystemBars() {
    boolean isDarkModeActive = UiUtil.isDarkModeActive(activity);

    window.setStatusBarColor(Color.TRANSPARENT);
    if (!isDarkModeActive) {
      UiUtil.setLightStatusBar(window.getDecorView(), true);
    }
    if (UiUtil.isNavigationModeGesture(activity)) {
      window.setNavigationBarColor(Color.TRANSPARENT);
      window.setNavigationBarContrastEnforced(true);
    } else {
      if (!isDarkModeActive) {
        UiUtil.setLightNavigationBar(window.getDecorView(), true);
      }
      window.setNavigationBarColor(Color.parseColor("#01000000"));
    }
  }

  private boolean hasContainer() {
    return container != null;
  }
}
