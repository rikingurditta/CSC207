package com.group0565.users;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Firebase implementation of com.group0565.users service interface - a singleton
 */
public class UsersInteractorFirebaseImpl implements IUsersInteractor {

    /**
     * Singleton instance
     */
    private static UsersInteractorFirebaseImpl instance;

    /**
     * Mutable LiveData object for making user data observable
     */
    private final MutableLiveData<IUser> userLiveData;

    /**
     * Reference to FirebaseAuth.Instance
     */
    private FirebaseAuth firebaseAuth;

    /**
     * Creates a new instance UsersInteractorFirebaseImpl
     */
    private UsersInteractorFirebaseImpl() {
        firebaseAuth = FirebaseAuth.getInstance();

        userLiveData = new MutableLiveData<>();

        // Listen in to all changes in current user Auth status
        firebaseAuth.addAuthStateListener(firebaseAuth -> updateUserOnAuthChange());
    }

    /**
     * Returns the singleton instance of this class, instantiates if needed
     *
     * @return The instance of this class
     */
    static IUsersInteractor getInstance() {
        if (instance == null) {
            instance = new UsersInteractorFirebaseImpl();
        }
        return instance;
    }

    /**
     * Gets the LiveData observable with the currently logged in user
     *
     * @return LiveData encapsulation of current user data
     */
    @Override
    public LiveData<IUser> getUserObservable() {
        return userLiveData;
    }

    /**
     * Set the value of the user LiveData to alert observers to change
     */
    private void updateUserOnAuthChange() {
        userLiveData.setValue(new UserFirebaseImp());
  }
}
