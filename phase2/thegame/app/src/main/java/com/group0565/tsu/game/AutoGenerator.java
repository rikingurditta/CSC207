package com.group0565.tsu.game;

import com.group0565.engine.gameobjects.InputEvent;
import com.group0565.math.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeMap;

public class AutoGenerator extends InputGenerator {
    private LinkedHashMap<HitObject, InputEvent> inputBuffer = null;
    private Vector size;
    private Vector pos;
    public AutoGenerator(TsuEngine engine, Vector pos, Vector size) {
        super(engine);
        this.size = size;
        this.pos = pos;
    }

    public void init(){
        Beatmap beatmap = getEngine().getBeatmap();
        if (beatmap == null) {
            inputBuffer = null;
            return;
        }
        List<HitObject> hitObjectList = beatmap.getHitObjects();
        if (hitObjectList == null){
            inputBuffer = null;
            return;
        }
        inputBuffer = new LinkedHashMap<>();
        float width = (beatmap.getNoteWidth() * size.getX());
        for (HitObject object : hitObjectList){
            float x = (float) (object.getPosition() * size.getX());
            InputEvent event = new InputEvent(new Vector(pos.getX() + x, pos.getY() + size.getY()/2f));
            inputBuffer.put(object, event);
        }
    }

    @Override
    public List<InputEvent> update(long ms) {
        List<InputEvent> outEvent = new ArrayList<>();
        Iterator<HitObject> objectIterator = inputBuffer.keySet().iterator();
        while(objectIterator.hasNext()){
            HitObject object = objectIterator.next();
            if (object.getHitTime() < 0 && object.getMsStart() <= getEngine().getCurrentTime())
                outEvent.add(inputBuffer.get(object));
            else if (object.getMsEnd() <= getEngine().getCurrentTime()) {
                inputBuffer.get(object).deactivate();
                objectIterator.remove();
            }
            if (object.getMsStart() > getEngine().getCurrentTime())
                break;
        }

        return outEvent;
    }
}
