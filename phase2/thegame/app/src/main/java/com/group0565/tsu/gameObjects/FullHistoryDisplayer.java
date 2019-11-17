package com.group0565.tsu.gameObjects;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;

import com.group0565.engine.gameobjects.GameObject;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.Observer;
import com.group0565.hitObjectsRepository.SessionHitObjects;
import com.group0565.math.Vector;
import com.group0565.theme.Themes;
import com.group0565.tsu.enums.Grade;
import com.group0565.tsu.enums.Scores;

import java.util.List;

public class FullHistoryDisplayer extends GameObject implements Observer {
    private static final float CHEAT_BUF = 10;
    private static final float XMAR = 25;
    private static final float YMAR = 35;
    private static final float CHEAT_SIZE = 75;
    private static final float SCORE_WIDTH = 300;
    private static final float SCORE_HEIGHT = 100;
    private static final float SCORE_YBUFF = 10;
    private static final int SAMPLES = 50;
    private Vector size;
    private Bitmap cheat;
    private Paint rim;
    private Paint center;
    private StatsMenu menu;
    private SessionHitObjects objects;
    private Paint gradePaint;
    private Paint countPaint;
    private Paint scorePaint;
    private Paint comboPaint;
    private Paint difficultyPaint;
    private Paint datePaint;
    private GraphRenderer graphRenderer;


    public FullHistoryDisplayer(StatsMenu menu, Vector position, Vector size) {
        super(position);
        this.size = size;
        this.rim = new Paint();
        this.rim.setARGB(255, 128, 128, 128);
        this.center = new Paint();
        this.menu = menu;
        this.menu.registerObserver(this);
        this.gradePaint = new Paint();
        this.gradePaint.setTextSize(size.getY() * 0.3f);
        this.gradePaint.setTypeface(Typeface.DEFAULT_BOLD);
        this.countPaint = new Paint();
        this.countPaint.setTextSize(SCORE_HEIGHT);
        this.scorePaint = new Paint();
        this.scorePaint.setTextSize(size.getY() * 0.1f);
        this.scorePaint.setARGB(255, 0, 0, 0);
        this.comboPaint = new Paint();
        this.comboPaint.setTextSize(size.getY() * 0.1f);
        this.comboPaint.setARGB(255, 0, 0, 0);
        this.difficultyPaint = new Paint();
        this.difficultyPaint.setTextSize(size.getY() * 0.075f);
        this.difficultyPaint.setARGB(255, 0, 0, 0);
        this.datePaint = new Paint();
        this.datePaint.setTextSize(size.getY() * 0.075f);
        this.datePaint.setARGB(255, 0, 0, 0);
    }

