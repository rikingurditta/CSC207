package com.group0565.menuUI.statistics.enums;

import com.example.thegame.R;
import com.group0565.statistics.enums.StatisticKey;

import java.util.HashMap;
import java.util.Map;

/** An Enum representation for the UI text for each statistic */
public enum StatisticName {
  BOMBER_BOMBS_PLACED(R.string.bomber_bombs_placed_text),
  BOMBER_DAMAGE_DEALT(R.string.bomber_damage_dealt_text),
  BOMBER_HP_REMAINING(R.string.bomber_hp_remaining_text),
  RACER_TIME_SURVIVED(R.string.racer_time_survived_text),
  TSU_SCORE(R.string.tsu_score_text),
  DEFAULT(R.string.stat_missing_text);

  /** A map from value to enum name */
  private static Map map = new HashMap<>();

  static {
    /* Fill map with defined values */
    for (StatisticName statisticName : StatisticName.values()) {
      map.put(statisticName.value, statisticName);
    }
  }

  /** The value of the enum */
  private int value;

  /**
   * Create a new instance of the enum with the given value
   *
   * @param value The given value
   */
  StatisticName(int value) {
    this.value = value;
  }

  public static StatisticName valueOf(int statisticName) {
    return (StatisticName) map.get(statisticName);
  }

  /**
   * Return the StatisticName associated with the StatisticKey
   *
   * @param statKey The DB key of the stat
   * @return The ID of the UI text
   */
  public static StatisticName fromStatisticKey(StatisticKey statKey) {
    switch (statKey) {
      case TSU_SCORE:
        return StatisticName.TSU_SCORE;
      case BOMBER_BOMBS_PLACED:
        return StatisticName.BOMBER_BOMBS_PLACED;
      case BOMBER_DAMAGE_DEALT:
        return StatisticName.BOMBER_DAMAGE_DEALT;
      case BOMBER_HP_REMAINING:
        return StatisticName.BOMBER_HP_REMAINING;
      case RACER_TIME_SURVIVED:
        return StatisticName.RACER_TIME_SURVIVED;
      default:
        return StatisticName.DEFAULT;
    }
  }

  /**
   * Get the value of the instance
   *
   * @return The value
   */
  public int getValue() {
    return value;
  }
}
