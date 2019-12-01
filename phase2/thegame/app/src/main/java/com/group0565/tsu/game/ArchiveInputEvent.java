package com.group0565.tsu.game;

public class ArchiveInputEvent {
    public float position;
    public long startTime;
    public long endTime;

    public ArchiveInputEvent(float position, long startTime, long endTime) {
        this.position = position;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
