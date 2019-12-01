package com.group0565.tsu.game;

import android.graphics.LinearGradient;

import com.group0565.engine.gameobjects.GameMenu;
import com.group0565.engine.gameobjects.InputEvent;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.ObservationEvent;
import com.group0565.engine.interfaces.Paint;
import com.group0565.engine.interfaces.Source;
import com.group0565.engine.render.ThemedPaintCan;
import com.group0565.math.Vector;
import com.group0565.tsu.enums.Scores;
import com.group0565.tsu.util.ColorCalculator;

import java.util.HashMap;
import java.util.List;

import static com.group0565.tsu.util.ScoreCalculator.calculateDistribution;
import static com.group0565.tsu.util.ScoreCalculator.computeHitScore;

public class Judgementer extends GameMenu {
    //Event Constants
    public static final String NOTE_HIT = "Note Hit";
    public static final String NOTE_RELEASE = "Note Release";
    //Asset Constants
    private static final String SET = "Tsu";
    private static final String ThemeFolder = "Renderer.";
    //Paint Constants
    private static final String JudgementLinePaintName = ThemeFolder + "JudgementLine";
    //Misc Constants
    private static final float GLOW_HEIGHT = 500;
    private static final int GLOW_OPACITY = 100;

    private Paint colorPaint = Paint.createInstance();
    private ThemedPaintCan judgementPaint = new ThemedPaintCan(SET, JudgementLinePaintName);
    private HashMap<InputEvent, HitObject> hitMap = new HashMap<>();
    private HashMap<InputEvent, HitObject> captureMap = new HashMap<>();

    private Source<Integer> passedPointer;
    private Source<Beatmap> beatmap;
    private Source<List<HitObject>> hitObjects;
    private Source<Long> currentTime;

    public Judgementer(Source<Integer> passedPointer, Source<Beatmap> beatmap, Source<List<HitObject>> hitObjects, Source<Long> currentTime) {
        this.passedPointer = passedPointer;
        this.beatmap = beatmap;
        this.hitObjects = hitObjects;
        this.currentTime = currentTime;
    }

    public Judgementer(Vector size, Source<Integer> passedPointer, Source<Beatmap> beatmap, Source<List<HitObject>> hitObjects, Source<Long> currentTime) {
        super(size);
        this.passedPointer = passedPointer;
        this.beatmap = beatmap;
        this.hitObjects = hitObjects;
        this.currentTime = currentTime;
    }

    @Override
    public void init() {
        super.init();
        judgementPaint.init(getGlobalPreferences(), getEngine().getGameAssetManager());
    }

    @Override
    protected void onEventCapture(InputEvent event) {
        super.onEventCapture(event);
        //Attempt to hit an object using this event
        for (int i = passedPointer.getValue(); i < hitObjects.getValue().size(); i ++){
            HitObject object = hitObjects.getValue().get(i);
            //Calculate the width and position of this object
            float width = beatmap.getValue().getNoteWidth() * getSize().getX();
            float x = (float) (getAbsolutePosition().getX() + (object.getPosition() * getSize().getX()) - width / 2f);
            //If it is outside the width of the object, continue
            if (event.getPos().getX() < x || x + width < event.getPos().getX())
                continue;
            if (object.getHitTime() > 0)
                continue;
            //Otherwise see if the timing is correct
            if (computeHitScore(object.getMsStart(), currentTime.getValue(), calculateDistribution(beatmap.getValue().getDifficulty()), false) != null){
                //Set the object as Hit
                captureMap.put(event, object);
                object.setHitTime(currentTime.getValue());
                //And notify everyone about it
                notifyObservers(new ObservationEvent<>(NOTE_HIT, object));
                return;
            }

            if (object.getMsStart() > currentTime.getValue())
                break;
        }

        int delta = 1;
        //We didn't find a object to hit. Try to find a lane to light up
        for (int i = passedPointer.getValue(); i >= 0; i += delta){
            //If we can't find anything forward, start looking backwards
            if (i >= hitObjects.getValue().size() - 1) {
                i = Math.min(passedPointer.getValue(), hitObjects.getValue().size()-1);
                delta = -1;
            }
            HitObject object = hitObjects.getValue().get(i);
            //Calculate the width and position of this object
            float width = beatmap.getValue().getNoteWidth() * getSize().getX();
            float x = (float) (getAbsolutePosition().getX() + (object.getPosition() * getSize().getX()) - width / 2f);
            //If it is inside the bounds of the object, register it to light up
            if (x <= event.getPos().getX() && event.getPos().getX() <= x + width) {
                captureMap.put(event, object);
                break;
            }
        }
    }

    @Override
    protected void onEventDisable(InputEvent event) {
        super.onEventDisable(event);
        if (hitMap.containsKey(event)){ //If the event was for hitting an object
            HitObject object = hitMap.remove(event);
            object.setReleaseTime(currentTime.getValue());
            notifyObservers(new ObservationEvent<>(NOTE_RELEASE, object));
        }
        captureMap.remove(event); //Remove it from capture map. No need to check if its there.
    }

    @Override
    public boolean processInput(InputEvent event) {
        if (Vector.inBounds(getAbsolutePosition(), getSize(), event.getPos())) {
            captureEvent(event);
            return true;
        }
        return super.processInput(event);
    }

    @Override
    public void draw(Canvas canvas, Vector pos, Vector size) {
        super.draw(canvas, pos, size);
        if (!(hitObjects == null || beatmap == null)) {
            //Draw Glows
            for (int y = 0; y >= -GLOW_HEIGHT; y --){
                for (InputEvent event : hitMap.keySet()){
                    HitObject object = hitMap.get(event);
                    drawGlowLine(canvas, pos, size, y, object);
                }
                for (InputEvent event : captureMap.keySet()){
                    HitObject object = captureMap.get(event);
                    drawGlowLine(canvas, pos, size, y, object);
                }
            }
        }

        for (InputEvent event : this.getCapturedEvents())
            canvas.drawCircle(event.getPos(), 20, judgementPaint);
        canvas.drawLine(pos, pos.add(size.newSetY(0)), judgementPaint);
    }

    private void drawGlowLine(Canvas canvas, Vector pos, Vector size, float y, HitObject object) {
        float width = beatmap.getValue().getNoteWidth() * size.getX();
        float x = (float) ((object.getPosition() * size.getX()) - width / 2f);
        colorPaint.setColor(ColorCalculator.computeColor(object));
        colorPaint.setAlpha((int) (GLOW_OPACITY * (1+y/GLOW_HEIGHT)));
        canvas.drawRect(pos.add(new Vector(x, y)), new Vector(width, 1), colorPaint);
    }
}