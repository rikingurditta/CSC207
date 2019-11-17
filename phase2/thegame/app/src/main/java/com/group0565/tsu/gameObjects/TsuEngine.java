package com.group0565.tsu.gameObjects;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.group0565.engine.assets.TileSheet;
import com.group0565.engine.gameobjects.Button;
import com.group0565.engine.gameobjects.GameObject;
import com.group0565.engine.gameobjects.InputEvent;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.Observer;
import com.group0565.hitObjectsRepository.SessionHitObjects;
import com.group0565.math.Vector;
import com.group0565.theme.Themes;
import com.group0565.tsu.enums.Align;
import com.group0565.tsu.enums.Scores;

import java.util.HashMap;
import java.util.List;

public class TsuEngine extends GameObject implements Observer, Observable {
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
    private static final float BUTTON_SIZE = 75;
    private static final float MARGIN = 75;
    private static final float PAUSE_W = 500;
    private static final float PAUSE_H = 300;
    private Button pause;
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
    private PauseMenu pauseMenu;
    private boolean ended = false;
    private boolean paused = false;
    private long[] distribution;
    private double difficulty;
    private boolean auto = false;

    private Paint paintText;

    private Scores score = null;
    private long lastScore = -1;

    private int combo = 0;
    private int totalScore = 0;
    private SessionHitObjects sessionHitObjects = null;

    private HashMap<InputEvent, HitObject> eventToHitObject = new HashMap<>();

    public TsuEngine() {
        super(new Vector());
    }

