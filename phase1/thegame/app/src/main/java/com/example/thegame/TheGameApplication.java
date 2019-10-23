package com.example.thegame;

import android.app.Application;
import android.widget.Toast;

import com.group0565.errorHandlers.IErrorDisplayer;

/** A singleton wrapper for the Application. Provides global access to ApplicationContext */
public class TheGameApplication extends Application implements IErrorDisplayer {

  /** The single object instance */
  private static TheGameApplication instance;

  /**
   * Getter for the single instance
   *
   * @return The single instance of TheGameApplication
   */
  public static TheGameApplication getInstance() {
    return instance;
  }

  /** Creates the Context and instantiates the instance variable */
  @Override
  public void onCreate() {
    super.onCreate();
    instance = this;
  }

  /**
   * Display a message to the UI using a Toast
   *
   * @param message The text to be displayed
   */
  @Override
  public void DisplayMessage(String message) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show();
  }
}
