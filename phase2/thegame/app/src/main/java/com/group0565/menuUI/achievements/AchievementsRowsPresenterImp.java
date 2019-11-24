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

    rowView.setDesc(achievements.get(position).getAchievementDesc());
    rowView.setName(achievements.get(position).getAchievementName());
    rowView.setImage(achievements.get(position).getAchievementKey());
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
