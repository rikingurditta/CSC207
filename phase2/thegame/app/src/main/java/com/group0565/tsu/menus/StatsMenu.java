package com.group0565.tsu.menus;

import com.group0565.engine.android.AndroidPaint;
import com.group0565.engine.enums.HorizontalAlignment;
import com.group0565.engine.enums.VerticalAlignment;
import com.group0565.engine.gameobjects.Button;
import com.group0565.engine.gameobjects.GameMenu;
import com.group0565.engine.gameobjects.MenuObject;
import com.group0565.engine.interfaces.Bitmap;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.Observer;
import com.group0565.engine.interfaces.Paint;
import com.group0565.hitObjectsRepository.SessionHitObjects;
import com.group0565.math.Vector;
import com.group0565.theme.Themes;
import com.group0565.tsu.core.TsuGame;
import com.group0565.tsu.enums.ButtonBitmap;
import com.group0565.tsu.enums.ScrollBitmap;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StatsMenu extends GameMenu implements Observable, Observer {
    private static final Vector BUTTON_SIZE = new Vector(75);
    private static final Vector MARGIN = new Vector(75);
    private static final float HISTORY_LEFT_MARGIN = 25;

    private static final float HISTORY_YMAR = 75;
    private static final float HISTORY_WIDTH = 650;
    private static final float HISTORY_BUTTON_HEIGHT = 75;
    private static final float HISTORY_COUNT = 4;
    private static final float LOADING_SIZE = 200;

    private static final float HISTORY_BOX_WIDTH = 650;
    private static final float HISTORY_BUFFER = 50;
    private static final Vector SCROLL_BUTTON_SIZE = new Vector(HISTORY_BOX_WIDTH, 75);

    private static final float TIME_BUTTON_YOFFSET = 200;

    private static final String SET = "Tsu";
    //Back Button Constants
    private static final String BackButtonName = "BackButton";
    //Scroll Up Constants
    private static final String ScrollUpName = "ScrollUp";
    //Scroll Up Constants
    private static final String ScrollDownName = "ScrollDown";
    //History List Constants
    private static final String HistoryListName = "HistoryList";
    //History Box Constants
    private static final String HistoryBoxName = "HistoryBox";
    //Time Button Constants
    private static final String TimeButtonName = "TimeButton";
    private static final String TimeUpButtonName = "TimeUpButton";
    private static final String TimeDownButtonName = "TimeDownButton";
    //Score Button Constants
    private static final String ScoreButtonName = "ScoreButton";
    private static final String ScoreUpButtonName = "ScoreUpButton";
    private static final String ScoreDownButtonName = "ScoreDownButton";
    //History Displayers Constants
    private static final String[] HistoryDisplayersName = {"HitoryDisplayer0", "HitoryDisplayer1", "HitoryDisplayer2", "HitoryDisplayer3"};
    //Full History Displayer Constants
    private static final String FullHistoryDisplayerName = "FullHistoryDisplayer";


    private Button back;
    private boolean exit;
    private SessionHitObjects selectedObject = null;
    private List<SessionHitObjects> history = null;
    private Bitmap loading;
    private TsuGame tsuGame;
    private Bitmap scrollUpEnb, scrollUpDsb;
    private Bitmap scrollDownEnb, scrollDownDsb;
    private Button scrollUp, scrollDown;
    private int scroll = 0;
    private List<HistoryDisplayer> historyDisplayers;
    private Button sortTime, sortScore;
    private Sort sort = Sort.TimeDown;
    private FullHistoryDisplayer fullHistoryDisplayer;

    public StatsMenu(TsuGame tsuGame) {
        super(null);
        this.tsuGame = tsuGame;
    }

    @Override
    public void init() {
        ButtonBitmap.init(getEngine().getGameAssetManager());
        // @formatter:off
        this.build()
            .add(BackButtonName, new Button(BUTTON_SIZE, ButtonBitmap.BackButton.getBitmap()).build()
                .addOffset(MARGIN)
                .close())
            .setRelativePosition(THIS, HorizontalAlignment.LeftAlign, VerticalAlignment.TopAlign)

            .add(HistoryListName, new HistoryList(new Vector(HISTORY_WIDTH,getSize().getY() - 2 * MARGIN.getY())).build()
                .addOffset(HISTORY_LEFT_MARGIN, 0)
                .close())
            .setRelativePosition(BackButtonName, HorizontalAlignment.RightOf, VerticalAlignment.TopAlign)

            .add(TimeButtonName, new Button(BUTTON_SIZE, ButtonBitmap.TimeButton.getBitmap()).build()
                .addOffset(0, TIME_BUTTON_YOFFSET)
                .setSelfEnable(() -> (sort == Sort.ScoreUp || sort == Sort.ScoreDown))
                .close())
            .setRelativePosition(BackButtonName, HorizontalAlignment.Center, VerticalAlignment.BottomOf)

            .add(TimeUpButtonName, new Button(BUTTON_SIZE, ButtonBitmap.TimeUpButton.getBitmap()).build()
                .setSelfEnable(() -> sort == Sort.TimeUp)
                .close())
            .setRelativePosition(TimeButtonName, HorizontalAlignment.Center, VerticalAlignment.Center)

            .add(TimeDownButtonName, new Button(BUTTON_SIZE, ButtonBitmap.TimeDownButton.getBitmap()).build()
                .setSelfEnable(() -> sort == Sort.TimeDown)
                .close())
            .setRelativePosition(TimeButtonName, HorizontalAlignment.Center, VerticalAlignment.Center)
                
            .add(ScoreButtonName, new Button(BUTTON_SIZE, ButtonBitmap.ScoreButton.getBitmap()).build()
                .addOffset(0, BUTTON_SIZE.getY())
                .setSelfEnable(() -> (sort == Sort.TimeUp || sort == Sort.TimeDown))
                .close())
            .setRelativePosition(TimeButtonName, HorizontalAlignment.Center, VerticalAlignment.BottomOf)

            .add(ScoreUpButtonName, new Button(BUTTON_SIZE, ButtonBitmap.ScoreUpButton.getBitmap()).build()
                .setSelfEnable(() -> sort == Sort.ScoreUp)
                .close())
            .setRelativePosition(ScoreButtonName, HorizontalAlignment.Center, VerticalAlignment.Center)

            .add(ScoreDownButtonName, new Button(BUTTON_SIZE, ButtonBitmap.ScoreDownButton.getBitmap()).build()
                .setSelfEnable(() -> sort == Sort.ScoreDown)
                .close())
            .setRelativePosition(ScoreButtonName, HorizontalAlignment.Center, VerticalAlignment.Center)

            .add(FullHistoryDisplayerName, new FullHistoryDisplayer(this, new Vector(),
                    getSize().subtract(new Vector(3*MARGIN.getX() + BUTTON_SIZE.getX() + HISTORY_LEFT_MARGIN + HISTORY_WIDTH, 2*MARGIN.getY()))).build()
                .addOffset(MARGIN.getX(), 0)
                .close())
            .setRelativePosition(HistoryListName, HorizontalAlignment.RightOf, VerticalAlignment.TopAlign)
        .close();
        // @formatter:on

//
//        loading = getEngine().getGameAssetManager().getTileSheet("Tsu", "Buttons").getTile(14, 0);
//
//        scrollUpEnb = getEngine().getGameAssetManager().getTileSheet("Tsu", "Scroll").getTile(0, 0);
//        scrollUpDsb = getEngine().getGameAssetManager().getTileSheet("Tsu", "Scroll").getTile(1, 0);
//        scrollDownEnb = getEngine().getGameAssetManager().getTileSheet("Tsu", "Scroll").getTile(0, 1);
//        scrollDownDsb = getEngine().getGameAssetManager().getTileSheet("Tsu", "Scroll").getTile(1, 1);
//
//        scrollUp = new Button(new Vector(HISTORY_LEFT, HISTORY_YMAR), new Vector(HISTORY_WIDTH, HISTORY_BUTTON_HEIGHT), scrollUpDsb, scrollUpDsb);
//        scrollDown = new Button(new Vector(HISTORY_LEFT, getEngine().getSize().getY() - HISTORY_YMAR - HISTORY_BUTTON_HEIGHT), new Vector(HISTORY_WIDTH, HISTORY_BUTTON_HEIGHT), scrollDownDsb, scrollDownDsb);
//
//        scrollUp.registerObserver(this);
//        scrollDown.registerObserver(this);
//        this.adopt(scrollUp);
//        this.adopt(scrollDown);
//
//        Bitmap backBitmap = getEngine().getGameAssetManager().getTileSheet("Tsu", "Buttons").getTile(8, 0);
//        this.back = new Button(this.getAbsolutePosition().add(new Vector(MARGIN, MARGIN)),
//                new Vector(BUTTON_SIZE, BUTTON_SIZE), backBitmap, backBitmap);
//        back.registerObserver(this);
//        adopt(back);
//
//        float hh = (getEngine().getSize().getY()) - HISTORY_TOP + HISTORY_BOT;
//        historyDisplayers = new ArrayList<>();
//        for (int i = 0; i < HISTORY_COUNT; i++) {
//            HistoryDisplayer displayer = new HistoryDisplayer(this, new Vector(HISTORY_LEFT, HISTORY_TOP + i * (hh / 4)),
//                    new Vector(HISTORY_WIDTH, hh / 4), null);
//            displayer.registerObserver(this);
//            adopt(displayer);
//            displayer.setEnable(false);
//            historyDisplayers.add(displayer);
//        }
//
//        Bitmap time = getEngine().getGameAssetManager().getTileSheet("Tsu", "Buttons").getTile(15, 0);
//        Bitmap timeUp = getEngine().getGameAssetManager().getTileSheet("Tsu", "Buttons").getTile(16, 0);
//        Bitmap timeDown = getEngine().getGameAssetManager().getTileSheet("Tsu", "Buttons").getTile(17, 0);
//        Bitmap score = getEngine().getGameAssetManager().getTileSheet("Tsu", "Buttons").getTile(18, 0);
//        Bitmap scoreUp = getEngine().getGameAssetManager().getTileSheet("Tsu", "Buttons").getTile(19, 0);
//        Bitmap scoreDown = getEngine().getGameAssetManager().getTileSheet("Tsu", "Buttons").getTile(20, 0);
//
//        Sort.TimeDown.setTimeBitmap(timeDown);
//        Sort.TimeDown.setScoreBitmap(score);
//
//        Sort.TimeUp.setTimeBitmap(timeUp);
//        Sort.TimeUp.setScoreBitmap(score);
//
//        Sort.ScoreDown.setTimeBitmap(time);
//        Sort.ScoreDown.setScoreBitmap(scoreDown);
//
//        Sort.ScoreUp.setTimeBitmap(time);
//        Sort.ScoreUp.setScoreBitmap(scoreUp);
//
//        sortTime = new Button(this.getAbsolutePosition().add(new Vector(MARGIN, getEngine().getSize().getY() / 2 - BUTTON_SIZE - BUTTON_SIZE / 2)),
//                new Vector(BUTTON_SIZE, BUTTON_SIZE));
//        sortScore = new Button(this.getAbsolutePosition().add(new Vector(MARGIN, getEngine().getSize().getY() / 2 + BUTTON_SIZE / 2)),
//                new Vector(BUTTON_SIZE, BUTTON_SIZE));
//
//        sortTime.registerObserver(this);
//        sortScore.registerObserver(this);
//
//        adopt(sortTime);
//        adopt(sortScore);
//
//        setSort(Sort.TimeDown);
//
//        fullHistoryDisplayer = new FullHistoryDisplayer(this, new Vector(HISTORY_LEFT + HISTORY_WIDTH + HISTORY_BUFFER,
//                HISTORY_YMAR), new Vector(getEngine().getSize().getX() - (HISTORY_LEFT + HISTORY_WIDTH + HISTORY_BUFFER) - HISTORY_YMAR, getEngine().getSize().getY() - 2 * HISTORY_YMAR));
//        this.adopt(fullHistoryDisplayer);
//        super.init();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (getGlobalPreferences().getTheme() == Themes.LIGHT)
            canvas.drawRGB(255, 255, 255);
        else if (getGlobalPreferences().getTheme() == Themes.DARK)
            canvas.drawRGB(0, 0, 0);
//        canvas.drawRect(HISTORY_LEFT, HISTORY_TOP, HISTORY_LEFT + HISTORY_WIDTH, canvas.getHeight() + HISTORY_BOT, historyPaint);
//        if (history == null) {
//            canvas.drawBitmap(loading, null, new RectF(HISTORY_LEFT + (HISTORY_WIDTH - LOADING_SIZE) / 2f, (canvas.getHeight() - LOADING_SIZE) / 2f,
//                    HISTORY_LEFT + (HISTORY_WIDTH + LOADING_SIZE) / 2f, (canvas.getHeight() + LOADING_SIZE) / 2f));
//        }
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
        this.updateDisplayers();
    }

    private class HistoryList extends GameMenu{
        private Paint historyPaint;
        public HistoryList(Vector size) {
            super(size);
        }

        @Override
        public void init(){
            super.init();
            ScrollBitmap.init(getEngine().getGameAssetManager());
            this.historyPaint = new AndroidPaint();
            this.historyPaint.setARGB(255, 128, 128, 128);
            this.build()
                .add(ScrollUpName, new Button(SCROLL_BUTTON_SIZE, ScrollBitmap.SCROLL_UP.getBitmap()).build()
                        .close())
                .setRelativePosition(THIS, HorizontalAlignment.Center, VerticalAlignment.TopAlign)

                .add(HistoryBoxName,
                    (new MenuObject(new Vector(HISTORY_WIDTH, getSize().getY() - 2 * (SCROLL_BUTTON_SIZE.getY() + HISTORY_BUFFER))){
                        @Override
                        public void draw(Canvas canvas, Vector pos, Vector size) {
                            super.draw(canvas, pos, size);
                            canvas.drawRect(pos, size, historyPaint);
                        }
                    }).build()
                        .addOffset(0, HISTORY_BUFFER)
                        .close())
                .setRelativePosition(ScrollUpName, HorizontalAlignment.Center, VerticalAlignment.BottomOf)

                .add(ScrollDownName, new Button(SCROLL_BUTTON_SIZE, ScrollBitmap.SCROLL_DOWN.getBitmap()).build()
                        .close())
                .setRelativePosition(THIS, HorizontalAlignment.Center, VerticalAlignment.BottomAlign)

//                .add(HistoryDisplayersName, new HistoryDisplayer(StatsMenu.this, ))
            .close();
        }

        @Override
        public void draw(Canvas canvas, Vector pos, Vector size) {
            super.draw(canvas, pos, size);
        }

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
        private Sort next;

        Sort(Comparator<SessionHitObjects> comparator) {
            this.comparator = comparator;
        }

        public Comparator<SessionHitObjects> getComparator() {
            return comparator;
        }
    }
}