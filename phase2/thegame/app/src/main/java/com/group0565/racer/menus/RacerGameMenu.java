package com.group0565.racer.menus;

import com.group0565.engine.gameobjects.GameMenu;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.Paint;
import com.group0565.racer.core.RacerEngine;
import com.group0565.theme.Themes;

public class RacerGameMenu extends GameMenu implements Observable {

    private RacerEngine engine;

    public RacerGameMenu(RacerEngine engine) {
        super(null);
        this.engine = engine;
    }

    @Override
    public void init() {
        super.init();

    }

    /**
     * Renders this object on the screen
     *
     * @param canvas The Canvas on which to draw
     */
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Paint time = Paint.createInstance();
        if (getGlobalPreferences().getTheme() == Themes.LIGHT) {
            // Set background to white
            canvas.drawRGB(255, 255, 255);
            // Set text colour to black
            time.setARGB(255, 0, 0, 0);
        } else {
            // Set background to black
            canvas.drawRGB(0, 0, 0);
            // Set text colour to white
            time.setARGB(255, 255, 255, 255);
        }
        time.setTextSize(128);
        // Set the colour of the lines
        Paint colour = Paint.createInstance();
        colour.setARGB(255, 255, 0, 0);
        // Draw the red lines that separate the lanes
        canvas.drawRect(canvas.getWidth() / 3 - 15, 0, canvas.getWidth() / 3 + 15, 2500, colour);
        canvas.drawRect(
                2 * canvas.getWidth() / 3 - 15, 0, 2 * canvas.getWidth() / 3 + 15, 2500, colour);
        canvas.drawText(Long.toString(engine.getTotalTime()), 600, 200, time);
    }
}