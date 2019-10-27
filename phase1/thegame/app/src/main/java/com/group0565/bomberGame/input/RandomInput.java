package com.group0565.bomberGame.input;

import com.group0565.engine.gameobjects.GameObject;
import com.group0565.math.Vector;

/**
 * InputSystem that moves in a random direction on a regular interval.
 */
public class RandomInput extends InputSystem {
    /**
     * The timer to keep track of time (in milliseconds) since last change of direction.
     */
    private long timer;

    /**
     * The period (in milliseconds) between changes of direction.
     */
    private long period;

    /**
     * Constructs a new RandomInput.
     * @param parent The parent object of this RandomInput.
     * @param period The period between changes of direction.
     */
    public RandomInput(GameObject parent, long period) {
        super(parent, new Vector(), false);
        this.period = period;
        this.timer = period - 1;
    }

    /**
     * @param ms elapsed time in milliseconds since last update
     */
    @Override
    public void update(long ms) {
        timer += ms;
        // if not enough time has passed since the last change of direction, do nothing
        if (timer < period) return;
        // otherwise, reset input and randomly decide a new direction to move in
        input.reset();
        timer = 0;
        int r = (int) (Math.random() * 4);
        if (r == 0) input.up = true;
        else if (r == 1) input.down = true;
        else if (r == 2) input.left = true;
        else if (r == 3) input.right = true;
    }
}
