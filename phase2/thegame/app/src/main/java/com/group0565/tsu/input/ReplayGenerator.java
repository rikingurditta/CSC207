package com.group0565.tsu.input;

import com.group0565.engine.gameobjects.InputEvent;
import com.group0565.math.Vector;
import com.group0565.tsu.game.ArchiveInputEvent;
import com.group0565.tsu.game.TsuEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * A InputGenerator for playing back replays
 */
public class ReplayGenerator extends InputGenerator {
    /**
     * The archive to replay
     */
    private List<ArchiveInputEvent> archive;

    /**
     * The position of the Judgement Area
     */
    private Vector pos;
    /**
     * The size of the Judgement Area
     */
    private Vector size;
    /**
     * The pointer at which the input events have passed
     */
    private int passedPointer;

    /**
     * The set of ArchiveInputEvents that have been processed
     */
    private HashSet<ArchiveInputEvent> used;

    /**
     * The set of InputEvents that have been dispatched, but yet to be deactivated
     */
    private HashMap<ArchiveInputEvent, InputEvent> active;


    /**
     * Creates a new ReplayGenerator
     * @param engine The TsuEngine to generate for
     * @param archive The archive to replay
     * @param pos The position of the Judgement Area
     * @param size The size of the Judgement Area
     */
    public ReplayGenerator(TsuEngine engine, List<ArchiveInputEvent> archive, Vector pos, Vector size) {
        super(engine);
        this.archive = new ArrayList<>(archive.size());
        this.archive.addAll(archive);
        this.used = new HashSet<>();
        this.active = new HashMap<>();
        this.pos = pos;
        this.size = size;
    }

    @Override
    public List<InputEvent> update(long ms) {
        List<InputEvent> inputEvents = new ArrayList<>();
        for (int i = passedPointer; i < archive.size(); i ++){
            ArchiveInputEvent event = archive.get(i);
            //If somehow a null get mixed in, make sure no null pointer is thrown
            if (event == null)
                continue;
            //If the first Event have completely finished, increment passed pointer
            if (i == passedPointer && event.endTime < getEngine().getCurrentTime())
                passedPointer ++;

            //If we haven't used a event and its starting time has passed
            if (!used.contains(event) && event.startTime < getEngine().getCurrentTime()){
                //Generate the Input Event and prepare to dispatch it
                used.add(event);
                InputEvent inputEvent = new InputEvent(new Vector(pos.getX() + (float)(size.getX() * event.position),
                        pos.getY() + size.getY()/2f));
                inputEvents.add(inputEvent);
                active.put(event, inputEvent);
            }else if (event.endTime < getEngine().getCurrentTime()){//If the end time has passed, deactivate the input event.
                InputEvent inputEvent = active.remove(event);
                if (inputEvent != null)
                    inputEvent.deactivate();
            }
        }
        return inputEvents;
    }
}
