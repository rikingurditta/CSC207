package com.group0565.bomberGame.droppables;

import com.group0565.bomberGame.BomberEngine;
import com.group0565.bomberGame.grid.Grid;
import com.group0565.bomberGame.grid.GridObject;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.render.ThemedPaintCan;
import com.group0565.math.Coords;
import com.group0565.math.Vector;

public abstract class Droppable extends GridObject {
    /** The game this Crate belongs to. */
    private BomberEngine game;

    /**
     * Constructs a new Crate.
     *
     * @param position The position of this object on the grid.
     * @param z The z-level of the object.
     * @param game The game this crate belongs to.
     * @param grid The grid this crate is within.
     */
    public Droppable(Coords position, double z, Grid grid, BomberEngine game) {
        super(position, z, grid);
        this.game = game;
        this.grid.addItem(this, position);
    }

    /**
     * Constructs a new Crate.
     *
     * @param position The position of this object on the grid.
     * @param game The game this crate belongs to.
     * @param grid The grid this crate is within.
     */
    public Droppable(Coords position, Grid grid, BomberEngine  game) {
        this(position, 0, grid, game);
    }



    /** Destroy this crate if the damage done to it is positive. */
    @Override
    public void damage(int d) {
        if (d > 0) {
            grid.remove(this);
            game.removeLater(this);
        }
    }

    @Override
    public boolean isBomb() {
        return false;
    }

    @Override
    public boolean isDroppable() {
        return true;
    }
}
