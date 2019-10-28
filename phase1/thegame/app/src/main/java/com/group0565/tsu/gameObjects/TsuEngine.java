package com.group0565.tsu.gameObjects;

import android.util.Log;

import com.group0565.engine.gameobjects.GameObject;
import com.group0565.math.Vector;

import java.util.List;

public class TsuEngine extends GameObject {
    private static final String TAG = "TsuEngine";
    private static final String BEATMAP_SET = "Tsu";
    private static final String BEATMAP_NAME = "BeatMap";
    private Beatmap beatmap;
    private List<HitObject> objects;
    private long timer = 0;
    private boolean running = false;
    private int lastActive = 0;
    private TsuRenderer renderer;

    public TsuEngine() {
        super(new Vector());
    }

    public void init() {
        this.beatmap = new Beatmap(BEATMAP_SET, BEATMAP_NAME, this.getEngine().getGameAssetManager());
        this.objects = beatmap.getHitObjects();
        this.renderer = new TsuRenderer(new Vector(), beatmap, null);
        adopt(renderer);
        this.startEngine();
    }

    @Override
    public void update(long ms) {
        super.update(ms);
        if (running) {
            timer = this.beatmap.getAudio().progress();

            while (objects.get(lastActive).getMsEnd() < timer)
                lastActive++;

            this.renderer.setTimer(timer);

            if (timer / 1000 < (timer + ms) / 1000)
                Log.i(TAG, String.valueOf(timer - this.beatmap.getAudio().progress()));
        }
    }

    public void startEngine() {
        this.timer = 0;
        this.beatmap.getAudio().seekTo((int) timer);
        this.beatmap.getAudio().play();
        this.running = true;
    }

    public void pauseEngine() {
        this.running = false;
        this.beatmap.getAudio().pause();
        this.beatmap.getAudio().seekTo((int) timer);
    }

    public void resumeEngine() {
        this.beatmap.getAudio().seekTo((int) timer);
        this.beatmap.getAudio().play();
        this.running = true;
    }

    public void stopEngine() {
        this.beatmap.getAudio().stop();
        this.running = false;
    }
}
