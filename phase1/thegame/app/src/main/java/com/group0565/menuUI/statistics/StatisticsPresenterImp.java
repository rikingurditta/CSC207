package com.group0565.menuUI.statistics;

import com.group0565.preferences.IPreferenceInteractor;
import com.group0565.preferences.PreferencesInjector;
import com.group0565.statistics.IAsyncStatisticsRepository;
import com.group0565.statistics.StatisticRepositoryInjector;
import com.group0565.theme.ThemeManager;

/**
 * Implementation of the SettingsPresenter
 */
public class StatisticsPresenterImp implements StatisticsMVP.StatisticsPresenter {

    /** Reference to the attached view */
  private StatisticsMVP.StatisticsView statisticsView;

  /**
   * Instantiate a new StatisticsPresenterImp
   *
   * @param statisticsView The attached StatisticsView
   */
  StatisticsPresenterImp(StatisticsMVP.StatisticsView statisticsView) {
    this.statisticsView = statisticsView;
  }

  /**
   * Fetches the repository and uses it to get and set the data in the recycler
   *
   * @param gameName The target game
   */
  @Override
  public void getGameStatRepo(String gameName) {
    StatisticRepositoryInjector.inject(gameName, repository -> setGameStats(gameName, repository));
  }

  /**
   * Sets the stats to the recycler using the given repository
   *
   * @param gameName The target game
   * @param repository The repository to get the data from
   */
  private void setGameStats(String gameName, IAsyncStatisticsRepository repository) {
    repository.getAll(data -> statisticsView.setGameStats(gameName, data));
  }

    /** Destroy all references in this object */
  @Override
  public void onDestroy() {
    this.statisticsView = null;
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
