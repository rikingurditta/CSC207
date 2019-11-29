package com.group0565.bomberGame.menus;

import com.group0565.engine.gameobjects.GameMenu;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.render.LanguageText;
import com.group0565.engine.render.TextRenderer;
import com.group0565.engine.render.ThemedPaintCan;
import com.group0565.math.Vector;

/** Menu to show that the game is over and show them their game-end options (e.g. replay). */
public class GameOverMenu extends GameMenu {
  /** PaintCan for normal text. */
  private ThemedPaintCan textPaintCan = new ThemedPaintCan("Bomber", "Text.Text");

  /** PaintCan for the body of the menu. Temporarily using Bomb explosion PaintCan. */
  private ThemedPaintCan bgPaintCan = new ThemedPaintCan("Bomber", "Bomb.Explosion");
  // TODO: make new PaintCan for menu body

  public GameOverMenu(Vector size) {
    super(size);
  }

  @Override
  public void init() {
    super.init();

    textPaintCan.init(getGlobalPreferences(), getEngine().getGameAssetManager());
    bgPaintCan.init(getGlobalPreferences(), getEngine().getGameAssetManager());
    LanguageText gameOverLT =
        new LanguageText(
            getGlobalPreferences(), getEngine().getGameAssetManager(), "Bomber", "Game_Over");

    MenuBuilder b = this.build();
    b.add("GameOverText", new TextRenderer(new Vector(), gameOverLT.getValue(), textPaintCan))
        .close();
  }

  @Override
  public void draw(Canvas canvas, Vector pos, Vector size) {
    super.draw(canvas, pos, size);
    canvas.drawRoundRect(pos, size, new Vector(1), bgPaintCan);
  }
}
