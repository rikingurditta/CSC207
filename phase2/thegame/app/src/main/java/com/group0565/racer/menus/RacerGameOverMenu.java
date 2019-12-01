package com.group0565.racer.menus;

import com.group0565.engine.gameobjects.GameMenu;
import com.group0565.engine.interfaces.Canvas;

import com.group0565.engine.render.LanguageText;
import com.group0565.engine.render.ThemedPaintCan;
import com.group0565.math.Vector;
import com.group0565.racer.core.RacerEngine;

/**
 * A GameOver Menu
 */
public class RacerGameOverMenu extends GameMenu {

  /**
   * ThemedPaintCan for the background
   */
  private static final ThemedPaintCan GAME_OVER_PAINT_CAN = new ThemedPaintCan("Racer", "GameOver.GameOver");

  /**
   * ThemedPaintCan for the colour and text of messages
   */
  private static final ThemedPaintCan MESSAGE_PAINT_CAN = new ThemedPaintCan("Racer", "Message.Message");

  /**
   * The vector posiiton where the retry message is drawn
   */
  private static final Vector RETRY_POSITION = new Vector(50, 1600);

  /**
   * The vector position where the title 'Game Over' is drawn
   */
  private static final Vector GAME_OVER_POSITION = new Vector(50, 200);

  /**
   * The vector position where the player's final score is drawn
   */
  private static final Vector SCORE_POSITION = new Vector(50, 400);

  /**
   * The engine that holds this GameOverMenu
   */
  private RacerEngine engine;

  /**
   * Constructor for a RacerGameOverMenu
   * @param size the size of the menu
   * @param engine the engine that the menu is held by
   */
  public RacerGameOverMenu(Vector size, RacerEngine engine) {
    super(size);
    this.engine = engine;
  }

  /**
   * Initializes the ThemedPaintCans
   */
  public void init() {
    GAME_OVER_PAINT_CAN.init(getGlobalPreferences(), getEngine().getGameAssetManager());
    MESSAGE_PAINT_CAN.init(getGlobalPreferences(), getEngine().getGameAssetManager());
  }

  /**
   * Renders the GameOverMenu
   *
   * @param canvas the canvas this is drawn on
   */
  @Override
  public void draw(Canvas canvas) {
    super.draw(canvas);
    canvas.drawRGB(GAME_OVER_PAINT_CAN);

    LanguageText gameOver =
        new LanguageText(
            getGlobalPreferences(), getEngine().getGameAssetManager(), "Racer", "Game Over");
    LanguageText score =
        new LanguageText(
            getGlobalPreferences(), getEngine().getGameAssetManager(), "Racer", "Score");
    LanguageText retry =
            new LanguageText(
                    getGlobalPreferences(), getEngine().getGameAssetManager(), "Racer", "Retry");

    canvas.drawText(gameOver.getValue(), GAME_OVER_POSITION, GAME_OVER_PAINT_CAN);
    canvas.drawText(score.getValue() + engine.getTotalTime(), SCORE_POSITION, MESSAGE_PAINT_CAN);
    canvas.drawText(retry.getValue(), RETRY_POSITION, MESSAGE_PAINT_CAN);
  }
}
