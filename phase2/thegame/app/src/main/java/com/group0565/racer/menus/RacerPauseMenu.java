package com.group0565.racer.menus;

import com.group0565.engine.gameobjects.Button;
import com.group0565.engine.gameobjects.GameMenu;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.ObservationEvent;
import com.group0565.engine.interfaces.Paint;
import com.group0565.math.Vector;
import com.group0565.racer.core.RacerEngine;
import com.group0565.theme.Themes;

public class RacerPauseMenu extends GameMenu {
    private Button resumeButton;
    private Button exitButton;
    private RacerEngine engine;

    public RacerPauseMenu(Vector size, RacerEngine engine) {
        super(size);
        this.engine = engine;
    }

    public void init() {
        resumeButton = new Button(new Vector(520, 1600),
                new Vector(150, 150),
                getEngine()
                        .getGameAssetManager()
                        .getTileSheet("RacerButton", "RacerButton")
                        .getTile(0, 0),
                getEngine()
                        .getGameAssetManager()
                        .getTileSheet("RacerButton", "RacerButton")
                        .getTile(0, 0));
        resumeButton.registerObserver(this::observe);
        this.adopt(resumeButton);

        exitButton = new Button(null);
        this.adopt(exitButton);
    }

    public void observe(Observable observable, ObservationEvent observationEvent) {
        if (observable == resumeButton && observationEvent.isEvent(Button.EVENT_DOWN)) {
            engine.unPauseGame();
        }
        else if (observable == exitButton && observationEvent.getMsg().equals(Button.EVENT_DOWN)) {

        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Paint font = Paint.createInstance();
        font.setARGB(255, 255, 255, 255);
        font.setTextSize(96);

        canvas.drawRGB(0, 0, 0);
        canvas.drawText("PAUSED", 50, 200, font);
        canvas.drawText("Score: " + engine.getTotalTime(), 50, 400, font);
    }
}
