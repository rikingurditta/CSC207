package com.group0565.tsu.gameObjects;

import android.graphics.Canvas;

import com.group0565.math.Vector;
import com.group0565.tsu.enums.Scores;

import java.util.List;

public class LinearTsuRenderer extends TsuRenderer {
    private int hitwidth;

    public LinearTsuRenderer(Vector position, Beatmap beatmap, Vector size, long window, int hitwidth) {
        super(position, beatmap, size, window);
        for (Scores score : Scores.values())
            score.getPaint().setStrokeWidth(hitwidth);
        this.hitwidth = hitwidth;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        List<HitObject> objects = getObjects();
        HitObject hitObject;
        long[] distribution = getBeatmap().getDistribution();
        for (int i = getLastActive(); i < objects.size() && (hitObject = objects.get(i)).getMsStart() <= getTimer() + getWindow(); i++) {
            double xstart = getAbsolutePosition().getX() + hitObject.getPositionStart() * getSize().getX();
            double ystart = getAbsolutePosition().getY() + (1 - (hitObject.getMsStart() - getTimer()) / (double) getWindow()) * getSize().getY();
            double xend = getAbsolutePosition().getX() + hitObject.getPositionEnd() * getSize().getX();
            double yend = getAbsolutePosition().getY() + (1 - (hitObject.getMsEnd() - getTimer()) / (double) getWindow()) * getSize().getY();

            canvas.drawLine((float) xstart, (float) ystart, (float) xend, (float) yend, hitObject.computeScore(distribution).getPaint());
        }
    }
}
