package com.group0565.preferences;

import java.util.List;

/**
 * An interface for consumption of Preferences in the app
 */
public interface IPreferenceInteractor {

    /**
     * Get the current selected theme
     *
     * @return The theme selected by the user
     */
    String getTheme();

    /**
     * Get the current selected language code
     *
     * @return The language code of the current selected language
     */
    String getLanguage();

    /**
     * Get the current selected volume
     *
     * @return The volume (0-100) selected by the user
     */
    int getVolume();

    /**
     * Add/Update a preference
     *
     * @param pref The preference to add/update
     */
    void updatePreference(IPreference pref);

    /**
     * Gets a preference with the given key
     *
     * @param prefKey      The key of the preference
     * @param defaultValue The default value in case value does not exist
     * @return The value of the preference
     */
    Object getPreference(String prefKey, Object defaultValue);

    /**
     * Add/Update a list of preferences
     *
     * @param prefs The preferences to add/update
     */
    void updatePreferences(List<IPreference> prefs);
}
