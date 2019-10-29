package com.group0565.tsu.gameObjects;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.group0565.math.Vector;

import java.util.List;

public class LinearTsuRenderer extends TsuRenderer {
    private Paint paint1;
    private Paint paint2;
    private Paint paint3;
    private Paint paint4;

    public LinearTsuRenderer(Vector position, Beatmap beatmap, Vector size, long window) {
        super(position, beatmap, size, window);
        this.paint1 = new Paint();
        this.paint1.setARGB(255, 255, 0, 0);
        this.paint1.setStrokeWidth(10);
        this.paint2 = new Paint();
        this.paint2.setARGB(255, 168, 127, 50);
        this.paint2.setStrokeWidth(10);
        this.paint3 = new Paint();
        this.paint3.setARGB(255, 212, 227, 73);
        this.paint3.setStrokeWidth(10);
        this.paint4 = new Paint();
        this.paint4.setARGB(255, 73, 227, 81);
        this.paint4.setStrokeWidth(10);
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

            Paint paint = paint1;
            long delta = Math.abs(hitObject.getMsStart() - hitObject.getHitTime());
            if (delta < distribution[0])
                paint = paint4;
            else if (delta < distribution[1])
                paint = paint3;
            else if (delta < distribution[2])
                paint = paint2;
            canvas.drawLine((float) xstart, (float) ystart, (float) xend, (float) yend, paint);
        }
    }
}
