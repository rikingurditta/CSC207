package com.thegame.theme;

import android.content.res.Resources;

import com.example.thegame.R;
import com.thegame.TheGameApplication;

/**
 * A manager for app theme
 */
public class ThemeManager {
    /**
     * Gets the theme for the current context given by the selected user theme
     *
     * @param themeKey The theme key to use
     * @return The id of the theme
     */
    public static int getTheme(String themeKey) {
        Resources res = TheGameApplication.getInstance().getResources();

        if (themeKey.equals(res.getString(R.string.light_theme_id))) {
            return R.style.Theme_AppCompat_Light;
        } else if (themeKey.equals(res.getString(R.string.dark_theme_id))) {
            return R.style.Theme_AppCompat;
        } else {
            return R.style.Theme_AppCompat_Light;
        }
    }
}
