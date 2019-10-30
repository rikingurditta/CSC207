package com.group0565.preferences;

/** An interface for consumption of Preferences in the app */
public interface IPreferenceInteractor {

  /**
   * Get the current selected theme
   *
   * @return The theme selected by the user
   */
  String getTheme();

  /**
   * Get the current selected language code
   *
   * @return The language code of the current selected language
   */
  String getLanguage();

  /**
   * Get the current selected volume
   *
   * @return The volume (0-100) selected by the user
   */
  int getVolume();
}
