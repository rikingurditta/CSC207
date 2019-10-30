package com.thegame.settings;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;

import com.example.thegame.R;
import com.thegame.locale.LocaleManager;

/** The SettingsActivity class */
public class SettingsActivity extends AppCompatActivity implements SettingsMVP.SettingsView {

    /**
     * The MainPresenter reference
     */
    SettingsMVP.SettingsPresenter settingsPresenter;

    /**
     * On attach of base context, set language for user selected language
     *
     * @param base The base context
     */
    @Override
    protected void attachBaseContext(Context base) {
        settingsPresenter = SettingsPresenterInjector.inject(this);

        super.attachBaseContext(
                LocaleManager.updateResources(base, settingsPresenter.getDisplayLanguage()));
    }

    /**
     * Set references to all objects and instantiate presenter
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setActivityTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
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
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    /**
     * Destroy all references in this object
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        settingsPresenter.onDestroy();
    }

    /**
     * Refresh the view
     */
    @Override
    public void refresh() {
        recreate();
    }

    /**
     * Sets the activity's theme to the given theme ID
     */
    public void setActivityTheme() {
        setTheme(settingsPresenter.getAppTheme());
    }
}
