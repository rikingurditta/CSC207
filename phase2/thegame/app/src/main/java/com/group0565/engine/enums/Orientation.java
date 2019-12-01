package com.group0565.engine.enums;

import android.content.pm.ActivityInfo;

/** An enum representation of screen orientation options */
public enum Orientation {
  Portrait(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT),
  Landscape(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE),
  ReversePortrait(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT),
  ReverseLandscape(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);

  /** The int value of Android orientation */
  public final int orientation;

  /**
   * Create a new orientation
   *
   * @param orientation The int orientation value
   */
  Orientation(int orientation) {
    this.orientation = orientation;
  }
}
