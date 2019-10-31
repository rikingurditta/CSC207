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

  /** @return the norm of the coordinates as if . */
  public double norm() {
    return Math.sqrt(x * x + y * y);
  }
}
