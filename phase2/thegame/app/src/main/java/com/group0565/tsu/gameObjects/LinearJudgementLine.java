package com.group0565.tsu.gameObjects;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.group0565.math.Vector;

public class LinearJudgementLine extends JudgementLine {
    private Vector position;
    private double width;
    private double box;
    private Paint paint;

    public LinearJudgementLine(Vector position, double width, double box) {
        this.position = position;
        this.width = width;
        this.box = box;
        this.paint = new Paint();
        paint.setARGB(128, 0, 255, 0);
        paint.setStrokeWidth(20);
    }

    @Override
    public Double convert(Vector target) {
        if ((target.getY() - position.getY()) > box)
            return null;
        if (target.getX() < position.getX() - box)
            return null;
        if (target.getX() > position.getX() + width + box)
            return null;
        double t = (target.getX() - position.getX()) / width;
        t = Math.max(0, Math.min(1, t));
        return t;
    }

    @Override
    public double convert(int size) {
        return size / width;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawLine(position.getX(), position.getY(), (float) (position.getX() + width), position.getY(), paint);
    }

    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getBox() {
        return box;
    }

    public void setBox(double box) {
        this.box = box;
    }
}
