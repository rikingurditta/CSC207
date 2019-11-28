package com.group0565.engine.render;

import com.group0565.engine.gameobjects.MenuObject;
import com.group0565.engine.interfaces.Bitmap;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.Source;
import com.group0565.math.Vector;

/**
 * MenuObject that draws a Bitmap
 */
public class BitmapDrawer extends MenuObject {

    /**
     * THe Bitmap to draw
     */
    private Source<Bitmap> bitmap;

    /**
     * Creates a new BitmapDrawer
     * @param size The size to draw the bitmap
     * @param bitmap The Bitmap to Draw
     */
    public BitmapDrawer(Vector size, Source<Bitmap> bitmap) {
        super(size);
        this.bitmap = bitmap;
    }

    @Override
    public void draw(Canvas canvas, Vector pos, Vector size) {
        super.draw(canvas, pos, size);
        canvas.drawBitmap(bitmap.getValue(), pos, size);
    }
}
