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

  private static Map map = new HashMap<>();

  static {
    for (StatisticKey statisticKey : StatisticKey.values()) {
      map.put(statisticKey.value, statisticKey);
    }
  }

  private String value;

  StatisticKey(String value) {
    this.value = value;
  }

  public static StatisticKey getEnum(String statisticName) {
    return (StatisticKey) map.get(statisticName);
  }

  public String getValue() {
    return value;
  }
}
