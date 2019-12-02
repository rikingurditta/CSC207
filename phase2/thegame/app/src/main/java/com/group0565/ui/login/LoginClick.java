package com.group0565.ui.login;

import android.content.Context;
import android.view.View;

import com.group0565.users.FirebaseUILoginInteractor;

/** An OnClickListener for callbacks to perform on Login, called from login menu button */
public class LoginClick implements View.OnClickListener {

  /** The calling context */
  private final Context mContext;

  /**
   * Instantiate a new LoginClick with the given context
   *
   * @param context The context of caller
   */
  public LoginClick(Context context) {
    this.mContext = context;
  }

  /**
   * Called when a view has been clicked.
   *
   * @param v The view that was clicked.
   */
  @Override
  public void onClick(View v) {
    FirebaseUILoginInteractor.getInstance().initiateSignIn(mContext);
  }
}
