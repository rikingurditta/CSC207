package com.group0565.preferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.PreferenceDataStore;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/** An extension of PreferenceDataStore that saves the data in Firebase Realtime Database */
public class FirebasePreferenceDataStore extends PreferenceDataStore {

  /** Interface for a Listener for the Firebase data query */
  public interface OnGetDataListener {
    /** The event to call after the data has been successfully received from Firebase */
    void onSuccess();

    /** The event to call in case of failure to receive data from Firebase */
    void onFailure();
  }

  /** A local list to host the preferences received from the db */
  private List<IPreference> userPreferences;

  /** A reference to the Firebase database */
  private DatabaseReference mDatabase;

  /**
   * Creates a new PreferenceDataStore
   *
   * @param currUser Current connected user
   * @param completeListener The listener to db draw event
   */
  public FirebasePreferenceDataStore(String currUser, OnGetDataListener completeListener) {

    userPreferences = new ArrayList<>();

    this.mDatabase =
        FirebaseDatabase.getInstance().getReference().child("users/" + currUser + "/preferences");

    mDatabase.addListenerForSingleValueEvent(new MyValueEventListener(completeListener));
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
    userPreferences.add(new UserPreference<>(key, value));
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
    userPreferences.add(new UserPreference<>(key, value));
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
    userPreferences.add(new UserPreference<>(key, value));
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
    userPreferences.add(new UserPreference<>(key, value));
    mDatabase.child(key).setValue(value);
  }

  /**
   * Retrieves a {@link String} value from the data store.
   *
   * @param key The name of the preference to retrieve
   * @param defValue Value to return if this preference does not exist in the storage
   * @return The value from the data store or the default return value
   * @see #putString(String, String)
   */
  @Nullable
  @Override
  public String getString(String key, @Nullable String defValue) {
    for (IPreference pref : userPreferences) {
      if (pref.getPrefName().equals(key)) {
        return (String) pref.getPrefVal();
      }
    }

    // return default value
    return super.getString(key, defValue);
  }

  /**
   * Retrieves an {@link Integer} value from the data store.
   *
   * @param key The name of the preference to retrieve
   * @param defValue Value to return if this preference does not exist in the storage
   * @return The value from the data store or the default return value
   * @see #putInt(String, int)
   */
  @Override
  public int getInt(String key, int defValue) {
    for (IPreference pref : userPreferences) {
      if (pref.getPrefName().equals(key)) {
        return (int) pref.getPrefVal();
      }
    }

    // return default value
    return super.getInt(key, defValue);
  }

  /**
   * Retrieves a {@link Float} value from the data store.
   *
   * @param key The name of the preference to retrieve
   * @param defValue Value to return if this preference does not exist in the storage
   * @return The value from the data store or the default return value
   * @see #putFloat(String, float)
   */
  @Override
  public float getFloat(String key, float defValue) {
    for (IPreference pref : userPreferences) {
      if (pref.getPrefName().equals(key)) {
        return (Float) pref.getPrefVal();
      }
    }

    // return default value
    return super.getFloat(key, defValue);
  }

  /**
   * Retrieves a {@link Boolean} value from the data store.
   *
   * @param key The name of the preference to retrieve
   * @param defValue Value to return if this preference does not exist in the storage
   * @return the value from the data store or the default return value
   * @see #getBoolean(String, boolean)
   */
  @Override
  public boolean getBoolean(String key, boolean defValue) {
    for (IPreference pref : userPreferences) {
      if (pref.getPrefName().equals(key)) {
        return (Boolean) pref.getPrefVal();
      }
    }

    // return default value
    return super.getBoolean(key, defValue);
  }

  /** Instance class implementing ValueEventListener for events after Firebase data querying */
  private class MyValueEventListener implements ValueEventListener {

    /** A reference to the OnGetDataListener with the callbacks */
    private final OnGetDataListener completeListener;

    /**
     * Creates a new MyValueEventListener
     *
     * @param completeListener The callbacks to perform
     */
    MyValueEventListener(OnGetDataListener completeListener) {
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

                userPreferences.add(pref);
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
