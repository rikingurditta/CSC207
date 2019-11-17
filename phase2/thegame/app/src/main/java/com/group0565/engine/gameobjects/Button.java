package com.group0565.engine.gameobjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import com.group0565.engine.interfaces.Observable;
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

    public Button(Vector size) {
        super(size);
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
            float sx = size.getX();
            float sy = size.getY();
            float ax = pos.getX();
            float ay = pos.getY();
            canvas.drawBitmap(drawable, null, new RectF(ax, ay, ax + sx, ay + sy), null);
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
