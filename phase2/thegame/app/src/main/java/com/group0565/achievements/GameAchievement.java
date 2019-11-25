package com.group0565.achievements;

import androidx.annotation.Nullable;

import java.util.HashMap;

/** An implementation of IAchievements for GameAchievement */
public class GameAchievement implements IAchievement {

  /** The key for the achievement */
  private String achievementKey;

  /** The achievement's status */
  private boolean isAchieved;

  /**
   * Creates a new GameStatistic with the given key
   *
   * @param achievementKey The achievement unique key
   */
  public GameAchievement(String achievementKey) {
    this(achievementKey, false);
  }

  /**
   * Creates a new GameStatistic with the given key and status
   *
   * @param achievementKey The achievement unique key
   * @param isAchieved The achievement's status
   */
  public GameAchievement(String achievementKey, boolean isAchieved) {
    this.achievementKey = achievementKey;
    this.isAchieved = isAchieved;
  }

  /** Default constructor - DO NOT USE! REQUIRED FOR FIREBASE DB */
  public GameAchievement() {
    // Default constructor required for calls to DataSnapshot.getStatVal(GameAchievement.class)
  }

  /**
   * Get the Achievement's key
   *
   * @return Achievement key
   */
  @Override
  public String getAchievementKey() {
    return this.achievementKey;
  }

  /**
   * Gets the Achievement's status
   *
   * @return True if achievement was unlocked and false otherwise
   */
  @Override
  public boolean isAchieved() {
    return isAchieved;
  }

  /** Sets the achievement's status to true */
  public void setAchieved() {
    isAchieved = true;
  }

  /**
   * Indicates whether some other object is "equal to" this one.
   *
   * @param obj the reference object with which to compare.
   * @return {@code true} if this object is the same as the obj argument; {@code false} otherwise.
   * @see #hashCode()
   * @see HashMap
   */
  @Override
  public boolean equals(@Nullable Object obj) {
    if (obj instanceof IAchievement) {
      return ((IAchievement) obj).getAchievementKey().equals(this.getAchievementKey());
    }
    return super.equals(obj);
  }
}
