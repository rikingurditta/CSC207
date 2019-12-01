package com.group0565.achievements.enums;

import com.group0565.achievements.GameAchievement;
import com.group0565.achievements.IAchievement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** An enum that acts as persistence for the UI view of all possible achievements */
public enum Achievements {
  RACER_15_SECONDS(new GameAchievement("racer_15_seconds")),
  RACER_60_SECONDS(new GameAchievement("racer_60_seconds")),
  ;

  /** A map from achievement key to achievement */
  private static Map map = new HashMap<>();

  /* Fill the map with preset achievements */
  static {
    for (Achievements achievement : Achievements.values()) {
      map.put(achievement.value.getAchievementKey(), achievement.value);
    }
  }

  /** The Enum's value */
  private GameAchievement value;

  /**
   * Create a new instance of the enum with the given value
   *
   * @param value The value of the enum
   */
  Achievements(GameAchievement value) {
    this.value = value;
  }

  /**
   * Get the list of values from the map
   *
   * @return A list of values
   */
  public static List<IAchievement> getAllAchievements() {
    return new ArrayList(map.values());
  }

  /**
   * Return the underlying value of the enum
   *
   * @return The object value
   */
  public GameAchievement getValue() {
    return value;
  }
}
