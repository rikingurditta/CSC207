package com.group0565.achievements;

import android.os.Handler;
import android.os.Looper;

import com.group0565.users.IUsersInteractor;

/** An injector that creates an Achievements Repository */
public class AchievementsRepositoryInjector {

  /**
   * Inject the caller with the statistics repository after getting user id from server
   *
   * @param listener A listener for success of injection
   */
  public static void inject(RepositoryInjectionListener listener) {
    // Make sure listener sits on main thread
//    Handler handler = new Handler(Looper.getMainLooper());
//    handler.post(
//        () ->
//            IUsersInteractor.getInstance()
//                .getUserObservable()
//                .observeForever(
//                    iUser -> {
//                      if (iUser.isConnected()) {
//                        listener.onSuccess(new FirebaseAchievementsRepository(iUser.getUid()));
//                      }
//                    }));

        listener.onSuccess(new MockAchievementsRepository());
  }

  /** The listener interface for the injection */
  public interface RepositoryInjectionListener {
    /**
     * Action to perform on successful return of repository
     *
     * @param repository The returned repository from the injector
     */
    void onSuccess(IAsyncAchievementsRepository repository);
  }
}
