package com.group0565.achievements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Achievements {
  TSU_ACHIEVEMENT(new GameAchievement("TSU_KEY", "Very descriptive for tsu", "Tsu achievement")),
  RACER_ACHIEVEMENT(
      new GameAchievement("RACER_KEY", "Very descriptive for racer", "Racer achievement")),
  BOMBER_ACHIEVEMENT(
      new GameAchievement("BOMBER_KEY", "Very descriptive for bomber", "Bomber achievement"));

  private static Map map = new HashMap<>();

  static {
    for (Achievements achievement : Achievements.values()) {
      map.put(achievement.value.getAchievementKey(), achievement);
    }
  }

  private GameAchievement value;

  Achievements(GameAchievement value) {
    this.value = value;
  }

  public static Achievements getByKey(String achievementKey) {
    return (Achievements) map.get(achievementKey);
  }

  public GameAchievement getValue() {
    return value;
  }

  public List<GameAchievement> getAllAchievements() {
      return new ArrayList(map.values());
  }
}
