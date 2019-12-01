package com.group0565.tsu.menus;

import com.group0565.engine.gameobjects.Button;
import com.group0565.engine.gameobjects.GameMenu;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.ObservationEvent;
import com.group0565.engine.interfaces.Observer;
import com.group0565.hitObjectsRepository.SessionHitObjects;
import com.group0565.math.Vector;
import com.group0565.theme.Themes;
import com.group0565.tsu.enums.ButtonBitmap;
import com.group0565.tsu.game.HitObject;

import static com.group0565.engine.enums.HorizontalEdge.*;
import static com.group0565.engine.enums.VerticalEdge.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StatsMenu extends GameMenu implements Observable {
    //Event Constants
    public static final String HISTORY_UPDATE_EVENT = "History Updated";

    private static final Vector BUTTON_SIZE = new Vector(75);
    private static final Vector MARGIN = new Vector(75);
    private static final float HISTORY_LEFT_MARGIN = 25;

    private static final float HISTORY_WIDTH = 650;

    private static final String SET = "Tsu";
    //Back Button Constants
    private static final String BackButtonName = "BackButton";

    //History List Constants
    private static final String HistoryListName = "HistoryList";
    //Time Button Constants
    private static final String TimeButtonName = "TimeButton";
    private static final String TimeUpButtonName = "TimeUpButton";
    private static final String TimeDownButtonName = "TimeDownButton";
    //Score Button Constants
    private static final String ScoreButtonName = "ScoreButton";
    private static final String ScoreUpButtonName = "ScoreUpButton";
    private static final String ScoreDownButtonName = "ScoreDownButton";
    //Full History Displayer Constants
    private static final String FullHistoryDisplayerName = "FullHistoryDisplayer";

    private List<SessionHitObjects> history = null;
    private Sort sort = Sort.TimeDown;

    @Override
    public void init() {
        super.init();
        ButtonBitmap.init(getEngine().getGameAssetManager());
        HistoryList historyList;
        // @formatter:off
        this.build()
            .add(BackButtonName, new Button(BUTTON_SIZE, ButtonBitmap.BackButton.getBitmap()).build()
                .addOffset(MARGIN)
                .close())
            .addAlignment(Left, THIS, Left)
            .addAlignment(Top, THIS, Top)

            .add(HistoryListName, (historyList = new HistoryList(this, new Vector(HISTORY_WIDTH, 0), this::getHistory)).build()
                .close())
            .addAlignment(Left, BackButtonName, Right, HISTORY_LEFT_MARGIN)
            .addAlignment(Top, THIS, Top, MARGIN.getY())
            .addAlignment(Bottom, THIS, Bottom, -MARGIN.getY())

            .add(TimeButtonName, new Button(BUTTON_SIZE, ButtonBitmap.TimeButton.getBitmap(), Sort.TimeDown).build()
                .setSelfEnable(() -> (sort == Sort.ScoreUp || sort == Sort.ScoreDown))
                .registerObserver(this::observeSort)
                .close())
            .addAlignment(HCenter, BackButtonName, HCenter)
            .addAlignment(Bottom, THIS, VCenter, -BUTTON_SIZE.getY()/2)

            .add(TimeUpButtonName, new Button(BUTTON_SIZE, ButtonBitmap.TimeUpButton.getBitmap(), Sort.TimeDown).build()
                .setSelfEnable(() -> sort == Sort.TimeUp)
                .registerObserver(this::observeSort)
                .close())
            .addCenteredAlignment(TimeButtonName)

            .add(TimeDownButtonName, new Button(BUTTON_SIZE, ButtonBitmap.TimeDownButton.getBitmap(), Sort.TimeUp).build()
                .setSelfEnable(() -> sort == Sort.TimeDown)
                .registerObserver(this::observeSort)
                .close())
            .addCenteredAlignment(TimeButtonName)

            .add(ScoreButtonName, new Button(BUTTON_SIZE, ButtonBitmap.ScoreButton.getBitmap(), Sort.ScoreDown).build()
                .addOffset(0, BUTTON_SIZE.getY())
                .setSelfEnable(() -> (sort == Sort.TimeUp || sort == Sort.TimeDown))
                .registerObserver(this::observeSort)
                .close())
            .addAlignment(HCenter, TimeButtonName, HCenter)
            .addAlignment(Bottom, THIS, VCenter, BUTTON_SIZE.getY()/2)

            .add(ScoreUpButtonName, new Button(BUTTON_SIZE, ButtonBitmap.ScoreUpButton.getBitmap(), Sort.ScoreDown).build()
                .setSelfEnable(() -> sort == Sort.ScoreUp)
                .registerObserver(this::observeSort)
                .close())
            .addCenteredAlignment(ScoreButtonName)

            .add(ScoreDownButtonName, new Button(BUTTON_SIZE, ButtonBitmap.ScoreDownButton.getBitmap(), Sort.ScoreUp).build()
                .setSelfEnable(() -> sort == Sort.ScoreDown)
                .registerObserver(this::observeSort)
                .close())
            .addCenteredAlignment(ScoreButtonName)

            .add(FullHistoryDisplayerName, new FullHistoryDisplayer(this, new Vector(), new Vector()).build()
                .addOffset(MARGIN.getX(), 0)
                .close())
            .addAlignment(Left, HistoryListName, Right, MARGIN.getX())
            .addAlignment(Right, THIS, Right, -MARGIN.getX())
            .addAlignment(Top, THIS, Top, MARGIN.getY())
            .addAlignment(Bottom, THIS, Bottom, -MARGIN.getY())
        .close();
        // @formatter:on
    }

    @Override
    public void postInit() {
        super.postInit();
        List<SessionHitObjects> hitObjects = new ArrayList<>();
        SessionHitObjects objects = new SessionHitObjects();
        objects.addToList(new HitObject());
        objects.addToList(new HitObject());
        objects.setMaxCombo(100);
        objects.setCheats(true);
        objects.setScore(1000);
        objects.setGrade(4);
        objects.setDifficulty(5);
        objects.setDatetime("ABCD");
        hitObjects.add(objects);
        hitObjects.add(objects);
        hitObjects.add(objects);
        hitObjects.add(objects);
        hitObjects.add(objects);
        hitObjects.add(objects);
        this.setHistory(hitObjects);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (getGlobalPreferences().getTheme() == Themes.LIGHT)
            canvas.drawRGB(255, 255, 255);
        else if (getGlobalPreferences().getTheme() == Themes.DARK)
            canvas.drawRGB(0, 0, 0);
    }

    private void observeSort(Observable observable, ObservationEvent<Sort> event){
        if (event.isEvent(Button.EVENT_DOWN)){
            this.setSort(event.getPayload());
        }
    }

    public List<SessionHitObjects> getHistory() {
        return history;
    }

    public void setHistory(List<SessionHitObjects> history) {
        this.history = history;
        setSort(sort);
    }

    public void setSort(Sort sort) {
        this.sort = sort;
        if (history != null)
            Collections.sort(history, sort.comparator);
        this.notifyObservers(new ObservationEvent<>(HISTORY_UPDATE_EVENT, history));
    }

    private enum Sort {
        ScoreUp((SessionHitObjects o1, SessionHitObjects o2) -> (o1.getScore() - o2.getScore())),
        ScoreDown((SessionHitObjects o1, SessionHitObjects o2) -> (o2.getScore() - o1.getScore())),
        TimeUp((SessionHitObjects o1, SessionHitObjects o2) -> (o1.getDatetime().compareTo(o2.getDatetime()))),
        TimeDown((SessionHitObjects o1, SessionHitObjects o2) -> (o2.getDatetime().compareTo(o1.getDatetime())));

        private Comparator<SessionHitObjects> comparator;

        Sort(Comparator<SessionHitObjects> comparator) {
            this.comparator = comparator;
        }
    }
}