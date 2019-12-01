package com.group0565.racer.menus;

import android.graphics.Color;

import com.group0565.engine.gameobjects.Button;
import com.group0565.engine.gameobjects.GameMenu;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.ObservationEvent;
import com.group0565.engine.interfaces.Paint;
import com.group0565.engine.render.LanguageText;
import com.group0565.math.Vector;
import com.group0565.racer.core.RacerEngine;
import com.group0565.theme.Themes;

public class RacerGameOverMenu extends GameMenu {
  private RacerEngine engine;

  public RacerGameOverMenu(Vector size, RacerEngine engine) {
    super(size);
    this.engine = engine;
  }

  @Override
  public void draw(Canvas canvas) {
    super.draw(canvas);
    Paint font = Paint.createInstance();
    font.setColor(Color.WHITE);
    font.setTextSize(96);
    canvas.drawRGB(0, 0, 0);

    LanguageText gameOver =
        new LanguageText(
            getGlobalPreferences(), getEngine().getGameAssetManager(), "Racer", "Game Over");
    LanguageText score =
        new LanguageText(
            getGlobalPreferences(), getEngine().getGameAssetManager(), "Racer", "Score");

    canvas.drawText(gameOver.getValue(), 50, 200, font);
    canvas.drawText(score.getValue() + engine.getTotalTime(), 50, 400, font);
  }
}
