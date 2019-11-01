package com.group0565.preferences;

import java.util.List;

public class MockPreferencesInteractor implements IPreferenceInteractor {
  /**
   * Get the current selected theme
   *
   * @return The theme selected by the user
   */
  @Override
  public String getTheme() {
    return "LIGHT";
  }

  /**
   * Get the current selected language code
   *
   * @return The language code of the current selected language
   */
  @Override
  public String getLanguage() {
    return "en";
  }

  /**
   * Get the current selected volume
   *
   * @return The volume (0-100) selected by the user
   */
  @Override
  public int getVolume() {
    return 0;
  }

  /**
   * Add/Update a preference
   *
   * @param pref The preference to add/update
   */
  @Override
  public void updatePreference(IPreference pref) {
  }

  /**
   * Gets a preference with the given key
   *
   * @param prefKey      The key of the preference
   * @param defaultValue The default value in case value does not exist
   * @return The value of the preference
   */
  @Override
  public Object getPreference(String prefKey, Object defaultValue) {
    return null;
  }

  /**
   * Add/Update a list of preferences
   *
   * @param prefs The preferences to add/update
   */
  @Override
  public void updatePreferences(List<IPreference> prefs) {
  }
}
