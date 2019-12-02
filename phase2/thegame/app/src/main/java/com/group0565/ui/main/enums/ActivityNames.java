package com.group0565.ui.main.enums;

import com.group0565.bombergame.core.BomberMainActivity;
import com.group0565.racer.core.RacerMainActivity;
import com.group0565.tsu.core.TsuActivity;
import com.group0565.ui.achievements.AchievementsActivity;
import com.group0565.ui.settings.SettingsActivity;
import com.group0565.ui.statistics.StatisticsActivity;

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
