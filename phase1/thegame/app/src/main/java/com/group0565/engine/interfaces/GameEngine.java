package com.group0565.engine.interfaces;

import com.group0565.engine.android.InputManager;
import com.group0565.engine.assets.GameAssetManager;

public interface GameEngine {
    InputManager getInputManager();
    GameAssetManager getGameAssetManager();
}
