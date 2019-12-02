package com.group0565.tsu.core;

import com.group0565.engine.android.GameActivity;

/** Entry Point for Tsu */
public class TsuActivity extends GameActivity {
  /** Constructor required by android */
  public TsuActivity() {
    // Pass in a copy of TsuGame to act as the root object
    super(new TsuGame());
  }
}
