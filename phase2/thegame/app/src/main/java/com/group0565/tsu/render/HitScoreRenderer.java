package com.group0565.tsu.render;

import com.group0565.engine.gameobjects.MenuObject;
import com.group0565.engine.interfaces.Bitmap;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.Source;
import com.group0565.math.Vector;
import com.group0565.tsu.enums.Scores;

public class HitScoreRenderer extends MenuObject {
  private static final float INCREMENT_FACTOR = 0.2f;
  private static final float DECAY_TIME = 250;
  private Source<Scores> score;
  private Scores lastScore = null;
  private float baseHeight;
  private float deltaSize = 0.0f;
  private long deltaTime = 0;

  public HitScoreRenderer(float baseHeight, Source<Scores> score) {
    super(new Vector(baseHeight));
    this.baseHeight = baseHeight;
    this.score = score;
  }

  @Override
  public void init() {
    super.init();
    Scores.init(getEngine().getGameAssetManager());
  }

  @Override
  public void update(long ms) {
    super.update(ms);
    if (lastScore != score.getValue()) {
      Scores csc = score.getValue();
      if (csc != null) {
        Bitmap bitmap = csc.getBitmap();
        if (bitmap != null) {
          setSize(new Vector(bitmap.getWidth() * (baseHeight / bitmap.getHeight()), baseHeight));
          updatePosition();
        }
      }
    }
    if (lastScore != score.getValue()) {
      deltaSize = INCREMENT_FACTOR;
      deltaTime = 0;
    }
    lastScore = score.getValue();
    if (deltaSize > 0) {
      deltaTime += ms;
      deltaSize = INCREMENT_FACTOR * (1 - (float) deltaTime / DECAY_TIME);
    }
  }

  @Override
  public void draw(Canvas canvas, Vector pos, Vector size) {
    super.draw(canvas, pos, size);
    Scores csc = score.getValue();
    if (csc != null) {
      Bitmap bitmap = csc.getBitmap();
      if (bitmap != null) {
        float deltaHeight = 0;
        if (deltaSize > 0) deltaHeight = deltaSize * baseHeight;
        canvas.drawBitmap(
            bitmap,
            pos.add(new Vector(0, -deltaHeight / 2f)),
            size.add(new Vector(0, deltaHeight)));
      }
    }
  }
}
