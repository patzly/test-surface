package com.patrickzedler.surface;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;
import com.google.android.material.color.DynamicColors;
import com.patrickzedler.surface.behavior.SystemBarBehavior;
import com.patrickzedler.surface.databinding.ActivityMainBinding;
import com.patrickzedler.surface.util.ResUtil;
import com.patrickzedler.surface.util.UiUtil;

public class MainActivity extends AppCompatActivity {

  private static final String DARK_MODE = "dark_mode";

  private ActivityMainBinding binding;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

    boolean darkMode = sharedPrefs.getBoolean(DARK_MODE, false);
    AppCompatDelegate.setDefaultNightMode(
        darkMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
    );

    DynamicColors.applyToActivityIfAvailable(this);

    binding = ActivityMainBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    SystemBarBehavior systemBarBehavior = new SystemBarBehavior(this);
    systemBarBehavior.setAppBar(binding.appBar);
    systemBarBehavior.setContainer(binding.frameContainer);
    systemBarBehavior.setUp();

    binding.appBar.setLiftOnScroll(false);
    binding.appBar.setLifted(true);

    binding.coverLowest.setBackgroundColor(
        ResUtil.getColorAttr(this, R.attr.colorSurfaceContainerLowest)
    );
    binding.coverLow.setBackgroundColor(
        ResUtil.getColorAttr(this, R.attr.colorSurfaceContainerLow)
    );
    binding.cover.setBackgroundColor(
        ResUtil.getColorAttr(this, R.attr.colorSurfaceContainer)
    );
    binding.coverHigh.setBackgroundColor(
        ResUtil.getColorAttr(this, R.attr.colorSurfaceContainerHigh)
    );
    binding.coverHighest.setBackgroundColor(
        ResUtil.getColorAttr(this, R.attr.colorSurfaceContainerHighest)
    );

    binding.buttonToggleDark.setOnClickListener(v -> {
      sharedPrefs.edit().putBoolean(DARK_MODE, !UiUtil.isDarkModeActive(this)).apply();
      startActivity(new Intent(this, MainActivity.class));
      finish();
      overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    });
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    binding = null;
  }
}
