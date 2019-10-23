package com.example.thegame;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import com.group0565.engine.gameobjects.GameObject;
import com.group0565.engine.gameobjects.InputEvent;
import com.group0565.math.Vector;

public class MainObject extends GameObject {
    private static final String TAG = "MainObject";
    private long total = 0;
    private int updates = 0;
    private int renders = 0;
    private Vector pos = new Vector();

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
    public MainObject(GameObject parent, Vector position, boolean relative) {
        super(parent, position, relative);
    }

    public void update(long ms) {
        //Increment our total time
        total += ms;
        //If we have elapsed more than 1000 ms
        if (total > 1000) {
            //Log how many updates and renders there have been
            Log.i(TAG, "Updates: " + updates + " Renders: " + renders);
            total = 0;
            updates = 0;
            renders = 0;
        }
        //If we have captured an event
        if (this.getCapturedEvents().size() > 0)
            //Make our position the captured event
            pos = this.getCapturedEvents().iterator().next().getPos();
        updates++;
    }

    @Override
    public boolean processInput(InputEvent event) {
        //Capture the event no matter what
        captureEvent(event);
        return true;
    }

    /**
     * Draws ONLY this object t o canvas. Its children is NOT drawn.
     *
     * @param canvas The Canvas on which to draw
     */
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        //Fill background with White
        canvas.drawRGB(255, 255, 255);
        //Set our color to Red
        Paint p = new Paint();
        p.setARGB(255, 255, 0, 0);
        //Draw an rectangle at our touch position
        canvas.drawRect(pos.getX(), pos.getY(), pos.getX() + 100, pos.getY() + 100, p);
        renders++;
    }
}
