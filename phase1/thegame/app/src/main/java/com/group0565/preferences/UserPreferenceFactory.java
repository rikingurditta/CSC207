package com.group0565.preferences;

/** A factory to create the correct UserPreference based on runtime value type */
public class UserPreferenceFactory {
  /**
   * Create a new UserPreference with the given key and value, and with T dependant on
   * preferenceValue.getClass()
   *
   * @param preferenceKey The preference's key
   * @param preferenceValue The preference's value
   * @return An UserPreference of type depending on preferenceValue
   */
  public static UserPreference getUserPreference(String preferenceKey, Object preferenceValue) {
    UserPreference pref;

    if (preferenceValue instanceof Long) { // If Long -> Integer
      pref = new UserPreference<>(preferenceKey, ((Long) preferenceValue).intValue());
    } else if (preferenceValue instanceof String) { // If String -> String
      pref = new UserPreference<>(preferenceKey, (String) preferenceValue);
    } else if (preferenceValue instanceof Double) { // If Double -> Float
      pref = new UserPreference<>(preferenceKey, ((Double) preferenceValue).floatValue());
    } else if (preferenceValue instanceof Boolean) { // If Boolean -> Boolean
      pref = new UserPreference<>(preferenceKey, (Boolean) preferenceValue);
    } else { // Throw casting error if unexpected type
      throw new ClassCastException("Unsupported data type");
    }

    return pref;
  }
}
