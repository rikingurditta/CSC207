package com.group0565.tsu.menus;

import com.group0565.engine.android.AndroidPaint;
import com.group0565.engine.gameobjects.Button;
import com.group0565.engine.gameobjects.GameMenu;
import com.group0565.engine.gameobjects.GameObject;
import com.group0565.engine.gameobjects.MenuObject;
import com.group0565.engine.interfaces.Bitmap;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.ObservationEvent;
import com.group0565.engine.interfaces.Observer;
import com.group0565.engine.interfaces.Paint;
import com.group0565.engine.interfaces.Source;
import com.group0565.engine.render.BitmapDrawer;
import com.group0565.engine.render.LanguageText;
import com.group0565.engine.render.TextRenderer;
import com.group0565.engine.render.ThemedPaintCan;
import com.group0565.hitObjectsRepository.SessionHitObjects;
import com.group0565.math.Vector;
import com.group0565.tsu.enums.ButtonBitmap;
import com.group0565.tsu.enums.Grade;
import com.group0565.tsu.enums.Scores;
import com.group0565.tsu.render.GradeRenderer;
import com.group0565.tsu.render.HitScoreRenderer;
import com.group0565.tsu.render.NumberRenderer;

import static com.group0565.engine.enums.HorizontalEdge.*;
import static com.group0565.engine.enums.VerticalEdge.*;

public class FullHistoryDisplayer extends GameMenu {
    //Event Constants
    public static final String TO_REPLAY = "To Replay";

    private static final int SAMPLES = 50;

    //Asset Constants
    private static final String SET = "Tsu";
    private static final String PaintFolder = "FullHistoryDisplayer.";
    private static final String RimPaintName = PaintFolder + "Rim";
    private static final String CenterPaintName = PaintFolder + "Center";
    private static final String CountPaintName = PaintFolder + "Count";
    private static final String ScorePaintName = PaintFolder + "Score";
    private static final String ComboPaintName = PaintFolder + "Combo";
    private static final String DifficultyPaintName = PaintFolder + "Difficulty";
    private static final String DatePaintName = PaintFolder + "Date";

    //General Constants
    private static final Vector MARGINS = new Vector(25);
    private static final Vector INTERNAL_MARGINS = new Vector(25);
    //Grade Constants
    private static final String GradeName = "Grade";
    private static final Vector GradeSize = new Vector(200);
    //Background Constants
    private static final String BackgroundName = "Background";
    private static final Vector RIM_SIZE = new Vector(10);
    private static final Vector RIM_RADIUS = new Vector(15);
    //S Constants
    private static final float SHeight = 100;
    private static final float NumberWidth = 50;
    //S300 Constants
    private static final String S300Name = "S300";
    private static final String S300CountName = "S300Count";
    //S150 Constants
    private static final String S150Name = "S150";
    private static final String S150CountName = "S150Count";
    //S50 Constants
    private static final String S50Name = "S50";
    private static final String S50CountName = "S50Count";
    //S0 Constants
    private static final String S0Name = "S0";
    private static final String S0CountName = "S0Count";
    //Score Constants
    private static final String ScoreName = "Score";
    //Combo Constants
    private static final String ComboName = "Combo";
    //Difficulty Constants
    private static final String DifficultyName = "Difficulty";
    private static final String DifficultyToken = "Difficulty";
    //Date Constants
    private static final String DateName = "Date";
    //Cheats Constants
    private static final String CheatName = "Cheat";
    private static final Vector CHEAT_SIZE = new Vector(75);
    //Replay Constants
    private static final String ReplayName = "Replay";
    private static final Vector REPLAY_SIZE = new Vector(75);

    private ThemedPaintCan rim;
    private ThemedPaintCan center;

    private Source<SessionHitObjects> objectsSource;

    private GraphRenderer graphRenderer;


    public FullHistoryDisplayer(Source<SessionHitObjects> objectsSource, Vector size) {
        super(size);
        this.objectsSource = objectsSource;
    }

