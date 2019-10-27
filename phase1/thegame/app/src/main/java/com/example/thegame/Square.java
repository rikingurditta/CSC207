package com.example.thegame;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.group0565.engine.gameobjects.GameObject;
import com.group0565.math.Vector;

public class Square extends GameObject {
    private Input input;
    private Paint paint;

    private Vector up = new Vector(0, -100);
    private Vector down = new Vector(0, 100);
    private Vector left = new Vector(-100, 0);
    private Vector right = new Vector(100, 0);

    public Square(GameObject parent, Vector position, boolean relative, Input input) {
        super(parent, position, relative, 0);
        this.input = input;
        this.paint = new Paint();
        this.paint.setARGB(255, 0, 0, 255);
    }

    @Override
    public void update(long ms) {
        super.update(ms);
        if (input.up)
            this.setRelativePosition(this.getRelativePosition().add(up.multiply(ms / 1000f)));
        if (input.down)
            this.setRelativePosition(this.getRelativePosition().add(down.multiply(ms / 1000f)));
        if (input.left)
            this.setRelativePosition(this.getRelativePosition().add(left.multiply(ms / 1000f)));
        if (input.right)
            this.setRelativePosition(this.getRelativePosition().add(right.multiply(ms / 1000f)));
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawRect(this.getAbsolutePosition().getX(), this.getAbsolutePosition().getY(), this.getAbsolutePosition().getX() + 100, this.getAbsolutePosition().getY() + 100, paint);
    }
}
