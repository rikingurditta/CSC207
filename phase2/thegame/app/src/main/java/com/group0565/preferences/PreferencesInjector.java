package com.group0565.preferences;

/**
 * An injector for preference interactors
 */
public class PreferencesInjector {
    /**
     * Inject an object of type IPreferenceInteractor
     *
     * @return A subclass of IPreferenceInteractor
     */
    public static IPreferenceInteractor inject() {
//    return new SPPreferencesInteractor();

        return new SPPreferencesInteractor();
    }
}
