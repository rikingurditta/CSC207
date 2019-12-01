package com.group0565.bomberGame.menus;

import com.group0565.engine.gameobjects.Button;
import com.group0565.engine.gameobjects.GameMenu;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.ObservationEvent;
import com.group0565.engine.render.ThemedPaintCan;
import com.group0565.math.Vector;

public class BomberMenu extends GameMenu {
  private final ThemedPaintCan bgPaintCan = new ThemedPaintCan("Bomber", "Background.Background");

  public BomberMenu() {
    super(null);
  }

  @Override
  public void init() {
    super.init();

    bgPaintCan.init(getGlobalPreferences(), getEngine().getGameAssetManager());
    this.build()
        // Play Button
        .add(
            "PlayButton",
            new Button(new Vector(593, 249), getEngine().getGameAssetManager(), "Bomber", "Title")
                .build()
                .registerObserver(this::observePlayButton)
                .close())
        .addCenteredAlignment(THIS)
        .close();
  }

  @Override
  public void draw(Canvas canvas) {
    super.draw(canvas);
    canvas.drawRGB(bgPaintCan);
  }

  public void observePlayButton(Observable observable, ObservationEvent event) {
    if (!event.getMsg().equals("Observer Registered"))
      notifyObservers(new ObservationEvent("To game"));
  }
}
