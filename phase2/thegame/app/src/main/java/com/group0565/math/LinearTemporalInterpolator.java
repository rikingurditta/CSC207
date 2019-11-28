package com.group0565.math;

public class LinearTemporalInterpolator{
    private Vector source;
    private Vector dest;
    private long totalTime;

    public LinearTemporalInterpolator(Vector source, Vector dest, long totalTime) {
        this.source = source;
        this.dest = dest;
        this.totalTime = totalTime;
    }

    public Vector interpolate(long time) {
        return source.add((dest.subtract(source).multiply(((float) time)/totalTime)));
    }
}
