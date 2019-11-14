package com.group0565.achievements;

/**
 * An interface for an Achievement in the game
 */
public interface IAchievement {

    /**
     * Get the Achievement's key
     *
     * @return Achievement key
     */
    String getAchievementKey();

    /**
     * Get the Achievement's description
     *
     * @return Achievement description
     */
    String getAchievementDesc();

    /**
     * Get the Achievement's name
     *
     * @return Achievement name
     */
    String getAchievementName();

    /**
     * Set the Achievement's description
     *
     * @param desc New desc
     */
    void setDesc(String desc);

    /**
     * Set the Achievement's name
     *
     * @param name New name
     */
    void setName(String name);
}
