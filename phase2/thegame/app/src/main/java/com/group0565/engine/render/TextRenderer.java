package com.group0565.engine.render;

import com.group0565.engine.gameobjects.MenuObject;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.Paint;
import com.group0565.engine.interfaces.Source;
import com.group0565.math.Vector;

public class TextRenderer extends MenuObject {
    private Paint paint;
    private PaintCan paintCan;
    private Source<String> textSource;

    public TextRenderer(Vector position, String string, PaintCan paintCan) {
        super(paintCan.getPaint().getTextBounds(string));
        this.setRelativePosition(position);
        this.setPaintCan(paintCan);
        this.textSource = new LanguageText(string);
    }

    public TextRenderer(Vector position, String string, Paint paint) {
        super(paint.getTextBounds(string));
        this.setRelativePosition(position);
        this.setPaint(paint);
        this.textSource = new LanguageText(string);
    }

    public TextRenderer(Vector position, Source<String> text, PaintCan paintCan) {
        super(paintCan.getPaint().getTextBounds(text.getValue()));
        this.setRelativePosition(position);
        this.setPaintCan(paintCan);
        this.textSource = text;
    }

    public TextRenderer(Vector position, Source<String> text, Paint paint) {
        super(paint.getTextBounds(text.getValue()));
        this.setRelativePosition(position);
        this.setPaint(paint);
        this.textSource = text;
    }

    public TextRenderer(String string, PaintCan paintCan) {
        super(paintCan.getPaint().getTextBounds(string));
        this.setPaintCan(paintCan);
        this.textSource = new LanguageText(string);
    }

    public TextRenderer(String string, Paint paint) {
        super(paint.getTextBounds(string));
        this.setPaint(paint);
        this.textSource = new LanguageText(string);
    }

    public TextRenderer(Source<String> text, PaintCan paintCan) {
        super(paintCan.getPaint().getTextBounds(text.getValue()));
        this.setPaintCan(paintCan);
        this.textSource = text;
    }

    public TextRenderer(Source<String> text, Paint paint) {
        super(paint.getTextBounds(text.getValue()));
        this.setPaint(paint);
        this.textSource = text;
    }


    @Override
    public void draw(Canvas canvas, Vector pos, Vector size) {
        super.draw(canvas, pos, size);
        if (paint != null)
            canvas.drawText(getString(), pos, paint);
        else if (paintCan != null)
            canvas.drawText(getString(), pos, paintCan.getPaint());
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
        this.paintCan = null;
    }

    public void setPaintCan(PaintCan paintCan) {
        this.paintCan = paintCan;
        this.paint = null;
    }

    public String getString(){
        return this.textSource.getValue();
    }

    protected Paint getPaint() {
        return paintCan != null ? paintCan.getPaint() : paint;
    }
}
