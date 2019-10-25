package com.example.thegame.main;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.LifecycleOwner;

import com.example.thegame.R;
import com.example.thegame.login.LoginClick;
import com.example.thegame.main.MainMVP.MainPresenter;
import com.example.thegame.main.MainMVP.MainView;
import com.group0565.users.FirebaseUILoginInteractor;

/** The MainActivity class */
public class MainActivity extends AppCompatActivity implements MainView {

  /** The MainPresenter reference */
  MainPresenter mainPresenter;

  /** The FirebaseUILoginInteractor reference */
  FirebaseUILoginInteractor fireBaseLogin;

  /** Set references to all objects and instantiate presenter */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mainPresenter = new MainPresenterImp(this);
    fireBaseLogin = FirebaseUILoginInteractor.getInstance();
  }

  /** Destroy all references in this object */
  @Override
  protected void onDestroy() {
    super.onDestroy();
    mainPresenter.onDestroy();
  }

  /**
   * Execute on CardView click
   *
   * @param view The calling CardView
   */
  public void onClick(View view) {
    CardView sendingCard = (CardView) view;
    mainPresenter.selectGame(GameID.valueOf(sendingCard.getId()));
  }

  /**
   * Display a message to the UI with the given text
   *
   * @param message The text to be displayed
   */
  @Override
  public void DisplayMessage(String message) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show();
  }

  /**
   * Get a LifeCycleOwner to enable LiveData observation
   *
   * @return A LifeCycleOwner
   */
  @Override
  public LifecycleOwner getLifeCycleOwner() {
    return this;
  }

  /** Show the "no user connected" screen */
  @Override
  public void showNoUserScreen() {
    setContentView(R.layout.no_user_layout);
    Button signIn = (Button) findViewById(R.id.logInBtn);
    LoginClick lg = new LoginClick(this);

    signIn.setOnClickListener(lg);
  }

  /** Show the normal Main screen */
  @Override
  public void showNormalScreen() {
    setContentView(R.layout.activity_main);
  }

  /** Redirect to game 1 activity */
  @Override
  public void goToGame1() {
    throw new UnsupportedOperationException("Operation not yet implemented");
    //    Intent intent = new Intent(this, new Class<>());
    //    startActivity(intent);
  }

  /** Redirect to game 2 activity */
  @Override
  public void goToGame2() {
    throw new UnsupportedOperationException("Operation not yet implemented");
    //    Intent intent = new Intent(this, new Class<>());
    //    startActivity(intent);
  }

  /** Redirect to game 3 activity */
  @Override
  public void goToGame3() {
    throw new UnsupportedOperationException("Operation not yet implemented");
    //    Intent intent = new Intent(this, new Class<>());
    //    startActivity(intent);
  }
}
