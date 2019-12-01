package com.group0565.tsu.game;

import com.group0565.engine.gameobjects.InputEvent;

import java.util.List;

public abstract class InputGenerator {
    private TsuEngine engine;

    public InputGenerator(TsuEngine engine) {
        this.engine = engine;
    }
    public void init() {
    }

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
