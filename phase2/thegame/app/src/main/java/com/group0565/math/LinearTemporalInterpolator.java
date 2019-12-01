package com.group0565.math;

public class LinearTemporalInterpolator{
    private Vector source;
    private Vector dest;
    private long totalTime;
    private boolean clamped;

    public LinearTemporalInterpolator(Vector source, Vector dest, long totalTime, boolean clamped) {
        this.source = source;
        this.dest = dest;
        this.totalTime = totalTime;
        this.clamped = clamped;
    }

    public LinearTemporalInterpolator(Vector source, Vector dest, long totalTime) {
        this(source, dest, totalTime, false);
    }

    public Vector interpolate(long time) {
        if (clamped)
            time = Math.max(0, Math.min(totalTime, time));
        return source.add((dest.subtract(source).multiply(((float) time)/totalTime)));
    }
}
