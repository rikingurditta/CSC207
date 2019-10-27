package com.group0565.bomberGame;

import android.graphics.Canvas;

import com.group0565.bomberGame.input.InputSystem;
import com.group0565.bomberGame.input.JoystickInput;
import com.group0565.bomberGame.input.RandomInput;
import com.group0565.engine.gameobjects.GameObject;
import com.group0565.math.Vector;

public class BomberGame extends GameObject {

    //make a array list that holds all the bomber mans inside of this class

    public BomberGame(Vector position) {
        super(position);
        InputSystem joystickInput = new JoystickInput(new Vector());
        InputSystem randomInput = new RandomInput(1000);
        this.adopt(new BomberMan(new Vector(100, 100), joystickInput));
        this.adopt(new BomberMan(new Vector(750, 500), randomInput));
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        // Fill background with White
        canvas.drawRGB(255, 255, 255);
    }


}
