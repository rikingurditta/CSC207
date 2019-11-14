package com.group0565.menuUI.achievements;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Toast;

import com.example.thegame.R;
import com.group0565.achievements.IAchievement;
import com.group0565.menuUI.locale.LocaleManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The class for the Achievements Activity
 */
public class AchievementsActivity extends AppCompatActivity
        implements AchievementsMVP.AchievementsView {

    /**
     * The AchievementsPresenter reference
     */
    AchievementsMVP.AchievementsPresenter achievementsPresenter;

    /**
     * A reference to the RecyclerView
     */
    RecyclerView achievementsRecycler;

    /**
     * On attach of base context, set language for user selected language
     *
     * @param base The base context
     */
    @Override
    protected void attachBaseContext(Context base) {
        achievementsPresenter = AchievementsPresenterInjector.inject(this);

        super.attachBaseContext(
                LocaleManager.updateResources(base, achievementsPresenter.getDisplayLanguage()));
    }

    /**
     * On creation of activity, set all references
     *
     * @param savedInstanceState The object Android state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setActivityTheme();

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_achievements);

        achievementsRecycler = findViewById(R.id.achievementsRecyclerView);
        achievementsRecycler.setLayoutManager(new LinearLayoutManager(this));

        achievementsPresenter.getGameAchievementsRepo();
    }

    /**
     * Set the Achievements in the recycler by connecting an adapter
     *
     * @param data The data to put in the recycler
     */
    @Override
    public void setAchievements(List<IAchievement> data) {
        AchievementsRecyclerAdapter gameAdapter =
                new AchievementsRecyclerAdapter(new AchievementsRowsPresenterImp(data));

        achievementsRecycler.setAdapter(gameAdapter);
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
        achievementsPresenter.onDestroy();
    }

    /**
     * Sets the activity's theme to the given theme ID
     */
    public void setActivityTheme() {
        setTheme(achievementsPresenter.getAppTheme());
  }
}
