package com.group0565.ui.statistics;

/** An injector for the StatisticsPresenter */
public class StatisticsPresenterInjector {
  /**
   * Injects the caller with an implementation of SettingsPresenter
   *
   * @param view The view to be associated with the SettingsPresenter
   * @return An instance of a SettingsPresenter
   */
  public static StatisticsMVP.StatisticsPresenter inject(StatisticsMVP.StatisticsView view) {
    return new StatisticsPresenterImp(view);
  }
}
