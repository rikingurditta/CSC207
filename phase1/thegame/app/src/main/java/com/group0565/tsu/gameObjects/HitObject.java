package com.group0565.tsu.gameObjects;

import org.json.JSONException;
import org.json.JSONObject;

public class HitObject {
    private long msStart;
    private double positionStart;
    private long msEnd;
    private double positionEnd;
    private long hitTime = -1;
    private long holdTime = -1;
    private long releaseTime = -1;

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
}
