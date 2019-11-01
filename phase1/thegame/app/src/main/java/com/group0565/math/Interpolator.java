package com.group0565.math;

/**
 * Abstract Class for Interpolators which are used to construct a path between two points.
 */
public abstract class Interpolator {
    /**
     * The Starting and Ending locations for the Interpolator
     */
    private final Vector source, dest;
    /**
     * The current progress of the Interpolator. 0 is source, 1 is dest. Can be out side this range to
     * extend the interpolation.
     */
    private double progress;

    /**
     * Create a new Interpolator
     *
     * @param source The starting location
     * @param dest   The ending location
     */
    public Interpolator(Vector source, Vector dest) {
        this.source = source;
        this.dest = dest;
    }

    /**
     * Method used to interpolate between source and dest.
     *
     * @param displacement The length to move
     * @return The current location.
     */
    public abstract Vector interpolate(float displacement);

    /**
     * Gets the current progress of ths Interpolator
     *
     * @return progress
     */
    public double getProgress() {
        return progress;
    }

    /**
     * Sets the current progress. Progress equaling 0 is source, 1 is dest. Can be out side this range
     * to extend the interpolation.
     *
     * @param progress The new progress
     */
    public void setProgress(float progress) {
        this.progress = progress;
    }

    /**
     * Gets the starting point of this Interpolator
     *
     * @return source
     */
    public Vector getSource() {
        return source;
    }

    /**
     * Gets the ending point of this Interpolator
     *
     * @return dest;
     */
    public Vector getDest() {
        return dest;
  }
}
