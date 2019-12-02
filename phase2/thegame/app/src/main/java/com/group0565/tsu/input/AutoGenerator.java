package com.group0565.tsu.input;

import com.group0565.engine.gameobjects.InputEvent;
import com.group0565.math.Vector;
import com.group0565.tsu.game.Beatmap;
import com.group0565.tsu.game.HitObject;
import com.group0565.tsu.game.TsuEngine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

/** A InputGenerator that will auto play beatmaps */
public class AutoGenerator extends InputGenerator {
  /** HashMap of InputEvents to be dispatched */
  private LinkedHashMap<HitObject, InputEvent> inputBuffer = null;
  /** The size of the Judgement Area */
  private Vector size;
  /** The position of the Judgement Area */
  private Vector pos;

  /**
   * Creates a new AutoGenerator
   *
   * @param engine The TsuEngine to generate for
   * @param pos The position of the Judgement Area
   * @param size The size of the Judgement Area
   */
  public AutoGenerator(TsuEngine engine, Vector pos, Vector size) {
    super(engine);
    this.size = size;
    this.pos = pos;
  }

  @Override
  public void init() {
    Beatmap beatmap = getEngine().getBeatmap();
    if (beatmap == null) { // If no beatmap is loaded, clear our input buffer
      inputBuffer = null;
      return;
    }
    // Otherwise load our hit objects
    List<HitObject> hitObjectList = beatmap.getHitObjects();
    if (hitObjectList == null) { // If no objects were able to load, clear input buffer
      inputBuffer = null;
      return;
    }
    // Otherwise populate our input Buffer
    inputBuffer = new LinkedHashMap<>();
    for (HitObject object : hitObjectList) {
      // Calculate the relative position of this event
      float x = (float) (object.getPosition() * size.getX());
      // Create the event in absolute coordinates
      InputEvent event = new InputEvent(new Vector(pos.getX() + x, pos.getY() + size.getY() / 2f));
      // Register the event
      inputBuffer.put(object, event);
    }
  }

  @Override
  public List<InputEvent> update(long ms) {
    List<InputEvent> outEvent = new ArrayList<>();
    Iterator<HitObject> objectIterator = inputBuffer.keySet().iterator();
    while (objectIterator.hasNext()) {
      HitObject object = objectIterator.next();
      // If the object have not been hit, and the starting time have passed, go hit it
      if (object.getHitTime() < 0 && object.getMsStart() <= getEngine().getCurrentTime())
        outEvent.add(inputBuffer.get(object));
      // If the object has ended, remove the object's input event
      else if (object.getMsEnd() <= getEngine().getCurrentTime()) {
        inputBuffer.get(object).deactivate();
        objectIterator.remove();
      }
      // If we have passed the current time, then no more inputs are needed.
      if (object.getMsStart() > getEngine().getCurrentTime()) break;
    }
    return outEvent;
  }
}
