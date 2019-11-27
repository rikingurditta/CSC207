package com.group0565.statistics;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

    return formatter.format(getDateFromKey(milli));
  }

  /**
   * Convert millisecond to date
   *
   * @param milli The milliseconds to convert
   * @return The date object
   */
  private Date getDateFromKey(Long milli) {
    // Create a calendar object that will convert the date and time value in milliseconds to date.
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(milli);

    return calendar.getTime();
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

  /**
   * Compares this object with the specified object for order. Returns a negative integer, zero, or
   * a positive integer as this object is less than, equal to, or greater than the specified object.
   *
   * @param o the object to be compared.
   * @return a negative integer, zero, or a positive integer as this object is less than, equal to,
   *     or greater than the specified object.
   * @throws NullPointerException if the specified object is null
   * @throws ClassCastException if the specified object's type prevents it from being compared to
   *     this object.
   */
  @Override
  public int compareTo(IStatistic<T> o) {
    DateFormat formatter = SimpleDateFormat.getDateTimeInstance();
    Date localDate;
    Date oDate;

    try {
      localDate = formatter.parse(this.getStatFormattedDate());
      oDate = formatter.parse(o.getStatFormattedDate());
    } catch (ParseException ex) {
      return 0;
    }

    if (statKey == null || o.getStatKey() == null) {
      return 0;
    } else {
      return localDate != null ? localDate.compareTo(oDate) : 0;
    }
  }
}
