package com.group0565.bomberGame.menus;

import android.util.Log;

import com.group0565.engine.enums.HorizontalEdge;
import com.group0565.engine.enums.VerticalEdge;
import com.group0565.engine.gameobjects.Button;
import com.group0565.engine.gameobjects.GameMenu;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.ObservationEvent;
import com.group0565.engine.render.LanguageText;
import com.group0565.engine.render.TextRenderer;
import com.group0565.engine.render.ThemedPaintCan;
import com.group0565.math.Vector;

/** Menu to show that the game is over and show them their game-end options (e.g. replay). */
public class GameOverMenu extends GameMenu {
  /** PaintCan for normal text. */
  private final ThemedPaintCan textPaintCan = new ThemedPaintCan("Bomber", "Text.Text");

  /** PaintCan for the body of the menu. Temporarily using Bomb explosion PaintCan. */
  private final ThemedPaintCan bgPaintCan = new ThemedPaintCan("Bomber", "Bomb.Explosion");
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

    build()
        .add("GameOverText", new TextRenderer(new Vector(), gameOverLT.getValue(), textPaintCan))
        .add(
            "BackButton",
            new Button(new Vector(593, 249), getEngine().getGameAssetManager(), "Bomber", "To_Menu")
                .build()
                .registerObserver(this::observeBackButton).addOffset(500, 325)
                .close())
        .close();
  }

  private void observeBackButton(Observable observable, ObservationEvent event) {
    Log.i("GameOverMenu", "BackButton");
    if (!event.getMsg().equals("Observer Registered")) {
      Log.i("GameOverMenu", "To menu");
      notifyObservers(new ObservationEvent("To menu"));
    }
  }

  @Override
  public void draw(Canvas canvas, Vector pos, Vector size) {
    super.draw(canvas, pos, size);
    canvas.drawRoundRect(pos, size, new Vector(50), bgPaintCan);
  }
}
