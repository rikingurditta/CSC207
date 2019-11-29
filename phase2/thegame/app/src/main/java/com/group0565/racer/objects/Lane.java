package com.group0565.racer.objects;

import android.graphics.Paint;

import com.group0565.engine.gameobjects.GameObject;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.math.Vector;

import java.util.ArrayList;

public class Lane extends GameObject {

    private boolean racerLane;
    private ArrayList laneContents;

    public Lane(Vector position, double z) {
        super(position, z);
        laneContents = new ArrayList();
    }

    public void setRacerLane(boolean racerLane) {
        this.racerLane = racerLane;
    }


    @Override
    public void draw(Canvas canvas) {
        Paint colour = new Paint();
        colour.setARGB(255, 255, 255, 255);

    }
}
