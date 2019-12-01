package com.group0565.engine.render;

import com.group0565.engine.gameobjects.MenuObject;
import com.group0565.engine.interfaces.Bitmap;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.Source;
import com.group0565.math.Vector;

/** MenuObject that draws a Bitmap */
public class BitmapDrawer extends MenuObject {

  /** THe Bitmap to draw */
  private Source<Bitmap> bitmap;

    /**
     * Whether or not to lock aspect ratio.
     */
    private boolean aspect;

    /**
     * Creates a new BitmapDrawer
     * @param size The size to draw the bitmap
     * @param bitmap The Bitmap to Draw
     * @param aspect Whether or not to lock aspect ratio
     */
    public BitmapDrawer(Vector size, Source<Bitmap> bitmap, boolean aspect) {
        super(size);
        this.bitmap = bitmap;
        this.aspect = aspect;
    }

    /**
     * Creates a new BitmapDrawer
     * @param size The size to draw the bitmap
     * @param bitmap The Bitmap to Draw
     */
    public BitmapDrawer(Vector size, Source<Bitmap> bitmap) {
        this(size, bitmap, false);
    }

    @Override
    public void draw(Canvas canvas, Vector pos, Vector size) {
        super.draw(canvas, pos, size);
        if (bitmap.getValue() != null)
            canvas.drawBitmap(bitmap.getValue(), pos, size, aspect);
    }
}
