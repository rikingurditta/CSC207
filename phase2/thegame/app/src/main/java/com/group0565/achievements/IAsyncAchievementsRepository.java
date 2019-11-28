package com.group0565.achievements;

import com.group0565.basePatterns.repository.IAsyncRepository;

/** A repository interface wrapping the IAsyncRepository for Achievements */
public interface IAsyncAchievementsRepository extends IAsyncRepository<IAchievement> {

  /**
   * Does the user already have the achievement, send true to callback if achievement IS NOT in db
   *
   * @param achievement The achievement earned
   * @param callBack The callback to return the answer to
   */
  void isNewAchievement(IAchievement achievement, AsyncDataCallBack<Boolean> callBack);
}
