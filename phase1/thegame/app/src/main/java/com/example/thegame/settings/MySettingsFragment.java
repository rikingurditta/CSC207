package com.example.thegame.settings;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.example.thegame.R;
import com.group0565.preferences.FirebasePreferenceDataStore;
import com.group0565.users.IUsersInteractor;

/** The fragment that displays User Preference Settings */
public class MySettingsFragment extends PreferenceFragmentCompat {
  /**
   * Called upon creation of the Preferences fragment, sets up the DataStore object for the
   * Preferences
   *
   * @param savedInstanceState
   * @param rootKey
   */
  @Override
  public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
    FirebasePreferenceDataStore dataStore =
        new FirebasePreferenceDataStore(
            IUsersInteractor.getInstance().getUserObservable().getValue().getUid(),
            new MyOnGetDataListener(rootKey));

    PreferenceManager preferenceManager = getPreferenceManager();
    preferenceManager.setPreferenceDataStore(dataStore);
  }

  /** An implementation of the OnGetDataListener for FirebasePreferenceDataStore */
  private class MyOnGetDataListener implements FirebasePreferenceDataStore.OnGetDataListener {
    private final String rootKey;

    /**
     * Create a new listener
     *
     * @param rootKey The given root key
     */
    public MyOnGetDataListener(String rootKey) {
      this.rootKey = rootKey;
    }

    /** The callback performed on successful call */
    @Override
    public void onSuccess() {
      setPreferencesFromResource(R.xml.preferences, rootKey);
    }

    /** The callback performed upon failed call */
    @Override
    public void onFailure() {
    }
  }
}
