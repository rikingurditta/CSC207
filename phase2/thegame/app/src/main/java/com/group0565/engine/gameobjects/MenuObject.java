package com.group0565.engine.gameobjects;

import com.group0565.engine.android.AndroidPaint;
import com.group0565.engine.enums.HorizontalEdge;
import com.group0565.engine.enums.VerticalEdge;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.EventObserver;
import com.group0565.engine.interfaces.GameEngine;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.ObservationEvent;
import com.group0565.engine.interfaces.Observer;
import com.group0565.engine.interfaces.Paint;
import com.group0565.engine.interfaces.Source;
import com.group0565.math.Vector;

import static com.group0565.engine.enums.HorizontalEdge.*;
import static com.group0565.engine.enums.VerticalEdge.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuObject extends GameObject implements Observable {
    public static final String MENUOBJ_UPDATE = "Menu Object Updated";
    private static Vector referenceSize = new Vector();
    private Vector size;
    private Vector offset = new Vector();
    private HorizontalAlignment[] hAligns = new HorizontalAlignment[2];
    private VerticalAlignment[] vAligns = new VerticalAlignment[2];
    private String name;
    private Source<Boolean> selfEnable = () -> true;

    //TODO: REMOVE
    public boolean debug = false;

    public MenuObject(Vector size) {
        this.setSize(size);
    }

    public MenuObject() {
        this(null);
    }

    public MenuObjectBuilder build(){
        return new MenuObjectBuilder();
    }

    @Override
    public void init() {
        super.init();
        if (size == null && this.getEngine() != null)
            this.size = (this.getEngine().getSize());
        else
            this.size = (size == null ? new Vector() : size);
    }

    @Override
    public void update(long ms) {
        super.update(ms);
    }

    @Override
    public void renderAll(Canvas canvas) {
        if (!isEnable())
            return;
        if (isSelfEnable()) this.draw(canvas);
        for (GameObject child : this.getChildren().values())
            child.renderAll(canvas);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (getSize() != null)
            this.draw(canvas, this.getAbsolutePosition(), this.getSize());
        if (debug) {
            Paint p = new AndroidPaint();
            p.setARGB(255, 255, 0, 0);
            p.setStrokeWidth(10);
            canvas.drawLine((new Vector(getEdgePosition(Left), getEdgePosition(Top))), (new Vector(getEdgePosition(Left), getEdgePosition(Bottom))), p);
            canvas.drawLine((new Vector(getEdgePosition(Left), getEdgePosition(Top))), (new Vector(getEdgePosition(Right), getEdgePosition(Top))), p);
            canvas.drawLine((new Vector(getEdgePosition(Right), getEdgePosition(Top))), (new Vector(getEdgePosition(Right), getEdgePosition(Bottom))), p);
            canvas.drawLine((new Vector(getEdgePosition(Left), getEdgePosition(Bottom))), (new Vector(getEdgePosition(Right), getEdgePosition(Bottom))), p);
            canvas.drawLine((new Vector(getEdgePosition(HCenter), getEdgePosition(Top))), (new Vector(getEdgePosition(HCenter), getEdgePosition(Bottom))), p);
            canvas.drawLine((new Vector(getEdgePosition(Left), getEdgePosition(VCenter))), (new Vector(getEdgePosition(Right), getEdgePosition(VCenter))), p);
        }
    }

    private void observeUpdate(Observable observable, ObservationEvent event){
        if (event.isEvent(MENUOBJ_UPDATE))
            this.updatePosition();
    }

    public void draw(Canvas canvas, Vector pos, Vector size) {
    }

    @Override
    public void setEngine(GameEngine engine) {
        super.setEngine(engine);
        if (this.getSize() == null)
            this.setSize(engine.getSize());
    }

    protected void updatePosition(){
        for (int i = 0; i < 2; i ++){
            if (hAligns[i] != null)
                hAligns[i].relativeTo.updatePosition();
            if (vAligns[i] != null)
                vAligns[i].relativeTo.updatePosition();
        }
        updateHorizontal();
        updateVertical();
        this.setRelativePosition(getRelativePosition().add(offset));
    }

    private void updateHorizontal(){
        Arrays.sort(hAligns, (o1, o2) -> {
            if (o1 == null && o2 == null)
                return 0;
            if (o1 == null)
                return 1;
            if (o2 == null)
                return -1;
            return o1.sourceEdge.ordinal() - o2.sourceEdge.ordinal();
        });
        if (hAligns[0] == null)
            return;
        //Sets The X coordinate
        {
            float x;
            switch (hAligns[0].sourceEdge){
                case Left:
                    x = hAligns[0].getTargetEdgePosition();
                    break;
                case HCenter:
                    x = hAligns[0].getTargetEdgePosition() - size.getX()/2f;
                    break;
                case Right:
                    x = hAligns[0].getTargetEdgePosition() - size.getX();
                    break;
                default:
                    x = getAbsolutePosition().getX();
            }
            setAbsolutePositionX(x);
        }
        if (hAligns[1] != null) {
            if (hAligns[0].sourceEdge == HorizontalEdge.Left){
                if (hAligns[1].sourceEdge == HorizontalEdge.HCenter){
                    this.size = this.size.newSetX(2*(hAligns[1].getTargetEdgePosition() - this.getAbsolutePosition().getX()));
                    this.initialSizeUpdate(size);
                } else if (hAligns[1].sourceEdge == HorizontalEdge.Right){
                    this.size = this.size.newSetX(hAligns[1].getTargetEdgePosition() - this.getAbsolutePosition().getX());
                    this.initialSizeUpdate(size);
                }
            }
            else if(hAligns[0].sourceEdge == HorizontalEdge.HCenter){
                this.size = this.size.newSetX(hAligns[1].getTargetEdgePosition() - hAligns[0].getTargetEdgePosition());
                this.initialSizeUpdate(size);
                setAbsolutePositionX(hAligns[0].getTargetEdgePosition() - size.getX()/2f);
            }
        }
    }

    protected void initialSizeUpdate(Vector size) {
    }

    private void updateVertical(){
        Arrays.sort(vAligns, (o1, o2) -> {
            if (o1 == null && o2 == null)
                return 0;
            if (o1 == null)
                return -1;
            if (o2 == null)
                return 1;
            return o1.sourceEdge.ordinal() - o2.sourceEdge.ordinal();
        });
        Arrays.sort(vAligns, (o1, o2) -> {
            if (o1 == null && o2 == null)
                return 0;
            if (o1 == null)
                return 1;
            if (o2 == null)
                return -1;
            return o1.sourceEdge.ordinal() - o2.sourceEdge.ordinal();
        });
        if (vAligns[0] == null)
            return;
        //Sets The Y coordinate
        {
            float y;
            switch (vAligns[0].sourceEdge){
                case Top:
                    y = vAligns[0].getTargetEdgePosition();
                    break;
                case VCenter:
                    y = vAligns[0].getTargetEdgePosition() - size.getY()/2f;
                    break;
                case Bottom:
                    y = vAligns[0].getTargetEdgePosition() - size.getY();
                    break;
                default:
                    y = getAbsolutePosition().getY();
            }
            setAbsolutePositionY(y);
        }
        if (vAligns[1] != null) {
            if (vAligns[0].sourceEdge == VerticalEdge.Top){
                if (vAligns[1].sourceEdge == VerticalEdge.VCenter){
                    this.size = this.size.newSetY(2*(vAligns[1].getTargetEdgePosition() - this.getAbsolutePosition().getY()));
                    this.initialSizeUpdate(size);
                } else if (vAligns[1].sourceEdge == VerticalEdge.Bottom){
                    this.size = this.size.newSetY(vAligns[1].getTargetEdgePosition() - this.getAbsolutePosition().getY());
                    this.initialSizeUpdate(size);
                }
            }
            else if(vAligns[0].sourceEdge == VerticalEdge.VCenter){
                this.size = this.size.newSetY(vAligns[1].getTargetEdgePosition() - vAligns[0].getTargetEdgePosition());
                this.initialSizeUpdate(size);
                setAbsolutePositionY(vAligns[0].getTargetEdgePosition() - size.getY()/2f);
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    protected class MenuObjectBuilder{
        private Vector offset = null;
        private boolean enable = true;
        private boolean enableSet = false;
        private Source<Boolean> selfEnable = null;
        private float z = 0;
        private String name = null;
        private List<Observer> observerList = new ArrayList<>();
        private List<EventObserver> eventObserverList = new ArrayList<>();
        protected MenuObjectBuilder(){
        }

        public MenuObjectBuilder addOffset(Vector offset){
            this.offset = offset;
            return this;
        }

        public MenuObjectBuilder addOffset(float x, float y){
            this.offset = new Vector(x, y);
            return this;
        }

        public MenuObjectBuilder setEnable(boolean enable){
            this.enable = enable;
            this.enableSet = true;
            return this;
        }

        public MenuObjectBuilder setSelfEnable(Source<Boolean> enable){
            this.selfEnable = enable;
            return this;
        }

        public MenuObjectBuilder registerObserver(Observer observer){
            this.observerList.add(observer);
            return this;
        }

        public MenuObjectBuilder registerObserver(EventObserver observer){
            this.eventObserverList.add(observer);
            return this;
        }

        public void setName(String name) {
            this.name = name;
        }

        public MenuObjectBuilder setZ(float z){
            this.z = z;
            return this;
        }

        public MenuObject close(){
            if (offset != null) MenuObject.this.setOffset(this.offset);
            if (enableSet) MenuObject.this.setEnable(this.enable);
            if (selfEnable != null) MenuObject.this.setSelfEnable(this.selfEnable);
            MenuObject.this.setZ(this.z);
            if (name != null) MenuObject.this.setName(name);
            for (Observer observer : this.observerList)
                MenuObject.this.registerObserver(observer);
            for (EventObserver eventObserver : this.eventObserverList)
                MenuObject.this.registerObserver(eventObserver);
            notifyObservers();
            return MenuObject.this;
        }

        protected class MenuObjectBuilderException extends IllegalStateException{
            public MenuObjectBuilderException(String s) {
                super(s);
            }
        }
    }

    public void addAlignment(HorizontalAlignment alignment) {
        alignment.relativeTo.registerObserver(this::observeUpdate);
        if (hAligns[0] == null && !alignment.sameEdge(hAligns[1]))
            this.hAligns[0] = alignment;
        else if (hAligns[0] != null && alignment.sameEdge(hAligns[0]))
            this.hAligns[0] = alignment;
        else if (hAligns[1] == null)
            this.hAligns[1] = alignment;
        else if (alignment.sameEdge(hAligns[1]))
            this.hAligns[1] = alignment;
        else
            throw new IllegalStateException("A MenuObject can have at most 2 Horizontal Alignments");
    }

    public void addAlignment(VerticalAlignment alignment) {
        if (vAligns[0] == null && !alignment.sameEdge(vAligns[1]))
            this.vAligns[0] = alignment;
        else if (vAligns[0] != null && alignment.sameEdge(vAligns[0]))
            this.vAligns[0] = alignment;
        else if (vAligns[1] == null)
            this.vAligns[1] = alignment;
        else if (alignment.sameEdge(vAligns[1]))
            this.vAligns[1] = alignment;
        else
            throw new IllegalStateException("A MenuObject can have at most 2 Horizontal Alignments");
    }

    public HorizontalAlignment[] getHorizontalAlignments(){
        return this.hAligns;
    }

    public VerticalAlignment[] getVerticalAlignments(){
        return this.vAligns;
    }

    public boolean isHorizontalAlignedTo(MenuObject other){
        if (other == null)
            return false;
        for (int i = 0; i < 2; i ++)
            if (hAligns[i] != null && hAligns[i].relativeTo.equals(other))
                return true;
        return false;
    }

    public boolean isVerticalAlignedTo(MenuObject other){
        if (other == null)
            return false;
        for (int i = 0; i < 2; i ++)
            if (vAligns[i] != null && vAligns[i].relativeTo.equals(other))
                return true;
        return false;
    }

    private float getEdgePosition(HorizontalEdge edge){
        switch (edge){
            case Left:
                return getAbsolutePosition().getX();
            case HCenter:
                return getAbsolutePosition().getX() + getSize().getX()/2f;
            case Right:
                return getAbsolutePosition().getX() + getSize().getX();
        }
        return 0;
    }

    private float getEdgePosition(VerticalEdge edge){
        switch (edge){
            case Top:
                return getAbsolutePosition().getY();
            case VCenter:
                return getAbsolutePosition().getY() + getSize().getY()/2f;
            case Bottom:
                return getAbsolutePosition().getY() + getSize().getY();
        }
        return 0;
    }

    public Vector getSize() {
        return size;
    }

    public void setSize(Vector size) {
        if (referenceSize != null && referenceSize.getX() != 0 && referenceSize.getY() != 0)
            this.size = size.elementMultiply(getEngine().getSize().elementDivide(referenceSize));
        else
            this.size = size;
        this.updatePosition();
        this.notifyObservers(new ObservationEvent(MENUOBJ_UPDATE));
    }

    public Vector getOffset() {
        return offset;
    }

    public void setOffset(Vector offset) {
        if (referenceSize != null && referenceSize.getX() != 0 && referenceSize.getY() != 0)
            this.offset = offset.elementMultiply(getEngine().getSize().elementDivide(referenceSize));
        else
            this.offset = offset;
        this.updatePosition();
        this.notifyObservers();
    }

    @Override
    public MenuObject setZ(double z) {
        super.setZ(z);
        return this;
    }

    public static Vector getReferenceSize() {
        return referenceSize;
    }

    public static void setReferenceSize(Vector referenceSize) {
        MenuObject.referenceSize = referenceSize;
    }

    public boolean isSelfEnable() {
        return selfEnable.getValue();
    }

    public void setSelfEnable(boolean selfEnable) {
        this.selfEnable = () -> selfEnable;
    }

    public void setSelfEnable(Source<Boolean> selfEnable) {
        this.selfEnable = selfEnable;
    }

    protected class HorizontalAlignment{
        public MenuObject relativeTo;
        public HorizontalEdge sourceEdge;
        public HorizontalEdge targetEdge;
        public float offset;

        public HorizontalAlignment(HorizontalEdge sourceEdge, MenuObject relativeTo, HorizontalEdge targetEdge, float offset) {
            this.relativeTo = relativeTo;
            this.sourceEdge = sourceEdge;
            this.targetEdge = targetEdge;
            this.offset = offset;
        }

        public boolean sameEdge(HorizontalAlignment other){
            if (other != null && other.sourceEdge == sourceEdge)
                return true;
            return false;
        }

        public float getTargetEdgePosition() {
            return this.relativeTo.getEdgePosition(this.targetEdge) + offset;
        }
    }

    protected class VerticalAlignment{
        public MenuObject relativeTo;
        public VerticalEdge sourceEdge;
        public VerticalEdge targetEdge;
        public float offset;

        public VerticalAlignment(VerticalEdge sourceEdge, MenuObject relativeTo, VerticalEdge targetEdge, float offset) {
            this.sourceEdge = sourceEdge;
            this.relativeTo = relativeTo;
            this.targetEdge = targetEdge;
            this.offset = offset;
        }

        public boolean sameEdge(VerticalAlignment other){
            if (other != null && other.sourceEdge == sourceEdge)
                return true;
            return false;
        }

        public float getTargetEdgePosition() {
            return this.relativeTo.getEdgePosition(this.targetEdge) + offset;
        }
    }
}
