package com.group0565.achievements;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

/** An implementation of IAsyncStatisticsRepository that returns mock data */
public class MockAchievementsRepository implements IAsyncAchievementsRepository {

  /** A collection of the user achievements */
  private List<IAchievement> userAchievements;

  /** An observable live collection of the user achievements */
  private MutableLiveData<List<IAchievement>> liveAchievements;

  MockAchievementsRepository() {
    userAchievements = new ArrayList<>();
    liveAchievements = new MutableLiveData<>();
  }

  /**
   * Gets the observable LiveData of all the objects
   *
   * @return An observable object wrapping a list
   */
  @Override
  public LiveData<List<IAchievement>> getObservable() {
    return liveAchievements;
  }

  /**
   * Gets the non-observable list of all objects using a callback
   *
   * @param callback The callback to execute on success
   */
  @Override
  public void getAll(AsyncDataListCallBack<IAchievement> callback) {
    callback.onDataReceived(userAchievements);
  }

  /**
   * Updates an object in the database for the user
   *
   * @param achievement The object to update based on its key
   */
  @Override
  public void put(IAchievement achievement) {
    userAchievements.add(achievement);

    updateLiveData();

    Log.d("MockStatisticRepository", "put " + achievement.getAchievementKey());
  }

  /**
   * Add an achievement to the database for the user
   *
   * @param achievement The object to add
   */
  @Override
  public void push(IAchievement achievement) {
    userAchievements.add(achievement);

    updateLiveData();

    Log.d("MockStatisticRepository", "push: " + achievement.getAchievementKey());
  }

  /**
   * Remove an achievement from the database for the user
   *
   * @param achievement The object to remove
   */
  @Override
  public void delete(IAchievement achievement) {
    userAchievements.add(achievement);

    updateLiveData();

    Log.d("MockStatisticRepository", "delete: " + achievement.getAchievementKey());
  }

  /** Remove all child objects */
  @Override
  public void deleteAll() {
    userAchievements = new ArrayList<>();
    liveAchievements.setValue(userAchievements);
  }

  /** Safely updates the live data */
  private void updateLiveData() {
    try {
      liveAchievements.setValue(userAchievements);
    } catch (IllegalStateException ex) {
      liveAchievements.postValue(userAchievements);
    }
  }

  /**
   * Does the user already have the achievement, send true to callback if achievement IS NOT in db
   *
   * @param achievementKey The achievement earned
   * @param callBack The callback to return the answer to
   */
  @Override
  public void isNewAchievement(String achievementKey, AsyncDataCallBack<Boolean> callBack) {
    boolean isFound = false;
    for (IAchievement currAchieve : userAchievements) {
      if (achievementKey.equals(currAchieve.getAchievementKey())) {
        isFound = true;
        break;
      }
    }
    callBack.onDataReceived(!isFound);
  }

  /**
   * Create and push a new achievement with the given key
   *
   * @param achievementName The achievement key
   */
  @Override
  public void push(String achievementName) {
    IAchievement achievement = AchievementFactory.createGameAchievement(achievementName);

    userAchievements.add(achievement);

    updateLiveData();

    Log.d("MockStatisticRepository", "push: " + achievement.getAchievementKey());
  }
}
