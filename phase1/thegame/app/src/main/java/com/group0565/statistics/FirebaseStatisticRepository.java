package com.group0565.statistics;

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

/**
 * A Firebase implementation of the IAsyncPreferencesRepository
 */
class FirebaseStatisticRepository implements IAsyncStatisticsRepository {

  /** A reference to the Firebase database */
  private DatabaseReference mDatabase;

  /** A collection of the user preferences */
  private List<IStatistic> userStatistics;

  /** An observable live collection of the user preferences */
  private MutableLiveData<List<IStatistic>> liveStatistics;

  /**
   * Create a new repository for the given user
   *
   * @param currUser The current user
   * @param gameName The current game
   */
  FirebaseStatisticRepository(String currUser, String gameName) {
    this.mDatabase =
            FirebaseDatabase.getInstance()
                    .getReference()
                    .child("users/" + currUser + "/statistics/" + gameName);

    userStatistics = new ArrayList<>();
    liveStatistics = new MutableLiveData<>();

    mDatabase.addChildEventListener(new FirebaseStatisticRepository.MyChildEventListener());
  }

  /**
   * Gets the observable LiveData of all the IStatistic objects in the database
   *
   * @return An observable object wrapping the list of IStatistic with all statistics
   */
  @Override
  public LiveData<List<IStatistic>> getObservable() {
    return liveStatistics;
  }

  /**
   * Gets the non-observable list of all objects using a callback
   *
   * @param callback The callback to execute on success
   */
  @Override
  public void getAll(AsyncDataListCallBack<IStatistic> callback) {
    this.mDatabase.addListenerForSingleValueEvent(
            new ValueEventListener() {
              @Override
              public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userStatistics = new ArrayList<>();

                dataSnapshot
                        .getChildren()
                        .forEach(
                                child -> {
                                  IStatistic preference =
                                          IStatisticFactory.createGameStatistic(child.getKey(), child.getValue());
                                  userStatistics.add(preference);
                                });

                callback.onDataReceived(userStatistics);
              }

              @Override
              public void onCancelled(@NonNull DatabaseError databaseError) {
              }
            });
  }

  /**
   * Updates a stat in the database
   *
   * @param stat The stat to update based on its key
   */
  @Override
  public void put(IStatistic stat) {
    mDatabase.child(stat.getStatKey()).setValue(stat.getStatVal());
  }

  /**
   * Add a stat to the database
   *
   * @param stat The stat to add
   */
  @Override
  public void push(IStatistic stat) {
    mDatabase.child(stat.getStatKey()).setValue(stat.getStatVal());
  }

  /**
   * Remove a stat from the database
   *
   * @param stat The stat to remove
   */
  @Override
  public void delete(IStatistic stat) {
    mDatabase.child(stat.getStatKey()).removeValue();
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
      String statKey = dataSnapshot.getKey();
      Object statValue = dataSnapshot.getValue();

      userStatistics.add(new GameStatistic<>(statKey, statValue));

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
      String statKey = dataSnapshot.getKey();
      Object statValue = dataSnapshot.getValue();

      for (IStatistic iterStats : userStatistics) {
        if (iterStats.getStatKey().equals(statKey)) {
          iterStats.setValue(statValue);
        }
      }

      updateLiveData();
    }

    /**
     * A child was removed from DB - remove from LiveData to notify listeners
     *
     * @param dataSnapshot The snapshot of the changed data
     */
    @Override
    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
      String statKey = dataSnapshot.getKey();

      userStatistics.removeIf(preference -> preference.getStatKey().equals(statKey));

      updateLiveData();
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
        liveStatistics.setValue(userStatistics);
      } catch (IllegalStateException ex) {
        liveStatistics.postValue(userStatistics);
      }
    }
  }
}
