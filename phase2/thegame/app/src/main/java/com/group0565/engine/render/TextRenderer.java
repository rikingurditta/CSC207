package com.group0565.engine.render;

import com.group0565.engine.gameobjects.MenuObject;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.Paint;
import com.group0565.engine.interfaces.sources.TextSource;
import com.group0565.math.Vector;

public class TextRenderer extends MenuObject {
    private Paint paint;
    private PaintCan paintCan;
    private TextSource languageText;

    public TextRenderer(Vector position, String string, PaintCan paintCan) {
        super(paintCan.getPaint().getTextBounds(string));
        this.setRelativePosition(position);
        this.setPaintCan(paintCan);
        this.languageText = new LanguageText(string);
    }

    public TextRenderer(Vector position, String string, Paint paint) {
        super(paint.getTextBounds(string));
        this.setRelativePosition(position);
        this.setPaint(paint);
        this.languageText = new LanguageText(string);
    }

    public TextRenderer(Vector position, TextSource text, PaintCan paintCan) {
        super(paintCan.getPaint().getTextBounds(text.getString()));
        this.setRelativePosition(position);
        this.setPaintCan(paintCan);
        this.languageText = text;
    }

    public TextRenderer(Vector position, TextSource text, Paint paint) {
        super(paint.getTextBounds(text.getString()));
        this.setRelativePosition(position);
        this.setPaint(paint);
        this.languageText = text;
    }

    public TextRenderer(String string, PaintCan paintCan) {
        super(paintCan.getPaint().getTextBounds(string));
        this.setPaintCan(paintCan);
        this.languageText = new LanguageText(string);
    }

    public TextRenderer(String string, Paint paint) {
        super(paint.getTextBounds(string));
        this.setPaint(paint);
        this.languageText = new LanguageText(string);
    }

    public TextRenderer(TextSource text, PaintCan paintCan) {
        super(paintCan.getPaint().getTextBounds(text.getString()));
        this.setPaintCan(paintCan);
        this.languageText = text;
    }

    public TextRenderer(TextSource text, Paint paint) {
        super(paint.getTextBounds(text.getString()));
        this.setPaint(paint);
        this.languageText = text;
    }


    @Override
    public void draw(Canvas canvas, Vector pos, Vector size) {
        super.draw(canvas, pos, size);
        if (paint != null)
            canvas.drawText(languageText.getString(), pos, paint);
        else if (paintCan != null)
            canvas.drawText(languageText.getString(), pos, paintCan.getPaint());
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
        this.paintCan = null;
    }

    public void setPaintCan(PaintCan paintCan) {
        this.paintCan = paintCan;
        this.paint = null;
    }
}
