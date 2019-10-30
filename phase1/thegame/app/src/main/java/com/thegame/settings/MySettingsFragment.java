package com.thegame.settings;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.thegame.R;

/** The fragment that displays User Preference Settings */
public class MySettingsFragment extends PreferenceFragmentCompat {
  /**
   * A reference to the calling view to refresh
   */
  private RefreshCallback callback;

  /**
   * Called when a fragment is first attached to its context. {@link #onCreate(Bundle)} will be
   * called after this.
   *
   * @param context
   */
  @Override
  public void onAttach(@NonNull Context context) {
    super.onAttach(context);
    if (context instanceof RefreshCallback) {
      callback = (RefreshCallback) context;
    }
  }

  /**
   * Called upon creation of the Preferences fragment, sets up the
   *
   * @param savedInstanceState The saved state of the instance
   * @param rootKey The preference root key
   */
  @Override
  public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
    setPreferencesFromResource(R.xml.preferences, rootKey);

    listenToVolatilePrefs(getResources().getString(R.string.theme_pref_id));

    listenToVolatilePrefs(getResources().getString(R.string.lan_pref_id));
  }

  /**
   * Add a listener to refresh on change of preference
   *
   * @param prefKey The preference to attach to
   */
  private void listenToVolatilePrefs(String prefKey) {
    Preference pref = findPreference(prefKey);
    if (pref != null) {
      pref.setOnPreferenceChangeListener(
              (preference, newValue) -> {
                callback.refresh();
                return true;
              });
    }
  }

  /**
   * An interface for callback to refresh caller
   */
  interface RefreshCallback {
    /** Refresh the caller */
    void refresh();
  }
}
