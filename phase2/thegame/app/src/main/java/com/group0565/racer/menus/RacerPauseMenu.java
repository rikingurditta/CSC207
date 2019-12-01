package com.group0565.racer.menus;

import com.group0565.engine.gameobjects.Button;
import com.group0565.engine.gameobjects.GameMenu;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.ObservationEvent;
import com.group0565.engine.interfaces.Paint;
import com.group0565.engine.render.ThemedPaintCan;
import com.group0565.math.Vector;
import com.group0565.racer.core.RacerEngine;
import com.group0565.theme.Themes;

public class RacerPauseMenu extends GameMenu {
    private Button resumeButton;
    private Button exitButton;
    private RacerEngine engine;
    private static final ThemedPaintCan MESSAGE_PAINT_CAN = new ThemedPaintCan("Racer", "Message.Message");

    public RacerPauseMenu(Vector size, RacerEngine engine) {
        super(size);
        this.engine = engine;
    }

    public void init() {
        resumeButton = new Button(new Vector(520, 1600),
                new Vector(150, 150),
                getEngine()
                        .getGameAssetManager()
                        .getTileSheet("Racer", "RacerButton")
                        .getTile(0, 0),
                getEngine()
                        .getGameAssetManager()
                        .getTileSheet("Racer", "RacerButton")
                        .getTile(0, 0));
        resumeButton.registerObserver(this::observe);
        this.adopt(resumeButton);

        exitButton = new Button(null);
        this.adopt(exitButton);

        MESSAGE_PAINT_CAN.init(getGlobalPreferences(), getEngine().getGameAssetManager());
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
        canvas.drawRGB(0, 0, 0);
        canvas.drawText("PAUSED", new Vector(50, 200), MESSAGE_PAINT_CAN);
        canvas.drawText("Score: " + engine.getTotalTime(), new Vector(50, 400) , MESSAGE_PAINT_CAN);
    }
}
