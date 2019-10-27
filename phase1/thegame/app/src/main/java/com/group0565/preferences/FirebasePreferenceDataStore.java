package com.group0565.preferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.group0565.users.IUsersInteractor;

/** An extension of PreferenceDataStore that saves the data in Firebase Realtime Database */
public class FirebasePreferenceDataStore extends AsyncPreferenceDataStore {

  /** A reference to the single instance of the class */
  private static FirebasePreferenceDataStore instance;

  /** A reference to the Firebase database */
  private DatabaseReference mDatabase;

  /** A reference to the user interactor object */
  private IUsersInteractor mUserInteractor;

  /** Creates a new PreferenceDataStore */
  private FirebasePreferenceDataStore() {
    super();

    mUserInteractor = IUsersInteractor.getInstance();

    String currUser = mUserInteractor.getUserObservable().getValue().getUid();

    this.mDatabase =
        FirebaseDatabase.getInstance().getReference().child("users/" + currUser + "/preferences");
  }

  /**
   * Gets the singleton instance of the class
   *
   * @param completeListener The listener to db draw event
   * @return the instance
   */
  public static FirebasePreferenceDataStore getInstance(OnSingleGetDataListener completeListener) {
    if (instance == null) {
      instance = new FirebasePreferenceDataStore();
    }

    instance.addGetDataListener(completeListener);

    return instance;
  }

  private void addGetDataListener(OnSingleGetDataListener completeListener) {
    mDatabase.addListenerForSingleValueEvent(new MyValueSingleEventListener(completeListener));
  }

  public void addRealtimeChildChangesListener(OnRealtimeChildListener realTimeListener) {
    mDatabase.addChildEventListener(new MyChildEventListener(realTimeListener));
  }

  /**
   * Sets a {@link String} value to the data store.
   *
   * <p>Once the value is set the data store is responsible for holding it.
   *
   * @param key The name of the preference to modify
   * @param value The new value for the preference
   * @see #getString(String, String)
   */
  @Override
  public void putString(String key, @Nullable String value) {
    super.putString(key, value);
    mDatabase.child(key).setValue(value);
  }

  /**
   * Sets an {@link Integer} value to the data store.
   *
   * <p>Once the value is set the data store is responsible for holding it.
   *
   * @param key The name of the preference to modify
   * @param value The new value for the preference
   * @see #getInt(String, int)
   */
  @Override
  public void putInt(String key, int value) {
    super.putInt(key, value);
    mDatabase.child(key).setValue(value);
  }

  /**
   * Sets a {@link Float} value to the data store.
   *
   * <p>Once the value is set the data store is responsible for holding it.
   *
   * @param key The name of the preference to modify
   * @param value The new value for the preference
   * @see #getFloat(String, float)
   */
  @Override
  public void putFloat(String key, float value) {
    super.putFloat(key, value);
    mDatabase.child(key).setValue(value);
  }

  /**
   * Sets a {@link Boolean} value to the data store.
   *
   * <p>Once the value is set the data store is responsible for holding it.
   *
   * @param key The name of the preference to modify
   * @param value The new value for the preference
   * @see #getBoolean(String, boolean)
   */
  @Override
  public void putBoolean(String key, boolean value) {
    super.putBoolean(key, value);
    mDatabase.child(key).setValue(value);
  }

  /** Instance class implementing ChildEventListener for realtime Firebase events */
  private static class MyChildEventListener implements ChildEventListener {

    /** A reference to the OnRealtimeChildListener with the callbacks */
    private final OnRealtimeChildListener realTimeListener;

    /**
     * Creates a new MyChildEventListener
     *
     * @param realTimeListener The callbacks to perform
     */
    MyChildEventListener(OnRealtimeChildListener realTimeListener) {
      this.realTimeListener = realTimeListener;
    }

    /**
     * Received data from Firebase - new preference was added
     *
     * @param dataSnapshot The db snapshot of the changed data
     * @param s A string description of the change
     */
    @Override
    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
      String preferenceKey = dataSnapshot.getKey();
      Object preferenceValue = dataSnapshot.getValue();

      IPreference preference =
          UserPreferenceFactory.getUserPreference(preferenceKey, preferenceValue);

      realTimeListener.onPreferenceAdd(preference);
    }

    /**
     * Received data from Firebase - a preference was changed
     *
     * @param dataSnapshot The db snapshot of the changed data
     * @param s A string description of the change
     */
    @Override
    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
      String preferenceKey = dataSnapshot.getKey();
      Object preferenceValue = dataSnapshot.getValue();

      IPreference preference =
          UserPreferenceFactory.getUserPreference(preferenceKey, preferenceValue);

      realTimeListener.onPreferenceChanged(preference);
    }

    /**
     * Received data from Firebase - a preference was removed
     *
     * @param dataSnapshot The db snapshot of the changed data
     */
    @Override
    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
      String preferenceKey = dataSnapshot.getKey();
      Object preferenceValue = dataSnapshot.getValue();

      IPreference preference =
          UserPreferenceFactory.getUserPreference(preferenceKey, preferenceValue);

      realTimeListener.onPreferenceRemoved(preference);
    }

    /**
     * Received data from Firebase - a preference was moved
     *
     * @param dataSnapshot The db snapshot of the changed data
     * @param s A string description of the change
     */
    @Override
    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}

    /**
     * Received data from Firebase - error occurred on query
     *
     * @param databaseError The db error
     */
    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {}
  }

  /** Instance class implementing ValueEventListener for events after Firebase data querying */
  private class MyValueSingleEventListener implements ValueEventListener {

    /** A reference to the OnSingleGetDataListener with the callbacks */
    private final OnSingleGetDataListener completeListener;

    /**
     * Creates a new MyValueSingleEventListener
     *
     * @param completeListener The callbacks to perform
     */
    MyValueSingleEventListener(OnSingleGetDataListener completeListener) {
      this.completeListener = completeListener;
    }

    /**
     * Received data from Firebase - organize to Preference objects and inform listener
     *
     * @param dataSnapshot The db snapshot of the changed data
     */
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
      dataSnapshot
          .getChildren()
          .forEach(
              childSnap -> {
                Object preferenceValue = childSnap.getValue();
                String preferenceKey = childSnap.getKey();

                IPreference pref =
                    UserPreferenceFactory.getUserPreference(preferenceKey, preferenceValue);

                updatePreference(pref);
              });

      completeListener.onSuccess();
    }

    /**
     * Failed to query Firebase - alert listener
     *
     * @param databaseError The error that occurred
     */
    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
      completeListener.onFailure();
    }
  }
}
