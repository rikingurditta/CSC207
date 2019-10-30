package com.menu.statistics;

import com.group0565.basePatterns.mvp.BaseMVP;

/** An interface for the Statistics module MVP */
public interface StatisticsMVP extends BaseMVP {

  /** An interface for the Statistics presenter */
  interface StatisticsPresenter extends BaseMVP.BasePresenter {}

  /** An interface for the Statistics view */
  interface StatisticsView extends BaseMVP.BaseView {}
}
