package com.group0565.engine.gameobjects;

import com.group0565.engine.enums.HorizontalAlignment;
import com.group0565.engine.enums.VerticalAlignment;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.Observer;
import com.group0565.math.Vector;

import java.util.HashMap;

public class GameMenu extends MenuObject implements Observer {
    public static final String THIS = "this";
    private HashMap<String, MenuObject> menuComponents;

    public GameMenu(Vector size) {
        super(size);
    }

    @Override
    public void preInit() {
        super.preInit();
        if (this.getSize() == null){
            this.setSize(getEngine().getSize());
        }
    }

    @Override
    public void observe(Observable observable) {
        super.observe(observable);
    }

    public MenuBuilder build(){
        return new MenuBuilder();
    }

    protected MenuObject getComponent(String name){
        return menuComponents.get(name);
    }

    protected class MenuBuilder extends MenuObjectBuilder{
        private HashMap<String, MenuObject> buildComponents;
        private MenuObject activeObject = null;
        private Alignment defaultAlign;
        protected MenuBuilder(){
            buildComponents = new HashMap<>();
            buildComponents.put(THIS, GameMenu.this);
            defaultAlign = new Alignment(GameMenu.this, HorizontalAlignment.Center, VerticalAlignment.Center);
        }

        public MenuBuilder add(String name, MenuObject object){
            if (name.equals(THIS))
                throw new MenuBuilderException("Name \"" + THIS + "\" is reserved.");
            buildComponents.put(name, object);
            activeObject = object;
            activeObject.setName(name);
            activeObject.registerObserver(GameMenu.this);
            activeObject.setAlignment(defaultAlign);
            return this;
        }

        public MenuBuilder setRelativePosition(String relativeTo, HorizontalAlignment hAlign, VerticalAlignment vAlign){
            if (activeObject == null)
                throw new MenuBuilderException("Unable to set relative position when no previous object has been added.");
            MenuObject object = buildComponents.get(relativeTo);
            if (object == null)
                throw new MenuBuilderException("No MenuObject with name" + relativeTo + " is found");
            Alignment alignment = new Alignment(object, hAlign, vAlign);
            activeObject.setAlignment(alignment);
            return this;
        }

        public MenuBuilder setRelativePosition(String relativeTo, HorizontalAlignment hAlign){
            return setRelativePosition(relativeTo, hAlign, VerticalAlignment.Center);
        }

        public MenuBuilder setRelativePosition(String relativeTo, VerticalAlignment vAlign){
            return setRelativePosition(relativeTo, HorizontalAlignment.Center, vAlign);
        }

        public MenuBuilder setCenteredRelativePosition(String relativeTo){
            return setRelativePosition(relativeTo, HorizontalAlignment.Center, VerticalAlignment.Center);
        }

        public GameMenu close(){
            super.close();
            buildComponents.remove(THIS);
            menuComponents = buildComponents;
            refreshAll();
            return GameMenu.this;
        }

        protected class MenuBuilderException extends MenuObjectBuilderException{
            public MenuBuilderException(String s) {
                super(s);
            }
        }
    }
}
