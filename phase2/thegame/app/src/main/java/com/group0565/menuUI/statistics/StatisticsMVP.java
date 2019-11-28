package com.group0565.menuUI.statistics;

import com.group0565.basePatterns.mvp.BaseMVP;
import com.group0565.statistics.IStatistic;

import java.util.List;

/** An interface for the Statistics module MVP */
public interface StatisticsMVP extends BaseMVP {

  /** An interface for the Statistics rows presenter */
  interface StatisticsRowsPresenter {

    /**
     * Execute on binding of a single row
     *
     * @param position Binding position
     * @param view The view to bind at the position
     */
    void onBindRepositoryRowViewAtPosition(int position, StatisticsRowView view);

    /**
     * Get the amount of rows that should appear
     *
     * @return The row count
     */
    int getStatsCount();
  }

  /** An interface for the Statistics row view */
  interface StatisticsRowView {

    /**
     * Sets the row title
     *
     * @param title The new title
     */
    void setTitle(String title);

    /**
     * Sets the row value
     *
     * @param value The new value
     */
    void setValue(String value);
  }

  /** An interface for the Statistics main presenter */
  interface StatisticsPresenter extends BaseMVP.BasePresenter {
    /**
     * Fetches the repository and uses it to get and set the data in the recycler
     *
     * @param gameName The target game
     */
    void getGameStatRepo(String gameName);
  }

  /** An interface for the Statistics main view */
  interface StatisticsView extends BaseMVP.BaseView {
    void setGameStats(String gameName, List<IStatistic> data);
  }
}
