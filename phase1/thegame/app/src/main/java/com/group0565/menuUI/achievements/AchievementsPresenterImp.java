package com.group0565.menuUI.achievements;

import com.group0565.achievements.AchievementsRepositoryInjector;
import com.group0565.achievements.IAsyncAchievementsRepository;
import com.group0565.preferences.IPreferenceInteractor;
import com.group0565.preferences.PreferencesInjector;
import com.group0565.theme.ThemeManager;

public class AchievementsPresenterImp implements AchievementsMVP.AchievementsPresenter {

  /** Reference to the attached view */
  private AchievementsMVP.AchievementsView achievementsView;

  /**
   * Instantiate a new StatisticsPresenterImp
   *
   * @param achievementsView The attached StatisticsView
   */
  AchievementsPresenterImp(AchievementsMVP.AchievementsView achievementsView) {
    this.achievementsView = achievementsView;
  }

  /** Fetches the repository and uses it to get and set the data in the recycler */
  @Override
  public void getGameAchievementsRepo() {
    AchievementsRepositoryInjector.inject(repository -> setAchievements(repository));
  }

  /**
   * Sets the stats to the recycler using the given repository
   *
   * @param repository The repository to get the data from
   */
  private void setAchievements(IAsyncAchievementsRepository repository) {
    repository.getAll(data -> achievementsView.setAchievements(data));
  }

  /** Destroy all references in this object */
  @Override
  public void onDestroy() {
    this.achievementsView = null;
  }

  /**
   * Gets the current display language
   *
   * @return The current display language
   */
  @Override
  public String getDisplayLanguage() {
    IPreferenceInteractor prefInteractor = PreferencesInjector.inject();

    return prefInteractor.getLanguage();
  }

  /**
   * Gets the current display theme id
   *
   * @return The current display theme
   */
  @Override
  public int getAppTheme() {
    IPreferenceInteractor prefInteractor = PreferencesInjector.inject();

    return ThemeManager.getTheme(prefInteractor.getTheme());
  }
}
