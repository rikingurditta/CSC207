package com.group0565.bomberGame.input;

import android.graphics.Canvas;

import com.group0565.engine.gameobjects.GameObject;
import com.group0565.engine.gameobjects.InputEvent;
import com.group0565.math.Vector;

/**
 * On-screen joystick and buttons input system.
 */
public class JoystickInput extends InputSystem {
    /**
     * Constructs a new JoystickInput.
     * @param parent    The parent object of this JoystickInput.
     * @param position  The position (relative or absolute) of this object.
     * @param relative  Whether the construction position is relative or absolute.
     * @param z         The z-level of the object.
     */
    public JoystickInput(GameObject parent, Vector position, boolean relative, double z) {
        super(parent, position, relative, z);
    }

    /**
     * Constructs a new JoystickInput.
     * @param parent    The parent object of this JoystickInput.
     * @param position  The position (relative or absolute) of this object.
     * @param relative  Whether the construction position is relative or absolute.
     */
    public JoystickInput(GameObject parent, Vector position, boolean relative) {
        super(parent, position, relative);
    }

    /**
     * Processes touch-based input to check how the joystick has been interacted with.
     *
     * @param event The input to be processed.
     * @return true
     */
    @Override
    public boolean processInput(InputEvent event) {
        Vector pos = event.getPos();
        input.up = true;
        System.out.println("input");
        // TODO
        return true;
    }

    /**
     * Draws the Joystick and buttons on the screen.
     * @param canvas The Canvas on which to draw this player.
     */
    @Override
    public void draw(Canvas canvas) {
        // TODO
    }

    /**
     * @param ms Elapsed time in milliseconds since last update.
     */
    @Override
    public void update(long ms) {
        // not safe for use with multiple objects using the same input system
        input.reset();
    }
}
