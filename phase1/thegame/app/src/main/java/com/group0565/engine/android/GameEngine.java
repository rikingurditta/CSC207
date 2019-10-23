package com.group0565.engine.android;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

import com.group0565.engine.gameobjects.GameObject;

public class GameEngine implements Runnable {
    private static final String TAG = "GameEngine";
    private static final double NS = 1E9;
    private static final long NSPM = 1000000;
    private final Object surfaceLock = new Object();
    private Thread thread = null;
    private boolean running = false;
    private boolean paused = false;
    private int fps;
    private double nspf;
    private SurfaceHolder surfaceHolder;
    private GameObject game;
    private InputManager inputManager;

    public GameEngine(GameObject game, int fps) {
        this.surfaceHolder = null;
        this.game = game;
        this.setFps(fps);
        this.inputManager = new InputManager(game);
    }

    @Override
    public void run() {

        long lastupdate = System.nanoTime();
        long lastms = System.currentTimeMillis();
        double renders = 0;
        MainLoop:
        while (running) {
            synchronized (this) {
                while (paused || surfaceHolder == null) {
                    game.pause();
                    lastupdate = 0;
                    lastms = 0;
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        break MainLoop;
                    }
                }
                if (lastupdate == 0) {
                    lastupdate = System.nanoTime();
                    lastms = System.currentTimeMillis();
                }
            }
            long current = System.nanoTime();
            long delta = current - lastupdate;
            lastupdate = current;

            long currentms = System.currentTimeMillis();
            long deltams = currentms - lastms;
            lastms = currentms;

            update(deltams);

            renders += delta / nspf;

            if (renders >= 1) {
                render();
                renders -= (int) renders;
            }
        }
        game.stop();
    }

    public void update(long ms) {
        game.updateAll(ms);
    }

    public void render() {
        Canvas canvas = null;
        try {
            canvas = this.surfaceHolder.lockCanvas();
            if (canvas != null)
                this.game.renderAll(canvas);
        } finally {
            if (canvas != null) {
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    public synchronized boolean start(long ms) throws InterruptedException {
        if (running) return false;
        if (this.thread != null) {
            this.thread.interrupt();
        }
        this.running = true;
        this.paused = false;
        this.thread = new Thread(this);
        this.thread.start();
        return true;
    }

    public synchronized boolean stop() {
        if (running) return false;
        this.running = true;
        this.paused = false;
        this.thread = new Thread(this);
        this.thread.start();
        return true;
    }

    public synchronized boolean pause() {
        if (!running) return false;
        if (!paused) return false;
        this.paused = true;
        return true;
    }

    public synchronized boolean resume() {
        if (!running) return false;
        if (paused) return false;
        try {
            this.paused = false;
            this.notifyAll();
        } catch (SecurityException e) {
            Log.e(TAG, "Error occurred while resuming Thread", e);
            this.paused = true;
            return false;
        }
        return true;
    }

    public SurfaceHolder getSurfaceHolder() {
        return surfaceHolder;
    }

    public synchronized void setSurfaceHolder(SurfaceHolder surfaceHolder) {
        synchronized (surfaceLock) {
            this.surfaceHolder = surfaceHolder;
        }
        this.notifyAll();
    }

    public int getFps() {
        return fps;
    }

    public void setFps(int fps) {
        this.fps = fps;
        this.nspf = NS / fps;
    }

    public InputManager getInputManager() {
        return inputManager;
    }
}
