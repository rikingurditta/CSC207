package com.group0565.engine.gameobjects;

import com.group0565.engine.interfaces.Bitmap;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.GameEngine;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.Paint;
import com.group0565.math.Vector;

public class Button extends MenuObject implements Observable {
    private boolean pressed;
    private Bitmap up;
    private Bitmap down;

    public Button(Vector position, Vector size) {
        this(position, size, null, null);
    }

    public Button(Vector position, Vector size, Bitmap up, Bitmap down) {
        super(size);
        this.setRelativePosition(position);
        this.up = up;
        this.down = down;
    }

    public Button(Vector position, Vector size, Bitmap image) {
        this(position, size, image, image);
    }

    public Button(Vector size) {
        super(size);
    }

    public Button(Vector size, Bitmap image) {
        super(size);
        this.up = image;
        this.down = image;
    }

    public Button(Vector size, GameEngine engine, String set, String name, int tileX, int tileY) {
        this(size);
        this.up = engine.getGameAssetManager().getTileSheet(set, name).getTile(tileX, tileY);
        this.down = up;
    }

    public Button(Vector size, GameEngine engine, String set, String name) {
        this(size, engine, set, name, 0, 0);
    }

    @Override
    public void update(long ms) {
        super.update(ms);
        float sx = getSize().getX();
        float sy = getSize().getY();
        float ax = getAbsolutePosition().getX();
        float ay = getAbsolutePosition().getY();
        boolean f = false;
        for (InputEvent event : this.getCapturedEvents()) {
            float ex = event.getPos().getX();
            float ey = event.getPos().getY();
            if (ax <= ex && ex <= ax + sx) {
                if (ay <= ey && ey <= ax + sy) {
                    f = true;
                    break;
                }
            }
        }
        if (!f && pressed) {
            setPressed(false);
            notifyObservers();
        }
    }

    @Override
    public void draw(Canvas canvas, Vector pos, Vector size) {
        super.draw(canvas, pos, size);
        Bitmap drawable = (pressed ? down : up);
        if (drawable != null) {
            canvas.drawBitmap(drawable, pos, size);
        }
    }

    @Override
    protected void onEventCapture(InputEvent event) {
        super.onEventCapture(event);
        setPressed(true);
    }

    @Override
    public boolean processInput(InputEvent event) {
        if (!isEnable())
            return super.processInput(event);
        Vector pos = event.getPos();
        float ex = pos.getX();
        float ey = pos.getY();
        float sx = getSize().getX();
        float sy = getSize().getY();
        float ax = getAbsolutePosition().getX();
        float ay = getAbsolutePosition().getY();
        if (ax <= ex && ex <= ax + sx) {
            if (ay <= ey && ey <= ay + sy) {
                captureEvent(event);
                return true;
            }
        }
        return super.processInput(event);
    }

    public boolean isPressed() {
        return pressed;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
        this.notifyObservers();
    }

    public Bitmap getUp() {
        return up;
    }

    public void setUp(Bitmap up) {
        this.up = up;
    }

    public Bitmap getDown() {
        return down;
    }

    public void setDown(Bitmap down) {
        this.down = down;
    }
}
