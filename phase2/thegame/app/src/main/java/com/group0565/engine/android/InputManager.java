package com.group0565.engine.android;

import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

import com.group0565.engine.gameobjects.GameObject;
import com.group0565.engine.gameobjects.InputEvent;
import com.group0565.math.Vector;

/** A class in charge of managing any input to the user in the game */
public class InputManager implements View.OnTouchListener {
  /** Array to store InputEvents based on their pointerID* */
  private SparseArray<InputEvent> eventManager = new SparseArray<>();
  /** The root GameObject to announce input events to* */
  private GameObject game;

  /**
   * Creates a new InputManager
   *
   * @param game The root GameObject of the game-
   */
  public InputManager(GameObject game) {
    this.game = game;
  }

  /**
   * Called when a touch event is dispatched to a view. This allows listeners to get a chance to
   * respond before the target view.
   *
   * @param v The view the touch event has been dispatched to.
   * @param event The MotionEvent object containing full information about the event.
   * @return True if the listener has consumed the event, false otherwise.
   */
  @Override
  public boolean onTouch(View v, MotionEvent event) {
    // Disable multitouch event
    v.getParent().requestDisallowInterceptTouchEvent(true);
    if (event.getActionMasked() == MotionEvent.ACTION_DOWN
        || event.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN) { // Press event
      // Find the index and id of this event
      int pointerIndex = event.getActionIndex();
      int pointerID = event.getPointerId(pointerIndex);
      // Convert the position into a Vector
      Vector pos = new Vector(event.getX(pointerIndex), event.getY(pointerIndex));
      // Construct the input Event and register it
      InputEvent inputEvent = new InputEvent(pos);
      InputEvent prev = eventManager.get(pointerID, null);
      // If somehow a input event with the same id is left over, deactivate it
      if (prev != null) prev.deactivate();
      // Register the new input event
      eventManager.put(pointerID, inputEvent);
      // Announce it to the game
      game.processInput(inputEvent);
    } else if (event.getActionMasked() == MotionEvent.ACTION_MOVE) {
      // Go through every active event and update their position
      for (int pointerIndex = 0; pointerIndex < event.getPointerCount(); pointerIndex++) {
        // Find the pointer id
        int pointerID = event.getPointerId(pointerIndex);
        // Construct and update the position of the input event
        Vector pos = new Vector(event.getX(pointerIndex), event.getY(pointerIndex));
        eventManager.get(pointerID).setPos(pos);
      }
    } else if (event.getActionMasked() == MotionEvent.ACTION_POINTER_UP) {
      // Triggered on releasing a finger, that is not the last one on the screen
      int pointerIndex = event.getActionIndex();
      int pointerID = event.getPointerId(pointerIndex);
      // Find the inputEvent and update the position
      InputEvent inputEvent = eventManager.get(pointerID);
      Vector pos = new Vector(event.getX(pointerIndex), event.getY(pointerIndex));
      inputEvent.setPos(pos);
      // And deactivate it
      inputEvent.deactivate();
      eventManager.remove(pointerID);
    } else if (event.getActionMasked() == MotionEvent.ACTION_UP) {
      // If we released all fingers, make sure all input events are deactivated
      for (int i = 0; i < eventManager.size(); i++) {
        InputEvent e = eventManager.get(i);
        if (e != null) e.deactivate();
      }
    } else return false;
    return true;
  }

  /**
   * Setter for game
   *
   * @param game The new value for game
   */
  public void setGame(GameObject game) {
    this.game = game;
  }
}
