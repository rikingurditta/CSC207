package com.group0565.tsu.game;

import com.group0565.engine.gameobjects.InputEvent;
import com.group0565.math.Vector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReplayGenerator extends InputGenerator {
    private List<ArchiveInputEvent> archive;
    private Vector pos;
    private Vector size;
    private int passedPointer;
    private HashSet<ArchiveInputEvent> used;
    private HashMap<ArchiveInputEvent, InputEvent> active;

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
            if (event == null)
                continue;
            if (i == passedPointer && event.endTime < getEngine().getCurrentTime())
                passedPointer ++;

            if (!used.contains(event) && event.startTime < getEngine().getCurrentTime()){
                used.add(event);
                InputEvent inputEvent = new InputEvent(new Vector(pos.getX() + (float)(size.getX() * event.position),
                        pos.getY() + size.getY()/2f));
                inputEvents.add(inputEvent);
                active.put(event, inputEvent);
            }else if (event.endTime < getEngine().getCurrentTime()){
                InputEvent inputEvent = active.remove(event);
                if (inputEvent != null)
                    inputEvent.deactivate();
            }
        }
        return inputEvents;
    }
}
