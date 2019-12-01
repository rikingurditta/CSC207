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

public class Lane extends GameMenu implements Observable {

    private boolean isOccupied = false;
    private ObstacleManager obstacleManager;
    private EventObserver buttonObserver;

    public Lane(Vector size, EventObserver buttonObserver) {
        super(size);
        this.buttonObserver = buttonObserver;
    }

    public void init() {
        this.build()
                .add("Button", new Button(
                        new Vector(100, 1750),
                        new Vector(150, 150),
                        getEngine()
                                .getGameAssetManager()
                                .getTileSheet("RacerButton", "RacerButton")
                                .getTile(0, 0),
                        getEngine()
                                .getGameAssetManager()
                                .getTileSheet("RacerButton", "RacerButton")
                                .getTile(0, 0)).build()
                                .registerObserver(buttonObserver)
                        .close()
                        )
                .add("ObstacleManager", (obstacleManager = new ObstacleManager(new Vector(150, 150), this)).build().close())
                        .addAlignment(HCenter, THIS, HCenter)
                        .addAlignment(Top, THIS, Top)
                .close();

        obstacleManager.registerObserver(this::observeObstacleManager);

    }

    public void setRacerLane(boolean occupied) {
        this.isOccupied = occupied;
    }

    public boolean getIsOccupied() {
        return this.isOccupied;
    }

    public void spawnObstacle() {
        obstacleManager.spawnObstacle();
    }

    public void observeObstacleManager(Observable observable, ObservationEvent observationEvent) {
        if (observationEvent.getMsg().equals("Collision")) {
            notifyObservers(observationEvent);
        }
    }
}
