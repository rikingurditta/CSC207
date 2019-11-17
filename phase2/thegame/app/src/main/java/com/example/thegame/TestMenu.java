package com.example.thegame;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.group0565.engine.enums.HorizontalAlignment;
import com.group0565.engine.enums.VerticalAlignment;
import com.group0565.engine.gameobjects.GameMenu;
import com.group0565.engine.gameobjects.MenuObject;
import com.group0565.math.Vector;

public class TestMenu extends GameMenu {
    public TestMenu(Vector size) {
        super(size);
        Vector s = new Vector(100, 100);
        build()
            .add("Block1",
                new Block(s, 0, 0, 0)
            )
            .addBuffer(50, 50)
            .setRelativePosition("this", HorizontalAlignment.LeftAlign, VerticalAlignment.TopAlign)
            .add("Block2",
                new Block(s, 255, 0, 0)
            )
            .addBuffer(50, 50)
            .setRelativePosition("Block1", HorizontalAlignment.RightOf, VerticalAlignment.BottomOf)
        .close();
    }

    @Override
    public void update(long ms) {
        super.update(ms);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawRGB(255, 255, 255);
    }
}

class Block extends MenuObject{
    private Paint paint;
    public Block(Vector size, int R, int G, int B) {
        super(size);
        this.paint = new Paint();
        this.paint.setARGB(255, R, G, B);
    }

    @Override
    public void draw(Canvas canvas, Vector pos, Vector size) {
        super.draw(canvas, pos, size);
        float x = pos.getX();
        float y = pos.getY();
        float w = size.getX();
        float h = size.getY();
        canvas.drawRect(x, y, x + w, x + h, paint);
    }
}
