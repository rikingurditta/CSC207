package com.group0565.preferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/** A Firebase implementation of the IAsyncPreferencesRepository */
class FirebasePreferenceRepository implements IAsyncPreferencesRepository {

  /** A reference to the Firebase database */
  private DatabaseReference mDatabase;

  /** A collection of the user preferences */
  private List<IPreference> userPreferences;

  /** An observable live collection of the user preferences */
  private MutableLiveData<List<IPreference>> livePreferences;

  /**
   * Create a new repository for the given user
   *
   * @param currUser The current user
   */
  FirebasePreferenceRepository(String currUser) {

    this.mDatabase =
        FirebaseDatabase.getInstance().getReference().child("users/" + currUser + "/preferences");

    userPreferences = new ArrayList<>();
    livePreferences = new MutableLiveData<>();

    mDatabase.addChildEventListener(new MyChildEventListener());
  }

  /**
   * Gets the observable LiveData of all the IPreference objects in the database
   *
   * @return An observable object wrapping the list of IPreference with all preferences
   */
  @Override
  public LiveData<List<IPreference>> getObservable() {
    return livePreferences;
  }

  /**
   * Updates a preference in the database
   *
   * @param preference The preference to update based on its key
   */
  @Override
  public void put(IPreference preference) {
    mDatabase.child(preference.getPrefName()).setValue(preference.getPrefVal());
  }

  /**
   * Add a preference to the database
   *
   * @param preference The preference to add
   */
  @Override
  public void push(IPreference preference) {
    mDatabase.child(preference.getPrefName()).setValue(preference.getPrefVal());
  }

  /**
   * Remove a preference from the database
   *
   * @param preference The preference to remove
   */
  @Override
  public void delete(IPreference preference) {
    mDatabase.child(preference.getPrefName()).removeValue();
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
      String preferenceName = dataSnapshot.getKey();
      Object preferenceValue = dataSnapshot.getValue();

      userPreferences.add(UserPreferenceFactory.getUserPreference(preferenceName, preferenceValue));

      livePreferences.setValue(userPreferences);
    }

    /**
     * A child was changed in DB - change LiveData to notify listeners
     *
     * @param dataSnapshot The snapshot of the changed data
     * @param s A string description of the change
     */
    @Override
    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
      String preferenceName = dataSnapshot.getKey();
      Object preferenceValue = dataSnapshot.getValue();

      for (IPreference iterPref : userPreferences) {
        if (iterPref.getPrefName().equals(preferenceName)) {
          iterPref.setValue(preferenceValue);
        }
      }

      livePreferences.setValue(userPreferences);
    }

    /**
     * A child was removed from DB - remove from LiveData to notify listeners
     *
     * @param dataSnapshot The snapshot of the changed data
     */
    @Override
    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
      String preferenceName = dataSnapshot.getKey();

      userPreferences.removeIf(preference -> preference.getPrefName().equals(preferenceName));

      livePreferences.setValue(userPreferences);
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
  }
}
