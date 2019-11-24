package com.group0565.menuUI.statistics;

import android.content.res.Resources;

import com.group0565.menuUI.TheGameApplication;
import com.group0565.menuUI.statistics.enums.StatisticName;
import com.group0565.statistics.IStatistic;
import com.group0565.statistics.enums.StatisticKey;

import java.util.List;

/** An implementation of the Statistics rows presenter */
public class StatisticsRowsPresenterImp implements StatisticsMVP.StatisticsRowsPresenter {

  /** A reference to the statistics list */
  private List<IStatistic> statistics;

  StatisticsRowsPresenterImp(List<IStatistic> statistics) {
    this.statistics = statistics;
  }

  /**
   * Sets the title and value of the given row
   *
   * @param position Binding position
   * @param rowView The row to bind to
   */
  @Override
  public void onBindRepositoryRowViewAtPosition(
      int position, StatisticsMVP.StatisticsRowView rowView) {

    IStatistic currStat = statistics.get(position);
    Resources res = TheGameApplication.getInstance().getResources();
    StatisticName statName =
        StatisticName.fromStatisticKey(StatisticKey.getEnum(currStat.getStatKey()));

    rowView.setValue(currStat.getStatVal().toString());
    rowView.setTitle(res.getString(statName.getValue()) + " " + currStat.getStatFormattedDate());
  }

  /**
   * Get the amount of rows that should appear
   *
   * @return The row count
   */
  @Override
  public int getStatsCount() {
    return statistics.size();
  }
}
