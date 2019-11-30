package com.group0565.engine.android;

import android.graphics.Rect;
import android.graphics.RectF;

import androidx.annotation.NonNull;

import com.group0565.engine.interfaces.Paint;
import com.group0565.engine.interfaces.Typeface;
import com.group0565.math.Vector;

/** An implementation of the Paint interface using the android.graphics.Paint */
public class AndroidPaint extends android.graphics.Paint implements Paint {
  /** Creates a new default paint */
  public AndroidPaint() {
    super();
  }

  /**
   * Create a new instance copying from paint
   *
   * @param paint THe paint to copy from
   */
  public AndroidPaint(android.graphics.Paint paint) {
    super(paint);
  }

  /** Returns the bounds on text */
  @Override
  public Vector getTextBounds(String text) {
    Rect size = new Rect();
    this.getTextBounds(text, 0, text.length(), size);
    return new Vector(size.width(), size.height());
  }

  @Override
  public void setTypeface(Typeface typeface) {
    if (!(typeface instanceof AndroidTypeface))
      throw new RuntimeException("AndroidPaint Only supports AndroidTypeFace");
    this.setTypeface(((AndroidTypeface) typeface).asNativeTypeface());
  }

  @NonNull
  @Override
  public AndroidPaint clone() {
    return new AndroidPaint(this);
  }
}
