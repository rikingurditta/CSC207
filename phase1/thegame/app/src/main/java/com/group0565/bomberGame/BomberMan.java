package com.group0565.bomberGame;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import com.group0565.engine.gameobjects.GameObject;
import com.group0565.engine.gameobjects.InputEvent;
import com.group0565.math.Vector;

public class BomberMan extends GameObject {
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
    public BomberMan(GameObject parent, Vector position, boolean relative) {
        super(parent, position, relative);
    }


    /**
     * Draws ONLY this object t o canvas. Its children is NOT drawn.
     *
     * @param canvas The Canvas on which to draw
     */
    @Override
    public void draw(Canvas canvas) {
        //Set our color to Red
        Paint p = new Paint();
        p.setARGB(255, 0, 255, 0);
        //Draw an rectangle at our touch position
        canvas.drawRect(getAbsolutePosition().getX(), getAbsolutePosition().getY(), getAbsolutePosition().getX() + 100, getAbsolutePosition().getY() + 100, p);
    }
}
