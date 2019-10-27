package com.example.thegame.settings;

import com.example.thegame.R;
import com.group0565.preferences.AsyncPreferenceDataStore;
import com.group0565.preferences.PreferenceDataStoreInjector;

/** Implementation of the SettingPresenter */
public class SettingsPresenterImp implements SettingsMVP.SettingsPresenter {

  private SettingsMVP.SettingsView settingsView;

  SettingsPresenterImp(SettingsMVP.SettingsView settingsView) {
    this.settingsView = settingsView;
  }

  /** Destroy all references in this object */
  @Override
  public void onDestroy() {
    settingsView = null;
  }

  /**
   * Gets an AsyncPreferenceDataStore
   *
   * @return An instance of AsyncPreferenceDataStore
   */
  @Override
  public AsyncPreferenceDataStore getDataStore(String rootKey) {
    return PreferenceDataStoreInjector.getDataStore(new MyOnSingleGetDataListener(rootKey));
  }

  /** An implementation of the OnSingleGetDataListener for FirebasePreferenceDataStore */
  private class MyOnSingleGetDataListener
      implements AsyncPreferenceDataStore.OnSingleGetDataListener {
    private final String rootKey;

    /**
     * Create a new listener
     *
     * @param rootKey The given root key
     */
    MyOnSingleGetDataListener(String rootKey) {
      this.rootKey = rootKey;
    }

    /** The callback performed on successful call */
    @Override
    public void onSuccess() {
      settingsView.setPreferencesFromResource(R.xml.preferences, rootKey);
    }

    /** The callback performed upon failed call */
    @Override
    public void onFailure() {}
  }
}
