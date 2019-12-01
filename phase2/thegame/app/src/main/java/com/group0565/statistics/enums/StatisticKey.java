package com.group0565.statistics.enums;

import java.util.HashMap;
import java.util.Map;

/** An Enum representation for the DB key for each statistic */
public enum StatisticKey {
  BOMBER_BOMBS_PLACED("bombsPlaced"),
  BOMBER_DAMAGE_DEALT("damageDealt"),
  BOMBER_HP_REMAINING("hpRemaining"),
  RACER_TIME_SURVIVED("timeSurvived"),
  TSU_SCORE("score");

  /** A map from value to enum name */
  private static Map map = new HashMap<>();

  static {
    /* Fill map with defined values */
    for (StatisticKey statisticKey : StatisticKey.values()) {
      map.put(statisticKey.value, statisticKey);
    }
  }

  /** The value of the enum */
  private String value;

  /**
   * Create a new instance of the enum with the given value
   *
   * @param value The given value
   */
  StatisticKey(String value) {
    this.value = value;
  }

  /**
   * Get the enum based on the name
   *
   * @param statisticName The name of desired statistic
   * @return The enum
   */
  public static StatisticKey getEnum(String statisticName) {
    return (StatisticKey) map.get(statisticName);
  }

  /**
   * Get the value of the instance
   *
   * @return The value
   */
  public String getValue() {
    return value;
  }
}
