package com.group0565.racer.objects;

import com.group0565.engine.gameobjects.Button;
import com.group0565.engine.gameobjects.GameMenu;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.EventObserver;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.ObservationEvent;
import com.group0565.engine.interfaces.Paint;
import com.group0565.math.Vector;
import com.group0565.theme.Themes;

import java.util.ArrayList;

import static com.group0565.engine.enums.HorizontalEdge.*;
import static com.group0565.engine.enums.VerticalEdge.*;

/**
 * A Lane within Racer.
 */
public class Lane extends GameMenu implements Observable {

    /*
     * The size of the button in the Lane.
     */
    private static final Vector BUTTON_SIZE = new Vector(150, 150);

    /*
     * The position of the button in the Lane.
     */
    private static final Vector BUTTON_POSITION = new Vector(100, 1750);

    /*
     * The size of the obstacle manager in the Lane.
     */
    private static final Vector OBSTACLE_MANAGER_SIZE = new Vector(150, 150);

    /*
     * The observation message passed when a collision occurs.
     */
    private static final String COLLISION_MESSAGE = "Collision";

    /**
     * A boolean value that shows whether the Lane is occupied by the Racer.
     */
    private boolean isOccupied = false;

    /**
     * The ObstacleManager for the Lane.
     */
    private ObstacleManager obstacleManager;

    /**
     * The ButtonObserver for the Lane.
     */
    private EventObserver buttonObserver;

    public Lane(Vector size, EventObserver buttonObserver) {
        super(size);
        this.buttonObserver = buttonObserver;
    }

    /**
     * The initialization method for the Lane.
     */
    public void init() {
        this.build()
                .add("Button", new Button(
                                BUTTON_POSITION,
                                BUTTON_SIZE,
                        getEngine()
                                .getGameAssetManager()
                                .getTileSheet("Racer", "RacerButton")
                                .getTile(0, 0),
                        getEngine()
                                .getGameAssetManager()
                                .getTileSheet("Racer", "RacerButton")
                                .getTile(0, 0)).build()
                                .registerObserver(buttonObserver)
                        .close()
                        )
                .add("ObstacleManager", (obstacleManager = new ObstacleManager(OBSTACLE_MANAGER_SIZE, this)).build().close())
                        .addAlignment(HCenter, THIS, HCenter)
                        .addAlignment(Top, THIS, Top)
                .close();

        obstacleManager.registerObserver(this::observeObstacleManager);

    }

    /**
     * Set whether this Lane is occupied or not.
     *
     * @param occupied the boolean value to set occupied.
     */
    public void setRacerLane(boolean occupied) {
        this.isOccupied = occupied;
    }

    /**
     * Returns the occupied status of this Lane.
     *
     * @return the occupied status of this Lane.
     */
    public boolean getIsOccupied() {
        return this.isOccupied;
    }

    /**
     * Spawn an Obstacle in this Lane.
     */
    public void spawnObstacle() {
        obstacleManager.spawnObstacle();
    }

    /**
     * Observe when a collision has occured in this Lane.
     *
     * @param observable
     * @param observationEvent
     */
    private void observeObstacleManager(Observable observable, ObservationEvent observationEvent) {
        if (observationEvent.getMsg().equals(COLLISION_MESSAGE)) {
            notifyObservers(observationEvent);
        }
    }
}
