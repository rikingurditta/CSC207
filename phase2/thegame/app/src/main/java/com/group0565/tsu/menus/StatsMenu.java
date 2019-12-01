package com.group0565.tsu.menus;

import com.group0565.engine.gameobjects.Button;
import com.group0565.engine.gameobjects.GameMenu;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.ObservationEvent;
import com.group0565.engine.render.ScrollingTextRenderer;
import com.group0565.engine.render.ThemedPaintCan;
import com.group0565.hitObjectsRepository.SessionHitObjects;
import com.group0565.math.Vector;
import com.group0565.theme.Themes;
import com.group0565.tsu.enums.ButtonBitmap;
import com.group0565.tsu.game.Beatmap;
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
    public static final String EXIT_EVENT = "Stats Exitted";
    public static final String TO_REPLAY = FullHistoryDisplayer.TO_REPLAY;

    private static final Vector BUTTON_SIZE = new Vector(75);
    private static final Vector MARGIN = new Vector(75);
    private static final Vector INTERNAL_MARGIN = new Vector(25);
    private static final float HISTORY_LEFT_MARGIN = 25;

    private static final float HISTORY_WIDTH = 650;

    private static final String SET = "Tsu";
    private static final String THEME_FOLDER = "StatsMenu.";
    private static final String BackgroundPaintName = THEME_FOLDER + "Background";

    //Back Button Constants
    private static final String BackButtonName = "BackButton";
    //BeatMap Name Constants
    private static final String BeatmapName = "Beatmap";
    private static final String BeatmapPaintName = THEME_FOLDER + "BeatMap";
    private static final long ScrollTime = 1500;
    private static final long HoldTime = 500;
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

    private String selectedBeatmapName;

    private HistoryList historyList;
    private List<SessionHitObjects> history = null;
    private List<SessionHitObjects> filteredHistory = null;
    private Sort sort = Sort.TimeDown;

    private ThemedPaintCan beatmapPaint;
    private ThemedPaintCan bgPaintCan;

    @Override
    public void init() {
        super.init();
        ButtonBitmap.init(getEngine().getGameAssetManager());
        beatmapPaint = new ThemedPaintCan(SET, BeatmapPaintName).init(getGlobalPreferences(), getEngine().getGameAssetManager());
        bgPaintCan = new ThemedPaintCan(SET, BackgroundPaintName).init(getGlobalPreferences(), getEngine().getGameAssetManager());

        // @formatter:off
        this.build()
            .add(BackButtonName, new Button(BUTTON_SIZE, ButtonBitmap.BackButton.getBitmap()).build()
                .addOffset(MARGIN)
                .registerObserver(this::observeBack)
                .close())
            .addAlignment(Left, THIS, Left)
            .addAlignment(Top, THIS, Top)

            .add(BeatmapName, new ScrollingTextRenderer(() -> selectedBeatmapName == null ? "Beatmap not found" : selectedBeatmapName,
                    ScrollTime, HoldTime,
                    beatmapPaint, bgPaintCan, new Vector(1, 1)))
            .addAlignment(Left, BackButtonName, Right, INTERNAL_MARGIN.getX())
            .addAlignment(Right, THIS, Right, -MARGIN.getX())
            .addAlignment(Top, THIS, Top, INTERNAL_MARGIN.getY())

            .add(HistoryListName, (historyList = new HistoryList(this, new Vector(HISTORY_WIDTH, 0), this::getFilteredHistory)).build()
                .close())
            .addAlignment(Left, BackButtonName, Right, HISTORY_LEFT_MARGIN)
            .addAlignment(Top, BeatmapName, Bottom, INTERNAL_MARGIN.getY())
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

            .add(FullHistoryDisplayerName, new FullHistoryDisplayer(historyList::getSelectedObject, new Vector()).build()
                .registerObserver(this::observeReplay)
                .close())
            .addAlignment(Left, HistoryListName, Right, INTERNAL_MARGIN.getX())
            .addAlignment(Right, THIS, Right, -MARGIN.getX())
            .addAlignment(Top, BeatmapName, Bottom, INTERNAL_MARGIN.getY())
            .addAlignment(Bottom, THIS, Bottom, -MARGIN.getY())
        .close();
        // @formatter:on
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (getGlobalPreferences().getTheme() == Themes.LIGHT)
            canvas.drawRGB(255, 255, 255);
        else if (getGlobalPreferences().getTheme() == Themes.DARK)
            canvas.drawRGB(0, 0, 0);
    }

    private void observeBack(Observable observable, ObservationEvent<Sort> event){
        if (event.isEvent(Button.EVENT_DOWN)){
            this.setSelectedObject(null);
            this.notifyObservers(new ObservationEvent(EXIT_EVENT));
        }
    }

    private void observeSort(Observable observable, ObservationEvent<Sort> event){
        if (event.isEvent(Button.EVENT_DOWN)){
            this.setSort(event.getPayload());
        }
    }

    private void observeReplay(Observable observable, ObservationEvent<SessionHitObjects> event){
        if (event.isEvent(TO_REPLAY))
            this.notifyObservers(event);
    }

    public List<SessionHitObjects> getHistory() {
        return history;
    }

    public void setHistory(List<SessionHitObjects> history) {
        this.history = history;
        refilter();
        setSort(sort);
    }

    public void setSort(Sort sort) {
        this.sort = sort;
        if (history != null)
            Collections.sort(history, sort.comparator);
        this.notifyObservers(new ObservationEvent<>(HISTORY_UPDATE_EVENT, history));
    }

    public void setSelectedObject(SessionHitObjects object) {
        historyList.setSelectedObject(object);
        this.setSelectedBeatmap(object == null ? null : object.getBeatmapName());
        refilter();
    }

    /**
     * Getter for filteredHistory
     *
     * @return filteredHistory
     */
    public List<SessionHitObjects> getFilteredHistory() {
        return filteredHistory;
    }

    /**
     * Setter for selected Beatmap
     *
     * @param selectedBeatmap The new value for selectedBeatmap
     */
    public void setSelectedBeatmap(String selectedBeatmap) {
        this.selectedBeatmapName = selectedBeatmap;
        refilter();
    }

    /**
     * Setter for selected Beatmap
     *
     * @param selectedBeatmap The new value for selectedBeatmap
     */
    public void setSelectedBeatmap(Beatmap selectedBeatmap) {
        this.setSelectedBeatmap(selectedBeatmap.getName());
    }

    private void refilter() {
        if (this.history == null){
            this.filteredHistory = null;
            return;
        }
        if (this.filteredHistory == null)
                this.filteredHistory = new ArrayList<>(history.size());
        else
            this.filteredHistory.clear();
        for (SessionHitObjects sessionHitObjects : history){
            if (sessionHitObjects.getBeatmapName().equals(this.selectedBeatmapName))
                filteredHistory.add(sessionHitObjects);
        }
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