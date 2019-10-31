package com.group0565.tsu.gameObjects;

import com.group0565.hitObjectsRepository.SessionHitObjects;
import com.group0565.tsu.enums.Scores;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ScoreCalculator {

    public static long[] calculateDistribution(double difficulty) {
        long[] distribution = new long[3];
        distribution[0] = 50 + (long) (30 * (1 - difficulty / 5D));
        distribution[1] = 100 + (long) (30 * (1 - difficulty / 5D));
        distribution[2] = 150 + (long) (30 * (1 - difficulty / 5D));
        return distribution;
    }

    public static SessionHitObjects computeScore(List<HitObject> objs, double difficulty){
        SessionHitObjects sessionHitObjects = new SessionHitObjects();
        sessionHitObjects.setDatetime(new SimpleDateFormat("yyyy/mm/dd hh:mm").format(new Date()));
        sessionHitObjects.setDifficulty(difficulty);
        sessionHitObjects.setHitObjects(objs);
        long[] dist = calculateDistribution(difficulty);
        int score = 0;
        int combo = 0;
        int mxc = 0;
        for (HitObject object : objs){
            Scores objscore = object.computeScore(dist);
              if (objscore != Scores.S0 && objscore != Scores.SU) {
                combo++;
                score += combo * objscore.getScore();
              }else{
                  combo = 0;
              }
              mxc = Math.max(mxc, combo);
        }
        sessionHitObjects.setScore(score);
        sessionHitObjects.setMaxCombo(mxc);
        sessionHitObjects.setGrade(0);
        return sessionHitObjects;
    }

}
