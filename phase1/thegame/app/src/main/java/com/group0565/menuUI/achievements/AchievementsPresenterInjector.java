package com.group0565.menuUI.achievements;

/** An injector for the StatisticsPresenter */
public class AchievementsPresenterInjector {
  /**
   * Injects the caller with an implementation of AchievementsPresenter
   *
   * @param view The view to be associated with the AchievementsPresenter
   * @return An instance of a AchievementsPresenter
   */
  public static AchievementsMVP.AchievementsPresenter inject(
      AchievementsMVP.AchievementsView view) {
    return new AchievementsPresenterImp(view);
  }
}
