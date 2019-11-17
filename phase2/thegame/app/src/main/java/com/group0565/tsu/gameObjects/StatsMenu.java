package com.group0565.tsu.gameObjects;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.RectF;

import com.group0565.engine.gameobjects.Button;
import com.group0565.engine.gameobjects.GameObject;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.Observer;
import com.group0565.hitObjectsRepository.SessionHitObjects;
import com.group0565.math.Vector;
import com.group0565.theme.Themes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StatsMenu extends GameObject implements Observable, Observer {
    private static final float BUTTON_SIZE = 75;
    private static final float MARGIN = 75;
    private static final float HISTORY_LEFT = 175;
    private static final float HISTORY_YMAR = 75;
    private static final float HISTORY_WIDTH = 650;
    private static final float HISTORY_BUTTON_HEIGHT = 75;
    private static final float HISTORY_BUFFER = 50;
    private static final float HISTORY_TOP = HISTORY_YMAR + HISTORY_BUTTON_HEIGHT + HISTORY_BUFFER;
    private static final float HISTORY_BOT = -(HISTORY_YMAR + HISTORY_BUTTON_HEIGHT + HISTORY_BUFFER);
    private static final float HISTORY_COUNT = 4;
    private static final float LOADING_SIZE = 200;
    private Button back;
    private boolean exit;
    private SessionHitObjects selectedObject = null;
    private List<SessionHitObjects> history = null;
    private Bitmap loading;
    private TsuGame tsuGame;
    private Paint historyPaint;
    private Bitmap scrollUpEnb, scrollUpDsb;
    private Bitmap scrollDownEnb, scrollDownDsb;
    private Button scrollUp, scrollDown;
    private int scroll = 0;
    private List<HistoryDisplayer> historyDisplayers;
    private Button sortTime, sortScore;
    private Sort sort = Sort.TimeDown;
    private FullHistoryDisplayer fullHistoryDisplayer;

    public StatsMenu(TsuGame tsuGame) {
        this.tsuGame = tsuGame;
    }

    @Override
    public void init() {
        this.historyPaint = new Paint();
        this.historyPaint.setARGB(255, 128, 128, 128);

        loading = getEngine().getGameAssetManager().getTileSheet("Tsu", "Buttons").getTile(14, 0);

        scrollUpEnb = getEngine().getGameAssetManager().getTileSheet("Tsu", "Scroll").getTile(0, 0);
        scrollUpDsb = getEngine().getGameAssetManager().getTileSheet("Tsu", "Scroll").getTile(1, 0);
        scrollDownEnb = getEngine().getGameAssetManager().getTileSheet("Tsu", "Scroll").getTile(0, 1);
        scrollDownDsb = getEngine().getGameAssetManager().getTileSheet("Tsu", "Scroll").getTile(1, 1);

        scrollUp = new Button(new Vector(HISTORY_LEFT, HISTORY_YMAR), new Vector(HISTORY_WIDTH, HISTORY_BUTTON_HEIGHT), scrollUpDsb, scrollUpDsb);
        scrollDown = new Button(new Vector(HISTORY_LEFT, getEngine().getSize().getY() - HISTORY_YMAR - HISTORY_BUTTON_HEIGHT), new Vector(HISTORY_WIDTH, HISTORY_BUTTON_HEIGHT), scrollDownDsb, scrollDownDsb);

        scrollUp.registerObserver(this);
        scrollDown.registerObserver(this);
        this.adopt(scrollUp);
        this.adopt(scrollDown);

        Bitmap backBitmap = getEngine().getGameAssetManager().getTileSheet("Tsu", "Buttons").getTile(8, 0);
        this.back = new Button(this.getAbsolutePosition().add(new Vector(MARGIN, MARGIN)),
                new Vector(BUTTON_SIZE, BUTTON_SIZE), backBitmap, backBitmap);
        back.registerObserver(this);
        adopt(back);

        float hh = (getEngine().getSize().getY()) - HISTORY_TOP + HISTORY_BOT;
        historyDisplayers = new ArrayList<>();
        for (int i = 0; i < HISTORY_COUNT; i++) {
            HistoryDisplayer displayer = new HistoryDisplayer(this, new Vector(HISTORY_LEFT, HISTORY_TOP + i * (hh / 4)),
                    new Vector(HISTORY_WIDTH, hh / 4), null);
            displayer.registerObserver(this);
            adopt(displayer);
            displayer.setEnable(false);
            historyDisplayers.add(displayer);
        }

        Bitmap time = getEngine().getGameAssetManager().getTileSheet("Tsu", "Buttons").getTile(15, 0);
        Bitmap timeUp = getEngine().getGameAssetManager().getTileSheet("Tsu", "Buttons").getTile(16, 0);
        Bitmap timeDown = getEngine().getGameAssetManager().getTileSheet("Tsu", "Buttons").getTile(17, 0);
        Bitmap score = getEngine().getGameAssetManager().getTileSheet("Tsu", "Buttons").getTile(18, 0);
        Bitmap scoreUp = getEngine().getGameAssetManager().getTileSheet("Tsu", "Buttons").getTile(19, 0);
        Bitmap scoreDown = getEngine().getGameAssetManager().getTileSheet("Tsu", "Buttons").getTile(20, 0);

        Sort.TimeDown.setTimeBitmap(timeDown);
        Sort.TimeDown.setScoreBitmap(score);

        Sort.TimeUp.setTimeBitmap(timeUp);
        Sort.TimeUp.setScoreBitmap(score);

        Sort.ScoreDown.setTimeBitmap(time);
        Sort.ScoreDown.setScoreBitmap(scoreDown);

        Sort.ScoreUp.setTimeBitmap(time);
        Sort.ScoreUp.setScoreBitmap(scoreUp);

        sortTime = new Button(this.getAbsolutePosition().add(new Vector(MARGIN, getEngine().getSize().getY() / 2 - BUTTON_SIZE - BUTTON_SIZE / 2)),
                new Vector(BUTTON_SIZE, BUTTON_SIZE));
        sortScore = new Button(this.getAbsolutePosition().add(new Vector(MARGIN, getEngine().getSize().getY() / 2 + BUTTON_SIZE / 2)),
                new Vector(BUTTON_SIZE, BUTTON_SIZE));

        sortTime.registerObserver(this);
        sortScore.registerObserver(this);

        adopt(sortTime);
        adopt(sortScore);

        setSort(Sort.TimeDown);

        fullHistoryDisplayer = new FullHistoryDisplayer(this, new Vector(HISTORY_LEFT + HISTORY_WIDTH + HISTORY_BUFFER,
                HISTORY_YMAR), new Vector(getEngine().getSize().getX() - (HISTORY_LEFT + HISTORY_WIDTH + HISTORY_BUFFER) - HISTORY_YMAR, getEngine().getSize().getY() - 2 * HISTORY_YMAR));
        this.adopt(fullHistoryDisplayer);
        super.init();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (getGlobalPreferences().theme == Themes.LIGHT)
            canvas.drawRGB(255, 255, 255);
        else if (getGlobalPreferences().theme == Themes.DARK)
            canvas.drawRGB(0, 0, 0);
        canvas.drawRect(HISTORY_LEFT, HISTORY_TOP, HISTORY_LEFT + HISTORY_WIDTH, canvas.getHeight() + HISTORY_BOT, historyPaint);
        if (history == null) {
            canvas.drawBitmap(loading, null, new RectF(HISTORY_LEFT + (HISTORY_WIDTH - LOADING_SIZE) / 2f, (canvas.getHeight() - LOADING_SIZE) / 2f,
                    HISTORY_LEFT + (HISTORY_WIDTH + LOADING_SIZE) / 2f, (canvas.getHeight() + LOADING_SIZE) / 2f), null);
        }
    }

    @Override
    public void observe(Observable observable) {
        if (observable == back) {
            if (back.isPressed()) {
                exit = true;
                this.notifyObservers();
            }
        } else if (observable == scrollUp) {
            if (scrollUp.isPressed()) {
                if (history != null && scroll > 0) {
                    scroll--;
                    updateDisplayers();
                }
            }
        } else if (observable == scrollDown) {
            if (scrollDown.isPressed()) {
                if (history != null && scroll + HISTORY_COUNT < history.size()) {
                    scroll++;
                    updateDisplayers();
                }
            }
        } else if (observable == sortTime) {
            if (sortTime.isPressed()) {
                if (sort == Sort.TimeDown || sort == Sort.TimeUp)
                    setSort(sort.next);
                else
                    setSort(Sort.TimeDown);
            }
        } else if (observable == sortScore) {
            if (sortScore.isPressed()) {
                if (sort == Sort.ScoreDown || sort == Sort.ScoreUp)
                    setSort(sort.next);
                else
                    setSort(Sort.ScoreDown);
            }
        } else if (observable instanceof HistoryDisplayer) {
            HistoryDisplayer displayer = (HistoryDisplayer) observable;
            if (displayer.isPressed()) {
                setSelectedObject(displayer.getObjects());
            }
        }
    }

    private void updateDisplayers() {
        if (this.history != null && historyDisplayers != null) {
            for (int i = 0; i < historyDisplayers.size(); i++) {
                historyDisplayers.get(i).setEnable(true);
                if (scroll + i < history.size())
                    historyDisplayers.get(i).setObjects(history.get(scroll + i));
                else
                    historyDisplayers.get(i).setObjects(null);
            }
            scrollUp.setUp(scroll <= 0 ? scrollUpDsb : scrollUpEnb);
            scrollUp.setDown(scroll <= 0 ? scrollUpDsb : scrollUpEnb);

            scrollDown.setUp(scroll + HISTORY_COUNT >= history.size() ? scrollDownDsb : scrollDownEnb);
            scrollDown.setDown(scroll + HISTORY_COUNT >= history.size() ? scrollDownDsb : scrollDownEnb);
        }
    }

    public boolean isExit() {
        return exit;
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }

    public SessionHitObjects getSelectedObject() {
        return selectedObject;
    }

    public void setSelectedObject(SessionHitObjects selectedObject) {
        this.selectedObject = selectedObject;
        this.notifyObservers();
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
        if (this.sortTime != null) {
            this.sortTime.setUp(sort.getTimeBitmap());
            this.sortTime.setDown(sort.getTimeBitmap());
        }
        if (this.sortScore != null) {
            this.sortScore.setUp(sort.getScoreBitmap());
            this.sortScore.setDown(sort.getScoreBitmap());
        }
        this.updateDisplayers();
    }

    private enum Sort {
        ScoreUp((SessionHitObjects o1, SessionHitObjects o2) -> (o1.getScore() - o2.getScore())),
        ScoreDown((SessionHitObjects o1, SessionHitObjects o2) -> (o2.getScore() - o1.getScore())),
        TimeUp((SessionHitObjects o1, SessionHitObjects o2) -> (o1.getDatetime().compareTo(o2.getDatetime()))),
        TimeDown((SessionHitObjects o1, SessionHitObjects o2) -> (o2.getDatetime().compareTo(o1.getDatetime())));

        static {
            ScoreUp.next = ScoreDown;
            ScoreDown.next = ScoreUp;
            TimeUp.next = TimeDown;
            TimeDown.next = TimeUp;
        }

        private Comparator<SessionHitObjects> comparator;
        private Bitmap timeBitmap, scoreBitmap;
        private Sort next;

        Sort(Comparator<SessionHitObjects> comparator) {
            this.comparator = comparator;
        }

        public Bitmap getTimeBitmap() {
            return timeBitmap;
        }

        public void setTimeBitmap(Bitmap timeBitmap) {
            this.timeBitmap = timeBitmap;
        }

        public Bitmap getScoreBitmap() {
            return scoreBitmap;
        }

        public void setScoreBitmap(Bitmap scoreBitmap) {
            this.scoreBitmap = scoreBitmap;
        }

        public Comparator<SessionHitObjects> getComparator() {
            return comparator;
        }
    }
}
