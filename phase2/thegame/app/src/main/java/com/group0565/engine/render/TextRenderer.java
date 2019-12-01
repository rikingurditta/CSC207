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
    private String last = "";

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

    public TextRenderer(Source<String> text, PaintCan paintCan, Vector size) {
        super(paintCan.getPaint().getTextBounds(text.getValue()));
        this.setPaintCan(paintCan);
        this.textSource = text;
        this.setSize(size);
    }

    public TextRenderer(Source<String> text, Paint paint) {
        super(paint.getTextBounds(text.getValue()));
        this.setPaint(paint);
        this.textSource = text;
    }

    @Override
    public void update(long ms) {
        super.update(ms);
        if (!last.equals(textSource.getValue()))
            setSize(getSize());
        last = textSource.getValue();
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
        this.paint = paint.clone();
        this.paintCan = null;
    }

    public void setPaintCan(PaintCan paintCan) {
        this.paintCan = paintCan.clone();
        this.paint = null;
    }

    @Override
    public void setSize(Vector size) {
        if (paint != null){
            paint.setTextSize(size.getY());
            super.setSize(size.newSetX(paint.getTextBounds(textSource.getValue()).getX()));
        }else if (paintCan != null){
            paintCan.setTextSize(size.getY());
            super.setSize(size.newSetX(paintCan.getPaint().getTextBounds(textSource.getValue()).getX()));
        }else
            super.setSize(size);
    }

    public String getString(){
        return this.textSource.getValue();
    }

    protected Paint getPaint() {
        return paintCan != null ? paintCan.getPaint() : paint;
    }

    /**
     * Setter for textSource
     *
     * @param textSource The new value for textSource
     */
    protected void setTextSource(Source<String> textSource) {
        this.textSource = textSource;
    }
}
