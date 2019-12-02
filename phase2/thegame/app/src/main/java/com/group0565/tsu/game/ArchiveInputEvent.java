package com.group0565.tsu.game;

/**
 * A simple data class to store an Input Event.
 *
 * This class is simple enough to not warrent getters and setters.
 */
public class ArchiveInputEvent {
    /**
     * The position of the Input Event, in 0-1, with 0 being the left side and 1 for the right
     */
    public double position = 0;
    /**
     * The time the Input Event start, in ms
     */
    public long startTime = 0;
    /**
     * The time the Input Event ends, in ms
     */
    public long endTime = 0;

    /**
     * Default constructor required for serialization
     */
    public ArchiveInputEvent() {
    }

    /**
     * Creates a new Archive Input Event
     * @param position The position of the Input Event, in 0-1, with 0 being the left side and 1 for the right
     * @param startTime The time the Input Event start, in ms
     * @param endTime The time the Input Event ends, in ms
     */
    public ArchiveInputEvent(double position, long startTime, long endTime) {
        this.position = position;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
