package com.group0565.statistics;

/**
 * An injector that creates a Statistics Repository
 */
public class StatisticRepositoryInjector {

    /**
     * Inject the caller with the statistics repository after getting user id from server
     *
     * @param gameName The caller game's name
     * @param listener A listener for success of injection
     */
    public static void inject(String gameName, RepositoryInjectionListener listener) {
//    IUsersInteractor.getInstance()
//        .getUserObservable()
//        .observeForever(
//            iUser -> {
//              if (iUser.isConnected()) {
//                listener.onSuccess(new FirebaseStatisticRepository(iUser.getUid(), gameName));
//              }
//            });

        listener.onSuccess(new MockStatisticRepository());
    }

    /**
     * The listener interface for the injection
     */
    public interface RepositoryInjectionListener {
        /**
         * Action to perform on successful return of repository
         *
         * @param repository The returned repository from the injector
         */
        void onSuccess(IAsyncStatisticsRepository repository);
    }
}