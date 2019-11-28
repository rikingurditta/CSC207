package com.group0565.menuUI.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.LifecycleOwner;

import com.example.thegame.R;
import com.group0565.achievements.enums.Achievements;
import com.group0565.achievements.AchievementsRepositoryInjector;
import com.group0565.achievements.IAsyncAchievementsRepository;
import com.group0565.bomberGame.BomberMainActivity;
import com.group0565.menuUI.achievements.AchievementsActivity;
import com.group0565.racerGame.RacerMainActivity;
import com.group0565.menuUI.locale.LocaleManager;
import com.group0565.menuUI.login.LoginClick;
import com.group0565.menuUI.main.MainMVP.MainPresenter;
import com.group0565.menuUI.main.MainMVP.MainView;
import com.group0565.menuUI.main.enums.GameID;
import com.group0565.menuUI.main.enums.MenuOptionID;
import com.group0565.menuUI.settings.SettingsActivity;
import com.group0565.menuUI.statistics.StatisticsActivity;
import com.group0565.tsu.core.TsuActivity;

/** The MainActivity class */
public class MainActivity extends AppCompatActivity implements MainView {

  /** The MainPresenter reference */
  MainPresenter mainPresenter;

  /**
   * On attach of base context, set language for user selected language
   *
   * @param base The base context
   */
  @Override
  protected void attachBaseContext(Context base) {
    mainPresenter = MainPresenterInjector.inject(this);

    super.attachBaseContext(
        LocaleManager.updateResources(base, mainPresenter.getDisplayLanguage()));
  }

  IAsyncAchievementsRepository achievementsRepository;

  /** Set references to all objects and instantiate presenter */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    setActivityTheme();
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    AchievementsRepositoryInjector.inject(
        repository -> {
          achievementsRepository = repository;
          achievementsRepository.isNewAchievement(
              Achievements.BOMBER_ACHIEVEMENT.getValue(),
              isNew -> {
                if (isNew) {
                  achievementsRepository.push(Achievements.BOMBER_ACHIEVEMENT.getValue());
                } else {
                  // Achievement already in DB, no need to do anything
                }
              });
        });
  }

  /** Destroy all references in this object */
  @Override
  protected void onDestroy() {
    super.onDestroy();
    mainPresenter.onDestroy();
  }

  /** On activity restart, recreate the activity to check for preference changes */
  @Override
  public void onRestart() {
    super.onRestart();
    recreate();
  }

  /**
   * Execute on CardView click
   *
   * @param view The calling CardView
   */
  public void onClick(View view) {
    CardView sendingCard = (CardView) view;
    mainPresenter.selectGame(GameID.valueOf(sendingCard.getId()));
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
   * Get a LifeCycleOwner to enable LiveData observation
   *
   * @return A LifeCycleOwner
   */
  @Override
  public LifecycleOwner getLifeCycleOwner() {
    return this;
  }

  /** Sets the activity's theme by querying the */
  public void setActivityTheme() {
    setTheme(mainPresenter.getAppTheme());
  }

  /** Show the "no user connected" screen */
  @Override
  public void showNoUserScreen() {
    invalidateOptionsMenu();

    setContentView(R.layout.no_user_layout);
    Button signIn = findViewById(R.id.logInBtn);
    LoginClick lg = new LoginClick(this);

    signIn.setOnClickListener(lg);
  }

  /** Show the normal Main screen */
  @Override
  public void showNormalScreen() {
    invalidateOptionsMenu();

    setContentView(R.layout.activity_main);
  }

  /** Redirect to game 1 activity */
  @Override
  public void goToGame1() {
    Intent intent = new Intent(this, TsuActivity.class);
    startActivity(intent);
  }

  /** Redirect to game 2 activity */
  @Override
  public void goToGame2() {
    Intent intent = new Intent(this, BomberMainActivity.class);
    startActivity(intent);
  }

  /** Redirect to game 3 activity */
  @Override
  public void goToGame3() {
    Intent intent = new Intent(this, RacerMainActivity.class);
    startActivity(intent);
  }

  /** Redirect to settings activity */
  @Override
  public void goToSettings() {
    Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
    startActivity(intent);
  }

  /** Redirect to statistics activity */
  @Override
  public void goToStatistics() {
    Intent intent = new Intent(MainActivity.this, StatisticsActivity.class);
    startActivity(intent);
  }

  /** Redirect to statistics activity */
  @Override
  public void goToAchievements() {
    Intent intent = new Intent(MainActivity.this, AchievementsActivity.class);
    startActivity(intent);
  }

  /**
   * Create the options menu at the top right
   *
   * @param menu The given activity's menu
   * @return True if menu is set and false otherwise
   */
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater menuInflater = getMenuInflater();
    menuInflater.inflate(R.menu.settings_menu, menu);

    return mainPresenter.isMenuAvailable();
  }

  /**
   * Manage MenuItem clicks in the options menu
   *
   * @param item The item clicked
   * @return False to allow normal menu processing to proceed, true to consume it here.
   */
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();

    return mainPresenter.handleMenuClick(MenuOptionID.valueOf(id));
  }
}
