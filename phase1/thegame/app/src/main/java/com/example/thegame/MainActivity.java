package com.example.thegame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.group0565.errorHandlers.ExceptionErrorHandler;
import com.group0565.errorHandlers.IErrorDisplayer;
import com.group0565.errorHandlers.IErrorHandler;
import com.group0565.users.IUsersService;
import com.group0565.users.UsersServiceFirebaseImpl;

public class MainActivity extends AppCompatActivity implements IErrorDisplayer {

  /** Reference to the UserService */
  IUsersService mUserInterface = UsersServiceFirebaseImpl.getInstance();

  /** Reference to the ErrorHandler */
  IErrorHandler<Exception> mErrorHandler = ExceptionErrorHandler.getInstance();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  /**
   * Go to a game based on the cardView that was pressed
   *
   * @param view The caller
   */
  public void goToGame(View view) {
    if (mUserInterface.isUserConnected()) {
      Class targetActivity = null;

      // Check which card called me
      CardView sendingCard = (CardView) view;
      switch (sendingCard.getId()) {
        case R.id.CardGame1:
          //        targetActivity = Game1Activity.class;
          break;
        case R.id.CardGame2:
          //        targetActivity = Game2Activity.class;
          break;
        case R.id.CardGame3:
          //        targetActivity = Game3Activity.class;
          break;
        default:
          break;
      }

      try {
        // Try to go to selected game
        Intent intent = new Intent(this, targetActivity);
        startActivity(intent);
      } catch (NullPointerException ex) {
        mErrorHandler.Alert(this, ex, "Illegal operation");
      }
    } else {
      startActivity(mUserInterface.initiateSignIn());
    }
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
}
