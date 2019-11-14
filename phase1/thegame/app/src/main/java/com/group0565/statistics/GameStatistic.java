package com.group0565.statistics;

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
   * Get the statistic's name
   *
   * @return Statistic name
   */
  @Override
  public String getStatKey() {
    return this.statKey;
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