    public void init() {
        this.sessionHitObjects = null;
        this.beatmap = new Beatmap(BEATMAP_SET, BEATMAP_NAME, this.getEngine().getGameAssetManager());
        this.objects = beatmap.getHitObjects();
        this.renderer = new LinearTsuRenderer(this, new Vector(MARGINS.getX(), 0), beatmap, null, 1000, HIT_WIDTH);
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

        Bitmap backBitmap = getEngine().getGameAssetManager().getTileSheet("Tsu", "Buttons").getTile(9, 0);
        this.pause = new Button(this.getAbsolutePosition().add(new Vector(MARGIN, MARGIN)),
                new Vector(BUTTON_SIZE, BUTTON_SIZE), backBitmap, backBitmap);
        pause.registerObserver(this);
        adopt(pause);

        float cx = getEngine().getSize().getX();
        float cy = getEngine().getSize().getY();
        this.pauseMenu = new PauseMenu(new Vector((cx - PAUSE_W) / 2, (cy - PAUSE_H) / 2),
                new Vector(PAUSE_W, PAUSE_H));
        this.pauseMenu.registerObserver(this);
        this.pauseMenu.setEnable(false);
        this.pauseMenu.setZ(1);
        adopt(pauseMenu);

        this.paintText = new Paint();
        this.paintText.setTextSize(75);
        this.paintText.setARGB(255, 0, 0, 0);
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
                    this.beatmap.getAudio().setVolume((float) getGlobalPreferences().volume);
                }
            } else if (timer < this.beatmap.getAudio().progress()) {
                timer = this.beatmap.getAudio().progress();
            } else
                timer += ms;
            this.renderer.setTimer(timer);
            for (int i = lastActive; i < objects.size() && timer > objects.get(i).getMsStart() + distribution[2]; i++) {
                objects.get(i).setPassed(true);
            }
            if (auto)
                for (int i = lastActive; i < objects.size(); i++) {
                    HitObject object = objects.get(i);
                    if (object.getMsStart() < timer && object.getInputEvent() == null) {
                        object.setInputEvent(new InputEvent());
                        object.setHitTime(object.getMsStart());
                        setHit(Scores.S300);
                        lastScore = timer;
                    } else if (object.getMsEnd() < timer) {
                        object.getInputEvent().deactivate();
                        object.setReleaseTime(object.getMsEnd());
                    }
                    if (object.getMsStart() > timer)
                        break;
                }
            while (lastActive < objects.size() && objects.get(lastActive).getMsEnd() < timer) {
                if (objects.get(lastActive).getHitTime() < 0)
                    setHit(Scores.S0);
                lastActive++;
            }

            if (timer > objects.get(objects.size() - 1).getMsEnd() + 2000) {
                endGame(true);
            }
        }
    }

    @Override
    public void observe(Observable observable) {
        if (observable == pause) {
            if (pause.isPressed()) {
                this.pauseEngine();
                this.pauseMenu.setEnable(true);
            }
        } else if (observable == pauseMenu) {
            if (pauseMenu.isResume()) {
                this.resumeEngine();
                this.pauseMenu.setResume(false);
                this.pauseMenu.setEnable(false);
            } else if (pauseMenu.isExit()) {
                this.pauseMenu.setExit(false);
                endGame(false);
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

    private void endGame(boolean finished) {
        if (finished) {
            this.sessionHitObjects = ScoreCalculator.computeScore(objects, difficulty);
            this.sessionHitObjects.setCheats(auto);
        }
        ended = true;
        this.pauseEngine();
        this.notifyObservers();
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
            long leftt = start - distribution[2];
            long rightt = start + distribution[2];
            if (timer < leftt)
                break;
            if (timer <= rightt) {
                double pos = obj.calculatePosition(timer);
                if (-HIT_ERROR < hit - pos && hit - (pos + width) < HIT_ERROR) {
                    obj.setInputEvent(event);
                    eventToHitObject.put(event, obj);
                    obj.setHitTime(timer);
                    setHit(obj.computeScore(distribution));
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
        Scores lastscore = object.computeScore(distribution);
        object.setReleaseTime(timer);
        if (lastscore != Scores.S0 && object.computeScore(distribution) == Scores.S0)
            setHit(Scores.S0);
    }

    @Override
    public boolean processInput(InputEvent event) {
        if (isEnable()) {
            if (super.processInput(event))
                return true;
            if (!isAuto() && !pauseMenu.isEnable()) {
                captureEvent(event);
                return true;
            }
        }
        return false;
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
        if (getGlobalPreferences().theme == Themes.LIGHT) {
            canvas.drawRGB(255, 255, 255);
            this.paintText.setARGB(255, 0, 0, 0);
        } else if (getGlobalPreferences().theme == Themes.DARK) {
            canvas.drawRGB(0, 0, 0);
            this.paintText.setARGB(255, 255, 255, 255);
        }
        if (timer < -2000) {
            float y = canvas.getHeight() / 3;
            {
                String str = getEngine().getGameAssetManager().getLanguagePack("Tsu", getGlobalPreferences().language).getToken("Title") + ": " +
                        beatmap.getTitle();
                Rect rect = new Rect();
                this.paintText.getTextBounds(str, 0, str.length(), rect);
                canvas.drawText(str, (canvas.getWidth() - rect.width()) / 2, y, paintText);
                y += rect.height() + 10;
            }
            {
                String str = getEngine().getGameAssetManager().getLanguagePack("Tsu", getGlobalPreferences().language).getToken("Artist") + ": " +
                        beatmap.getArtist();
                Rect rect = new Rect();
                this.paintText.getTextBounds(str, 0, str.length(), rect);
                canvas.drawText(str, (canvas.getWidth() - rect.width()) / 2, y, paintText);
                y += rect.height() + 10;
            }
            {
                String str = getEngine().getGameAssetManager().getLanguagePack("Tsu", getGlobalPreferences().language).getToken("Arranger") + ": " +
                        beatmap.getArranger();
                Rect rect = new Rect();
                this.paintText.getTextBounds(str, 0, str.length(), rect);
                canvas.drawText(str, (canvas.getWidth() - rect.width()) / 2, y, paintText);
                y += rect.height() + 10;
            }
            {
                String str = getEngine().getGameAssetManager().getLanguagePack("Tsu", getGlobalPreferences().language).getToken("Mapper") + ": " +
                        beatmap.getMapper();
                Rect rect = new Rect();
                this.paintText.getTextBounds(str, 0, str.length(), rect);
                canvas.drawText(str, (canvas.getWidth() - rect.width()) / 2, y, paintText);
            }
            this.comboRenderer.setEnable(false);
            this.scoreRenderer.setEnable(false);
        } else {
            this.comboRenderer.setEnable(true);
            this.scoreRenderer.setEnable(true);
            this.comboRenderer.setNumber(combo);
            double sh = (1 / 3d * Math.exp(-3 * Math.sqrt((timer - lastScore) / 250D)) + 1);
            this.comboRenderer.setHeight((float) (sh * COMBO_HEIGHT));
            this.scoreRenderer.setNumber(totalScore);
            this.scoreRenderer.setHeight((float) (sh * TSCORE_HEIGHT));
        }
    }

    public void startEngine() {
        this.timer = -beatmap.getLeadin();
        this.lastActive = 0;
        this.beatmap.getAudio().seekTo((int) timer);
        this.beatmap.getAudio().setVolume(0);
        this.beatmap.getAudio().play();
        this.running = true;
        this.paused = false;
    }

    public void pauseEngine() {
        this.running = false;
        this.beatmap.getAudio().pause();
        this.beatmap.getAudio().seekTo((int) timer);
        this.paused = true;
    }

    public void resumeEngine() {
        this.beatmap.getAudio().seekTo((int) timer);
        this.beatmap.getAudio().play();
        this.running = true;
        this.paused = false;
    }

    public void restartEngine() {
        this.beatmap.getAudio().seekTo(0);
        this.getChildren().clear();
        this.init();
        this.startEngine();
        this.paused = false;
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
        this.paused = false;
    }

    public boolean hasEnded() {
        return ended;
    }

    public void setEnded(boolean ended) {
        this.ended = ended;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public long[] getDistribution() {
        return distribution;
    }

    public void setDistribution(long[] distribution) {
        this.distribution = distribution;
    }

    public double getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(double difficulty) {
        this.difficulty = difficulty;
        this.distribution = ScoreCalculator.calculateDistribution(difficulty);
    }

    public SessionHitObjects getSessionHitObjects() {
        return sessionHitObjects;
    }

    public boolean isAuto() {
        return auto;
    }

    public void setAuto(boolean auto) {
        this.auto = auto;
    }
}
