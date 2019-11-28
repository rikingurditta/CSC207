package com.group0565.statistics;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * An implementation of IStatistic for GameStatistics
 *
 * @param <T> The type of value
 */
class GameStatistic<T> implements IStatistic<T> {

  /** The key for the statistic */
  private String statKey;

  /** The value of the statistic */
  private T statValue;

  /**
   * Creates a new GameStatistic with the given key and value
   *
   * @param statKey The statistic unique key
   * @param statValue The statistic value
   */
  GameStatistic(String statKey, T statValue) {
    this.statKey = statKey;
    this.statValue = statValue;
  }

  /** Default constructor - DO NOT USE! REQUIRED FOR FIREBASE DB */
  public GameStatistic() {
    // Default constructor required for calls to DataSnapshot.getStatVal(GameStatistic.class)
  }

  /**
   * Get the statistic's key
   *
   * @return Statistic key
   */
  @Override
  public String getFullStatKey() {
    return this.statKey;
  }

  /**
   * Get the statistic's key without date
   *
   * @return Statistic key without date
   */
  @Override
  public String getStatKey() {
    String[] part = this.statKey.split("(?<=\\D)(?=\\d)");
    return part[0];
  }

  /**
   * Get the statistic's formatted date
   *
   * @return Statistic formatted date
   */
  @Override
  public String getStatFormattedDate() {
    String[] part = this.statKey.split("(?<=\\D)(?=\\d)");

    return formatMilliToDate(Long.parseLong(part[1]));
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

    // Create a calendar object that will convert the date and time value in milliseconds to date.
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(milli);

    return formatter.format(calendar.getTime());
  }

  /**
   * Get the statistic's value
   *
   * @return Statistic value
   */
  @Override
  public T getStatVal() {
    return this.statValue;
  }

  /**
   * Set the statistic's value
   *
   * @param value New value
   */
  @Override
  public void setValue(T value) {
    this.statValue = value;
  }
}
