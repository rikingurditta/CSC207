package com.group0565.engine.gameobjects;

import com.group0565.engine.interfaces.EventObserver;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.ObservationEvent;
import com.group0565.theme.Themes;

public class GlobalPreferences implements Observable {
    public static final String THEME_CHANGE = "Theme Changed";
    public static final String VOLUME_CHANGE = "Volume Changed";
    public static final String LANGUAGE_CHANGE = "Language Changed";
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
        this.notifyObservers(new ObservationEvent<>(THEME_CHANGE, theme));
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
        this.notifyObservers(new ObservationEvent<>(LANGUAGE_CHANGE, language));
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
        this.notifyObservers(new ObservationEvent<>(VOLUME_CHANGE, volume));
    }

    @Override
    public void registerObserver(EventObserver eventObserver) {
        Observable.super.registerObserver(eventObserver);
        this.notifyObservers(new ObservationEvent<>(THEME_CHANGE, theme));
        this.notifyObservers(new ObservationEvent<>(LANGUAGE_CHANGE, language));
        this.notifyObservers(new ObservationEvent<>(VOLUME_CHANGE, volume));
    }
}
