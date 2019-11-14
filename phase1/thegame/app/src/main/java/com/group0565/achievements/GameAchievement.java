package com.group0565.achievements;

/**
 * An implementation of IAchievements for GameAchievement
 */
public class GameAchievement implements IAchievement {

  /**
   * The key for the achievement
   */
  private String achievementKey;

  /**
   * The description of the achievement
   */
  private String achievementDesc;

  /**
   * The name of the achievement
   */
  private String achievementName;

  /**
   * Creates a new GameStatistic with the given key and value
   *
   * @param achievementKey  The achievement unique key
   * @param achievementDesc The achievement description
   * @param achievementName The achievement name
   */
  GameAchievement(String achievementKey, String achievementDesc, String achievementName) {
    this.achievementName = achievementName;
    this.achievementDesc = achievementDesc;
    this.achievementKey = achievementKey;
  }

  /**
   * Default constructor - DO NOT USE! REQUIRED FOR FIREBASE DB
   */
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
   * Get the Achievement's description
   *
   * @return Achievement description
   */
  @Override
  public String getAchievementDesc() {
    return this.achievementDesc;
  }

  /**
   * Get the Achievement's name
   *
   * @return Achievement name
   */
  @Override
  public String getAchievementName() {
    return this.achievementName;
  }

  /**
   * Set the Achievement's description
   *
   * @param desc New desc
   */
  @Override
  public void setDesc(String desc) {
    this.achievementDesc = desc;
  }

  /**
   * Set the Achievement's name
   *
   * @param name New name
   */
  @Override
  public void setName(String name) {
    this.achievementName = name;
  }
}
