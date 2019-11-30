package com.group0565.racer.core;

import android.content.res.Resources;

import com.example.thegame.R;
import com.group0565.engine.gameobjects.GlobalPreferences;
import com.group0565.engine.interfaces.EventObserver;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.ObservationEvent;
import com.group0565.menuUI.TheGameApplication;
import com.group0565.preferences.IPreferenceInteractor;
import com.group0565.preferences.PreferencesInjector;
import com.group0565.preferences.UserPreferenceFactory;
import com.group0565.theme.Themes;

/**
 * Class Responsible for reading and writing preferences to the repository;
 */
public class RacerPreferences extends GlobalPreferences implements Observable {
    public static final String DIFFICULTY_CHANGE = "Difficulty Changed";

    //Constants for the names of the preferences
    private String ThemePrefName = "";
    private String LanguagePrefName = "";
    private String DifficultyPrefName = "";

    //Local Copies of the preferences
    private float difficulty;

    /**
     * Reload the preferences to match the most updated preferences
     */
    public void reload(){
        Resources resources = TheGameApplication.getInstance().getResources();
        ThemePrefName = resources.getString(R.string.theme_pref_id);
        LanguagePrefName = resources.getString(R.string.lan_pref_id);
        DifficultyPrefName = "racer-difficulty";

        IPreferenceInteractor prefInter = PreferencesInjector.inject();

        super.setTheme(Themes.valueOf(prefInter.getTheme()));
        super.setLanguage(prefInter.getLanguage());
        Object difficulty = prefInter.getPreference(DifficultyPrefName, 5);
        if (!(difficulty instanceof Double) && !(difficulty instanceof Float))
            difficulty = 5D;
        if (difficulty instanceof Double)
            this.difficulty = ((float) (double) difficulty);
        else if (difficulty instanceof Float)
            this.difficulty = ((float) difficulty);
    }

    /**
     * Setter for the theme
     * @param theme The new theme
     */
    @Override
    public void setTheme(Themes theme) {
        super.setTheme(theme);
        setPreferences(ThemePrefName, theme.name());
    }

    /**
     * Setter for the language
     * @param language The new language
     */
    @Override
    public void setLanguage(String language) {
        super.setLanguage(language);
        setPreferences(LanguagePrefName, language);
    }

    /**
     * Getter for the difficulty
     * @return The difficulty
     */
    public float getDifficulty() {
        return difficulty;
    }

    /**
     * Setter for the difficulty
     * @param difficulty The new difficulty
     */
    public void setDifficulty(float difficulty) {
        this.difficulty = difficulty;
        this.notifyObservers(new ObservationEvent<>(DIFFICULTY_CHANGE, difficulty));
        setPreferences(DifficultyPrefName, difficulty);
    }

    @Override
    public void registerObserver(EventObserver eventObserver) {
        super.registerObserver(eventObserver);
        this.notifyObservers(new ObservationEvent<>(DIFFICULTY_CHANGE, difficulty));
    }

    /**
     * Helper method to write changes to the repository
     */
    private void setPreferences(String name, Object value) {
        IPreferenceInteractor interactor = PreferencesInjector.inject();
        interactor.updatePreference(UserPreferenceFactory.getUserPreference(name, value));
    }
}