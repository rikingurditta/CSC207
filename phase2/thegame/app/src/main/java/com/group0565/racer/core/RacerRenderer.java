package com.group0565.racer.core;

import com.group0565.engine.gameobjects.GameObject;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.math.Vector;
import com.group0565.tsu.game.Beatmap;

public class RacerRenderer extends GameObject {
  private Vector size;
  private long timer;
  private RacerEngine engine;
  private int lastActive = 0;
  private long window;

  public RacerRenderer(RacerEngine engine, Vector position, Beatmap beatmap, Vector size, long window) {
    super(position);
    this.engine = engine;
    this.size = size;
    this.window = window;
  }

  public Vector getSize() {
    return size;
  }

  public void setSize(Vector size) {
    this.size = size;
  }

  @Override
  public void draw(Canvas canvas) {
    super.draw(canvas);
    if (size == null) {
      size = new Vector(canvas.getWidth(), canvas.getHeight());
    }
  }

  public long getTimer() {
    return timer;
  }

  public void setTimer(long ms) {
    this.timer = ms;
    if (timer <= 0) lastActive = 0;
  }

  protected int getLastActive() {
    return lastActive;
  }

  public long getWindow() {
    return window;
  }

  public void setWindow(long window) {
    this.window = window;
  }

  public RacerEngine getRacerEngine() {
    return engine;
  }

  public void setRacerEngine(RacerEngine engine) {
    this.engine = engine;
  }
}