    @Override
    public void init() {
        super.init();
        Grade.init(getGlobalPreferences(), getEngine().getGameAssetManager());
        ButtonBitmap.init(getEngine().getGameAssetManager());
        rim = new ThemedPaintCan(SET, RimPaintName).init(getGlobalPreferences(), getEngine().getGameAssetManager());
        center = new ThemedPaintCan(SET, CenterPaintName).init(getGlobalPreferences(), getEngine().getGameAssetManager());
        ThemedPaintCan countPaint = new ThemedPaintCan(SET, CountPaintName).init(getGlobalPreferences(), getEngine().getGameAssetManager());
        ThemedPaintCan scorePaint = new ThemedPaintCan(SET, ScorePaintName).init(getGlobalPreferences(), getEngine().getGameAssetManager());
        ThemedPaintCan comboPaint = new ThemedPaintCan(SET, ComboPaintName).init(getGlobalPreferences(), getEngine().getGameAssetManager());
        ThemedPaintCan difficultyPaint = new ThemedPaintCan(SET, DifficultyPaintName).init(getGlobalPreferences(), getEngine().getGameAssetManager());
        ThemedPaintCan datePaint = new ThemedPaintCan(SET, DatePaintName).init(getGlobalPreferences(), getEngine().getGameAssetManager());
        LanguageText difficultyLabel = new LanguageText(getGlobalPreferences(), getEngine().getGameAssetManager(), SET, DifficultyToken);
        // @formatter:off
        this.build()
            .add(BackgroundName, new MenuObject(){
                { setZ(-1); }
                @Override
                public void draw(Canvas canvas, Vector pos, Vector size) {
                    super.draw(canvas, pos, size);
                    canvas.drawRoundRect(pos, size, RIM_RADIUS, rim);
                    canvas.drawRoundRect(pos.add(RIM_SIZE), size.subtract(RIM_SIZE.multiply(2)), RIM_RADIUS, center);
                }
            })
            .addAlignment(Left, THIS, Left)
            .addAlignment(Right, THIS, Right)
            .addAlignment(Top, THIS, Top)
            .addAlignment(Bottom, THIS, Bottom)

            .add(GradeName, new GradeRenderer(GradeSize, () -> (objectsSource.getValue() == null ? null : Grade.num2Grade(objectsSource.getValue().getGrade()))))
            .addAlignment(Left, THIS, Left, MARGINS.getX())
            .addAlignment(Top, THIS, Top, MARGINS.getY())

            .add(S300Name, new HitScoreRenderer(SHeight, () -> Scores.S300).build()
                .setSelfEnable(() -> objectsSource.getValue() != null)
                .close())
            .addAlignment(Left, THIS, Left, MARGINS.getX())
            .addAlignment(Top, GradeName, Bottom, INTERNAL_MARGINS.getY())

            .add(S300CountName, new TextRenderer(() -> (objectsSource.getValue() == null ? "0" : String.valueOf(objectsSource.getValue().getS300())), countPaint).build()
                .setSelfEnable(() -> objectsSource.getValue() != null)
                .close())
            .addAlignment(Left, S300Name, Right, INTERNAL_MARGINS.getX())
            .addAlignment(VCenter, S300Name, VCenter, 15)
                
            .add(S150Name, new HitScoreRenderer(SHeight, () -> Scores.S150).build()
                .setSelfEnable(() -> objectsSource.getValue() != null)
                .close())
            .addAlignment(Left, THIS, Left, MARGINS.getX())
            .addAlignment(Top, S300Name, Bottom, INTERNAL_MARGINS.getY())

            .add(S150CountName, new TextRenderer(() -> (objectsSource.getValue() == null ? "0" : String.valueOf(objectsSource.getValue().getS150())), countPaint).build()
                .setSelfEnable(() -> objectsSource.getValue() != null)
                .close())
            .addAlignment(Left, S150Name, Right, INTERNAL_MARGINS.getX())
            .addAlignment(VCenter, S150Name, VCenter, 15)
                
            .add(S50Name, new HitScoreRenderer(SHeight, () -> Scores.S50).build()
                .setSelfEnable(() -> objectsSource.getValue() != null)
                .close())
            .addAlignment(Left, THIS, Left, MARGINS.getX())
            .addAlignment(Top, S150Name, Bottom, INTERNAL_MARGINS.getY())

            .add(S50CountName, new TextRenderer(() -> (objectsSource.getValue() == null ? "0" : String.valueOf(objectsSource.getValue().getS50())), countPaint).build()
                .setSelfEnable(() -> objectsSource.getValue() != null)
                .close())
            .addAlignment(Left, S50Name, Right, INTERNAL_MARGINS.getX())
            .addAlignment(VCenter, S50Name, VCenter, 15)
                
            .add(S0Name, new HitScoreRenderer(SHeight, () -> Scores.S0).build()
                .setSelfEnable(() -> objectsSource.getValue() != null)
                .close())
            .addAlignment(Left, THIS, Left, MARGINS.getX())
            .addAlignment(Top, S50Name, Bottom, INTERNAL_MARGINS.getY())

            .add(S0CountName, new TextRenderer(() -> (objectsSource.getValue() == null ? "0" : String.valueOf(objectsSource.getValue().getS0())), countPaint).build()
                .setSelfEnable(() -> objectsSource.getValue() != null)
                .close())
            .addAlignment(Left, S0Name, Right, INTERNAL_MARGINS.getX())
            .addAlignment(VCenter, S0Name, VCenter, 15)

            .add(ScoreName, new TextRenderer(() -> (objectsSource.getValue() == null ? "0" : String.valueOf(objectsSource.getValue().getScore())), scorePaint).build()
                .setSelfEnable(() -> objectsSource.getValue() != null)
                .close())
            .addAlignment(Right, THIS, Right, -MARGINS.getX())
            .addAlignment(Top, THIS, Top, MARGINS.getY())

            .add(ComboName, new TextRenderer(() -> (objectsSource.getValue() == null ? "0" : String.valueOf(objectsSource.getValue().getMaxCombo())), comboPaint).build()
                .setSelfEnable(() -> objectsSource.getValue() != null)
                .close())
            .addAlignment(Right, THIS, Right, -MARGINS.getX())
            .addAlignment(Top, ScoreName, Bottom, INTERNAL_MARGINS.getY())

            .add(DifficultyName, new TextRenderer(() -> difficultyLabel.getValue() + ": "
                    + (objectsSource.getValue() == null ? "" : String.valueOf(objectsSource.getValue().getDifficulty())), difficultyPaint).build()
                .setSelfEnable(() -> objectsSource.getValue() != null)
                .close())
            .addAlignment(Right, THIS, Right, -MARGINS.getX())
            .addAlignment(Top, ComboName, Bottom, INTERNAL_MARGINS.getY())

            .add(DateName, new TextRenderer(() -> (objectsSource.getValue() == null ? "0" : objectsSource.getValue().getDatetime()), datePaint).build()
                .setSelfEnable(() -> objectsSource.getValue() != null)
                .close())
            .addAlignment(Left, THIS, Left, MARGINS.getX())
            .addAlignment(Top, S0Name, Bottom, INTERNAL_MARGINS.getY())

            .add(CheatName, new BitmapDrawer(CHEAT_SIZE, ButtonBitmap.CheatIcon::getBitmap).build()
                .setSelfEnable(() -> (objectsSource.getValue() != null && objectsSource.getValue().hasCheats()))
                .close())
            .addAlignment(Left, GradeName, Right, INTERNAL_MARGINS.getX())
            .addAlignment(Bottom, GradeName, Bottom)

            .add(ReplayName, new Button(REPLAY_SIZE, ButtonBitmap.ReplayButton.getBitmap()).build()
                .setSelfEnable(() -> (true || objectsSource.getValue() != null))
                .registerObserver(this::observeReplay)
                .close())
            .addAlignment(Right, THIS, Right, -MARGINS.getX())
            .addAlignment(Bottom, THIS, Bottom, -MARGINS.getY())
        .close();
        // @formatter:on
    }

