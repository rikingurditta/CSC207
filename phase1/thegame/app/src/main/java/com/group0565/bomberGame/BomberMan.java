package com.group0565.bomberGame;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.group0565.bomberGame.input.BomberInput;
import com.group0565.bomberGame.input.InputSystem;
import com.group0565.engine.gameobjects.GameObject;
import com.group0565.math.Vector;

/**
 * A BomberMan, aka a player in the game.
 */
public class BomberMan extends GameObject {
    /**
     * The object representing the state of the inputs for this player.
     */
    private BomberInput input;

    /**
     * The object processing the input for this player. Is adopted by this BomberMan, so all input
     * events get passed down to it. (maybe get rid of this field because we p much never need it)
     */
    private InputSystem inputSystem;

    /**
     * Constructs a new BomberMan.
     * @param position      The position (relative or absolute) of this object.
     * @param z             The z-level of the object.
     * @param inputSystem   The object managing the inputs controlling this player.
     */
    public BomberMan(Vector position, double z, InputSystem inputSystem) {
        super(position, z);
        this.inputSystem = inputSystem;
        this.adopt(inputSystem);
        this.input = inputSystem.getInput();
    }

    /**
     * Constructs a new BomberMan.
     * @param position      The position (relative or absolute) of this object.
     * @param inputSystem   The object managing the inputs controlling this player.
     */
    public BomberMan(Vector position, InputSystem inputSystem) {
        super(position);
        this.inputSystem = inputSystem;
        this.adopt(inputSystem);
        this.input = inputSystem.getInput();
    }

    /**
     * Draws ONLY this object to canvas. Its children are not drawn by this method.
     *
     * @param canvas The Canvas on which to draw this player.
     */
    @Override
    public void draw(Canvas canvas) {
        // Set our color to Red
        Paint p = new Paint();
        p.setARGB(255, 0, 255, 0);
        // Draw an rectangle at our touch position
        canvas.drawRect(
                getAbsolutePosition().getX(),
                getAbsolutePosition().getY(),
                getAbsolutePosition().getX() + 100,
                getAbsolutePosition().getY() + 100,
                p);
    }

    /**
     * Updates the player based on input, as processed by this player's InputSystem.
     *
     * @param ms Elapsed time in milliseconds since last update.
     */
    @Override
    public void update(long ms) {
        Vector pos = this.getAbsolutePosition();
        Vector delta = new Vector();
        float speed = 0.1f;
        if (input.up) delta = delta.add(new Vector(0, -speed));
        if (input.down) delta = delta.add(new Vector(0, speed));
        if (input.left) delta = delta.add(new Vector(-speed, 0));
        if (input.right) delta = delta.add(new Vector(speed, 0));
        delta = delta.multiply(ms);
        this.setAbsolutePosition(pos.add(delta));

        if (input.bomb) dropBomb();
    }

    /**
     * Drops bomb at current location.
     */
    private void dropBomb() {
    }
}
