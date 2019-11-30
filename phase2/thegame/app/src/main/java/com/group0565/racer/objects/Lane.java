package com.group0565.racer.objects;

import com.group0565.engine.gameobjects.Button;
import com.group0565.engine.gameobjects.GameMenu;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.EventObserver;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.Paint;
import com.group0565.math.Vector;
import com.group0565.theme.Themes;

import java.util.ArrayList;

import static com.group0565.engine.enums.HorizontalEdge.*;
import static com.group0565.engine.enums.VerticalEdge.*;

public class Lane extends GameMenu implements Observable {

    private boolean racerLane;
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
                .close();

    }

    public void setRacerLane(boolean racerLane) {
        this.racerLane = racerLane;
    }

    public boolean getRacerLane() {
        return this.racerLane;
    }

    public void spawnObstacle() {
        obstacleManager.spawnObstacle();
    }

    @Override
    public void draw(Canvas canvas) {
        com.group0565.engine.interfaces.Paint time = Paint.createInstance();
//        if (getGlobalPreferences().getTheme() == Themes.LIGHT) {
//            // Set background to white
//            canvas.drawRGB(255, 255, 255);
//            // Set text colour to black
//            time.setARGB(255, 0, 0, 0);
//        } else {
//            // Set background to black
//            canvas.drawRGB(0, 0, 0);
//            // Set text colour to white
//            time.setARGB(255, 255, 255, 255);
//        }
    }
}
