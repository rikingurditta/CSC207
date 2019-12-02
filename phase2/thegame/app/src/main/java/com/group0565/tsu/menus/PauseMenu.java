package com.group0565.tsu.menus;

import com.group0565.engine.gameobjects.Button;
import com.group0565.engine.gameobjects.GameMenu;
import com.group0565.engine.gameobjects.MenuObject;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.ObservationEvent;
import com.group0565.engine.render.ThemedPaintCan;
import com.group0565.math.Vector;
import com.group0565.tsu.enums.ButtonBitmap;

import static com.group0565.engine.enums.HorizontalEdge.HCenter;
import static com.group0565.engine.enums.HorizontalEdge.Left;
import static com.group0565.engine.enums.HorizontalEdge.Right;
import static com.group0565.engine.enums.VerticalEdge.Bottom;
import static com.group0565.engine.enums.VerticalEdge.Top;
import static com.group0565.engine.enums.VerticalEdge.VCenter;

public class PauseMenu extends GameMenu implements Observable {
  // Event Constants
  public static final String RESUME = "Resume";
  public static final String HOME = "Home";
  // Asset Constants
  private static final String SET = "Tsu";
  private static final String PaintFolder = "PauseMenu.";
  private static final String RimName = PaintFolder + "Rim";
  private static final String CenterName = PaintFolder + "Center";
  // Background Constants
  private static final String BackgroundName = "Background";
  private static final Vector RIM_SIZE = new Vector(10);
  private static final Vector RIM_RADIUS = new Vector(15);
  // Button Constants
  private static final Vector BUTTON_SIZE = new Vector(150);
  // Resume Constants
  private static final String ResumeName = "Resume";
  // Home Constants
  private static final String HomeName = "Home";

  private ThemedPaintCan rim;
  private ThemedPaintCan center;

  public PauseMenu(Vector size) {
    super(size);
    this.setEnable(false);
  }

  @Override
  public void init() {
    super.init();
    this.rim =
        new ThemedPaintCan(SET, RimName)
            .init(getGlobalPreferences(), getEngine().getGameAssetManager());
    this.center =
        new ThemedPaintCan(SET, CenterName)
            .init(getGlobalPreferences(), getEngine().getGameAssetManager());
    ButtonBitmap.init(getEngine().getGameAssetManager());
    // @formatter:off
    this.build()
        .add(
            BackgroundName,
            new MenuObject() {
              {
                setZ(-1);
              }

              @Override
              public void draw(Canvas canvas, Vector pos, Vector size) {
                super.draw(canvas, pos, size);
                canvas.drawRoundRect(pos, size, RIM_RADIUS, rim);
                canvas.drawRoundRect(
                    pos.add(RIM_SIZE), size.subtract(RIM_SIZE.multiply(2)), RIM_RADIUS, center);
              }
            })
        .addAlignment(Left, THIS, Left)
        .addAlignment(Right, THIS, Right)
        .addAlignment(Top, THIS, Top)
        .addAlignment(Bottom, THIS, Bottom)
        .add(
            ResumeName,
            new Button(BUTTON_SIZE, ButtonBitmap.ResumeButton.getBitmap(), RESUME)
                .build()
                .registerObserver(this::observeButtons)
                .close())
        .addAlignment(Right, THIS, HCenter, -BUTTON_SIZE.getX() / 2f)
        .addAlignment(VCenter, THIS, VCenter)
        .add(
            HomeName,
            new Button(BUTTON_SIZE, ButtonBitmap.HomeButton.getBitmap(), HOME)
                .build()
                .registerObserver(this::observeButtons)
                .close())
        .addAlignment(Left, THIS, HCenter, BUTTON_SIZE.getX() / 2f)
        .addAlignment(VCenter, THIS, VCenter)
        .close();
    // @formatter:on
  }

  private void observeButtons(Observable observable, ObservationEvent<String> event) {
    if (event.isEvent(Button.EVENT_DOWN)) {
      notifyObservers(event);
    }
  }
}
