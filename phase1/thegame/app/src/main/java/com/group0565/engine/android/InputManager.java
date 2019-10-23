package com.group0565.engine.android;

import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

import com.group0565.engine.gameobjects.GameObject;
import com.group0565.engine.gameobjects.InputEvent;
import com.group0565.math.Vector;


public class InputManager implements View.OnTouchListener {
    private static final String TAG = "InputManager";

    private SparseArray<InputEvent> eventManager = new SparseArray<>();
    private GameObject game;

    public InputManager(GameObject game) {
        this.game = game;
    }

    /**
     * Called when a touch event is dispatched to a view. This allows listeners to
     * get a chance to respond before the target view.
     *
     * @param v     The view the touch event has been dispatched to.
     * @param event The MotionEvent object containing full information about
     *              the event.
     * @return True if the listener has consumed the event, false otherwise.
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //        Log.i(TAG, event.toString());
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN || event.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN) {
            int pointerIndex = event.getActionIndex();
            int pointerID = event.getPointerId(pointerIndex);
            Vector pos = new Vector(event.getX(pointerIndex), event.getY(pointerIndex));
            InputEvent inputEvent = new InputEvent(pos);
            InputEvent prev = eventManager.get(pointerID, null);
            if (prev != null)
                prev.deactivate();
            eventManager.put(pointerID, inputEvent);
            game.processInput(inputEvent);
        } else if (event.getActionMasked() == MotionEvent.ACTION_MOVE) {
            for (int pointerIndex = 0; pointerIndex < event.getPointerCount(); pointerIndex++) {
                int pointerID = event.getPointerId(pointerIndex);
                Vector pos = new Vector(event.getX(pointerIndex), event.getY(pointerIndex));
                eventManager.get(pointerID).setPos(pos);
            }
        } else if (event.getActionMasked() == MotionEvent.ACTION_UP || event.getActionMasked() == MotionEvent.ACTION_POINTER_UP) {
            int pointerIndex = event.getActionIndex();
            int pointerID = event.getPointerId(pointerIndex);
            InputEvent inputEvent = eventManager.get(pointerID);
            Vector pos = new Vector(event.getX(pointerIndex), event.getY(pointerIndex));
            inputEvent.setPos(pos);
            inputEvent.deactivate();
            eventManager.remove(pointerID);
        } else return false;
        return true;
    }

    public void setGame(GameObject game) {
        this.game = game;
    }
}

