package com.group0565.tsu.menus;

import com.group0565.engine.gameobjects.Button;
import com.group0565.engine.gameobjects.GameMenu;
import com.group0565.engine.gameobjects.InputEvent;
import com.group0565.engine.gameobjects.MenuObject;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.ObservationEvent;
import com.group0565.engine.interfaces.Source;
import com.group0565.engine.render.BitmapDrawer;
import com.group0565.engine.render.TextRenderer;
import com.group0565.engine.render.ThemedPaintCan;
import com.group0565.hitobjectsrepository.SessionHitObjects;
import com.group0565.math.Vector;
import com.group0565.tsu.enums.ButtonBitmap;
import com.group0565.tsu.enums.Grade;
import com.group0565.tsu.render.GradeRenderer;

import static com.group0565.engine.enums.HorizontalEdge.Left;
import static com.group0565.engine.enums.HorizontalEdge.Right;
import static com.group0565.engine.enums.VerticalEdge.Bottom;
import static com.group0565.engine.enums.VerticalEdge.Top;

class HistoryDisplayer extends GameMenu {
  // Asset Constants
  private static final String SET = "Tsu";
  private static final String ThemeFolder = "HistoryDisplayer.";
  // Paint Constants
  private static final String DatePaintName = ThemeFolder + "Date";
  private static final String ScorePaintName = ThemeFolder + "Score";
  private static final String ComboPaintName = ThemeFolder + "Combo";
  private static final String CenterPaintName = ThemeFolder + "Center";
  private static final String CenterSelectedPaintName = ThemeFolder + "CenterSelected";
  private static final String RimPaintName = ThemeFolder + "Rim";

  // Misc Constants
  private static final Vector RIM = new Vector(10);
  private static final Vector MARGIN = new Vector(20, 15);

  // Rim Constants
  private static final String RimName = "Rim";
  // Grade Constants
  private static final String GradeName = "Grade";
  private static final float GradeScale = 0.75f;
  // Date Constants
  private static final String DateName = "Date";
  private static final float DateScale = 0.2f;
  // Date Constants
  private static final String ScoreName = "Score";
  private static final float ScoreScale = 0.45f;
  // Combo Constants
  private static final String ComboName = "Combo";
  private static final float ComboScale = 0.45f;
  // Cheat Constants
  private static final String CheatName = "Cheat";
  private static final Vector CheatSize = new Vector(50);
  private static final float CheatBuffer = 10;

  private SessionHitObjects objects;
  private Source<SessionHitObjects> active;

  public HistoryDisplayer(
      Vector size, SessionHitObjects objects, Source<SessionHitObjects> active) {
    super(size);
    this.objects = objects;
    this.active = active;
  }

  @Override
  public void init() {
    super.init();
    Grade.init(getGlobalPreferences(), getEngine().getGameAssetManager());
    ThemedPaintCan rim =
        new ThemedPaintCan(SET, RimPaintName)
            .init(getGlobalPreferences(), getEngine().getGameAssetManager());
    ThemedPaintCan center =
        new ThemedPaintCan(SET, CenterPaintName)
            .init(getGlobalPreferences(), getEngine().getGameAssetManager());
    ThemedPaintCan centerSel =
        new ThemedPaintCan(SET, CenterSelectedPaintName)
            .init(getGlobalPreferences(), getEngine().getGameAssetManager());
    ThemedPaintCan datePaint =
        new ThemedPaintCan(SET, DatePaintName)
            .init(getGlobalPreferences(), getEngine().getGameAssetManager());
    ThemedPaintCan scorePaint =
        new ThemedPaintCan(SET, ScorePaintName)
            .init(getGlobalPreferences(), getEngine().getGameAssetManager());
    ThemedPaintCan comboPaint =
        new ThemedPaintCan(SET, ComboPaintName)
            .init(getGlobalPreferences(), getEngine().getGameAssetManager());

    this.build()
        .add(
            RimName,
            new MenuObject() {
              {
                setZ(-1);
              }

              @Override
              public void draw(Canvas canvas, Vector pos, Vector size) {
                super.draw(canvas, pos, size);
                canvas.drawRect(pos, size, rim);
                canvas.drawRect(
                    pos.add(RIM),
                    size.subtract(RIM.multiply(2)),
                    (active.getValue() == objects) ? centerSel : center);
              }
            })
        .addAlignment(Left, THIS, Left)
        .addAlignment(Right, THIS, Right)
        .addAlignment(Top, THIS, Top)
        .addAlignment(Bottom, THIS, Bottom)
        .add(
            GradeName,
            new GradeRenderer(
                getSize().multiply(GradeScale),
                () -> (objects == null ? null : Grade.num2Grade(objects.getGrade()))))
        .addAlignment(Left, THIS, Left, MARGIN.getX())
        .addAlignment(Top, THIS, Top, MARGIN.getY())
        .add(
            DateName,
            new TextRenderer(
                () -> (objects == null ? "" : objects.getDatetime()),
                datePaint,
                getSize().multiply(DateScale)))
        .addAlignment(Left, THIS, Left, MARGIN.getX())
        .addAlignment(Bottom, THIS, Bottom, -MARGIN.getY())
        .add(
            ScoreName,
            new TextRenderer(
                () -> (objects == null ? "" : String.valueOf(objects.getScore())),
                scorePaint,
                getSize().multiply(ScoreScale)))
        .addAlignment(Right, THIS, Right, -MARGIN.getX())
        .addAlignment(Top, THIS, Top, MARGIN.getY())
        .add(
            ScoreName,
            new TextRenderer(
                () -> (objects == null ? "" : String.valueOf(objects.getScore())),
                scorePaint,
                getSize().multiply(ScoreScale)))
        .addAlignment(Right, THIS, Right, -MARGIN.getX())
        .addAlignment(Top, THIS, Top, MARGIN.getY())
        .add(
            ComboName,
            new TextRenderer(
                () -> (objects == null ? "" : String.valueOf(objects.getMaxCombo())),
                comboPaint,
                getSize().multiply(ComboScale)))
        .addAlignment(Right, THIS, Right, -MARGIN.getX())
        .addAlignment(Bottom, THIS, Bottom, -MARGIN.getY())
        .add(
            CheatName,
            new BitmapDrawer(CheatSize, ButtonBitmap.CheatIcon::getBitmap)
                .build()
                .setSelfEnable(() -> (objects != null && objects.hasCheats()))
                .close())
        .addAlignment(Left, GradeName, Right, CheatBuffer)
        .addAlignment(Bottom, DateName, Top, -CheatBuffer)
        .close();
  }

  @Override
  protected void captureEvent(InputEvent event) {
    super.captureEvent(event);
    notifyObservers(new ObservationEvent<>(Button.EVENT_DOWN, objects));
  }

  @Override
  public boolean processInput(InputEvent event) {
    if (this.objects == null) return super.processInput(event);
    if (!isEnable() || !isSelfEnable()) return super.processInput(event);
    Vector pos = event.getPos();
    if (Vector.inBounds(getAbsolutePosition(), getSize(), pos)) {
      captureEvent(event);
      return true;
    }
    return super.processInput(event);
  }

  public SessionHitObjects getObjects() {
    return objects;
  }

  public void setObjects(SessionHitObjects objects) {
    this.objects = objects;
  }
}
