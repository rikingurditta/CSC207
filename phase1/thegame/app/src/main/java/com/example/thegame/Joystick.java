package com.example.thegame;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.group0565.engine.gameobjects.GameObject;
import com.group0565.engine.gameobjects.InputEvent;
import com.group0565.math.Vector;

public class Joystick extends GameObject {
    public Input input;
    private Vector size;
    private Paint red, green;

    public Joystick(GameObject parent, Vector position, boolean relative, Vector size, double z) {
        super(parent, position, relative, z);
        this.size = size;
        this.red = new Paint();
        this.red.setARGB(255, 255, 0, 0);
        this.green = new Paint();
        this.green.setARGB(255, 0, 255, 0);
        this.input = new Input();
    }

    @Override
    public void update(long ms) {
        super.update(ms);
        float thisx = getAbsolutePosition().getX();
        float thisy = getAbsolutePosition().getY();
        float w = size.getX();
        float h = size.getY();
        input.up = checkbound(thisx + w / 3, thisy, w / 3, h / 3);
        input.left = checkbound(thisx, thisy + w / 3, w / 3, h / 3);
        input.right = checkbound(thisx + 2 * w / 3, thisy + w / 3, w / 3, h / 3);
        input.down = checkbound(thisx + w / 3, thisy + 2 * w / 3, w / 3, h / 3);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        float thisx = getAbsolutePosition().getX();
        float thisy = getAbsolutePosition().getY();
        float w = size.getX();
        float h = size.getY();
        canvas.drawRect(thisx + w / 3, thisy + h / 3, thisx + 2 * w / 3, thisy + 2 * w / 3, red);

        canvas.drawRect(thisx + w / 3, thisy, thisx + 2 * w / 3, thisy + 1 * w / 3, input.up ? green : red);
        canvas.drawRect(thisx, thisy + w / 3, thisx + 1 * w / 3, thisy + 2 * w / 3, input.left ? green : red);
        canvas.drawRect(thisx + 2 * w / 3, thisy + w / 3, thisx + w, thisy + 2 * w / 3, input.right ? green : red);
        canvas.drawRect(thisx + w / 3, thisy + 2 * w / 3, thisx + 2 * w / 3, thisy + w, input.down ? green : red);
    }

    private boolean checkbound(float x, float y, float w, float h) {
        for (InputEvent event : this.getCapturedEvents()) {
            Vector eventpos = event.getPos();
            float ex = eventpos.getX(), ey = eventpos.getY();
            if (x <= ex && ex <= x + w && y <= ey && ey <= y + h)
                return true;
        }
        return false;
    }

    @Override
    public boolean processInput(InputEvent event) {
        //Gets the position of this event
        Vector eventpos = event.getPos();
        //Check if we are in the bounding box based on our position and size
        Vector net = eventpos.subtract(this.getAbsolutePosition());
        if (net.getX() < 0 || net.getY() < 0)
            return super.processInput(event);
        Vector rem = size.subtract(net);
        if (rem.getX() < 0 || rem.getY() < 0)
            return super.processInput(event);
        //Capture the event if all checks pass
        captureEvent(event);
        return true;
    }
}

class Input {
    boolean up = false;
    boolean down = false;
    boolean left = false;
    boolean right = false;
}
