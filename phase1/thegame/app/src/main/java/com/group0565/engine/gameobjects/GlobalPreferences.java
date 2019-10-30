package com.group0565.engine.gameobjects;

public class GlobalPreferences {
    public Theme theme = Theme.LIGHT;
    public String language = "en";
    public double volume = 1.0;

    public GlobalPreferences() {
    }

    public GlobalPreferences(Theme theme, String language, double volume) {
        this.theme = theme;
        this.language = language;
        this.volume = volume;
    }

    public enum Theme {
        LIGHT, DARK
    }
}
