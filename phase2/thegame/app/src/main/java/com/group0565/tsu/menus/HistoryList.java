package com.group0565.tsu.menus;

import com.group0565.engine.android.AndroidPaint;
import com.group0565.engine.gameobjects.Button;
import com.group0565.engine.gameobjects.GameMenu;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.EventObserver;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.ObservationEvent;
import com.group0565.engine.interfaces.Paint;
import com.group0565.engine.interfaces.Source;
import com.group0565.engine.render.BitmapDrawer;
import com.group0565.hitObjectsRepository.SessionHitObjects;
import com.group0565.math.Vector;
import com.group0565.tsu.enums.ScrollBitmap;

import java.util.List;

import static com.group0565.engine.enums.HorizontalEdge.Left;
import static com.group0565.engine.enums.HorizontalEdge.Right;
import static com.group0565.engine.enums.VerticalEdge.Bottom;
import static com.group0565.engine.enums.VerticalEdge.Top;

class HistoryList extends GameMenu {
    //Event Constants
    public static final String SELECTION_CHANGED = "Selected History Changed";
    //Scroll Up Constants
    private static final String ScrollUpName = "ScrollUp";
    private static final String ScrollUpDsbName = "ScrollUpDsb";
    //Scroll Down Constants
    private static final String ScrollDownName = "ScrollDown";
    private static final String ScrollDownDsbName = "ScrollDownDsb";
    private static final Vector SCROLL_BUTTON_SIZE = new Vector(0, 75);
    //History Displayers Constants
    private static final String[] HistoryDisplayersName = {"HitoryDisplayer0", "HitoryDisplayer1", "HitoryDisplayer2", "HitoryDisplayer3"};
    //History Box Constants
    private static final String HistoryBoxName = "HistoryBox";
    private static final float HISTORY_BUFFER = 50;
    private static final int DISPLAYER_COUNT = 4;

    private Paint historyPaint;
    private int scroll;
    private HistoryBox box;
    private SessionHitObjects selectedObject;
    private Source<List<SessionHitObjects>> history;

    public HistoryList(StatsMenu statsMenu, Vector size, Source<List<SessionHitObjects>> history) {
        super(size);
        statsMenu.registerObserver(this::observeStatsMenu);
        this.history = history;
    }

    @Override
    public void init(){
        super.init();
        ScrollBitmap.init(getEngine().getGameAssetManager());
        this.historyPaint = new AndroidPaint();
        this.historyPaint.setARGB(255, 128, 128, 128);
        this.build()
            .add(ScrollUpName, new Button(SCROLL_BUTTON_SIZE, ScrollBitmap.SCROLL_UP.getBitmap(), -1).build()
                    .registerObserver(this::observeScroll)
                    .setSelfEnable(() -> (getHistory() != null && scroll > 0))
                    .close())
            .addAlignment(Left, THIS, Left)
            .addAlignment(Right, THIS, Right)
            .addAlignment(Top, THIS, Top)

            .add(ScrollUpDsbName,  new BitmapDrawer(null, ScrollBitmap.SCROLL_UP_DSB::getBitmap).setZ(-1))
                .addAlignment(Left, ScrollUpName, Left)
                .addAlignment(Right, ScrollUpName, Right)
                .addAlignment(Top, ScrollUpName, Top)
                .addAlignment(Bottom, ScrollUpName, Bottom)

            .add(ScrollDownName, new Button(SCROLL_BUTTON_SIZE, ScrollBitmap.SCROLL_DOWN.getBitmap(), 1).build()
                    .registerObserver(this::observeScroll)
                    .setSelfEnable(() -> (getHistory() != null && scroll < getHistory().size()-1-DISPLAYER_COUNT))
                    .close())
            .addAlignment(Left, THIS, Left)
            .addAlignment(Right, THIS, Right)
            .addAlignment(Bottom, THIS, Bottom)

            .add(ScrollDownDsbName, new BitmapDrawer(null, ScrollBitmap.SCROLL_DOWN_DSB::getBitmap).setZ(-1))
            .addAlignment(Left, ScrollDownName, Left)
            .addAlignment(Right, ScrollDownName, Right)
            .addAlignment(Top, ScrollDownName, Top)
            .addAlignment(Bottom, ScrollDownName, Bottom)

            .add(HistoryBoxName, box = new HistoryBox(DISPLAYER_COUNT))
            .addAlignment(Left, THIS, Left)
            .addAlignment(Right, THIS, Right)
            .addAlignment(Top, ScrollUpName, Bottom, HISTORY_BUFFER)
            .addAlignment(Bottom, ScrollDownName, Top, -HISTORY_BUFFER)
        .close();
    }

