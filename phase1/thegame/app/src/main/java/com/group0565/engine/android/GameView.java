package com.group0565.engine.android;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.group0565.engine.interfaces.LifecycleListener;

public class GameView extends SurfaceView implements LifecycleListener {
    public GameView(Context context, SurfaceHolder.Callback callback, OnTouchListener listener) {
        super(context);
        this.getHolder().addCallback(callback);
        this.setFocusable(true);
        this.setOnTouchListener(listener);
    }

    /**
     * Call this view's OnClickListener, if it is defined.  Performs all normal
     * actions associated with clicking: reporting accessibility event, playing
     * a sound, etc.
     *
     * @return True there was an assigned OnClickListener that was called, false
     * otherwise is returned.
     */
    @Override
    public boolean performClick() {
        return super.performClick();
    }
}
