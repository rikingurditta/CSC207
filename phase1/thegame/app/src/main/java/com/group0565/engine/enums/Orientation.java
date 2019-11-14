package com.group0565.engine.enums;

import android.content.pm.ActivityInfo;

public enum Orientation {
  Portrait(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT),
  Landscape(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE),
  ReversePortrait(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT),
  ReverseLandscape(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);

  public final int orientation;

  Orientation(int orientation) {
    this.orientation = orientation;
  }
}