    @Override
    public void init() {
        float x = getAbsolutePosition().getX();
        float y = getAbsolutePosition().getY();
        float w = size.getX();
        float h = size.getY();
        this.cheat = getEngine().getGameAssetManager().getTileSheet("Tsu", "Buttons").getTile(21, 0);
        this.graphRenderer = new GraphRenderer(new Vector(x + w / 2, y + h / 2),
                new Vector(w / 2 - XMAR, h / 2 - YMAR), SAMPLES);
        adopt(graphRenderer);
        super.init();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        float x = getAbsolutePosition().getX();
        float y = getAbsolutePosition().getY();
        float w = size.getX();
        float h = size.getY();
        canvas.drawRoundRect(x, y, x + w, y + h, 50, 50, rim);
        if (getGlobalPreferences().theme == Themes.LIGHT) {
            center.setARGB(255, 255, 255, 255);
            countPaint.setARGB(255, 0, 0, 0);
            this.scorePaint.setARGB(255, 0, 0, 0);
            this.comboPaint.setARGB(255, 0, 0, 0);
            this.difficultyPaint.setARGB(255, 0, 0, 0);
            this.datePaint.setARGB(255, 0, 0, 0);
        } else if (getGlobalPreferences().theme == Themes.DARK) {
            center.setARGB(255, 0, 0, 0);
            countPaint.setARGB(255, 255, 255, 255);
            this.scorePaint.setARGB(255, 255, 255, 255);
            this.comboPaint.setARGB(255, 255, 255, 255);
            this.difficultyPaint.setARGB(255, 255, 255, 255);
            this.datePaint.setARGB(255, 255, 255, 255);
        }
        canvas.drawRoundRect(x + 10, y + 10, x + w - 10, y + h - 10, 50, 50, center);

        if (objects == null)
            return;
        float gyb = 0;
        {
            Grade grade = Grade.num2Grade(objects.getGrade());
            gradePaint.setARGB(255, grade.getR(), grade.getG(), grade.getB());
            String gradeStr = grade.getString();
            Rect gradeRect = new Rect();
            this.gradePaint.getTextBounds(gradeStr, 0, gradeStr.length(), gradeRect);
            canvas.drawText(gradeStr, x + XMAR, y + YMAR + gradeRect.height(), gradePaint);
            gyb = y + YMAR + gradeRect.height();
            if (objects.hasCheats()) {
                canvas.drawBitmap(cheat, null, new RectF(x + XMAR + gradeRect.width() + CHEAT_BUF,
                        y + YMAR + gradeRect.height() - CHEAT_SIZE, x + XMAR + gradeRect.width() + CHEAT_BUF + CHEAT_SIZE, y + YMAR + gradeRect.height()), null);
            }
        }
        {
            int i = 0;
            for (Scores scores : Scores.values()) {
                if (scores == Scores.SU)
                    continue;
                float fy = gyb + SCORE_YBUFF + i * (SCORE_HEIGHT + SCORE_YBUFF);
                canvas.drawBitmap(scores.getBitmap(), null, new RectF(x + XMAR, y + fy,
                        x + XMAR + SCORE_WIDTH, y + fy + SCORE_HEIGHT), null);
                int count = getScoreCount(scores);
                String countStr = ": " + count;
                Rect countRect = new Rect();
                this.countPaint.getTextBounds(countStr, 0, countStr.length(), countRect);
                canvas.drawText(countStr, x + XMAR + SCORE_WIDTH, y + fy + SCORE_HEIGHT, countPaint);
                i++;
            }
            float fy = gyb + SCORE_YBUFF + i * (SCORE_HEIGHT + SCORE_YBUFF);
            String dateStr = objects.getDatetime();
            Rect dateRect = new Rect();
            this.difficultyPaint.getTextBounds(dateStr, 0, dateStr.length(), dateRect);
            canvas.drawText(dateStr, x + XMAR, y + fy + dateRect.height(), difficultyPaint);
        }
        {
            String scoreStr = getEngine().getGameAssetManager().getLanguagePack("Tsu", getGlobalPreferences().language).getToken("Score") + ": " +
                    objects.getScore();
            Rect scoreRect = new Rect();
            this.scorePaint.getTextBounds(scoreStr, 0, scoreStr.length(), scoreRect);
            canvas.drawText(scoreStr, x + size.getX() - scoreRect.width() - XMAR, y + YMAR + scoreRect.height(), scorePaint);

            String comboStr = getEngine().getGameAssetManager().getLanguagePack("Tsu", getGlobalPreferences().language).getToken("Combo") + ": " + objects.getMaxCombo();
            Rect comboRect = new Rect();
            this.comboPaint.getTextBounds(comboStr, 0, comboStr.length(), comboRect);
            canvas.drawText(comboStr, x + size.getX() - comboRect.width() - XMAR, y + YMAR + scoreRect.height() + comboRect.height() + SCORE_YBUFF, comboPaint);

            String difficultyStr = String.format(getEngine().getGameAssetManager().getLanguagePack("Tsu", getGlobalPreferences().language).getToken("Difficulty")
                            + ": %.1f",
                    objects.getDifficulty());
            Rect difficultyRect = new Rect();
            this.difficultyPaint.getTextBounds(difficultyStr, 0, difficultyStr.length(), difficultyRect);
            canvas.drawText(difficultyStr, x + size.getX() - difficultyRect.width() - XMAR, y + YMAR + scoreRect.height() + comboRect.height() + SCORE_YBUFF
                    + difficultyRect.height() + SCORE_YBUFF, difficultyPaint);
        }
    }

