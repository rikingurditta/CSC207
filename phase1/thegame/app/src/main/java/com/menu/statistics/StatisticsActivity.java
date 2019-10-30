package com.menu.statistics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;

import android.os.Bundle;
import android.widget.Toast;

import com.example.thegame.R;

public class StatisticsActivity extends AppCompatActivity implements StatisticsMVP.StatisticsView {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_statistics);
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
