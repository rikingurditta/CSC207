package com.group0565.racer;

import com.group0565.engine.gameobjects.GameObject;

abstract class Obstacle extends GameObject {

    String appearance;

    Obstacle(String appearance) {
        this.appearance = appearance;
    }


}
