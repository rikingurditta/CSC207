package com.group0565.tsu.gameObjects;

import com.group0565.hitObjectsRepository.SessionHitObjects;
import com.group0565.tsu.enums.Grade;
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

  public static SessionHitObjects computeScore(List<HitObject> objs, double difficulty) {
    SessionHitObjects sessionHitObjects = new SessionHitObjects();
    sessionHitObjects.setDatetime(new SimpleDateFormat("yyyy/mm/dd hh:mm").format(new Date()));
    sessionHitObjects.setDifficulty(difficulty);
    sessionHitObjects.setHitObjects(objs);
    long[] dist = calculateDistribution(difficulty);
    int score = 0;
    int combo = 0;
    int mxc = 0;
    int S300 = 0;
    int S150 = 0;
    int S50 = 0;
    int S0 = 0;
    for (HitObject object : objs) {
      Scores objscore = object.computeScore(dist);
      if (objscore != Scores.S0 && objscore != Scores.SU) {
        combo++;
        score += combo * objscore.getScore();
      } else {
        combo = 0;
      }
      mxc = Math.max(mxc, combo);
      switch (objscore) {
        case SU:
        case S0:
          S0 += 1;
          break;
        case S300:
          S300 += 1;
          break;
        case S150:
          S150 += 1;
          break;
        case S50:
          S50 += 1;
          break;
      }
    }
    sessionHitObjects.setS300(S300);
    sessionHitObjects.setS150(S150);
    sessionHitObjects.setS50(S50);
    sessionHitObjects.setS0(S0);
    sessionHitObjects.setScore(score);
    sessionHitObjects.setMaxCombo(mxc);
    sessionHitObjects.setGrade(Grade.score2Grade(score).getValue());
    return sessionHitObjects;
  }
}
