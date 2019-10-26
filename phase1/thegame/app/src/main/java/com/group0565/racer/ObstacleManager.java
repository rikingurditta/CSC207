package com.group0565.racer;

import com.group0565.engine.gameobjects.GameObject;

import java.util.ArrayList;
import com.group0565.math.Vector;

class ObstacleManager extends GameObject {

    /**
     * An ArrayList containing all the obstacles in the game.
     */
    ArrayList<Object> obstacleList;

    /**
     * Constructs an ObstacleManager object
     * @param parent the parent of this ObstacleManager
     * @param position the co-ordinates of this object in vector form
     * @param relative boolean where true -> position vector is relative to the parent, false ->
     *                 position vector is absolute
     */

    ObstacleManager(GameObject parent, Vector position, boolean relative) {
        super(parent, position, relative);
        obstacleList = new ArrayList<>(1);
    }

    /**
     * Adds an Obstacle from ObstacleManager
     * @param obstacle an Obstacle Object to be added to the manager
     */
    void addObstacle(Obstacle obstacle) {
        obstacleList.add(obstacle);
    }

    /**
     * Removes an Obstacle from ObstacleManager
     * @param obstacle
     */
    void removeObstacle(Obstacle obstacle) {
        obstacleList.remove(obstacle);
    }
}
