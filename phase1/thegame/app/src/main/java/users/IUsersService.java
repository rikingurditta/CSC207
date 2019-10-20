package users;

import android.content.Intent;

import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.OnCompleteListener;

/** Interface for users service */
public interface IUsersService {

  /** The Request Code */
  int RC_SIGN_IN = 123;

  /**
   * Creates the Firebase UI SignIn Intent - opens the UI built-in auth window
   *
   * @return Firebase Sign In Intent
   */
  Intent signIn();

  /** Sign the current user out */
  void signOut();

  /**
   * Deletes the current user
   *
   * @param listener Method to perform OnComplete
   * @throws NoUserException If no user is signed in
   */
  void delete(OnCompleteListener<Void> listener) throws NoUserException;

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
  LiveData<IUser> getUser();
}
