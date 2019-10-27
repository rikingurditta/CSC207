package com.group0565.preferences;

import androidx.annotation.Nullable;
import androidx.preference.PreferenceDataStore;

import java.util.ArrayList;
import java.util.List;

/** Base class for Async implementations of PreferenceDataStore */
public abstract class AsyncPreferenceDataStore extends PreferenceDataStore {

  /** Interface for a Listener for the Firebase single data query */
  public interface OnSingleGetDataListener {
    /** The event to call after the data has been successfully received from Firebase */
    void onSuccess();

    /** The event to call in case of failure to receive data from Firebase */
    void onFailure();
  }

  /** Interface for a Listener for the Firebase single data query */
  public interface OnRealtimeChildListener {
    /**
     * The event to call on creation of new child
     *
     * @param preference The preference added
     */
    void onPreferenceAdd(IPreference preference);

    /**
     * The event to call on update of child
     *
     * @param preference The updated preference
     */
    void onPreferenceChanged(IPreference preference);

    /**
     * The event to call on removal of child
     *
     * @param preference The preference removed
     */
    void onPreferenceRemoved(IPreference preference);
  }

  /** A local list to host the preferences received from the db */
  private List<IPreference> userPreferences;

  /** Creates a new AsyncDataStore */
  AsyncPreferenceDataStore() {
    userPreferences = new ArrayList<>();
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
    updatePreference(new UserPreference<>(key, value));
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
    updatePreference(new UserPreference<>(key, value));
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
    updatePreference(new UserPreference<>(key, value));
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
    updatePreference(new UserPreference<>(key, value));
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

  /**
   * Update preference to the local preferences store
   *
   * @throws ClassCastException if trying to change preference type
   * @param preference The updated preference
   */
  void updatePreference(IPreference preference) throws ClassCastException {
    for (IPreference iterPref : userPreferences) {
      if (iterPref.getPrefName().equals(preference.getPrefName())) {
        iterPref.setValue(preference.getPrefVal());
        return;
      }
    }

    userPreferences.add(preference);
  }
}
