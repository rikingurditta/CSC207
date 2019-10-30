package com.group0565.tsu.gameObjects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.group0565.engine.assets.TileSheet;
import com.group0565.engine.gameobjects.GameObject;
import com.group0565.engine.gameobjects.InputEvent;
import com.group0565.math.Vector;
import com.group0565.tsu.enums.Align;
import com.group0565.tsu.enums.Scores;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class TsuEngine extends GameObject {
    private static final String TAG = "TsuEngine";
    private static final String BEATMAP_SET = "Tsu";
    private static final String BEATMAP_NAME = "BeatMap";
    private static final Vector MARGINS = new Vector(200, 250);
    private static final int HIT_WIDTH = 20;
    private static final double HIT_ERROR = 0.075;
    private static final float SCORE_WIDTH = 300;
    private static final float TSCORE_HEIGHT = 50;
    private static final float COMBO_HEIGHT = 75;
    private static final float SCORE_Y = 0.10f;
    private Beatmap beatmap;
    private List<HitObject> objects;
    private long timer = 0;
    private boolean running = false;
    private int lastActive = 0;
    private TsuRenderer renderer;
    private LinearJudgementLine judgementLine;
    private TileSheet scoresTileSheet;
    private NumberRenderer comboRenderer;
    private NumberRenderer scoreRenderer;

    private Scores score = null;
    private long lastScore = -1;

    private int combo = 0;
    private int totalScore = 0;

    private HashMap<InputEvent, HitObject> eventToHitObject = new HashMap<>();

    public TsuEngine() {
        super(new Vector());
    }

    public void init() {
        this.beatmap = new Beatmap(BEATMAP_SET, BEATMAP_NAME, this.getEngine().getGameAssetManager());
        this.objects = beatmap.getHitObjects();
        this.renderer = new LinearTsuRenderer(new Vector(MARGINS.getX(), 0), beatmap, null, 1000, HIT_WIDTH);
        this.judgementLine = new LinearJudgementLine(MARGINS, 100, 20);
        this.comboRenderer = new NumberRenderer(COMBO_HEIGHT, Align.CENTER, new Vector(0, MARGINS.getY()));
        this.comboRenderer.setNumber(combo);
        this.comboRenderer.setZ(1);

        this.scoreRenderer = new NumberRenderer(TSCORE_HEIGHT, Align.RIGHT, new Vector(50, 50));
        this.scoreRenderer.setNumber(totalScore);
        this.scoreRenderer.setZ(1);

        adopt(renderer);
        adopt(judgementLine);
        adopt(comboRenderer);
        adopt(scoreRenderer);
        scoresTileSheet = getEngine().getGameAssetManager().getTileSheet("Tsu", "Score");
        Scores.S300.setBitmap(scoresTileSheet.getTile(0, 0));
        Scores.S150.setBitmap(scoresTileSheet.getTile(0, 1));
        Scores.S50.setBitmap(scoresTileSheet.getTile(0, 2));
        Scores.S0.setBitmap(scoresTileSheet.getTile(0, 3));
        Scores.SU.setBitmap(scoresTileSheet.getTile(0, 3));
        super.init();
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
            } else if (timer < this.beatmap.getAudio().progress()) {
                timer = this.beatmap.getAudio().progress();
            } else
                timer += ms;
            this.renderer.setTimer(timer);
            for (int i = lastActive; i < objects.size() && timer > objects.get(i).getMsStart() + beatmap.getDistribution()[2]; i++) {
                objects.get(i).setPassed(true);
            }
            while (lastActive < objects.size() && objects.get(lastActive).getMsEnd() < timer) {
                if (objects.get(lastActive).getHitTime() < 0)
                    setHit(Scores.S0);
                lastActive++;
            }

            if (timer > objects.get(objects.size() - 1).getMsEnd() + 2000) {
                endGame();
            }
        }
    }

    private void setHit(Scores score) {
        this.score = score;
        if (score != Scores.S0)
            combo += 1;
        else
            combo = 0;
        totalScore += score.getScore() * combo;
    }

    private void endGame() {
        try {
            JSONArray array = new JSONArray();
            for (HitObject object : objects) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("HitTime", object.getHitTime());
                jsonObject.put("ReleaseTime", object.getReleaseTime());
                array.put(jsonObject);
            }
            System.out.println(array.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
                    eventToHitObject.put(event, obj);
                    obj.setHitTime(timer);
                    setHit(obj.computeScore(beatmap.getDistribution()));
                    lastScore = timer;
                    break;
                }
            }
        }
    }

    @Override
    protected void onEventDisable(InputEvent event) {
        super.onEventDisable(event);
        HitObject object = eventToHitObject.get(event);
        if (object == null)
            return;
        Scores lastscore = object.computeScore(beatmap.getDistribution());
        object.setReleaseTime(timer);
        if (lastscore != Scores.S0 && object.computeScore(beatmap.getDistribution()) == Scores.S0)
            setHit(Scores.S0);
    }

    @Override
    public boolean processInput(InputEvent event) {
        if (isEnable()) {
            captureEvent(event);
            return true;
        }
        return super.processInput(event);
    }

    @Override
    public void renderAll(Canvas canvas) {
        super.renderAll(canvas);
        if (score != null) {
            float sw = (float) (SCORE_WIDTH * (1 / 3d * Math.exp(-3 * Math.sqrt((timer - lastScore) / 250D)) + 1));
            float width = canvas.getWidth();
            float height = canvas.getHeight();
            float bwidth = score.getBitmap().getWidth();
            float bheight = score.getBitmap().getHeight();
            float bscale = sw / bwidth;
            canvas.drawBitmap(score.getBitmap(), null, new RectF((width - sw) / 2f, height * SCORE_Y, (width + sw) / 2f, height * SCORE_Y + bheight * bscale), new Paint());
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
        this.comboRenderer.setNumber(combo);
        double sh = (1 / 3d * Math.exp(-3 * Math.sqrt((timer - lastScore) / 250D)) + 1);
        this.comboRenderer.setHeight((float) (sh * COMBO_HEIGHT));
        this.scoreRenderer.setNumber(totalScore);
        this.scoreRenderer.setHeight((float) (sh * TSCORE_HEIGHT));
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
}
