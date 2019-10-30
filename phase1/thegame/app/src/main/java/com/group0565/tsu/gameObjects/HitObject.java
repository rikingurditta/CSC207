package com.group0565.tsu.gameObjects;

import com.group0565.engine.gameobjects.InputEvent;
import com.group0565.tsu.enums.Scores;

import org.json.JSONException;
import org.json.JSONObject;

public class HitObject {
    private long msStart;
    private double positionStart;
    private long msEnd;
    private double positionEnd;
    private boolean passed;
    private long hitTime = -1 << 31;
    private long holdTime = -1 << 31;
    private long releaseTime = -1 << 31;
    private InputEvent inputEvent;

    public InputEvent getInputEvent() {
        return inputEvent;
    }

    public void setInputEvent(InputEvent inputEvent) {
        this.inputEvent = inputEvent;
    }

    public HitObject(JSONObject jsonObject) throws JSONException {
        super();
        this.msStart = jsonObject.getLong("Time");
        this.msEnd = msStart + jsonObject.getLong("Duration");
        this.positionStart = jsonObject.getDouble("PositionStart");
        this.positionEnd = jsonObject.getDouble("PositionEnd");
    }

    public HitObject(long msStart, double positionStart, long msEnd, double positionEnd) {
        super();
        this.msStart = msStart;
        this.positionStart = positionStart;
        this.msEnd = msEnd;
        this.positionEnd = positionEnd;
    }

    public Double calculatePosition(long ms) {
        double t;
        if (msStart == msEnd)
            t = 0;
        t = (ms - msStart) / (msEnd - msStart);
        t = Math.max(0, Math.min(1, t));
        return positionStart * (1 - t) + positionEnd * t;
    }

    public Scores computeScore(long[] distribution) {
        long delta = Math.abs(msStart - hitTime);
        Scores score = null;
        if (delta < distribution[0])
            score = Scores.S300;
        else if (delta < distribution[1])
            score = Scores.S150;
        else if (delta < distribution[2])
            score = Scores.S50;
        else
            score = passed ? Scores.S0 : Scores.SU;
        if (score != Scores.S0 && score != Scores.SU) {
            if (releaseTime > 0) {
                if (Math.abs(releaseTime - msEnd) > distribution[2])
                    return Scores.S0;
            }
        }
        return score;
    }

    public long getMsStart() {
        return msStart;
    }

    public void setMsStart(long msStart) {
        this.msStart = msStart;
    }

    public double getPositionStart() {
        return positionStart;
    }

    public void setPositionStart(double positionStart) {
        this.positionStart = positionStart;
    }

    public long getMsEnd() {
        return msEnd;
    }

    public void setMsEnd(long msEnd) {
        this.msEnd = msEnd;
    }

    public double getPositionEnd() {
        return positionEnd;
    }

    public void setPositionEnd(double positionEnd) {
        this.positionEnd = positionEnd;
    }

    public long getHitTime() {
        return hitTime;
    }

    public void setHitTime(long hitTime) {
        this.hitTime = hitTime;
    }

    public long getHoldTime() {
        return holdTime;
    }

    public void setHoldTime(long holdTime) {
        this.holdTime = holdTime;
    }

    public void addHoldTime(long holdTime) {
        this.holdTime += holdTime;
    }

    public long getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(long releaseTime) {
        this.releaseTime = releaseTime;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }
}
