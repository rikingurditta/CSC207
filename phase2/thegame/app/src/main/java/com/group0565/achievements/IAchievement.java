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
  boolean getIsAchieved();

  /**
   * Gets the achievement's achieve date
   *
   * @return The date and time of achievement in milliseconds
   */
  Long getAchievementDate();

  /**
   * Sets the achievement's status to true
   *
   * @param achieveDate The date of achievement
   */
  void setAchieved(Long achieveDate);
}
