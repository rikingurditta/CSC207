package com.group0565.tsu.gameObjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.group0565.engine.gameobjects.Button;
import com.group0565.engine.gameobjects.GameObject;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.Observer;
import com.group0565.math.Vector;

public class SettingsMenu extends GameObject implements Observer {
    private static final float EXIT_SIZE = 50;
    private Vector size;
    private Paint rim;
    private Paint center;
    private Button exit;

    public SettingsMenu(Vector position, Vector size) {
        super(position);
        this.size = size;
    }

    @Override
    public void init() {
        super.init();
        this.rim = new Paint();
        this.rim.setARGB(255, 255, 0, 255);
        this.center = new Paint();
        this.center.setARGB(255, 0, 0, 0);
        Bitmap exitBitmap = getEngine().getGameAssetManager().getTileSheet("Tsu", "Buttons").getTile(0, 0);
        this.exit = new Button(this.getAbsolutePosition(),
                new Vector(EXIT_SIZE, EXIT_SIZE), exitBitmap, exitBitmap);
        exit.registerObserver(this);
        adopt(exit);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        float x = getAbsolutePosition().getX();
        float y = getAbsolutePosition().getY();
        float w = size.getX();
        float h = size.getY();
        canvas.drawRoundRect(x, y, x + w, y + h, 50, 50, rim);
        canvas.drawRoundRect(x + 10, y + 10, x + w - 10, y + h - 10, 50, 50, center);
    }

    @Override
    public void observe(Observable observable) {
        if (observable == exit)
            if (exit.isPressed())
                this.setEnable(false);
    }
}
