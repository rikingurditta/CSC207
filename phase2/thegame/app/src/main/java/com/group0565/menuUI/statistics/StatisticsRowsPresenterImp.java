package com.group0565.menuUI.statistics;

import android.content.res.Resources;

import com.group0565.menuUI.TheGameApplication;
import com.group0565.menuUI.statistics.enums.StatisticName;
import com.group0565.statistics.IStatistic;
import com.group0565.statistics.enums.StatisticKey;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/** An implementation of the Statistics rows presenter */
public class StatisticsRowsPresenterImp implements StatisticsMVP.StatisticsRowsPresenter {

  /** A reference to the statistics list */
  private List<IStatistic> statistics;

  /**
   * Create a new instance of StatisticsRowsPresenterImp
   * @param statistics The list of given statistics
   */
  StatisticsRowsPresenterImp(List<IStatistic> statistics) {
    this.statistics = statistics;

    // Sort statistics by date
    Collections.sort(this.statistics);
    Collections.reverse(this.statistics);
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
    rowView.setTitle(res.getString(statName.getValue()));
    rowView.setDate(formatMilliToDate(currStat.getStatDate()));
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

  /**
   * Convert millisecond to local datetime format
   *
   * @param milli The milliseconds to convert
   * @return The formatted date as a string
   */
  private String formatMilliToDate(Long milli) {
    // Create a DateFormatter object for displaying date in specified format.
    DateFormat formatter = SimpleDateFormat.getDateTimeInstance();

    return formatter.format(extractDateFromMilli(milli));
  }

  /**
   * Convert millisecond to date
   *
   * @param milli The milliseconds to convert
   * @return The date object
   */
  private Date extractDateFromMilli(Long milli) {
    // Create a calendar object that will convert the date and time value in milliseconds to date.
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(milli);

    return calendar.getTime();
  }
}