    private void observeScroll(Observable observable, ObservationEvent<Integer> event){
        if (event.isEvent(Button.EVENT_DOWN))
            this.setScroll(scroll + event.getPayload());
    }

    private void observeStatsMenu(Observable observable, ObservationEvent event){
        if (event.getMsg().equals(StatsMenu.HISTORY_UPDATE_EVENT))
            updateScroll();
    }

    @Override
    public void draw(Canvas canvas, Vector pos, Vector size) {
        super.draw(canvas, pos, size);
    }

    public void updateScroll(){
        box.updateScroll();
    }

    /**
     * Setter for scroll
     *
     * @param scroll The new value for scroll
     */
    private void setScroll(int scroll) {
        this.scroll = scroll;
        updateScroll();
    }

    /**
     * Getter for history
     *
     * @return history
     */
    public List<SessionHitObjects> getHistory() {
        return history.getValue();
    }

    /**
     * Getter for selectedObject
     *
     * @return selectedObject
     */
    public SessionHitObjects getSelectedObject() {
        return selectedObject;
    }

    /**
     * Setter for selectedObject
     *
     * @param selectedObject The new value for selectedObject
     */
    public void setSelectedObject(SessionHitObjects selectedObject) {
        this.selectedObject = selectedObject;
        if (getHistory().contains(this.selectedObject)) {
            int index = getHistory().indexOf(this.selectedObject);
            if (!(scroll <= index && index < scroll + DISPLAYER_COUNT))
                setScroll(index);
        }
        notifyObservers(new ObservationEvent<>(SELECTION_CHANGED, selectedObject));
    }

    private class HistoryBox extends GameMenu{
        private int count;
        private HistoryDisplayer[] displayers;

        public HistoryBox(int count) {
            this.count = count;
            this.displayers = new HistoryDisplayer[count];
        }

        @Override
        public void draw(Canvas canvas, Vector pos, Vector size) {
            super.draw(canvas, pos, size);
            canvas.drawRect(pos, size, historyPaint);
        }

        @Override
        public void init() {
            super.init();
            float height = getSize().getY()/4f;
            MenuBuilder builder = build();

            if (count > 1) {
                for (int i = 0; i < count; i++) {
                    builder.add(HistoryDisplayersName[i], displayers[i] = new HistoryDisplayer(new Vector(0, height), null, () -> selectedObject))
                            .addAlignment(Left, THIS, Left)
                            .addAlignment(Right, THIS, Right);
                    displayers[i].registerObserver(this::buttonPressed);
                    if (i == 0)
                        builder.addAlignment(Top, THIS, Top);
                    else
                        builder.addAlignment(Top, HistoryDisplayersName[i-1], Bottom);
                }
            }
            builder.close();
        }

        private void buttonPressed(Observable observable, ObservationEvent<SessionHitObjects> event){
            if (event.isEvent(Button.EVENT_DOWN)){
                setSelectedObject(event.getPayload());
            }
        }

        public void updateScroll(){
            if (getHistory() == null)
                return;
            for (int i = scroll; i < scroll + count; i ++){
                if (0 <= i && i < getHistory().size())
                    displayers[i-scroll].setObjects(getHistory().get(i));
                else
                    displayers[i-scroll].setObjects(null);
            }
        }
    }
}
