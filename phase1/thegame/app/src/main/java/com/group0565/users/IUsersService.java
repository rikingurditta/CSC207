package com.group0565.users;

import android.content.Intent;

import androidx.lifecycle.LiveData;

/** Interface for com.group0565.users service */
public interface IUsersService {

  /** The Request Code */
  int RC_SIGN_IN = 123;

  /**
   * Creates the SignIn Intent - opens a UI auth activity
   *
   * @return Firebase Sign In Intent
   */
  Intent initiateSignIn();

  /** Sign the current user out */
  void signOut();

  /**
   * Deletes the current user
   *
   * @throws NoUserException If no user is signed in
   */
  void delete() throws NoUserException;

  /**
   * Checks if there currently is a logged in user
   *
   * @return True if a user is signed in, false otherwise
   */
  boolean isUserConnected();

  /**
   * Gets the LiveData observable with the currently logged in user
   *
   * @return LiveData encapsulation of current user data
   */
  LiveData<IUser> getUserObservable();

  /**
   * Gets the currently logged in user
   *
   * @return current user data
   */
  IUser getUser();
}
