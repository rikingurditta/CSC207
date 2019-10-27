package com.group0565.bomberGame.input;

import com.group0565.engine.gameobjects.GameObject;
import com.group0565.math.Vector;

public abstract class InputSystem extends GameObject {
    /**
     * The BomberInput object that will be managed by this InputSystem.
     */
    BomberInput input = new BomberInput();

    /**
     * Constructs a new InputSystem.
     * @param parent        The parent object of this InputSystem.
     * @param position      The position (relative or absolute) of this object.
     * @param relative      Whether the construction position is relative or absolute.
     * @param z             The z-level of the object.
     */
    InputSystem(GameObject parent, Vector position, boolean relative, double z) {
        super(parent, position, relative, z);
    }

    /**
     * Constructs a new InputSystem.
     * @param parent        The parent object of this InputSystem.
     * @param position      The position (relative or absolute) of this object.
     * @param relative      Whether the construction position is relative or absolute.
     */
    InputSystem(GameObject parent, Vector position, boolean relative) {
        super(parent, position, relative);
    }

    /**
     * Setter for this InputSystem's BomberInput object.
     */
    public void setInput(BomberInput input) {
        this.input = input;
    }

    /**
     * Getter for this InputSystem's BomberInput object.
     */
    public BomberInput getInput() {
        return input;
    }
}
