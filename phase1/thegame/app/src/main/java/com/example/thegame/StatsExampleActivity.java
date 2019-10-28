package com.example.thegame;

import com.group0565.engine.android.GameActivity;
import com.group0565.math.Vector;
import com.group0565.statistics.IAsyncStatisticsRepository;
import com.group0565.statistics.IStatistic;
import com.group0565.statistics.IStatisticFactory;
import com.group0565.statistics.StatisticRepositoryInjector;

import java.util.ArrayList;
import java.util.List;

public class StatsExampleActivity extends GameActivity {
  private static final String TAG = "MainActivity";

  /** A list of the game's statistics */
  List<IStatistic> myGameStatistics;

  /** The repository to interact with the stats DB */
  IAsyncStatisticsRepository myStatRepo;

  /** Create a STRONG reference to the listener so it won't get garbage collected */
  StatisticRepositoryInjector.RepositoryInjectionListener listener;

  public StatsExampleActivity() {
    super(new MainObject(new Vector()));

    listener =
        repository -> {
          myStatRepo = repository;

          // Get and observe a list of statistics
          // The inside of this lambda expression is called once on listener connection and then
          // again for every update in the db. Note that if you update the db it will also call
          // this
          // function therefore no need to manually update the local myGameStatistics list
          myStatRepo
              .getObservable()
              .observe(
                  this,
                  statistics -> {
                    myGameStatistics = statistics;
                  });

          // If calling from a non-lifeCycleOwner (if "this" isn't accepted in the observe()),
          // use the following instead:
          //              myStatRepo.getObservable().observeForever(statistics -> {
          //                  myGameStatistics = statistics;
          //              });

          // Don't put this here - only for example
          updateDB();
        };

    // Initialize to empty in case there are no statistics in DB
    // Also makes sure game can continue working without waiting for server
    myGameStatistics = new ArrayList<>();

    // Asynchronously retrieve the statistic repository - the things in the lambda expression (the
    // function after -> ) HAPPEN OUTSIDE THE REGULAR FLOW!!!!!!!!
    StatisticRepositoryInjector.inject(TAG, listener);
  }

  /** Update the DB with a new stat */
  private void updateDB() {
    if (myStatRepo != null) {
      // You can always use put (also for new objects) because of the way that Firebase DB works
      myStatRepo.put(IStatisticFactory.createGameStatistic("newStat", 50));
    }
  }
}
