package com.thegame.settings;

import com.group0565.preferences.IPreferenceInteractor;
import com.group0565.preferences.PreferencesInjector;

/**
 * Implementation of the SettingsPresenter
 */
class SettingsPresenterImp implements SettingsMVP.SettingsPresenter {
    /**
     * Reference to the attached view
     */
    private SettingsMVP.SettingsView settingsView;

    /**
     * Instantiate a new SettingsPresenterImp
     *
     * @param view The attached SettingsView
     */
    SettingsPresenterImp(SettingsMVP.SettingsView view) {
        this.settingsView = view;
    }

    /**
     * Destroy all references in this object
     */
    @Override
    public void onDestroy() {
        this.settingsView = null;
    }

    /**
     * Gets the current display language
     *
     * @return The current display language
     */
    @Override
    public String getDisplayLanguage() {
        IPreferenceInteractor prefInteractor = PreferencesInjector.inject();

        return prefInteractor.getLanguage();
    }

    /**
     * Gets the current display theme
     *
     * @return The current display theme
     */
    @Override
    public String getAppTheme() {
        IPreferenceInteractor prefInteractor = PreferencesInjector.inject();

        return prefInteractor.getTheme();
    }
}
