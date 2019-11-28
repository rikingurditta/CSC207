package com.group0565.achievements;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/** A Firebase implementation of the IAsyncAchievementsRepository */
public class FirebaseAchievementsRepository implements IAsyncAchievementsRepository {

  /** A reference to the Firebase database */
  private DatabaseReference mDatabase;

  /** A collection of the user achievements */
  private List<IAchievement> userAchievements;

  /** An observable live collection of the user achievements */
  private MutableLiveData<List<IAchievement>> liveAchievements;

  /**
   * Create a new repository for the given user
   *
   * @param currUser The current user
   */
  FirebaseAchievementsRepository(String currUser) {
    this.mDatabase =
        FirebaseDatabase.getInstance().getReference().child("users/" + currUser + "/achievements/");

    userAchievements = new ArrayList<>();
    liveAchievements = new MutableLiveData<>();

    mDatabase.addChildEventListener(new FirebaseAchievementsRepository.MyChildEventListener());
  }

  /**
   * Gets the observable LiveData of all the IStatistic objects in the database
   *
   * @return An observable object wrapping the list of IStatistic with all statistics
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
    this.mDatabase.addListenerForSingleValueEvent(
        new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            userAchievements = new ArrayList<>();

            dataSnapshot
                .getChildren()
                .forEach(
                    child -> {
                      // todo: check for correct values
                      IAchievement achievement = child.getValue(GameAchievement.class);
                      userAchievements.add(achievement);
                    });

            callback.onDataReceived(userAchievements);
          }

          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
  }

  /**
   * Updates a achievement in the database
   *
   * @param achievement The achievement to update based on its key
   */
  @Override
  public void put(IAchievement achievement) {
    throw new UnsupportedOperationException("Put operation is not supported for achievements");
  }

  /**
   * Add a achievement to the database
   *
   * @param achievement The achievement to add
   */
  @Override
  public void push(IAchievement achievement) {
    mDatabase.push().setValue(achievement);
  }

  /**
   * Remove a achievement from the database
   *
   * @param achievement The achievement to remove
   */
  @Override
  public void delete(IAchievement achievement) {
    throw new UnsupportedOperationException("Delete operation is not supported for achievements");
  }

  /** Remove all child objects */
  @Override
  public void deleteAll() {
    mDatabase.removeValue();
  }

  /** An implementation of ChildEventListener for PreferenceRepository */
  private class MyChildEventListener implements ChildEventListener {

    /**
     * A child was added to DB - add to LiveData to notify listeners
     *
     * @param dataSnapshot The snapshot of the changed data
     * @param s A string description of the change
     */
    @Override
    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
      IAchievement achievement = dataSnapshot.getValue(GameAchievement.class);

      userAchievements.add(achievement);

      updateLiveData();
    }

    /**
     * A child was changed in DB - change LiveData to notify listeners
     *
     * @param dataSnapshot The snapshot of the changed data
     * @param s A string description of the change
     */
    @Override
    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
      // Should never occur
    }

    /**
     * A child was removed from DB - remove from LiveData to notify listeners
     *
     * @param dataSnapshot The snapshot of the changed data
     */
    @Override
    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
      IAchievement achievement = dataSnapshot.getValue(GameAchievement.class);

      if (achievement != null) {
        userAchievements.removeIf(
            iterAchievement ->
                iterAchievement.getAchievementKey().equals(achievement.getAchievementKey()));

        updateLiveData();
      }
    }

    /**
     * A child was moved in DB - ignore
     *
     * @param dataSnapshot The snapshot of the changed data
     * @param s A string description of the change
     */
    @Override
    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}

    /**
     * Operation was cancelled - ignore
     *
     * @param databaseError The cancellation error
     */
    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {}

    /** Safely updates the live data */
    private void updateLiveData() {
      try {
        liveAchievements.setValue(userAchievements);
      } catch (IllegalStateException ex) {
        liveAchievements.postValue(userAchievements);
      }
    }
  }
}
