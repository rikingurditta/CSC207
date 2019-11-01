package com.group0565.theme;

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
        try {
            return Themes.valueOf(themeKey).getValue();
        } catch (Exception ex) {
            return Themes.getDefault().getValue();
        }
  }
}
