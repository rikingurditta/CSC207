package com.group0565.statistics;

import android.os.Handler;
import android.os.Looper;

import com.group0565.users.IUsersInteractor;

/** An injector that creates a Statistics Repository */
public class StatisticRepositoryInjector {

  /**
   * Inject the caller with the statistics repository after getting user id from server
   *
   * @param gameName The caller game's name
   * @param listener A listener for success of injection
   */
  public static void inject(String gameName, RepositoryInjectionListener listener) {
      // Make sure listener sits on main thread
      Handler handler = new Handler(Looper.getMainLooper());
      handler.post(
              () ->
                      IUsersInteractor.getInstance()
                              .getUserObservable()
                              .observeForever(
                                      iUser -> {
                                          if (iUser.isConnected()) {
                                              listener.onSuccess(
                                                      new FirebaseStatisticRepository(iUser.getUid(), gameName));
                                          }
                                      }));

    //        listener.onSuccess(new MockStatisticRepository());
  }

  /** The listener interface for the injection */
  public interface RepositoryInjectionListener {
    /**
     * Action to perform on successful return of repository
     *
     * @param repository The returned repository from the injector
     */
    void onSuccess(IAsyncStatisticsRepository repository);
  }
}
