package com.example.thegame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import preferences.UserPreference;
import preferences.UserPreferenceDao;

public class PreferenceDemoActivity extends AppCompatActivity {
  private UserPreferenceDao myPrefDao;

  // todo: make LiveData
  private UserPreference colorPref;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_preference_demo);
    myPrefDao = new UserPreferenceDao("sean");
    colorPref = new UserPreference("color", "black");

    myPrefDao.post(colorPref);

    colorPref.setValue("blue");

    myPrefDao.put(colorPref);
//
//    colorPref = myPrefDao.get("-Lrezl6JECrZCDqxtAIc");
//
//    myPrefDao.delete("-Lrezl6JECrZCDqxtAIc");
//
//    List<UserPreference> all = myPrefDao.getAll();
  }
}
