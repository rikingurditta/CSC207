package com.example.thegame;

import android.graphics.Canvas;

import com.group0565.engine.gameobjects.GameObject;
import com.group0565.math.Vector;

public class JoystickExample extends GameObject {
    private static final String TAG = "JoystickExample";

    /**
     * Creates a new GameObject with z-level defaulting to 0.
     * <p>
     * For more information on other parameters see the javadoc of the constructer with signature
     * (GameObject parent, Vector position, boolean relative, Vector charsize, double z).
     *
     * @param parent   The parent of this object. Can be null if this is a top level object.
     * @param position The position (relative or absolute) of this object.
     * @param relative Whether the position is relative or absolute.
     */
    public JoystickExample(GameObject parent, Vector position, boolean relative) {
        super(parent, position, relative);
    }

    public void init(){
        Joystick stick = new Joystick(this, new Vector(0, 400), true, new Vector(500, 500), 2);
        adopt(stick);
        Square square = new Square(this, new Vector(0, 0), true, stick.input);
        adopt(square);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawRGB(255, 255, 255);
    }
}
