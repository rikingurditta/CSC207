package com.group0565.tsu.gameObjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.group0565.engine.assets.TileSheet;
import com.group0565.engine.gameobjects.GameObject;
import com.group0565.engine.gameobjects.InputEvent;
import com.group0565.math.Vector;

import java.util.List;

public class TsuEngine extends GameObject {
    private static final String TAG = "TsuEngine";
    private static final String BEATMAP_SET = "Tsu";
    private static final String BEATMAP_NAME = "BeatMap";
    private static final Vector MARGINS = new Vector(200, 250);
    private static final int HIT_WIDTH = 20;
    private static final double HIT_ERROR = 0.075;
    private static final float SCORE_WIDTH = 300;
    private static final float SCORE_Y = 0.25f;
    private Beatmap beatmap;
    private List<HitObject> objects;
    private long timer = 0;
    private boolean running = false;
    private int lastActive = 0;
    private TsuRenderer renderer;
    private LinearJudgementLine judgementLine;
    private TileSheet scoresTileSheet;

    private Scores score = null;
    private long lastScore = -1;

    public TsuEngine() {
        super(new Vector());
    }

    public void init() {
        this.beatmap = new Beatmap(BEATMAP_SET, BEATMAP_NAME, this.getEngine().getGameAssetManager());
        this.objects = beatmap.getHitObjects();
        this.renderer = new LinearTsuRenderer(new Vector(MARGINS.getX(), 0), beatmap, null, 1000, HIT_WIDTH);
        this.judgementLine = new LinearJudgementLine(MARGINS, 100, 20);
        adopt(renderer);
        adopt(judgementLine);
        scoresTileSheet = getEngine().getGameAssetManager().getTileSheet("Tsu", "Score");
        Scores.S300.bitmap = scoresTileSheet.getTile(0, 0);
        Scores.S150.bitmap = scoresTileSheet.getTile(0, 1);
        Scores.S50.bitmap = scoresTileSheet.getTile(0, 2);
        Scores.S0.bitmap = scoresTileSheet.getTile(0, 3);
        this.startEngine();
    }

    @Override
    public void update(long ms) {
        super.update(ms);
        if (running) {
            if (timer <= 0) {
                timer += ms;
                if (timer >= 0) {
                    this.beatmap.getAudio().seekTo((int) timer);
                    this.beatmap.getAudio().setVolume(1.0f);
                }
            } else {
                timer = this.beatmap.getAudio().progress();
            }
            this.renderer.setTimer(timer);

            while (lastActive < objects.size() && objects.get(lastActive).getMsEnd() < timer) {
                if (objects.get(lastActive).getHitTime() < 0)
                    setHit(Scores.S0);
                lastActive++;
            }
        }
    }

    private void setHit(Scores score) {
        this.score = score;
    }

    @Override
    protected void onEventCapture(InputEvent event) {
        super.onEventCapture(event);
        Double hit = judgementLine.convert(event.getPos());
        double width = judgementLine.convert(HIT_WIDTH);
        if (hit == null)
            return;
        for (int i = lastActive; i < objects.size(); i++) {
            HitObject obj = objects.get(i);
            if (obj.getInputEvent() != null)
                continue;
            long start = obj.getMsStart();
            long leftt = start - beatmap.getDistribution()[2];
            long rightt = start + beatmap.getDistribution()[2];
            if (timer < leftt)
                break;
            if (timer <= rightt) {
                double pos = obj.calculatePosition(timer);
                if (-HIT_ERROR < hit - pos && hit - (pos + width) < HIT_ERROR) {
                    obj.setInputEvent(event);
                    obj.setHitTime(timer);
                    long[] distribution = beatmap.getDistribution();
                    long delta = Math.abs(obj.getMsStart() - obj.getHitTime());
                    if (delta < distribution[0])
                        setHit(Scores.S300);
                    else if (delta < distribution[1])
                        setHit(Scores.S150);
                    else if (delta < distribution[2])
                        setHit(Scores.S50);
                    lastScore = timer;
                    break;
                }
            }
        }
    }

    @Override
    public boolean processInput(InputEvent event) {
        captureEvent(event);
        return true;
    }

    @Override
    public void renderAll(Canvas canvas) {
        super.renderAll(canvas);
        if (score != null) {
            float width = canvas.getWidth();
            float height = canvas.getHeight();
            float bwidth = score.bitmap.getWidth();
            float bheight = score.bitmap.getHeight();
            float bscale = SCORE_WIDTH / bwidth;
            canvas.drawBitmap(score.bitmap, null, new RectF((width - SCORE_WIDTH) / 2f, height * SCORE_Y, (width + SCORE_WIDTH) / 2f, height * SCORE_Y + bheight * bscale), new Paint());
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.renderer.getSize() == null) {
            this.renderer.setSize(new Vector(canvas.getWidth() - 2 * MARGINS.getX(), canvas.getHeight() - MARGINS.getY()));
            this.judgementLine.setPosition(new Vector(MARGINS.getX(), canvas.getHeight() - MARGINS.getY()));
            this.judgementLine.setWidth(canvas.getWidth() - 2 * MARGINS.getX());
        }
        canvas.drawRGB(255, 255, 255);
    }

    public void startEngine() {
        this.timer = -beatmap.getLeadin();
        this.lastActive = 0;
        this.beatmap.getAudio().seekTo((int) timer);
        this.beatmap.getAudio().setVolume(0);
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

    @Override
    public void stop() {
        super.stop();
        stopEngine();
    }

    @Override
    public void pause() {
        super.pause();
        pauseEngine();
    }

    @Override
    public void resume() {
        super.resume();
        resumeEngine();
    }

    public void stopEngine() {
        this.beatmap.getAudio().stop();
        this.running = false;
    }

    private enum Scores {
        S300, S150, S50, S0;
        private Bitmap bitmap;
    }
}
