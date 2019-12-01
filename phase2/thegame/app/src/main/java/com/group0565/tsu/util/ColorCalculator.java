package com.group0565.tsu.util;

import com.group0565.tsu.game.HitObject;

import java.util.Random;

/**
 * Util class used to generate colors for HitObjects
 */
public class ColorCalculator {
    private static final Random random = new Random();
    /**
     * Computes a color using the position as a seed
     * @param object The HitObject to compute for
     * @return The color
     */
    public static int computeColor(HitObject object){
        long hash = Double.valueOf(object.getPosition()).hashCode();
        random.setSeed(hash);
        return (random.nextInt() & 0xFFFFFF) | 0xFF000000;
    }
}
