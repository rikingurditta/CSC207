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
    private int lastActive = 0;
    private long window;

    public TsuRenderer(Vector position, Beatmap beatmap, Vector size, long window) {
        super(position);
        this.beatmap = beatmap;
        this.objects = beatmap.getHitObjects();
        this.size = size;
        this.window = window;
    }

    public Vector getSize() {
        return size;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (size == null) {
            size = new Vector(canvas.getWidth(), canvas.getHeight());
        }
    }

    public void setSize(Vector size) {
        this.size = size;
    }

    public Beatmap getBeatmap() {
        return beatmap;
    }

    public long getTimer() {
        return timer;
    }

    public void setTimer(long ms) {
        this.timer = ms;
        if (timer <= 0)
            lastActive = 0;
        while (lastActive < objects.size() && objects.get(lastActive).getMsEnd() < timer)
            lastActive++;
    }

    protected List<HitObject> getObjects() {
        return objects;
    }

    protected int getLastActive() {
        return lastActive;
    }

    public long getWindow() {
        return window;
    }

    public void setWindow(long window) {
        this.window = window;
    }
}
