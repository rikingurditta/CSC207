package com.example.thegame.settings;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.example.thegame.R;

/** The fragment that displays User Preference Settings */
public class MySettingsFragment extends PreferenceFragmentCompat {

  /**
   * Called upon creation of the Preferences fragment, sets up the
   *
   * @param savedInstanceState The saved state of the instance
   * @param rootKey The preference root key
   */
  @Override
  public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
    setPreferencesFromResource(R.xml.preferences, rootKey);
  }
}
