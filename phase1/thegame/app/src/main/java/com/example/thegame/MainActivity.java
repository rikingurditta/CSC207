package com.example.thegame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import users.IUsersService;
import users.UsersServiceFirebaseImpl;

public class MainActivity extends AppCompatActivity {
  IUsersService userService;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    userService = UsersServiceFirebaseImpl.getInstance();
  }

  /**
   * Go to a game based on the cardView that was pressed
   *
   * @param view The caller
   */
  public void goToGame(View view) {
    if (userService.isUserConnected()) {
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
        Toast toast = Toast.makeText(this, "Illegal operation", Toast.LENGTH_SHORT);
        toast.show();
      }
    } else {
      startActivity(userService.initiateSignIn());
    }
  }
}
