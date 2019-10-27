package com.example.thegame.settings;

import com.group0565.mvp.BaseMVP;
import com.group0565.preferences.AsyncPreferenceDataStore;

/** An interface for the Settings module MVP */
public interface SettingsMVP extends BaseMVP {
  /** An interface for the Settings presenter */
  interface SettingsPresenter extends BaseMVP.BasePresenter {
    /**
     * Gets an AsyncPreferenceDataStore
     *
     * @param rootKey The preference rootKey
     * @return An instance of AsyncPreferenceDataStore
     */
    AsyncPreferenceDataStore getDataStore(String rootKey);
  }

  /** An interface for the Settings view */
  interface SettingsView extends BaseMVP.BaseView {
    /**
     * Set the PreferenceDataStore for the preferences view
     *
     * @param rootKey The preference rootKey
     */
    void setDataStore(String rootKey);

    /**
     * Connects the view to the XML file of the preferences
     *
     * @param xmlID The xml resource ID
     * @param rootKey The preference rootKey
     */
    void setPreferencesFromResource(int xmlID, String rootKey);
  }
}
