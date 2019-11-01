package com.group0565.preferences;

import android.content.SharedPreferences;
import android.content.res.Resources;

import com.example.thegame.R;
import com.menu.TheGameApplication;

import java.util.List;
import java.util.Objects;

class SPPreferencesInteractor implements IPreferenceInteractor {
  /**
   * Get the current selected theme
   *
   * @return The theme selected by the user
   */
  @Override
  public String getTheme() {
    Resources resources = TheGameApplication.getInstance().getResources();

    return (String)
        getPreference(
            resources.getString(R.string.theme_pref_id),
            resources.getString(R.string.def_theme_pref));
  }

  /**
   * Get the current selected language code
   *
   * @return The language code of the current selected language
   */
  @Override
  public String getLanguage() {
    Resources resources = TheGameApplication.getInstance().getResources();

    return (String)
        getPreference(
            resources.getString(R.string.lan_pref_id), resources.getString(R.string.def_lan_pref));
  }

  /**
   * Get the current selected volume
   *
   * @return The volume (0-100) selected by the user
   */
  @Override
  public int getVolume() {
    Resources resources = TheGameApplication.getInstance().getResources();
    return (int)
        getPreference(
            resources.getString(R.string.vol_pref_id),
            resources.getInteger(R.integer.def_vol_pref));
  }

  /**
   * Add/Update a preference
   *
   * @param pref The preference to add/update
   */
  @Override
  public void updatePreference(IPreference pref) {
    SharedPreferences sp = TheGameApplication.getInstance().getPreferences();
    SharedPreferences.Editor prefEditor = sp.edit();
    Object preferenceValue = pref.getPrefVal();
    String preferenceKey = pref.getPrefName();

    IPreferencePutStrategy putStrategy;

    putStrategy = IPreferencePutStrategy.chooseStrategy(preferenceValue);

    putStrategy.put(preferenceKey, preferenceValue, prefEditor);

    prefEditor.apply();
  }

  /**
   * Add/Update a list of preferences
   *
   * @param prefs The preferences to add/update
   */
  @Override
  public void updatePreferences(List<IPreference> prefs) {
    SharedPreferences sp = TheGameApplication.getInstance().getPreferences();
    SharedPreferences.Editor prefEditor = sp.edit();
    for (IPreference pref : prefs) {

      Object preferenceValue = pref.getPrefVal();
      String preferenceKey = pref.getPrefName();

      IPreferencePutStrategy putStrategy;

      putStrategy = IPreferencePutStrategy.chooseStrategy(preferenceValue);

      putStrategy.put(preferenceKey, preferenceValue, prefEditor);
    }

    prefEditor.apply();
  }

  /**
   * Gets a preference with the given key
   *
   * @param prefKey The key of the preference
   * @param defaultValue The default value in case value does not exist
   * @return The value of the preference
   */
  @Override
  public Object getPreference(String prefKey, Object defaultValue) {
    SharedPreferences sp = TheGameApplication.getInstance().getPreferences();

    Object value = sp.getAll().get(prefKey);

    if (value != null) {
      return value;
    }

    return defaultValue;
  }
}
