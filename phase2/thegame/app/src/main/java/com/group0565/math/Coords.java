package com.group0565.math;

/** 2D integer coordinates. Pretty much just an immutable vector of ints. */
public class Coords {
  public final int x;
  public final int y;

  /** Create a new coordinate at the origin. */
  public Coords() {
    this(0, 0);
  }
  /** Create a new coordinate (x, y). */
  public Coords(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /** @return a coordinate with minX <= x < maxX, minY <= y < maxY. */
  public static Coords random(int minX, int minY, int maxX, int maxY) {
    if (minX > maxX)
      throw new IllegalArgumentException("min x must be less than max x");
    else if (minY > maxY)
      throw new IllegalArgumentException("min x must be less than max x");

    int randX = minX + (int) (Math.random() * (maxX - minX));
    int randY = minY + (int) (Math.random() * (maxY - minY));

    return new Coords(randX, randY);
  }

  /** @return true if the coordinates have the same components. */
  public boolean equals(Coords other) {
    return x == other.x && y == other.y;
  }

  /** @return sum the coordinates as if they are vectors. */
  public Coords add(Coords other) {
    return new Coords(x + other.x, y + other.y);
  }

  /** @return sum the coordinates as if it is a vector. */
  public Coords subtract(Coords other) {
    return new Coords(x - other.x, y - other.y);
  }

  /** @return the norm of the coordinates as if it is a vector. */
  public double norm() {
    return Math.sqrt(x * x + y * y);
  }
}
