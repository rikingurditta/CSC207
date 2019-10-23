package com.group0565.preferences;

/** An interface for game com.group0565.preferences */
interface IPreference {
  /**
   * Get the preference's name
   *
   * @return Preference name
   */
  String getPrefName();

  /**
   * Get the preference's value
   *
   * @return Preference value
   */
  String getPrefVal();

  /**
   * Set the preference's value
   *
   * @param value New value
   */
  void setValue(String value);
}
