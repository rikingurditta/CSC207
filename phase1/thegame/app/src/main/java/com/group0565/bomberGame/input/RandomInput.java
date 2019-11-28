package com.group0565.bomberGame.input;

import com.group0565.math.Vector;

/** InputSystem that moves in a random direction on a regular interval. */
public class RandomInput extends InputSystem {

  /**
   * Constructs a new RandomInput.
   *
   * @param period The period between changes of direction.
   */
  public RandomInput(long period) {
    super(new Vector());
  }

  /** @param ms elapsed time in milliseconds since last update */
  @Override
  public void update(long ms) {}

  /** Randomly choose and return the next direction to move in. */
  @Override
  public BomberInput nextInput() {
    input = new BomberInput();
    int r = (int) (Math.random() * 5);
    if (r == 0) input.up = true;
    else if (r == 1) input.down = true;
    else if (r == 2) input.left = true;
    else if (r == 3) input.right = true;
    else if (r == 4) input.bomb = true;
    return input;
  }
}
