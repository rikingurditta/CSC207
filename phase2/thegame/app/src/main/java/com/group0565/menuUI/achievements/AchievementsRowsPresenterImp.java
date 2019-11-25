package com.group0565.menuUI.achievements;

import com.group0565.achievements.IAchievement;

import java.util.List;

/** An implementation of the Achievements rows presenter */
public class AchievementsRowsPresenterImp implements AchievementsMVP.AchievementsRowsPresenter {

  /** A reference to the achievements list */
  private List<IAchievement> achievements;

  AchievementsRowsPresenterImp(List<IAchievement> achievements) {
    this.achievements = achievements;
  }

  /**
   * Sets the title and value of the given row
   *
   * @param position Binding position
   * @param rowView The row to bind to
   */
  @Override
  public void onBindRepositoryRowViewAtPosition(
      int position, AchievementsMVP.AchievementsRowView rowView) {

    String achievementKey = achievements.get(position).getAchievementKey();
    boolean achievementStatus = achievements.get(position).isAchieved();

    rowView.setDesc(achievementKey);
    rowView.setName(achievementKey);
    rowView.setImage(achievementKey, achievementStatus);
  }

  /**
   * Get the amount of rows that should appear
   *
   * @return The row count
   */
  @Override
  public int getAchievementsCount() {
    return achievements.size();
  }
}
