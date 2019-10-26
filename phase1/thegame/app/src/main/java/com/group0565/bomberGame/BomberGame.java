package com.group0565.bomberGame;

import android.graphics.Canvas;

import com.group0565.engine.gameobjects.GameObject;
import com.group0565.math.Vector;

public class BomberGame extends GameObject {

    //make a array list that holds all the bomber mans inside of this class

    public BomberGame(GameObject parent, Vector position, boolean relative) {
        super(parent, position, relative);
        this.adopt(new BomberMan(this, new Vector(100, 100), false));
        this.adopt(new BomberMan(this, new Vector(200, 200), false));
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        // Fill background with White
        canvas.drawRGB(255, 255, 255);
    }


}
