package com.group0565.tsu.render;

import com.group0565.engine.gameobjects.MenuObject;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.Source;
import com.group0565.math.Vector;
import com.group0565.tsu.enums.NumberTiles;

public class NumberRenderer extends MenuObject {
  private static final float INCREMENT_FACTOR = 0.2f;
  private static final float DECAY_TIME = 250;
  private Source<Integer> score;
  private int lastScore = 0;
  private Vector numSize;
  private float deltaSize = 0.0f;
  private long deltaTime = 0;

  public NumberRenderer(Source<Integer> score, Vector size) {
    super(size);
    this.score = score;
    this.numSize = size;
  }

  @Override
  public void init() {
    super.init();
    NumberTiles.init(getEngine().getGameAssetManager());
  }

  @Override
  public void update(long ms) {
    super.update(ms);
    if (String.valueOf(lastScore).length() != getScoreString().length()) {
      setSize(numSize.elementMultiply(new Vector(getScoreString().length(), 1)));
      updatePosition();
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
    String str = getScoreString();
    float height = size.getY();
    float deltaHeight = 0;
    if (deltaSize > 0) deltaHeight = deltaSize * height;
    for (int i = 0; i < str.length(); i++) {
      NumberTiles tiles = NumberTiles.char2num(str.charAt(i));
      if (tiles != null)
        canvas.drawBitmap(
            tiles.getBitmap(),
            pos.add(new Vector(numSize.getX() * i, -deltaHeight / 2f)),
            numSize.add(new Vector(0, deltaHeight)));
    }
  }

  private String getScoreString() {
    return String.valueOf(score.getValue());
  }
}
