package com.group0565.engine.gameobjects;

import com.group0565.engine.interfaces.Observable;
import com.group0565.theme.Themes;

public class GlobalPreferences implements Observable {
    private Themes theme = Themes.LIGHT;
    private String language = "en";
    private double volume = 1.0;

    public GlobalPreferences() {
    }

    public GlobalPreferences(Themes theme, String language, double volume) {
        this.setTheme(theme);
        this.setLanguage(language);
        this.setVolume(volume);
    }

    public Themes getTheme() {
        return theme;
    }

    public void setTheme(Themes theme) {
        this.theme = theme;
        this.notifyObservers();
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
        this.notifyObservers();
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
        this.notifyObservers();
    }
}