    private void observeReplay(Observable observable, ObservationEvent observationEvent){
        if (observationEvent.isEvent(Button.EVENT_DOWN)){
            this.notifyObservers(new ObservationEvent<>(TO_REPLAY, objectsSource.getValue()));
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
            this.axis = new AndroidPaint();
            this.axis.setARGB(255, 128, 128, 128);
            this.axis.setStrokeWidth(10);
            this.red = new AndroidPaint();
            this.red.setARGB(255, 255, 0, 0);
            this.red.setStrokeWidth(10);
            this.green = new AndroidPaint();
            this.green.setARGB(255, 0, 255, 0);
            this.green.setStrokeWidth(10);
        }

        public void calculate() {
//            if (objects == null) {
//                this.results = null;
//                return;
//            }
//            List<HitObject> hitObjects = objects.getHitObjects();
//            if (hitObjects.isEmpty()) {
//                this.results = null;
//                return;
//            }
//            this.results = new double[samples];
//            long start = hitObjects.get(0).getMsStart();
//            long end = hitObjects.get(hitObjects.size() - 1).getMsStart();
//            double interval = (end - start) / samples;
//            int counter = 0;
//            for (int i = 0; i < samples; i++) {
//                long istart = start + (long) (i * interval);
//                long iend = istart + (long) interval;
//                int count = 0;
//                double sum = 0;
//                HitObject hitObject;
//                while (counter < hitObjects.size() && (hitObject = hitObjects.get(counter++)).getMsStart() < iend) {
//                    sum += toPct(hitObject.computeScore(ScoreCalculator.calculateDistribution(objects.getDifficulty())));
//                    count++;
//                }
//                results[i] = Math.max(0, Math.max(1, count / sum));
//            }
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
