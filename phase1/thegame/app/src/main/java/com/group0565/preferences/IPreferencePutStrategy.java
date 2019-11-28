package com.group0565.preferences;

import android.content.SharedPreferences;

/**
 * A strategy pattern interface for putting the correct type into the SharedPreference dictionary
 *
 * @param <T> Object value type
 */
public interface IPreferencePutStrategy<T> {

    /**
     * Choose correct put strategy based on value type
     *
     * @param value The preference value
     * @return The correct subclass of IPreferencePutStrategy
     */
    static IPreferencePutStrategy chooseStrategy(Object value) {
        if (value instanceof Long) {
            return new PreferencePutStrategies.PreferencePutLong();
        } else if (value instanceof String) { // If String -> String
            return new PreferencePutStrategies.PreferencePutString();
        } else if (value instanceof Double) { // If Double -> Float
            return new PreferencePutStrategies.PreferencePutDouble();
        } else if (value instanceof Float) { // If Float -> Float
            return new PreferencePutStrategies.PreferencePutFloat();
        } else if (value instanceof Integer) { // If Int -> Int
            return new PreferencePutStrategies.PreferencePutInt();
        } else if (value instanceof Boolean) { // If Boolean -> Boolean
            return new PreferencePutStrategies.PreferencePutBool();
        } else { // Throw casting error if unexpected type
            throw new ClassCastException("Unsupported data type");
        }
    }

    /**
     * Put the given value into the editor
     *
     * @param key    The object key
     * @param value  The object value
     * @param editor The SharedPreference.Editor
     */
    void put(String key, T value, SharedPreferences.Editor editor);
}
