package com.group0565.bomberGame.input;

/**
 * Object representing current inputs.
 */
public class BomberInput {
    public boolean up;
    public boolean down;
    public boolean left;
    public boolean right;
    public boolean bomb;
    // can add more later

    /**
     * Resets all inputs so that they are all false, as if nothing is being input.
     */
    void reset() {
        up = false;
        down = false;
        left = false;
        right = false;
        bomb = false;
    }
}
