package com.group0565.hitObjectsRepository;

import com.group0565.tsu.gameObjects.HitObject;

import java.util.ArrayList;
import java.util.List;

public class SessionHitObjects {

    private List<HitObject> hitObjects;

    public SessionHitObjects(List<HitObject> hitObjects) {
        this.hitObjects = hitObjects;
    }

    public SessionHitObjects() {
        this.hitObjects = new ArrayList<>();
    }

    public List<HitObject> getHitObjects() {
        return hitObjects;
    }

    public void setHitObjects(List<HitObject> hitObjects) {
        this.hitObjects = hitObjects;
    }

    public void addToList(HitObject newHitObject) {
        this.hitObjects.add(newHitObject);
    }
}
