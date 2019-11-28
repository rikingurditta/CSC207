package com.group0565.racer.core;

import com.group0565.engine.gameobjects.GameObject;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.Paint;
import com.group0565.math.Vector;
import com.group0565.theme.Themes;
import com.group0565.tsu.game.Beatmap;

public class RacerRenderer extends GameObject {
  private Vector size;
  private RacerEngine engine;

  public RacerRenderer(RacerEngine engine, Vector position, Beatmap beatmap, Vector size, long window) {
    super(position);
    this.engine = engine;
    this.size = size;
  }

  public Vector getSize() {
    return size;
  }

  public void setSize(Vector size) {
    this.size = size;
  }

  @Override
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

  public RacerEngine getRacerEngine() {
    return engine;
  }

  public void setRacerEngine(RacerEngine engine) {
    this.engine = engine;
  }
}
