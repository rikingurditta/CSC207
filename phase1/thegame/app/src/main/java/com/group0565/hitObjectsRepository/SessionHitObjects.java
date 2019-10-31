package com.group0565.hitObjectsRepository;

import com.group0565.tsu.gameObjects.HitObject;

import java.util.ArrayList;
import java.util.List;

public class SessionHitObjects {

    private List<HitObject> hitObjects;
    private int score;
    private int grade;
    private double difficulty;
    private String datetime;
    private int maxCombo;

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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public double getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(double difficulty) {
        this.difficulty = difficulty;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public int getMaxCombo() {
        return maxCombo;
    }

    public void setMaxCombo(int maxCombo) {
        this.maxCombo = maxCombo;
    }
}
