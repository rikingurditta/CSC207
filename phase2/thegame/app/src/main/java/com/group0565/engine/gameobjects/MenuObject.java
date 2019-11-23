package com.group0565.engine.gameobjects;

import com.group0565.engine.enums.HorizontalAlignment;
import com.group0565.engine.enums.VerticalAlignment;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.GameEngine;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.Observer;
import com.group0565.math.Vector;

public class MenuObject extends GameObject implements Observable, Observer {
    private static Vector referenceSize = new Vector();
    private Vector size;
    private Vector buffer = new Vector();
    private Vector parentSize = null;
    private Vector parentBuffer = null;
    private Alignment alignment = null;

    public MenuObject(Vector size) {
        this.setSize(size);
        if (this.getSize() == null && this.getEngine() != null)
            this.setSize(this.getEngine().getSize());
    }

    public MenuObjectBuilder build(){
        return new MenuObjectBuilder();
    }

    @Override
    public void update(long ms) {
        super.update(ms);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (getSize() != null)
            this.draw(canvas, this.getAbsolutePosition(), this.getSize());
    }

    public void draw(Canvas canvas, Vector pos, Vector size) {
    }

    @Override
    public void observe(Observable observable) {
        if (alignment != null && observable == alignment.relativeTo){
            parentSize = alignment.relativeTo.getSize();
            parentBuffer = alignment.relativeTo.getBuffer();
            updatePosition();
        }
    }

    @Override
    public void setEngine(GameEngine engine) {
        super.setEngine(engine);
        if (this.getSize() == null)
            this.setSize(engine.getSize());
    }

    protected void updatePosition(){
        if (parentSize == null || parentBuffer == null)
            return;
        float pw = parentSize.getX();
        float ph = parentSize.getY();
        float pbw = parentBuffer.getX();
        float pbh = parentBuffer.getY();
        float w = getSize().getX();
        float h = getSize().getY();
        float x;
        switch (alignment.hAlign){
            case Center:
                x = (pw - w)/2;
                break;
            case LeftOf:
                x = -(w + pbw);
                break;
            case RightOf:
                x = (pw + pbw);
                break;
            case LeftAlign:
                x = 0;
                break;
            case RightAlign:
                x = pw - w;
                break;
            default:
                x = 0;
                break;
        }
        float y;
        switch (alignment.vAlign){
            case Center:
                y = (ph - h)/2;
                break;
            case TopOf:
                y = -(h + pbh);
                break;
            case BottomOf:
                y = (ph + pbh);
                break;
            case TopAlign:
                y = 0;
                break;
            case BottomAlign:
                y = ph - h;
                break;
            default:
                y = 0;
                break;
        }
        this.setRelativePosition(new Vector(x, y));
    }

    protected class MenuObjectBuilder{
        private Vector buffer;
        protected MenuObjectBuilder(){
        }

        public MenuObjectBuilder addBuffer(Vector buffer){
            this.buffer = buffer;
            return this;
        }

        public MenuObjectBuilder addBuffer(float x, float y){
            this.buffer = new Vector(x, y);
            return this;
        }
        public MenuObject close(){
            setBuffer(this.buffer);
            return MenuObject.this;
        }

        protected class MenuObjectBuilderException extends IllegalStateException{
            public MenuObjectBuilderException(String s) {
                super(s);
            }
        }
    }

    public void setAlignment(Alignment alignment) {
        if (this.alignment != null && this.alignment.relativeTo != null)
            this.alignment.relativeTo.unregisterObserver(this);
        this.alignment = alignment;
        this.alignment.relativeTo.registerObserver(this);
        this.alignment.relativeTo.adopt(this);
    }

    public Vector getBuffer() {
        return buffer;
    }

    public void setBuffer(Vector buffer) {
        if (referenceSize != null && referenceSize.getX() != 0 && referenceSize.getY() != 0)
            this.buffer = buffer.elementMultiply(getEngine().getSize().elementDivide(referenceSize));
        else
            this.buffer = buffer;
        this.notifyObservers();
    }

    public Alignment getAlignment() {
        return alignment;
    }

    public Vector getSize() {
        return size;
    }

    public void setSize(Vector size) {
        if (referenceSize != null && referenceSize.getX() != 0 && referenceSize.getY() != 0)
            this.size = size.elementMultiply(getEngine().getSize().elementDivide(referenceSize));
        else
            this.size = size;
        this.notifyObservers();
    }

    public static Vector getReferenceSize() {
        return referenceSize;
    }

    public static void setReferenceSize(Vector referenceSize) {
        MenuObject.referenceSize = referenceSize;
    }

    protected class Alignment{
        public MenuObject relativeTo;
        public HorizontalAlignment hAlign;
        public VerticalAlignment vAlign;

        public Alignment(MenuObject relativeTo, HorizontalAlignment hAlign, VerticalAlignment vAlign) {
            this.relativeTo = relativeTo;
            this.hAlign = hAlign;
            this.vAlign = vAlign;
        }
    }


}
