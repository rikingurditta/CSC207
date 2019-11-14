package com.group0565.users;

import androidx.lifecycle.LiveData;

/** Interface for com.group0565.users service */
public interface IUsersInteractor {

  /**
   * Get a IUserInteractor object
   *
   * @return The Firebase implementation of IUserInteractor
   */
  static IUsersInteractor getInstance() {
    return UsersInteractorFirebaseImpl.getInstance();
  }

  /**
   * Gets the LiveData observable with the currently logged in user
   *
   * @return LiveData encapsulation of current user data
   */
  LiveData<IUser> getUserObservable();
}
