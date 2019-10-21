package preferences;

/** An interface for game preferences */
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
   * Get the preference's key
   *
   * @return Preference key
   */
  String getPrefKey();
}
