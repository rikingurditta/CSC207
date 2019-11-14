package com.group0565.tsu.gameObjects;

import com.group0565.engine.gameobjects.GameObject;
import com.group0565.math.Vector;

public abstract class JudgementLine extends GameObject {
  public abstract Double convert(Vector position);

  public abstract double convert(int width);
}
