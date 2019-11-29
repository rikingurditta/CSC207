package com.group0565.menuUI.achievements;

import com.group0565.achievements.IAchievement;
import com.group0565.basePatterns.mvp.BaseMVP;

import java.util.Date;
import java.util.List;

/** An interface for the Achievements module MVP */
public interface AchievementsMVP extends BaseMVP {
  /** An interface for the Achievements rows presenter */
  interface AchievementsRowsPresenter {

    /**
     * Execute on binding of a single row
     *
     * @param position Binding position
     * @param view The view to bind at the position
     */
    void onBindRepositoryRowViewAtPosition(int position, AchievementsRowView view);

    /**
     * Get the amount of rows that should appear
     *
     * @return The row count
     */
    int getAchievementsCount();
  }

  /** An interface for the Achievements row view */
  interface AchievementsRowView {

    /**
     * Sets the row date
     *
     * @param achievedAt The achievement's date
     */
    void setDate(String achievedAt);

    /**
     * Sets the row name based on the achievement's key
     *
     * @param key The achievement's key
     */
    void setName(String key);

    /**
     * Sets the row description based on the achievement's key
     *
     * @param key The achievement's key
     */
    void setDesc(String key);

    /**
     * Sets the row image by the key
     *
     * @param key The achievement's key
     */
    void setImage(String key);

    /**
     * Sets the row alpha to full if achieved and to 0.1 if not achieved
     *
     * @param achieved Did the user achieve it yet
     */
    void setRowAlpha(boolean achieved);
  }

  /** An interface for the Achievements main presenter */
  interface AchievementsPresenter extends BaseMVP.BasePresenter {
    /** Fetches the repository and uses it to get and set the data in the recycler */
    void getGameAchievementsRepo();
  }

  /** An interface for the Achievements main view */
  interface AchievementsView extends BaseMVP.BaseView {
    void setAchievements(List<IAchievement> data);
  }
}
