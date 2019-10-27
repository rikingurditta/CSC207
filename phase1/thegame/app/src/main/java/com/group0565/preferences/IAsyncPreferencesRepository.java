package com.group0565.preferences;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import java.util.List;

/** An interface defining the repository for IPreferences */
public interface IAsyncPreferencesRepository {

  /**
   * Gets the observable LiveData of all the IPreference objects for the user in the database
   *
   * @return An observable object wrapping the list of IPreference with all preferences
   */
  LiveData<List<IPreference>> observeAllUserPreferences();

  /**
   * Updates a preference in the database for the user
   *
   * @param preference The preference to update based on its key
   */
  void updatePreference(IPreference preference);

  /**
   * Add a preference to the database for the user
   *
   * @param preference The preference to add
   */
  void addPreference(IPreference preference);

  /**
   * Remove a preference from the database for the user
   *
   * @param preference The preference to remove
   */
  void deletePreference(IPreference preference);
}
