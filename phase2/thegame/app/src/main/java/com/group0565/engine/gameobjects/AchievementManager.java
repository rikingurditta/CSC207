package com.group0565.engine.gameobjects;

import com.group0565.achievements.AchievementsRepositoryInjector;
import com.group0565.achievements.IAsyncAchievementsRepository;
import com.group0565.engine.Achievement;

import java.util.HashMap;

/** A manager of Achievements */
public class AchievementManager extends GameObject {
  /** A map of achievements name to object */
  private HashMap<String, HashMap<String, Achievement>> achievements = new HashMap<>();

  /** Achievements repository for DB interactions */
  private IAsyncAchievementsRepository achievementsRepository;

  @Override
  public void init() {
    super.init();
    for (HashMap<String, Achievement> set : achievements.values()) {
      for (Achievement achievement : set.values()) achievement.init();
    }
  }

  /**
   * Registers the achievement under set
   *
   * @param set The set that the achievement belongs to
   * @param achievement The achievement to register
   */
  public void registerAchivement(String set, Achievement achievement) {
    if (!achievements.containsKey(set)) achievements.put(set, new HashMap<>());
    achievements.get(set).put(achievement.getName(), achievement);
    this.adopt(achievement);
  }

  /**
   * Mark the achievement as unlocked
   *
   * @param set The set the achievement belongs to
   * @param name The name of the achievement
   */
  public void unlockAchievement(String set, String name) {
    if (achievementsRepository != null) {
      achievementsRepository.isNewAchievement(name, isNew -> handleAchievement(isNew, set, name));
    } else {
      AchievementsRepositoryInjector.inject(
          repository -> {
            achievementsRepository = repository;
            unlockAchievement(set, name);
          });
    }
  }

  /**
   * Add achievement to DB
   *
   * @param achievement The achievement to add to DB
   */
  private void addAchievementToDB(String achievement) {
    if (achievementsRepository != null) {
      achievementsRepository.push(achievement);
    }
  }

  /**
   * Handle the achievement based on the info from the DB, if new then add to DB and alert user
   *
   * @param isNew Is the achievement new
   * @param achievementEarned The achievement the user earned
   */
  private void handleAchievement(boolean isNew, String set, String achievementEarned) {
    if (isNew) {
      addAchievementToDB(achievementEarned);
      achievements.get(set).get(achievementEarned).unlock();
    }
  }
}
