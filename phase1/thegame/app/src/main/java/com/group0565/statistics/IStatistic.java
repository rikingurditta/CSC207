package com.group0565.statistics;

/**
 * An interface for a Statistic in the game
 *
 * @param <T> The type of value in the statistic
 */
public interface IStatistic<T> {

  /**
   * Get the statistic's name
   *
   * @return Statistic name
   */
  String getStatKey();

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
