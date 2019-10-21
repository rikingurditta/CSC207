package com.example.thegame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import preferences.UserPreference;
import preferences.UserPreferenceDao;

public class PreferenceDemoActivity extends AppCompatActivity {
  private UserPreferenceDao myPrefDao;

  private List<UserPreference> userPrefs;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_preference_demo);

    userPrefs = new ArrayList<>();
    myPrefDao = new UserPreferenceDao("sean");

    myPrefDao
        .getObservableList()
        .observe(
            this,
            new Observer<List<UserPreference>>() {
              @Override
              public void onChanged(List<UserPreference> resultUserPreferences) {
                userPrefs = resultUserPreferences;
                if (userPrefs.size() != 0) {
                  ((TextView) findViewById(R.id.colorTextView))
                      .setText(userPrefs.get(0).getPrefVal());
                }
              }
            });

    UserPreference colorPref = new UserPreference("color", "black");

    myPrefDao.post(colorPref);

    colorPref.setValue("blue");

    myPrefDao.put(colorPref);

    myPrefDao.delete(colorPref.getPrefName());
  }
}
