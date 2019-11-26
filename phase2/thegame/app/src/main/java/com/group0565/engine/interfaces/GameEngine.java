package com.group0565.engine.interfaces;

import com.group0565.engine.android.InputManager;
import com.group0565.engine.assets.GameAssetManager;
import com.group0565.math.Vector;

/**
 * Interface for abstraction from a concrete implementation of GameEngine
 */
public interface GameEngine {
    /**
     * Getter for the InputManager
     * @return The instance of the Input Manager
     */
    InputManager getInputManager();

    /**
     * Getter for gameAssetManager.
     *
     * @return The instance of the Input Manager
     */
    GameAssetManager getGameAssetManager();

    /**
     * Getter for the size of the drawable area.
     *
     * @return size of the drawable area.
     */
    Vector getSize();
}
