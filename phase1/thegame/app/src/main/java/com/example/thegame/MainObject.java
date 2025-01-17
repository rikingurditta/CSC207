package com.example.thegame;

import com.group0565.engine.android.AndroidPaint;
import com.group0565.engine.assets.AudioAsset;
import com.group0565.engine.gameobjects.Button;
import com.group0565.engine.gameobjects.GameObject;
import com.group0565.engine.gameobjects.InputEvent;
import com.group0565.engine.interfaces.Bitmap;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.Observer;
import com.group0565.math.Vector;

public class MainObject extends GameObject implements Observer {
    private long total = 0;
    private int updates = 0;
    private int renders = 0;
    private Vector pos = new Vector();
    private int captured = 0;
    private AudioAsset audio;
    private Button button;

    /**
     * Creates a new GameObject with z-level defaulting to 0.
     * <p>
     * For more information on other parameters see the javadoc of the constructer with signature
     * (GameObject parent, Vector position, boolean relative, Vector charsize, double z).
     *
     * @param position The position absolute of this object.
     */
    public MainObject(Vector position) {
        super(position);
    }

    public void init(){
        audio = getEngine().getGameAssetManager().getAudioAsset("Test", "AudioTest");
        button = new Button(this.getAbsolutePosition(), new Vector(50, 50), getEngine().getGameAssetManager().getTileSheet("Test", "Test1").getTile(0, 0), getEngine().getGameAssetManager().getTileSheet("Test", "Test1").getTile(0, 1));
        button.registerObserver(this);
        adopt(button);
    }


    public void update(long ms) {
        //Increment our total time
        total += ms;
        //If we have elapsed more than 1000 ms
        if (total > 1000) {
            //Log how many updates and renders there have been
//            Log.i(TAG, "Updates: " + updates + " Renders: " + renders);
            total = 0;
            updates = 0;
            renders = 0;
        }
        //If we have captured an event
        if (this.getCapturedEvents().size() > 0)
            //Make our position the captured event
            pos = this.getCapturedEvents().iterator().next().getPos();
        updates++;
    }

    /**
     * Callback when an event has been captured. This is ran on the same thread as update.
     * Use this to process events when the are captured, instead of using captureEvent
     *
     * @param event The event that has been captured.
     */
    @Override
    protected void onEventCapture(InputEvent event) {
        captured = (captured + 1) % 16;
        audio.play();
    }

    @Override
    protected void onEventDisable(InputEvent event) {
        super.onEventDisable(event);
        audio.pause();
    }

    @Override
    public boolean processInput(InputEvent event) {
        if (!super.processInput(event))
            //Capture the event no matter what
            captureEvent(event);
        return true;
    }

    /**
     * Draws ONLY this object t o canvas. Its children is NOT drawn.
     *
     * @param canvas The Canvas on which to draw
     */
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        //Fill background with White
        canvas.drawRGB(255, 255, 255);
        //Set our color to Red
        AndroidPaint p = new AndroidPaint();
        p.setARGB(255, 255, 0, 0);
        //Draw an rectangle at our touch position
        Bitmap bitmap = getEngine().getGameAssetManager().getTileSheet("Test", "Test1").getTile(captured%4, captured/4);
        canvas.drawBitmap(bitmap, pos, new Vector(100));
        canvas.drawText(getEngine().getGameAssetManager().getLanguagePack("Test", "en_us").getToken("Test"), 100, 100, p);
        canvas.drawText(getEngine().getGameAssetManager().getLanguagePack("Test", "en_us").getToken("Non-existing Name"), 100, 200, p);
        renders++;
    }

    @Override
    public void observe(Observable observable) {

    }
}
