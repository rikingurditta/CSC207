package com.example.thegame.settings;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.lifecycle.LifecycleOwner;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

/** The fragment that displays User Preference Settings */
public class MySettingsFragment extends PreferenceFragmentCompat
    implements SettingsMVP.SettingsView {

  /** A context object for the preferences */
  private Context preferenceContext;

  private SettingsMVP.SettingsPresenter settingsPresenter;

  /**
   * Called upon creation of the Preferences fragment, sets up the DataStore object for the
   * Preferences
   *
   * @param savedInstanceState The saved state of the instance
   * @param rootKey The preference root key
   */
  @Override
  public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
    settingsPresenter = new SettingsPresenterImp(this);

    setDataStore(rootKey);
  }

  /**
   * Get a LifeCycleOwner to enable LiveData observation
   *
   * @return A LifeCycleOwner
   */
  @Override
  public LifecycleOwner getLifeCycleOwner() {
    return this;
  }

  /**
   * Display a message to the UI with the given text
   *
   * @param message The text to be displayed
   */
  @Override
  public void DisplayMessage(String message) {
    Toast.makeText(preferenceContext, message, Toast.LENGTH_LONG).show();
  }

  /** Set the PreferenceDataStore for the preferences view */
  @Override
  public void setDataStore(String rootKey) {
    PreferenceManager preferenceManager = getPreferenceManager();
    preferenceManager.setPreferenceDataStore(settingsPresenter.getDataStore(rootKey));
    preferenceContext = preferenceManager.getContext();
  }
}
