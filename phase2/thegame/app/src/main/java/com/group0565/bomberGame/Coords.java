package com.group0565.bomberGame;

/** 2D integer coordinates. Pretty much just an immutable vector of ints. */
public class Coords {
  public final int x;
  public final int y;

  /** Create a new coordinate at the origin. */
  public Coords() {
    this.x = 0;
    this.y = 0;
  }
  /** Create a new coordinate (x, y). */
  public Coords(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /** @return a coordinate with minX <= x < maxX, minY <= y < maxY. */
  public static Coords random(int minX, int minY, int maxX, int maxY) {
    // TODO: make this function throw an error properly instead of just asserting
    assert minX <= maxX;
    assert minY <= maxY;

    return new Coords(
        minX + (int) (Math.random() * (maxX - minX)), minY + (int) (Math.random() * (maxY - minY)));
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
