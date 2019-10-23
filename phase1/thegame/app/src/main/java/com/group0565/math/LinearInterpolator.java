package com.group0565.math;

/**
 * A LinearInterpolator to move between two points in a straight line.
 */
public class LinearInterpolator extends Interpolator {
    /**
     * The total distance between source and dest
     */
    private final float norm;

    /**
     * Create a new LinearInterpolator
     *
     * @param source The starting location
     * @param dest   The ending location
     */
    public LinearInterpolator(Vector source, Vector dest) {
        super(source, dest);
        this.norm = dest.subtract(source).norm();
    }

    /**
     * Method used to interpolate between source and dest.
     *
     * @param displacement The length to move
     * @return The current location.
     */
    public Vector interpolate(float displacement) {
        double dp = displacement / norm;
        double p = this.getProgress() + dp;
        this.setProgress((float) p);
        return getDest().subtract(getSource()).multiply((float) p).add(getSource());
    }
}
