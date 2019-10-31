package com.group0565.hitObjectsRepository;

import com.group0565.users.IUsersInteractor;

public class HitObjectsRepositoryInjector {

    /**
     * Inject the caller with the HitObjects repository after getting user id from server
     *
     * @param listener A listener for success of injection
     */
    public static void inject(RepositoryInjectionListener listener) {
        IUsersInteractor.getInstance()
                .getUserObservable()
                .observeForever(
                        iUser -> {
                            if (iUser.isConnected()) {
                                listener.onSuccess(new FirebaseSessionHitObjectsRepository(iUser.getUid()));
                            }
                        });
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
        void onSuccess(ISessionHitObjectsRepository repository);
    }
}