package com.group0565.tsu.game;

import com.group0565.engine.gameobjects.GameObject;
import com.group0565.engine.gameobjects.InputEvent;

import java.util.List;

public class InputIntercepter extends GameObject {
    private InputGenerator generator = null;

    public void init(){

    }

    @Override
    public void update(long ms) {
        super.update(ms);
        if (generator != null){
            List<InputEvent> inputEvents = generator.update(ms);
            if (inputEvents != null)
                for (InputEvent event : inputEvents)
                    super.processInput(event);
        }
    }

    @Override
    public boolean processInput(InputEvent event) {
        if (generator != null)
            return false;
        return super.processInput(event);
    }

    /**
     * Getter for generator.
     *
     * @return generator
     */
    public InputGenerator getGenerator() {
        return generator;
    }

    /**
     * Setter for generator.
     *
     * @param generator The new value for generator
     */
    public void setGenerator(InputGenerator generator) {
        this.generator = generator;
    }
}