    private int getScoreCount(Scores score) {
        if (objects == null)
            return 0;
        switch (score) {
            case S300:
                return objects.getS300();
            case S150:
                return objects.getS150();
            case S50:
                return objects.getS50();
            default:
                return objects.getS0();
        }
    }

    @Override
    public void observe(Observable observable) {
        if (observable == menu) {
            this.objects = menu.getSelectedObject();
            if (this.graphRenderer != null)
                this.graphRenderer.calculate();
        }
    }

    private class GraphRenderer extends GameObject {
        private static final double MIN = 0.75;
        private Vector size;
        private int samples;
        private double[] results;
        private Paint axis;
        private Paint red;
        private Paint green;

        public GraphRenderer(Vector position, Vector size, int samples) {
            super(position);
            this.size = size;
            this.samples = samples;
            this.results = null;
            this.axis = new Paint();
            this.axis.setARGB(255, 128, 128, 128);
            this.axis.setStrokeWidth(10);
            this.red = new Paint();
            this.red.setARGB(255, 255, 0, 0);
            this.red.setStrokeWidth(10);
            this.green = new Paint();
            this.green.setARGB(255, 0, 255, 0);
            this.green.setStrokeWidth(10);
        }

        public void calculate() {
            if (objects == null) {
                this.results = null;
                return;
            }
            List<HitObject> hitObjects = objects.getHitObjects();
            if (hitObjects.isEmpty()) {
                this.results = null;
                return;
            }
            this.results = new double[samples];
            long start = hitObjects.get(0).getMsStart();
            long end = hitObjects.get(hitObjects.size() - 1).getMsStart();
            double interval = (end - start) / samples;
            int counter = 0;
            for (int i = 0; i < samples; i++) {
                long istart = start + (long) (i * interval);
                long iend = istart + (long) interval;
                int count = 0;
                double sum = 0;
                HitObject hitObject;
                while (counter < hitObjects.size() && (hitObject = hitObjects.get(counter++)).getMsStart() < iend) {
                    sum += toPct(hitObject.computeScore(ScoreCalculator.calculateDistribution(objects.getDifficulty())));
                    count++;
                }
                results[i] = Math.max(0, Math.max(1, count / sum));
            }
        }

        @Override
        public void draw(Canvas canvas) {
            super.draw(canvas);
            if (this.results == null)
                return;
            float x = getAbsolutePosition().getX();
            float y = getAbsolutePosition().getY();
            float w = size.getX();
            float h = size.getY();
            canvas.drawLine(x, y, x, y + h, axis);
            canvas.drawLine(x, y + h, x + h, y + h, axis);
            double dw = w / samples;
            for (int i = 0; i < samples - 1; i++) {
                double c = results[i];
                double n = results[i + 1];
                double ws = x + i * dw;
                double we = x + (i + 1) * dw;
                if (c <= MIN && n <= MIN) {
                    canvas.drawLine((float) ws, (float) (c * h), (float) we, (float) (n * h), red);
                } else if (c > MIN && n > MIN) {
                    canvas.drawLine((float) ws, (float) (c * h), (float) we, (float) (n * h), green);
                } else {
                    double l = Math.min(c, n);
                    double u = Math.min(c, n);
                    double dy = (MIN - l) / (u - l);
                    double cx = ws + dy * (we - ws);
                    canvas.drawLine((float) ws, (float) (c * h), (float) cx, (float) (MIN * h), c <= MIN ? red : green);
                    canvas.drawLine((float) cx, (float) (MIN * h), (float) we, (float) (n * h), n <= MIN ? red : green);
                }
            }
        }

        private double toPct(Scores score) {
            switch (score) {
                case S300:
                    return 1.0;
                case S150:
                    return 0.75;
                case S50:
                    return 0.5;
                default:
                    return 0.0;
            }
        }
    }
}
