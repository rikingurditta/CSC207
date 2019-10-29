package com.group0565.bomberGame;

import android.graphics.Canvas;

import com.group0565.bomberGame.input.InputSystem;
import com.group0565.bomberGame.input.JoystickInput;
import com.group0565.bomberGame.input.RandomInput;
import com.group0565.engine.gameobjects.GameObject;
import com.group0565.math.Vector;

import java.util.ArrayList;

public class BomberGame extends GameObject {

    private ArrayList<GameObject> itemsToBeAdopted = new ArrayList<GameObject>();


    public BomberGame(Vector position) {
        super(position);
        InputSystem joystickInput = new JoystickInput(new Vector());
        joystickInput.setAbsolutePosition(new Vector(100, 750));
        this.adopt(joystickInput);
        InputSystem randomInput = new RandomInput(1000);
        this.adopt(randomInput);
        GameObject bm = new BomberMan(new Vector(100, 100), joystickInput, this);
        bm.setAbsolutePosition(new Vector(300, 300));
        this.adopt(bm);
        GameObject bm2 = new BomberMan(new Vector(750, 500), randomInput, this);
        bm2.setAbsolutePosition(new Vector(750, 500));
        this.adopt(bm2);
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        // Fill background with White
        canvas.drawRGB(255, 255, 255);
    }

    @Override
    public void update(long ms) {
        for (GameObject item : itemsToBeAdopted){
            this.adopt(item);
        }
        itemsToBeAdopted.clear();
    }

    public void adoptLater(GameObject obj){
        itemsToBeAdopted.add(obj);
    }


}
