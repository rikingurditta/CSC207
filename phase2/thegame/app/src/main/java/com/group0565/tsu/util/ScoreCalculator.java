package com.group0565.tsu.util;

import com.group0565.hitObjectsRepository.SessionHitObjects;
import com.group0565.tsu.enums.Grade;
import com.group0565.tsu.enums.Scores;
import com.group0565.tsu.game.ArchiveInputEvent;
import com.group0565.tsu.game.Beatmap;
import com.group0565.tsu.game.HitObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Static util class containing helper methods with score
 */
public class ScoreCalculator {

  /**
   * Calculates the hit windows for a given distribution
   * @param difficulty The difficulty to calculate for
   * @return A long[3] array corresponding to the 300, 150, 50, and 0 hit windows respectively
   */
  public static long[] calculateDistribution(double difficulty) {
    long[] distribution = new long[4];
    distribution[0] = 50 + (long) (30 * (1 - difficulty / 5D));
    distribution[1] = 100 + (long) (30 * (1 - difficulty / 5D));
    distribution[2] = 150 + (long) (30 * (1 - difficulty / 5D));
    distribution[3] = 200 + (long) (30 * (1 - difficulty / 5D));
    return distribution;
  }

  /**
   * Constructs a SessionHitObjects given the finished Beatmap
   * @param beatmap The beatmap containg the finished hit objects
   * @param archive Set of input history
   * @return The constructed SessionHitObjects
   */
  public static SessionHitObjects constructSessionHitObjects(Beatmap beatmap, List<ArchiveInputEvent> archive) {
    List<HitObject> objs = beatmap.getHitObjects();
    double difficulty = beatmap.getDifficulty();
    SessionHitObjects sessionHitObjects = new SessionHitObjects();
    //Set the name of the beatmap
    sessionHitObjects.setBeatmapName(beatmap.getName());
    //Set the current Time
    sessionHitObjects.setDatetime(SimpleDateFormat.getDateTimeInstance().format(new Date()));
    //Set difficulty and hitObjects
    sessionHitObjects.setDifficulty(difficulty);
    sessionHitObjects.setHitObjects(objs);
    sessionHitObjects.setArchive(archive);
    //Start calculating the remaining parameters
    long[] dist = calculateDistribution(difficulty); //First get the hit window
    int score = 0; //Accumulator for score
    int combo = 0; //Accumulator for combo
    int mxc = 0; //Keeps track of maximum combo
    HashMap<Scores, Integer> scores = new HashMap<>(); //Stores the number of hits for every accuracy (Score)
    for (HitObject object : objs) {
      Scores objscore = computeScore(object, dist, true);
      if (objscore != Scores.S0) { //If the object was hit, increase the combo and give score
        combo++;
        score += combo * objscore.getScore();
      } else { //Otherwise reset their combo
        combo = 0;
      }
      mxc = Math.max(mxc, combo); //Update maximum combo
      //Update number of hits at an accuracy
      Integer prev = scores.getOrDefault(objscore, 1);
      scores.put(objscore, (prev == null ? 1 : prev + 1));
    }
    //Write the calculated parameters to the sessionHitObjects
    sessionHitObjects.setHitScore(scores);
    sessionHitObjects.setScore(score);
    sessionHitObjects.setMaxCombo(mxc);
    sessionHitObjects.setGrade(Grade.score2Grade(score).getValue());
    return sessionHitObjects;
  }

  /**
   * Computes the Score given a startTime and a hittime
   * @param msStart The startTime of a note
   * @param hitTime The time the note was hit
   * @param distribution The distribution to evaluate at
   * @param strict If true, then any timing other than S300/S150/S50 is a miss
   * @return The Score for this hit
   */
  public static Scores computeHitScore(long msStart, long hitTime, long[] distribution, boolean strict){
    //The time between start time and hit time
    long delta = Math.abs(msStart - hitTime);
    Scores score;
    //Calculate the Score
    if (delta < distribution[0]) score = Scores.S300;
    else if (delta < distribution[1]) score = Scores.S150;
    else if (delta < distribution[2]) score = Scores.S50;
    else if (delta < distribution[3]) score = Scores.S0;
    else score = strict ? Scores.S0 : null;
    return score;
  }

  /**
   * Calculate the Score for HitObject given the hit window
   * @param object The HitObject to compute for
   * @param distribution The hit window distribution
   * @param strict If true, then any timing other than S300/S150/S50 is a miss
   * @return The score for this HitObject
   */
  public static Scores computeScore(HitObject object, long[] distribution, boolean strict) {
    Scores score = computeHitScore(object.getMsStart(), object.getHitTime(), distribution, strict);
    if (score != Scores.S0 && object.getMsStart() != object.getMsEnd()) {
      if (object.getReleaseTime() > 0) {
        return computeHitScore(object.getMsEnd(), object.getReleaseTime(), distribution, true);
      }
    }
    return score;
  }
}
