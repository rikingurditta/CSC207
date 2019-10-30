package com.group0565.racer;

import com.group0565.engine.gameobjects.GameObject;
import com.group0565.math.Vector;

abstract class Obstacle extends GameObject {

    String appearance;

    Obstacle(String appearance, GameObject parent, Vector position, boolean relative) {
        super(parent, position, relative);
        this.appearance = appearance;
    }
}
