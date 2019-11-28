package com.group0565.users;

import android.content.Context;

/**
 * Defines the Login Interactor interface
 */
public interface ILoginInteractor {
    /**
     * The Request Code
     */
    int RC_SIGN_IN = 123;

    /**
     * Creates the Firebase UI SignIn Intent - opens the UI built-in auth window
     */
    void initiateSignIn(Context activity);
}
