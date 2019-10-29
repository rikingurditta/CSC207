package com.group0565.preferences;

import android.content.SharedPreferences;
import android.content.res.Resources;

import com.example.thegame.R;
import com.thegame.TheGameApplication;

class SPPreferencesInteractor implements IPreferenceInteractor {
    /**
     * Get the current selected theme
     *
     * @return The theme selected by the user
     */
    @Override
    public String getTheme() {
        SharedPreferences sp = TheGameApplication.getInstance().getPreferences();
        Resources resources = TheGameApplication.getInstance().getResources();
        return sp.getString(
                resources.getString(R.string.theme_pref_id), resources.getString(R.string.def_theme_pref));
    }

    /**
     * Get the current selected language code
     *
     * @return The language code of the current selected language
     */
    @Override
    public String getLanguage() {
        SharedPreferences sp = TheGameApplication.getInstance().getPreferences();
        Resources resources = TheGameApplication.getInstance().getResources();
        return sp.getString(
                resources.getString(R.string.lan_pref_id), resources.getString(R.string.def_lan_pref));
    }

    /**
     * Get the current selected volume
     *
     * @return The volume (0-100) selected by the user
     */
    @Override
    public int getVolume() {
        SharedPreferences sp = TheGameApplication.getInstance().getPreferences();
        Resources resources = TheGameApplication.getInstance().getResources();
        return sp.getInt(
                resources.getString(R.string.vol_pref_id), resources.getInteger(R.integer.def_vol_pref));
    }
}
