package com.group0565.tsu.input;

import com.group0565.engine.gameobjects.GameObject;
import com.group0565.engine.gameobjects.InputEvent;

import java.util.List;

/**
 * A class that intercepts inputs and attaches InputGenerators to generate inputs
 */
public class InputIntercepter extends GameObject {
    /**
     * The InputGenerator to use to generate Inputs
     */
    private InputGenerator generator = null;

    @Override
    public void update(long ms) {
        super.update(ms);
        if (generator != null){
            //Obtain the inputEvents for this frame and dispatch it
            List<InputEvent> inputEvents = generator.update(ms);
            if (inputEvents != null)
                for (InputEvent event : inputEvents)
                    super.processInput(event);
        }
    }

    @Override
    public boolean processInput(InputEvent event) {
        //If we have a generator, intercept all Input Events
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
