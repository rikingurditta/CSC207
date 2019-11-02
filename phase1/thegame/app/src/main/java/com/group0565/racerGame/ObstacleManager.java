package com.group0565.racerGame;

import com.group0565.engine.gameobjects.GameObject;

/**
 * An ObstacleManager that manages Obstacle objects
 */
public class ObstacleManager extends GameObject {

    /**
     * The RacerGame that this ObstacleManager is adopted by
     */
    RacerGame parent;

    /**
     * A constructor for this ObstacleManager object
     *
     * @param parent the RacerGame that is this object's parent
     */
    ObstacleManager(RacerGame parent) {
        this.parent = parent;
    }

    /**
     * Randomly spawns a new Obstacle for the game
     */
    void spawnObstacle() {
        // Randomly decide which type of Obstacle to spawn
        double d = Math.random();
        // Randomly decide which lane to spawn an Obstacle in
        int randomLane = 1 + (int) (Math.random() * ((3 - 1) + 1));
        // Spawn Obstacle object according to RNG values
        if (d < 0.5) {
            this.adopt(new SquareObstacle(randomLane, 0, this));
        } else {
            this.adopt(new CircleObstacle(randomLane, 0, this));
        }
  }
}
