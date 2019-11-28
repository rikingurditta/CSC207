package com.group0565.achievements;

/** An interface for an Achievement in the game */
public interface IAchievement {

  /**
   * Get the Achievement's key
   *
   * @return Achievement key
   */
  String getAchievementKey();

  /**
   * Gets the Achievement's status
   *
   * @return True if achievement was unlocked and false otherwise
   */
  boolean isAchieved();

  /** Sets the achievement's status to true */
  void setAchieved();
}
