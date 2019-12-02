package com.group0565.ui.statistics;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thegame.R;
import com.group0565.statistics.IStatistic;
import com.group0565.ui.locale.LocaleManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** The class for the Statistics Activity */
public class StatisticsActivity extends AppCompatActivity implements StatisticsMVP.StatisticsView {

  /** The StatisticsPresenter reference */
  StatisticsMVP.StatisticsPresenter statisticsPresenter;

  /** A map of recycler views to game names */
  Map<String, RecyclerView> gameRecyclerMap;

  /**
   * On attach of base context, set language for user selected language
   *
   * @param base The base context
   */
  @Override
  protected void attachBaseContext(Context base) {
    statisticsPresenter = StatisticsPresenterInjector.inject(this);

    super.attachBaseContext(
        LocaleManager.updateResources(base, statisticsPresenter.getDisplayLanguage()));
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

    setContentView(R.layout.activity_statistics);
    Resources resources = this.getResources();

    gameRecyclerMap = new HashMap<>();

    RecyclerView tsuStats = findViewById(R.id.recyclerViewTsu);
    tsuStats.setLayoutManager(new LinearLayoutManager(this));
    RecyclerView bomberStats = findViewById(R.id.recyclerViewBomber);
    bomberStats.setLayoutManager(new LinearLayoutManager(this));
    RecyclerView racerStats = findViewById(R.id.recyclerViewRacer);
    racerStats.setLayoutManager(new LinearLayoutManager(this));

    gameRecyclerMap.put(resources.getString(R.string.Game1Name), tsuStats);
    gameRecyclerMap.put(resources.getString(R.string.Game2Name), bomberStats);
    gameRecyclerMap.put(resources.getString(R.string.Game3Name), racerStats);

    statisticsPresenter.getGameStatRepo(resources.getString(R.string.Game1Name));
    statisticsPresenter.getGameStatRepo(resources.getString(R.string.Game2Name));
    statisticsPresenter.getGameStatRepo(resources.getString(R.string.Game3Name));
  }

  /**
   * Set the statistics in the recycler by connecting an adapter
   *
   * @param gameName The target game's name
   * @param data The data to put in the recycler
   */
  @Override
  public void setGameStats(String gameName, List<IStatistic> data) {
    StatisticsRecyclerAdapter gameAdapter =
        new StatisticsRecyclerAdapter(new StatisticsRowsPresenterImp(data));

    gameRecyclerMap.get(gameName).setAdapter(gameAdapter);
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

  /** Destroy all references in this object */
  @Override
  protected void onDestroy() {
    super.onDestroy();
    statisticsPresenter.onDestroy();
  }

  /** Sets the activity's theme to the given theme ID */
  public void setActivityTheme() {
    setTheme(statisticsPresenter.getAppTheme());
  }
}
