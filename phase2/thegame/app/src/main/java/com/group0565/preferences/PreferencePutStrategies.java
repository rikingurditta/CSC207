package com.group0565.preferences;

import android.content.SharedPreferences;

/** A wrapper class for the strategy design pattern to deal with persistence put calls */
class PreferencePutStrategies {
  /** The strategy for float values */
  public static class PreferencePutFloat implements IPreferencePutStrategy<Float> {
    /**
     * Put the given value into the editor
     *
     * @param value The object value
     * @param editor The SharedPreference.Editor
     */
    @Override
    public void put(String key, Float value, SharedPreferences.Editor editor) {
      editor.putFloat(key, value);
    }
  }

  /** The strategy for int values */
  public static class PreferencePutInt implements IPreferencePutStrategy<Integer> {

    /**
     * Put the given value into the editor
     *
     * @param key The object key
     * @param value The object value
     * @param editor The SharedPreference.Editor
     */
    @Override
    public void put(String key, Integer value, SharedPreferences.Editor editor) {
      editor.putInt(key, value);
    }
  }

  /** The strategy for string values */
  public static class PreferencePutString implements IPreferencePutStrategy<String> {

    /**
     * Put the given value into the editor
     *
     * @param key The object key
     * @param value The object value
     * @param editor The SharedPreference.Editor
     */
    @Override
    public void put(String key, String value, SharedPreferences.Editor editor) {
      editor.putString(key, value);
    }
  }

  /** The strategy for bool values */
  public static class PreferencePutBool implements IPreferencePutStrategy<Boolean> {

    /**
     * Put the given value into the editor
     *
     * @param key The object key
     * @param value The object value
     * @param editor The SharedPreference.Editor
     */
    @Override
    public void put(String key, Boolean value, SharedPreferences.Editor editor) {
      editor.putBoolean(key, value);
    }
  }

  /** The strategy for long values */
  public static class PreferencePutLong implements IPreferencePutStrategy<Long> {

    /**
     * Put the given value into the editor
     *
     * @param key The object key
     * @param value The object value
     * @param editor The SharedPreference.Editor
     */
    @Override
    public void put(String key, Long value, SharedPreferences.Editor editor) {
      editor.putInt(key, value.intValue());
    }
  }

  /** The strategy for double values */
  public static class PreferencePutDouble implements IPreferencePutStrategy<Double> {

    /**
     * Put the given value into the editor
     *
     * @param key The object key
     * @param value The object value
     * @param editor The SharedPreference.Editor
     */
    @Override
    public void put(String key, Double value, SharedPreferences.Editor editor) {
      editor.putFloat(key, value.floatValue());
    }
  }
}
