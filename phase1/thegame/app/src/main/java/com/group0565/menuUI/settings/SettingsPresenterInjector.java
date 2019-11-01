package com.group0565.menuUI.settings;

/**
 * An injector for the SettingsPresenter
 */
class SettingsPresenterInjector {
    /**
     * Injects the caller with an implementation of SettingsPresenter
     *
     * @param view The view to be associated with the SettingsPresenter
     * @return An instance of a SettingsPresenter
     */
    public static SettingsMVP.SettingsPresenter inject(SettingsMVP.SettingsView view) {
        return new SettingsPresenterImp(view);
    }
}
