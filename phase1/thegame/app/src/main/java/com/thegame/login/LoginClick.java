package com.thegame.login;

import android.content.Context;
import android.view.View;

import com.group0565.users.FirebaseUILoginInteractor;

public class LoginClick implements View.OnClickListener {

    private final Context mContext;

    public LoginClick(Context context){
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
