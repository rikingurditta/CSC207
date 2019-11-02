package com.group0565.preferences;

/**
 * An implementation of the IPreference interface for user-specific com.group0565.preferences
 */
class UserPreference<T> implements IPreference<T> {

    /**
     * The preference's name
     */
    private String prefName;

    /**
     * The preference's value
     */
    private T prefVal;

    /**
     * Default constructor - DO NOT USE! REQUIRED FOR FIREBASE DB
     */
    public UserPreference() {
        // Default constructor required for calls to DataSnapshot.getPrefVal(UserPreference.class)
    }

    /**
     * Create a new UserPreference object
     *
     * @param prefName Preference Name
     * @param prefVal  Preference Value
     */
    UserPreference(String prefName, T prefVal) {
        this.prefName = prefName;
        this.prefVal = prefVal;
    }

    /**
     * Get the preference's name
     *
     * @return Preference name
     */
    @Override
    public String getPrefName() {
        return this.prefName;
    }

    /**
     * Get the preference's value
     *
     * @return Preference value
     */
    @Override
    public T getPrefVal() {
        return this.prefVal;
    }

    /**
     * Set the preference's value
     *
     * @param value New value
     */
    @Override
    public void setValue(T value) {
        this.prefVal = value;
  }
}
