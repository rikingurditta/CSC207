package com.group0565.bomberGame;

import android.graphics.Canvas;

import com.group0565.bomberGame.input.InputSystem;
import com.group0565.bomberGame.input.JoystickInput;
import com.group0565.bomberGame.input.RandomInput;
import com.group0565.engine.gameobjects.GameObject;
import com.group0565.math.Vector;

public class BomberGame extends GameObject {

    //make a array list that holds all the bomber mans inside of this class

    public BomberGame(GameObject parent, Vector position, boolean relative) {
        super(parent, position, relative);
        InputSystem joystickInput = new JoystickInput(this, new Vector(), false);
        InputSystem randomInput = new RandomInput(null, 1000);
        this.adopt(new BomberMan(null, new Vector(100, 100), false, joystickInput));
        this.adopt(new BomberMan(null, new Vector(750, 500), false, randomInput));
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        // Fill background with White
        canvas.drawRGB(255, 255, 255);
    }


}
