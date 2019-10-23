package com.example.thegame;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.auth.IdpResponse;

import com.group0565.users.IUser;
import com.group0565.users.IUsersService;
import com.group0565.users.NoUserException;
import com.group0565.users.UsersServiceFirebaseImpl;

public class LoginDemoActivity extends AppCompatActivity {

    private IUsersService IUsersService;
    private IUser currIUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        IUsersService = UsersServiceFirebaseImpl.getInstance();

        IUsersService
                .getUserObservable()
                .observe(
                        this,
                        new Observer<IUser>() {
                            @Override
                            public void onChanged(@Nullable IUser IUser) {
                                currIUser = IUser;
                                if (currIUser != null && currIUser.isConnected()) {
                                    if (IUser.getDisplayName() != null) {
                                        ((TextView) findViewById(R.id.nameView)).setText(IUser.getDisplayName());
                                    }
                                    else{
                                        ((TextView) findViewById(R.id.nameView)).setText(IUser.getEmail());
                                    }
                                } else {
                                    ((TextView) findViewById(R.id.nameView)).setText(null);
                                }
                            }
                        });
    }

    public void createSignInIntent(View view) {
        Intent signInIntent = IUsersService.initiateSignIn();

        startActivityForResult(signInIntent, IUsersService.RC_SIGN_IN);
    }

    public void deleteUser(View view) {
        try {
            IUsersService.delete();
        } catch (NoUserException e) {
            e.printStackTrace();
        }
    }

    public void signOut(View view) {
        IUsersService.signOut();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IUsersService.RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                System.out.println("success");
                // Successfully signed in
                //        IUsersService.getUserObservable();
                // ...
            } else {
                System.out.println("fail");
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }
}
