package com.group0565.tsu.game;

import com.group0565.engine.gameobjects.GameMenu;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.Paint;
import com.group0565.engine.interfaces.Source;
import com.group0565.engine.render.ThemedPaintCan;
import com.group0565.math.Vector;
import com.group0565.tsu.util.ColorCalculator;

import java.util.List;

import static com.group0565.tsu.game.TsuEngine.HIT_AREA;

public class TsuRenderer extends GameMenu {
  private static final float NOTE_HEIGHT = 60;
  private static final int TAIL_ALPHA = 100;

  // Asset Constants
  private static final String SET = "Tsu";
  private static final String ThemeFolder = "Renderer.";
  // Paint Constants
  private static final String BackgroundPaintName = ThemeFolder + "Background";

  private Source<Integer> passedPointer;
  private Source<Long> currentTime;
  private Source<Long> hitWindow;
  private Source<List<HitObject>> hitObjects;
  private Source<Beatmap> beatmap;

  private Paint colorPaint = Paint.createInstance();
  private ThemedPaintCan bgPaint = new ThemedPaintCan(SET, BackgroundPaintName);

  public TsuRenderer(
      Source<Integer> passedPointer,
      Source<Long> currentTime,
      Source<Long> hitWindow,
      Source<List<HitObject>> hitObjects,
      Source<Beatmap> beatmap) {
    this.passedPointer = passedPointer;
    this.currentTime = currentTime;
    this.hitWindow = hitWindow;
    this.hitObjects = hitObjects;
    this.beatmap = beatmap;
  }

  public TsuRenderer(
      Vector size,
      Source<Integer> passedPointer,
      Source<Long> currentTime,
      Source<Long> hitWindow,
      Source<List<HitObject>> hitObjects,
      Source<Beatmap> beatmap) {
    super(size);
    this.passedPointer = passedPointer;
    this.currentTime = currentTime;
    this.hitWindow = hitWindow;
    this.hitObjects = hitObjects;
    this.beatmap = beatmap;
  }

  @Override
  public void init() {
    super.init();
    bgPaint.init(getGlobalPreferences(), getEngine().getGameAssetManager());
  }

  @Override
  public void draw(Canvas canvas, Vector pos, Vector size) {
    super.draw(canvas, pos, size);
    if (!(hitObjects == null || beatmap == null)) {
      Vector incrementSize = size.elementMultiply(new Vector(beatmap.getValue().getNoteWidth(), 0));
      canvas.drawRect(pos.subtract(incrementSize.multiply(0.5f)), size.add(incrementSize), bgPaint);
      // Number of pixels per every Milliseconds
      double pxpms = (double) (size.getY() - HIT_AREA) / hitWindow.getValue();
      // Draw Hit Objects
      for (int i = passedPointer.getValue(); i < hitObjects.getValue().size(); i++) {
        HitObject hitObject = hitObjects.getValue().get(i);
        if (hitObject.getMsStart() > currentTime.getValue() + 1.25 * hitWindow.getValue()) break;
        else if (hitObject.getMsStart() != hitObject.getMsEnd() || hitObject.getHitTime() < 0) {
          // Otherwise if this is not a long note and we haven't hit it, draw it.
          // Actual screen values
          float startY =
              size.getY()
                  - (float)
                      (HIT_AREA + ((hitObject.getMsStart() - currentTime.getValue()) * pxpms));
          float finishY =
              size.getY()
                  - (float) (HIT_AREA + ((hitObject.getMsEnd() - currentTime.getValue()) * pxpms));

          float width = beatmap.getValue().getNoteWidth() * size.getX();
          float x = (float) ((hitObject.getPosition() * size.getX()) - width / 2f);

          // Determine the color to draw with
          colorPaint.setColor(ColorCalculator.computeColor(hitObject));
          // Draw the rectangle for it
          canvas.drawRect(
              pos.add(new Vector(x, startY)), new Vector(width, NOTE_HEIGHT), colorPaint);
          // Draw the rectangle
          if (hitObject.getMsStart() != hitObject.getMsEnd()) {
            colorPaint.setAlpha(TAIL_ALPHA);
            canvas.drawRect(
                pos.add(new Vector(x, startY)), new Vector(width, finishY - startY), colorPaint);
          }
        }
      }
    }
  }

  public long getScreenTime() {
    // Number of Milliseconds each pixel represents
    double msppx = (double) hitWindow.getValue() / (getSize().getY() - HIT_AREA);
    // The time at the bottom of the screen
    return (long) (currentTime.getValue() - (HIT_AREA * msppx));
  }
}
