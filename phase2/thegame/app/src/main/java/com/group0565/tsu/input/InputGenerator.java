package com.group0565.tsu.input;

import com.group0565.engine.gameobjects.InputEvent;
import com.group0565.tsu.game.TsuEngine;

import java.util.List;

/** Abstract class for generating Inputs */
public abstract class InputGenerator {
  /** The TsuEngine to generate Inputs for */
  private TsuEngine engine;

  /**
   * Creates a new Input Generator
   *
   * @param engine The TsuEngine to generate Inputs for
   */
  public InputGenerator(TsuEngine engine) {
    this.engine = engine;
  }

  /** Initializes this Input Generator */
  public void init() {}

  /**
   * Update the Input Generator
   *
   * @param ms The number of milliseconds since the last update
   * @return A list of Input Events generated for this frame
   */
  public abstract List<InputEvent> update(long ms);

  /**
   * Getter for engine.
   *
   * @return engine
   */
  public TsuEngine getEngine() {
    return engine;
  }

  /**
   * Setter for engine.
   *
   * @param engine The new value for engine
   */
  public void setEngine(TsuEngine engine) {
    this.engine = engine;
  }
}
