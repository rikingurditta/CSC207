package com.group0565.engine.android;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.group0565.engine.android.assets.AndroidAssetManager;
import com.group0565.engine.assets.GameAssetManager;
import com.group0565.engine.gameobjects.GameObject;
import com.group0565.engine.interfaces.GameEngine;
import com.group0565.math.Vector;

/**
 * Concrete implementation of GameEngine for the Android System
 */
public class AndroidGameEngine implements Runnable, GameEngine {
    /**Constant for number of nanoseconds per second**/
    private static final double NS = 1E9;
    /**Object for locking the surface holder**/
    private final Object surfaceLock = new Object();
     /**The Thread the engine runs on. Null if engine is not started**/
    private Thread thread = null;
     /**Whether or not the engine is running**/
    private boolean running = false;
     /**Whether or not the engine is paused**/
    private boolean paused = false;
     /**The number of frames per second to render**/
    private int fps;
    /**The number of nanoseconds per frame**/
    private double nspf;
    /**Holder for the surface from which canvases shoul be obtained**/
    private SurfaceHolder surfaceHolder;
    /**Root object of the game**/
    private GameObject game;
    /**Instance of the InputManager**/
    private InputManager inputManager;
    /**Instance of the AssetManager**/
    private GameAssetManager gameAssetManager;
    /**Size of drawable area**/
    private Vector size;

    /**
     * Create an instance of the GameEngine
     * @param game The root object of the Game
     * @param fps The number of frames per second
     * @param assetManager The android assetManager from which assets should be loaded
     */
    public AndroidGameEngine(GameObject game, int fps, AssetManager assetManager) {
        this.surfaceHolder = null;
        this.game = game;
        this.game.setEngine(this);
        this.setFps(fps);
        this.inputManager = new InputManager(game);
        this.gameAssetManager = new AndroidAssetManager(assetManager);
    }

    /**
     * Main executing method of the GameEngine
     */
    @Override
    public void run() {
        //If surface is not ready yet, wait for it.
        synchronized (this) {
            while (surfaceHolder == null) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                }
            }
        }
        //Initialize manager and game
        gameAssetManager.init();
        game.fullInit();

        //Last update in ns
        long lastupdate = System.nanoTime();
        //Used to avoid rounding errors and GameObject wants delta time in ms
        long lastms = System.currentTimeMillis();
        //Number of renders we "owe"
        double renders = 0;
        MainLoop:
        while (running) {
            synchronized (this) {
                //If surface is gone or we want to pause, go pause
                while (paused || surfaceHolder == null) {
                    game.pause();
                    lastupdate = 0;
                    lastms = 0;
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        break MainLoop;
                    }
                    game.resume();
                }
                //If we paused, write new data into these to avoid "oweing" renders while we were paused.
                if (lastupdate == 0) {
                    lastupdate = System.nanoTime();
                    lastms = System.currentTimeMillis();
                }
            }
            //Calculate delta time in ns and ms
            long current = System.nanoTime();
            long delta = current - lastupdate;
            lastupdate = current;

            long currentms = System.currentTimeMillis();
            long deltams = currentms - lastms;
            lastms = currentms;

            update(deltams);

            renders += delta / nspf;
            //Render if we "owe" renders
            if (renders >= 1) {
                render();
                renders -= (int) renders;
            }
        }
        game.stop();
    }

    /**
     * Propogates the update even through the game
     * @param ms Milliseconds since last update
     */
    public void update(long ms) {
        game.updateAll(ms);
    }

    /**
     * Render the game for a frame
     */
    public void render() {
        Canvas canvas = null;
        try {
            //If no surface is ready, we can't render
            if (this.surfaceHolder == null)
                return;
            canvas = this.surfaceHolder.lockCanvas();
            if (canvas != null) {
                size = new Vector(canvas.getWidth(), canvas.getHeight());
                com.group0565.engine.interfaces.Canvas canvasFacade = new AndroidCanvas(canvas);
                this.game.renderAll(canvasFacade);
            }
        } finally {
            if (canvas != null) {
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    /**
     * Starts the Engine
     * @return True iff the engine was started successfully
     */
    public synchronized boolean start() {
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

    /**
     * Stops the Engine
     * @return True iff the engine was Stopped successfully
     */
    public synchronized boolean stop() {
        if (running) return false;
        this.running = true;
        this.paused = false;
        this.thread = new Thread(this);
        this.thread.start();
        this.gameAssetManager.close();
        return true;
    }

    /**
     * Pauses the Engine
     * @return True iff the engine was paused successfully
     */
    public synchronized boolean pause() {
        if (!running) return false;
        if (!paused) return false;
        this.paused = true;
        return true;
    }

    /**
     * Resumes the Engine
     * @return True iff the engine was resumed successfully
     */
    public synchronized boolean resume() {
        if (!running) return false;
        if (paused) return false;
        try {
            this.paused = false;
            this.notifyAll();
        } catch (SecurityException e) {
            this.paused = true;
            return false;
        }
        return true;
    }

    /**
     * Setter for surfaceHolder.
     *
     * @param surfaceHolder The new value for surfaceHolder
     */
    public synchronized void setSurfaceHolder(SurfaceHolder surfaceHolder) {
        synchronized (surfaceLock) {
            this.surfaceHolder = surfaceHolder;
            if (surfaceHolder != null) {
                Canvas canvas = null;
                try {
                  canvas = this.surfaceHolder.lockCanvas();
                  if (canvas != null) size = new Vector(canvas.getWidth(), canvas.getHeight());
                } finally {
                  if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                  }
                }
            }
        }
        this.notifyAll();
    }

    /**
     * Getter for fps.
     *
     * @return fps
     */
    public int getFps() {
        return fps;
    }

    /**
     * Setter for fps.
     *
     * @param fps The new value for fps
     */
    public void setFps(int fps) {
        this.fps = fps;
        this.nspf = NS / fps;
    }

    /**
     * Getter for inputManager.
     *
     * @return inputManager
     */
    @Override
    public InputManager getInputManager() {
        return inputManager;
    }

    /**
     * Getter for gameAssetManager.
     *
     * @return gameAssetManager
     */
    @Override
    public GameAssetManager getGameAssetManager() {
        return gameAssetManager;
    }

    /**
     * Getter for size.
     *
     * @return size
     */
    @Override
    public Vector getSize() {
        return size;
    }
}
