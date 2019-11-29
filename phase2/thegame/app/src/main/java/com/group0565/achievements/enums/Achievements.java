package com.group0565.achievements.enums;

import com.group0565.achievements.GameAchievement;
import com.group0565.achievements.IAchievement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Achievements {
  TSU_ACHIEVEMENT(new GameAchievement("tsu_achievement")),
  RACER_ACHIEVEMENT(new GameAchievement("racer_achievement")),
  BOMBER_ACHIEVEMENT(new GameAchievement("bomber_achievement"));

  private static Map map = new HashMap<>();

  static {
    for (Achievements achievement : Achievements.values()) {
      map.put(achievement.value.getAchievementKey(), achievement.value);
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

  public static List<IAchievement> getAllAchievements() {
    return new ArrayList(map.values());
  }
}
