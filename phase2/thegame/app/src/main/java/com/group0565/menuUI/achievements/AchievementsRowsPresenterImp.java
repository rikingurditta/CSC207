package com.group0565.menuUI.achievements;

import com.group0565.achievements.IAchievement;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/** An implementation of the Achievements rows presenter */
public class AchievementsRowsPresenterImp implements AchievementsMVP.AchievementsRowsPresenter {

  /** A reference to the achievements list */
  private List<IAchievement> achievements;

  /**
   * Create a new AchievementsRowsPresenterImp with the given achievements list
   * @param achievements The list of achievements
   */
  AchievementsRowsPresenterImp(List<IAchievement> achievements) {
    this.achievements = achievements;
  }

  /**
   * Sets the title and value of the given row
   *
   * @param position Binding position
   * @param rowView The row to bind to
   */
  @Override
  public void onBindRepositoryRowViewAtPosition(
      int position, AchievementsMVP.AchievementsRowView rowView) {

    String achievementKey = achievements.get(position).getAchievementKey();
    boolean achievementStatus = achievements.get(position).getIsAchieved();
    Long achievementDate = achievements.get(position).getAchievementDate();

    rowView.setDesc(achievementKey);
    rowView.setName(achievementKey);
    rowView.setImage(achievementKey);

    if (achievementDate != null) {
      rowView.setDate(formatMilliToDate(achievementDate));
    }

    rowView.setRowAlpha(achievementStatus);
  }

  /**
   * Get the amount of rows that should appear
   *
   * @return The row count
   */
  @Override
  public int getAchievementsCount() {
    return achievements.size();
  }

  /**
   * Convert millisecond to local datetime format
   *
   * @param milli The milliseconds to convert
   * @return The formatted date as a string
   */
  private String formatMilliToDate(Long milli) {
    // Create a DateFormatter object for displaying date in specified format.
    DateFormat formatter = SimpleDateFormat.getDateTimeInstance();

    return formatter.format(extractDateFromMilli(milli));
  }

  /**
   * Convert millisecond to date
   *
   * @param milli The milliseconds to convert
   * @return The date object
   */
  private Date extractDateFromMilli(Long milli) {
    // Create a calendar object that will convert the date and time value in milliseconds to date.
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(milli);

    return calendar.getTime();
  }
}
