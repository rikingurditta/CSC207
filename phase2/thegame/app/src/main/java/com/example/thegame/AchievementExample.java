package com.example.thegame;

import com.group0565.achievements.enums.Achievements;
import com.group0565.achievements.AchievementsRepositoryInjector;
import com.group0565.achievements.IAchievement;
import com.group0565.achievements.IAsyncAchievementsRepository;

public class AchievementExample {

  /** Achievements repository */
  private IAsyncAchievementsRepository achievementsRepository;

  /** Check if achievement already in DB, and insert if not */
  void bla() {
    // todo: choose the correct achievement
    /* The achievement earned by the user */
    IAchievement achievementEarned = Achievements.BOMBER_ACHIEVEMENT.getValue();

    AchievementsRepositoryInjector.inject(
        repository -> {
          achievementsRepository = repository;

          achievementsRepository.isNewAchievement(
              achievementEarned.getAchievementKey(), isNew -> handleAchievement(isNew, achievementEarned));
        });
  }

  /**
   * Add achievement to DB
   *
   * @param achievement The achievement to add to DB
   */
  private void addAchievementToDB(IAchievement achievement) {
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
  private void handleAchievement(boolean isNew, IAchievement achievementEarned) {
    if (isNew) {
      addAchievementToDB(achievementEarned);
    } else {
      // Achievement already in DB, no need to do anything
    }
  }
}
