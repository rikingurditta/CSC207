package com.group0565.preferences;

public class MockPreferencesInteractor implements IPreferenceInteractor {
  /**
   * Get the current selected theme
   *
   * @return The theme selected by the user
   */
  @Override
  public String getTheme() {
    return "Light";
  }

  /**
   * Get the current selected language code
   *
   * @return The language code of the current selected language
   */
  @Override
  public String getLanguage() {
    return "EN";
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
}
