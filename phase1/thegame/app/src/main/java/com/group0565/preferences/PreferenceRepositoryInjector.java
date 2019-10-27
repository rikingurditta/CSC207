package com.group0565.preferences;

/** * An injector for IAsyncPreferenceRepository */
public class PreferenceRepositoryInjector {
  /**
   * Returns the instance of FirebasePreferenceDataStore
   *
   * @param currUser The current user
   * @return An instance of IAsyncPreferenceRepository
   */
  public static IAsyncPreferencesRepository inject(String currUser) {
    return new FirebasePreferenceRepository(currUser);
  }
}
