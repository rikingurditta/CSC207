package com.group0565.engine.interfaces;

import com.group0565.engine.android.InputManager;
import com.group0565.engine.assets.GameAssetManager;
import com.group0565.math.Vector;

public interface GameEngine {
  InputManager getInputManager();

  GameAssetManager getGameAssetManager();

  abstract Vector getSize();
}
