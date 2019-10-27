package com.group0565.preferences;

/** * An injector for PreferenceDataStore */
public class PreferenceDataStoreInjector {
  /**
   * Returns the instance of FirebasePreferenceDataStore
   *
   * @param listener The OnSingleGetDataListener with callback to perform on data querying
   * @return An instance of AsyncPreferenceDataStore
   */
  public static AsyncPreferenceDataStore getDataStore(
      AsyncPreferenceDataStore.OnSingleGetDataListener listener) {
    return FirebasePreferenceDataStore.getInstance(listener);
  }
}
