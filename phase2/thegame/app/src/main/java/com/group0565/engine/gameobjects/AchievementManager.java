package com.group0565.engine.gameobjects;

import com.group0565.engine.Achievement;

import java.util.HashMap;

/**
 * A manager of Achievements
 */
public class AchievementManager extends GameObject{
    private HashMap<String, HashMap<String, Achievement>> achievements = new HashMap<>();

    @Override
    public void init() {
        super.init();
        for (HashMap<String, Achievement> set : achievements.values()){
            for (Achievement achievement : set.values())
                achievement.init();
        }
    }

    /**
     * Registers the achivement under set
     * @param set The set that the achievement belongs to
     * @param achievement The achievement to register
     */
    public void registerAchivement(String set, Achievement achievement){
        if (!achievements.containsKey(set))
            achievements.put(set, new HashMap<>());
        achievements.get(set).put(achievement.getName(), achievement);
        this.adopt(achievement);
    }

    /**
     * Mark the achievement as unlocked
     * @param set The set the achievement belongs to
     * @param name The name of the achievement
     */
    public void unlockAchievement(String set, String name){
        achievements.get(set).get(name).unlock();
    }
}
