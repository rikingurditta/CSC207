package com.group0565.bomberGame.input;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import com.group0565.engine.gameobjects.InputEvent;
import com.group0565.math.Vector;

/**
 * On-screen joystick and buttons input system.
 */
public class JoystickInput extends InputSystem {

    /**
     * Constructs a new JoystickInput.
     * @param position  The position (relative or absolute) of this object.
     * @param z         The z-level of the object.
     */
    public JoystickInput(Vector position, double z) {
        super(position, z);
    }

    /**
     * Constructs a new JoystickInput.
     * @param position  The position (relative or absolute) of this object.
     */
    public JoystickInput(Vector position) {
        super(position);
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
        if (this.getAbsolutePosition().getX() < pos.getX() && pos.getX() < this.getAbsolutePosition().getX()+100
        && this.getAbsolutePosition().getY() < pos.getY() && pos.getY() < this.getAbsolutePosition().getY()+100){
            input.up = true;
            Log.i("input received", "Up");
        }
        else if (this.getAbsolutePosition().getX()-100 < pos.getX() && pos.getX() < this.getAbsolutePosition().getX()
                && this.getAbsolutePosition().getY() + 100 < pos.getY() && pos.getY() < this.getAbsolutePosition().getY()+200){
            input.left = true;
            Log.i("input received", "Left");
        }
        else if (this.getAbsolutePosition().getX()+100 < pos.getX() && pos.getX() < this.getAbsolutePosition().getX()+200
                && this.getAbsolutePosition().getY() + 100 < pos.getY() && pos.getY() < this.getAbsolutePosition().getY()+200){
            input.right = true;
            Log.i("input received", "Right");
        }
        else if (this.getAbsolutePosition().getX() < pos.getX() && pos.getX() < this.getAbsolutePosition().getX()+100
                && this.getAbsolutePosition().getY()+200 < pos.getY() && pos.getY() < this.getAbsolutePosition().getY()+300){
            input.down = true;
            Log.i("input received", "Down");
        }


        //drop bomb
        if (this.getAbsolutePosition().getX() +1700 < pos.getX() && pos.getX() < this.getAbsolutePosition().getX()+1800
                && this.getAbsolutePosition().getY()+100 < pos.getY() && pos.getY() < this.getAbsolutePosition().getY()+200){
            input.bomb = true;
            Log.i("input received", "Dropped Bomb");
        }


        return true;
    }

    /**
     * Draws the Joystick and buttons on the screen.
     * @param canvas The Canvas on which to draw this player.
     */
    @Override
    public void draw(Canvas canvas) {
        Paint Black = new Paint();
        Black.setARGB(255, 0, 0, 0);

        //LEFT RIGHT UP DOWN PAD  (draws a long vertical rectangle and two smaller squares beside it to make a + sign)
        canvas.drawRect(
                getAbsolutePosition().getX(),
                getAbsolutePosition().getY(),
                getAbsolutePosition().getX() + 100,
                getAbsolutePosition().getY() + 300,
                Black);
        canvas.drawRect(
                getAbsolutePosition().getX()+100,
                getAbsolutePosition().getY()+100,
                getAbsolutePosition().getX() + 200,
                getAbsolutePosition().getY()+200,
                Black);
        canvas.drawRect(
                getAbsolutePosition().getX()-100,
                getAbsolutePosition().getY()+100,
                getAbsolutePosition().getX(),
                getAbsolutePosition().getY()+200,
                Black);

        //DROP BOMB BUTTON
        Paint RED = new Paint();
        RED.setARGB(255, 255, 0, 0);
        canvas.drawRect(
                getAbsolutePosition().getX()+1700,
                getAbsolutePosition().getY()+100,
                getAbsolutePosition().getX()+1800,
                getAbsolutePosition().getY()+200,
                RED);


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
