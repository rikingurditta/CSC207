package com.group0565.tsu.gameObjects;

import android.graphics.Canvas;

import com.group0565.engine.gameobjects.GameObject;
import com.group0565.math.Vector;

import java.util.List;

public class TsuRenderer extends GameObject {
    private Vector size;
    private Beatmap beatmap;
    private long timer;
    private List<HitObject> objects;

    public TsuRenderer(Vector position, Beatmap beatmap, Vector size) {
        super(position);
        this.beatmap = beatmap;
        this.objects = beatmap.getHitObjects();
    }

    public void setTimer(long ms) {
        this.timer = ms;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (size == null) {
            size = new Vector(canvas.getWidth(), canvas.getHeight());
        }
    }
}
