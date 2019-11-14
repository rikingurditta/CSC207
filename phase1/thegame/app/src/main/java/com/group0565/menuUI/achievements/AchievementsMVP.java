package com.group0565.menuUI.achievements;

import com.group0565.achievements.IAchievement;
import com.group0565.basePatterns.mvp.BaseMVP;

import java.util.List;

/**
 * An interface for the Achievements module MVP
 */
public interface AchievementsMVP extends BaseMVP {
    /**
     * An interface for the Achievements rows presenter
     */
    interface AchievementsRowsPresenter {

        /**
         * Execute on binding of a single row
         *
         * @param position Binding position
         * @param view     The view to bind at the position
         */
        void onBindRepositoryRowViewAtPosition(int position, AchievementsRowView view);

        /**
         * Get the amount of rows that should appear
         *
         * @return The row count
         */
        int getAchievementsCount();
    }

    /**
     * An interface for the Achievements row view
     */
    interface AchievementsRowView {

        /**
         * Sets the row name
         *
         * @param name The new name
         */
        void setName(String name);

        /**
         * Sets the row description
         *
         * @param desc The new desc
         */
        void setDesc(String desc);

        /**
         * Sets the row image by the key
         *
         * @param key The achievement's key
         */
        void setImage(String key);
    }

    /**
     * An interface for the Achievements main presenter
     */
    interface AchievementsPresenter extends BaseMVP.BasePresenter {
        /**
         * Fetches the repository and uses it to get and set the data in the recycler
         */
        void getGameAchievementsRepo();
    }

    /**
     * An interface for the Achievements main view
     */
    interface AchievementsView extends BaseMVP.BaseView {
        void setAchievements(List<IAchievement> data);
    }
}
