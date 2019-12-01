package com.group0565.tsu.game;

public class ArchiveInputEvent {
    public double position = 0;
    public long startTime = 0;
    public long endTime = 0;

    public ArchiveInputEvent() {
    }

    public ArchiveInputEvent(double position, long startTime, long endTime) {
        this.position = position;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
