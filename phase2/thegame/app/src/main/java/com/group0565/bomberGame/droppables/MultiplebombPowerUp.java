package com.group0565.bomberGame.droppables;

import com.group0565.bomberGame.BomberEngine;
import com.group0565.bomberGame.BomberMan;
import com.group0565.bomberGame.grid.Grid;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.render.ThemedPaintCan;
import com.group0565.math.Coords;
import com.group0565.math.Vector;

public class MultiplebombPowerUp extends Droppable {

    /** PaintCan for this crate's fill. */
    private ThemedPaintCan paintCan;

    public MultiplebombPowerUp(Coords position, double z, Grid grid, BomberEngine game) {
        super(position, z, grid, game);
        this.paintCan = new ThemedPaintCan("Bomber", "Droppable.MultiplebombDroppable");
    }

    public void affectPlayer(BomberMan bm){
        bm.setNumSimultaneousBombs(bm.getNumSimultaneousBombs() + 1);
    }

    /**
     * Draws ONLY this object to canvas. Its children are not drawn by this method.
     *
     * @param canvas The Canvas on which to draw this crate.
     */
    @Override
    public void draw(Canvas canvas) {
        Vector pos = getAbsolutePosition();
        // Draw a rectangle at our touch position
        canvas.drawRect(pos, new Vector(grid.getTileWidth(), grid.getTileWidth()), paintCan);
    }

    @Override
    public void init() {
        super.init();
        paintCan.init(getGlobalPreferences(), getEngine().getGameAssetManager());
    }
}
