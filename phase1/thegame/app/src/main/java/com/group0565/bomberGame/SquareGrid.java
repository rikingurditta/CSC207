package com.group0565.bomberGame;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.group0565.engine.gameobjects.GameObject;
import com.group0565.math.Vector;

import java.util.HashSet;
import java.util.Set;

/** A grid with square-shaped tiles. */
public class SquareGrid extends GameObject {
  private final int width;
  private final int height;
  private int tileWidth;

  private final Set<GameObject> items = new HashSet<>();
  private final Set<GameObject> itemsToBeAdded = new HashSet<>();

  private float windowWidth;
  private float windowHeight;

  public SquareGrid(Vector position, double z, int width, int height, int tileWidth) {
    super(position, z);
    this.width = width;
    this.height = height;
    this.tileWidth = tileWidth;
    this.windowWidth = width * tileWidth;
    this.windowHeight = height * tileWidth;
  }

  public SquareGrid(Vector position, int width, int height, int tileWidth) {
    super(position);
    this.width = width;
    this.height = height;
    this.tileWidth = tileWidth;
    this.windowWidth = width * tileWidth;
    this.windowHeight = height * tileWidth;
  }

  public boolean addItem(GameObject obj, int x, int y) {
    if (items.contains(obj)) {
      return false;
    }
    return itemsToBeAdded.add(obj);
  }

  @Override
  public void update(long ms) {
    items.addAll(itemsToBeAdded);
    itemsToBeAdded.clear();
  }

  @Override
  public void draw(Canvas canvas) {
    Vector pos = this.getAbsolutePosition();
    float x = pos.getX();
    float y = pos.getY();
    float endX = x + windowWidth;
    float endY = y + windowHeight;
    Paint p = new Paint();
    p.setARGB(255, 0, 0, 0);
    p.setStrokeWidth(1f);
    canvas.drawLine(x, y, endX, y, p);
    canvas.drawLine(endX, y, endX, endY, p);
    canvas.drawLine(endX, endY, x, endY, p);
    canvas.drawLine(x, endY, x, y, p);
    for (int i = 0; i < width; i += 1) {
      canvas.drawLine(x + i * tileWidth, y, x + i * tileWidth, endY, p);
    }
    for (int j = 0; j < height; j += 1) {
      canvas.drawLine(x, y + j * tileWidth, endX, y + j * tileWidth, p);
    }
  }

  public boolean isValidTile(Coords p) {
    return p.x >= 0 && p.x < width && p.y >= 0 && p.y < height;
  }

  public boolean isValidPosition(Vector v) {
    return isValidTile(absolutePositionToGridCoords(v));
  }

  public boolean isValidMove(Coords p) {
    // TODO: make this take into account whether or not the tile is occupied
    return isValidTile(p);
  }

  public boolean isValidMove(Vector v) {
    // TODO: make this take into account whether or not the tile is occupied
    return isValidMove(absolutePositionToGridCoords(v));
  }

  public Vector gridCoordsToAbsolutePosition(Coords p) {
    Vector pos = this.getAbsolutePosition();
    return new Vector(pos.getX() + p.x * tileWidth, pos.getY() + p.y * tileWidth);
  }

  public Coords absolutePositionToGridCoords(Vector v) {
    Vector pos = this.getAbsolutePosition();
    // aggressive rounding here may cause logic errors, beware
    // TODO: fix the rounding issue!!
    return new Coords(
        (int) ((v.getX() - pos.getX()) / tileWidth), (int) ((v.getY() - pos.getY()) / tileWidth));
  }
}
