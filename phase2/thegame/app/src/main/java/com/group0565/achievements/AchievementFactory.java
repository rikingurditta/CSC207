package com.group0565.achievements;

import java.util.Calendar;

/** A factory for creating IAchievements */
class AchievementFactory {
  /**
   * Create a new GameAchievement with the given key and set achieved to true
   *
   * @param achievementKey The achievement key
   * @return The created achievement
   */
  static IAchievement createGameAchievement(String achievementKey) {
    return new GameAchievement(achievementKey, true, Calendar.getInstance().getTime());
  }
}
