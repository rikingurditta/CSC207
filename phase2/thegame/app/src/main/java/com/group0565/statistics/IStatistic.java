package com.group0565.statistics;

/**
 * An interface for a Statistic in the game
 *
 * @param <T> The type of value in the statistic
 */
public interface IStatistic<T> extends Comparable<IStatistic<T>> {

  /**
   * Get the statistic's full key
   *
   * @return Statistic full key
   */
  String getFullStatKey();

  /**
   * Get the statistic's key without date
   *
   * @return Statistic key without date
   */
  String getStatKey();

  /**
   * Get the statistic's formatted date
   *
   * @return Statistic formatted date
   */
  String getStatFormattedDate();

  /**
   * Get the statistic's value
   *
   * @return Statistic value
   */
  T getStatVal();

  /**
   * Set the statistic's value
   *
   * @param value New value
   */
  void setValue(T value);
}
