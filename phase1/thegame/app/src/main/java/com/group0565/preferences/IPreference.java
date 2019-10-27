package com.group0565.preferences;

public interface IPreference<T> {

    /**
     * Get the preference's name
     *
     * @return Preference name
     */
    String getPrefName();

    /**
     * Get the preference's value
     *
     * @return Preference value
     */
    T getPrefVal();

    /**
     * Set the preference's value
     *
     * @param value New value
     */
    void setValue(T value);
}
