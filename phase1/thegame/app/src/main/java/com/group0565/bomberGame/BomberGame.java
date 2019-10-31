package com.group0565.bomberGame;

import android.graphics.Canvas;

import com.group0565.bomberGame.input.InputSystem;
import com.group0565.bomberGame.input.JoystickInput;
import com.group0565.bomberGame.input.RandomInput;
import com.group0565.engine.gameobjects.GameObject;
import com.group0565.math.Vector;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public class BomberGame extends GameObject {

  private ArrayList<GameObject> itemsToBeAdopted = new ArrayList<GameObject>();
  private ArrayList<GameObject> itemsToBeRemoved = new ArrayList<GameObject>();

  public BomberGame(Vector position) {
    super(position);
    InputSystem joystickInput =
        new JoystickInput(new Vector(150, 750), 100, new Vector(0, 0), new Vector(1700, 100), 100);
    adopt(joystickInput);
    InputSystem randomInput = new RandomInput(1000);
    adopt(randomInput);
    SquareGrid grid = new SquareGrid(new Vector(100, 100), 0, 15, 8, 100, this);
    adopt(grid);
    GameObject bm = new BomberMan(new Coords(0, 0), joystickInput, this, grid, 10);
    adopt(bm);
    GameObject bm2 = new BomberMan(new Coords(10, 6), randomInput, this, grid, 10);
    adopt(bm2);
    // make 25 crates
    for (int i = 0; i < 25; i++) grid.makeRandomCrate();
  }

  public void draw(Canvas canvas) {
    super.draw(canvas);
    // Fill background with White
    canvas.drawRGB(255, 255, 255);
  }

  @Override
  public void update(long ms) {
    for (GameObject item : itemsToBeAdopted) {
      this.adopt(item);
    }
    itemsToBeAdopted.clear();

    for (GameObject item : itemsToBeRemoved) {
      UUID objID = item.getUUID();
      Map<UUID, GameObject> gameChildren = this.getChildren();
      gameChildren.remove(objID);
    }
    itemsToBeRemoved.clear();
  }

  public void adoptLater(GameObject obj) {
    itemsToBeAdopted.add(obj);
  }

  public void removeLater(GameObject obj) {
    itemsToBeRemoved.add(obj);
  }
}
