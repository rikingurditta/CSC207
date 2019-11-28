package com.group0565.achievements;

import java.util.Date;

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

  /**
   * Gets the achievement's achieve date
   *
   * @return The date and time of achievement
   */
  Date getAchieveDate();

  /** Sets the achievement's status to true and the achievement time to now */
  void setAchieved();
}
