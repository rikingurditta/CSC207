package com.group0565.users;

import android.content.Context;

import com.firebase.ui.auth.AuthUI;

import java.util.Collections;
import java.util.List;

/** A Firebase implementation for ILoginInteractor using Firebase AuthUI */
public class FirebaseUILoginInteractor implements ILoginInteractor {

  /** Singleton instance */
  private static FirebaseUILoginInteractor instance;

  /** Reference to AuthUI.Instance */
  private AuthUI firebaseAuthUI;

  /** A list of the used log in providers */
  private List<AuthUI.IdpConfig> providers =
      Collections.singletonList(new AuthUI.IdpConfig.EmailBuilder().build());

  /** Initialize the interactor and get reference to Firebase UI */
  private FirebaseUILoginInteractor() {
    firebaseAuthUI = AuthUI.getInstance();
  }

  /**
   * Returns the singleton instance of this class, instantiates if needed
   *
   * @return The instance of this class
   */
  public static FirebaseUILoginInteractor getInstance() {
    if (instance == null) {
      instance = new FirebaseUILoginInteractor();
    }

    return instance;
  }

  /** Creates the Firebase UI SignIn Intent - opens the UI built-in auth window */
  public void initiateSignIn(Context activity) {
    // Starts a new login activity from the given activity
    activity.startActivity(
        firebaseAuthUI.createSignInIntentBuilder().setAvailableProviders(providers).build());
  }
}
