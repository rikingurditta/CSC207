package com.group0565.tsu.game;

public class ArchiveInputEvent {
    public double position;
    public long startTime;
    public long endTime;

    public ArchiveInputEvent(double position, long startTime, long endTime) {
        this.position = position;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
