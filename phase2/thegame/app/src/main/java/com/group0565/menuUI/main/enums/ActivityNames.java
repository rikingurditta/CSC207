package com.group0565.menuUI.main.enums;

import com.group0565.bomberGame.core.BomberMainActivity;
import com.group0565.menuUI.achievements.AchievementsActivity;
import com.group0565.menuUI.settings.SettingsActivity;
import com.group0565.menuUI.statistics.StatisticsActivity;
import com.group0565.racer.core.RacerMainActivity;
import com.group0565.tsu.core.TsuActivity;

import java.util.HashMap;
import java.util.Map;

/** An Enum representation of Activities to map activity to class */
public enum ActivityNames {
  TSU(TsuActivity.class),
  BOMBER(BomberMainActivity.class),
  RACER(RacerMainActivity.class),
  ACHIEVEMENTS(AchievementsActivity.class),
  SETTINGS(SettingsActivity.class),
  STATISTICS(StatisticsActivity.class);

  /** A map from value to enum name */
  private static Map map = new HashMap<>();

  static {
    /* Fill map with defined values */
    for (ActivityNames activityName : ActivityNames.values()) {
      map.put(activityName.value, activityName);
    }
  }

  /** The value of the enum */
  private Class value;

  /**
   * Create a new instance of the enum with the given value
   *
   * @param value The given value
   */
  ActivityNames(Class value) {
    this.value = value;
  }

  /**
   * Get the value of the instance
   *
   * @return The value
   */
  public Class getValue() {
    return value;
  }
}
