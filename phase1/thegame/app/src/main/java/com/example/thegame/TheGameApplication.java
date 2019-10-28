package com.example.thegame;

import android.app.Application;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.preference.PreferenceManager;

import com.group0565.errorHandlers.IErrorDisplayer;
import com.group0565.preferences.IAsyncPreferencesRepository;
import com.group0565.preferences.IPreference;
import com.group0565.preferences.IPreferencePutStrategy;
import com.group0565.preferences.PreferenceRepositoryInjector;
import com.group0565.preferences.UserPreferenceFactory;
import com.group0565.users.IUsersInteractor;

import java.util.List;
import java.util.Objects;

/** A singleton wrapper for the Application. Provides global access to ApplicationContext */
public class TheGameApplication extends Application implements IErrorDisplayer {

  /** The single object instance */
  private static TheGameApplication instance;

  /** A reference to the SharedPreference in use */
  private SharedPreferences preferences;

  /** A strong reference to the listener so it want get garbage collected */
  private MyOnSharedPreferenceChangeListener listener;

  /**
   * Getter for the single instance
   *
   * @return The single instance of TheGameApplication
   */
  public static TheGameApplication getInstance() {
    return instance;
  }

  /**
   * Expose SharedPreference to entire app
   *
   * @return SharedPreference
   */
  public SharedPreferences getPreferences() {
    return preferences;
  }

  /**
   * Creates the Context and instantiates the instance variable, sets a listener to preference
   * changes when user is connected
   */
  @Override
  public void onCreate() {
    super.onCreate();
    instance = this;

    this.preferences = PreferenceManager.getDefaultSharedPreferences(instance);

    IUsersInteractor mUserInteractor = IUsersInteractor.getInstance();

    mUserInteractor
        .getUserObservable()
        .observeForever(
            iUser -> {
              if (iUser.isConnected()) {
                listenToPreferenceChanges(iUser.getUid());
              }
            });
  }

  /**
   * Attach listener to SharedPreference to put changes into db
   *
   * @param userID The user ID of the target user
   */
  private void listenToPreferenceChanges(String userID) {
    IAsyncPreferencesRepository rep = PreferenceRepositoryInjector.inject(userID);

    // If already listening, remove the existing listener (sign out and sign in as another user)
    preferences.unregisterOnSharedPreferenceChangeListener(listener);

    listener = new MyOnSharedPreferenceChangeListener(rep);

    rep.getObservable().observeForever(this::editSharedPreferences);

    preferences.registerOnSharedPreferenceChangeListener(listener);
  }

  /**
   * Edit the SharedPreference with the given preferences
   *
   * @param iPreferences A list of all the preferences from the db
   */
  private void editSharedPreferences(List<IPreference> iPreferences) {
    SharedPreferences.Editor prefEditor = preferences.edit();
    for (IPreference pref : iPreferences) {
      Object preferenceValue = pref.getPrefVal();
      String preferenceKey = pref.getPrefName();

      // If preference already in sharedPreference then exit
      if (Objects.equals(preferences.getAll().get(preferenceKey), pref.getPrefVal())) {
        continue;
      }

      IPreferencePutStrategy putStrategy;

      putStrategy = IPreferencePutStrategy.chooseStrategy(preferenceValue);

      putStrategy.put(preferenceKey, preferenceValue, prefEditor);
    }

    prefEditor.apply();
  }

  /**
   * Display a message to the UI using a Toast
   *
   * @param message The text to be displayed
   */
  @Override
  public void DisplayMessage(String message) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show();
  }

  /** A listener for changes in SharedPreferences */
  private static class MyOnSharedPreferenceChangeListener
      implements SharedPreferences.OnSharedPreferenceChangeListener {
    /** An instance of the PreferenceRepository */
    private final IAsyncPreferencesRepository rep;

    /**
     * Creates a new MyOnSharedPreferenceChangeListener with the given db repository
     *
     * @param rep The given repository
     */
    MyOnSharedPreferenceChangeListener(IAsyncPreferencesRepository rep) {
      this.rep = rep;
    }

    /**
     * Update the db preference based on the change in sharedPreferences
     *
     * @param sharedPreferences The SharedPreference object currently in use
     * @param key The preference key
     */
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
      rep.put(
          UserPreferenceFactory.getUserPreference(key, sharedPreferences.getAll().get(key)));
    }
  }
}
