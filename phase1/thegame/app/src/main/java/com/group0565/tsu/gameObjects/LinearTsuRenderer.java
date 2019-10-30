package com.group0565.tsu.gameObjects;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.group0565.math.Vector;

import java.util.List;

public class LinearTsuRenderer extends TsuRenderer {
    private int hitwidth;

    public LinearTsuRenderer(Vector position, Beatmap beatmap, Vector size, long window, int hitwidth) {
        super(position, beatmap, size, window);
        for (Scores score : Scores.values())
            score.paint.setStrokeWidth(hitwidth);
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

            Scores score = Scores.S0;
            long delta = Math.abs(hitObject.getMsStart() - hitObject.getHitTime());
            if (delta < distribution[0])
                score = Scores.S300;
            else if (delta < distribution[1])
                score = Scores.S150;
            else if (delta < distribution[2])
                score = Scores.S50;
            canvas.drawLine((float) xstart, (float) ystart, (float) xend, (float) yend, score.paint);
        }
    }

    private enum Scores {
        S300(29, 255, 0), S150(208, 255, 0), S50(255, 170, 0), S0(255, 0, 0);
        private Paint paint;

        Scores(int r, int g, int b) {
            this.paint = new Paint();
            paint.setARGB(255, r, g, b);
        }
    }
}
