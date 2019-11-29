package com.group0565.engine.gameobjects;

import com.group0565.engine.enums.HorizontalEdge;
import com.group0565.engine.enums.VerticalEdge;
import com.group0565.math.Vector;

import static com.group0565.engine.enums.HorizontalEdge.*;
import static com.group0565.engine.enums.VerticalEdge.*;

import java.util.HashMap;

public class GameMenu extends MenuObject {
    public static final String THIS = "this";
    private HashMap<String, MenuObject> menuComponents;

    public GameMenu() {
        super();
    }

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
    public void postInit() {
        super.postInit();
        updateAllPosition();
    }

    public MenuBuilder build(){
        return new MenuBuilder();
    }

    protected MenuObject getComponent(String name){
        return menuComponents.get(name);
    }

    public void updateAllPosition(){
        super.updatePosition();
        if (this.menuComponents != null)
            for (MenuObject obj : this.menuComponents.values())
                obj.updatePosition();
    }

    protected class MenuBuilder extends MenuObjectBuilder{
        private HashMap<String, MenuObject> buildComponents;
        private MenuObject activeObject = null;
        protected MenuBuilder(){
            buildComponents = new HashMap<>();
            buildComponents.put(THIS, GameMenu.this);
        }

        public MenuBuilder add(String name, MenuObject object){
            if (name.equals(THIS))
                throw new MenuBuilderException("Name \"" + THIS + "\" is reserved.");
            buildComponents.put(name, object);
            activeObject = object;
            activeObject.setName(name);
            return this;
        }

        public MenuBuilder addAlignment(HorizontalEdge sourceEdge, String relativeTo, HorizontalEdge targetEdge){
            return addAlignment(sourceEdge, relativeTo, targetEdge, 0);
        }

        public MenuBuilder addAlignment(VerticalEdge sourceEdge, String relativeTo, VerticalEdge targetEdge){
            return addAlignment(sourceEdge, relativeTo, targetEdge, 0);
        }

        public MenuBuilder addAlignment(HorizontalEdge sourceEdge, String relativeTo, HorizontalEdge targetEdge, float offset){
            if (activeObject == null)
                throw new MenuBuilderException("Unable to set relative position when no previous object has been added.");
            MenuObject object = buildComponents.get(relativeTo);
            if (object == null)
                throw new MenuBuilderException("No MenuObject with name" + relativeTo + " is found");
            activeObject.addAlignment(new HorizontalAlignment(sourceEdge, object, targetEdge, offset));
            return this;
        }

        public MenuBuilder addAlignment(VerticalEdge sourceEdge, String relativeTo, VerticalEdge targetEdge, float offset){
            if (activeObject == null)
                throw new MenuBuilderException("Unable to set relative position when no previous object has been added.");
            MenuObject object = buildComponents.get(relativeTo);
            if (object == null)
                throw new MenuBuilderException("No MenuObject with name" + relativeTo + " is found");
            activeObject.addAlignment(new VerticalAlignment(sourceEdge, object, targetEdge, offset));
            return this;
        }

        public MenuBuilder addCenteredAlignment(String relativeTo){
            addAlignment(HCenter, relativeTo, HCenter);
            addAlignment(VCenter, relativeTo, VCenter);
            return this;
        }

        public MenuBuilder addCenteredAlignment(String relativeTo, float offset){
            addAlignment(HCenter, relativeTo, HCenter, offset);
            addAlignment(VCenter, relativeTo, VCenter, offset);
            return this;
        }

        public GameMenu close(){
            super.close();
            buildComponents.remove(THIS);
            menuComponents = buildComponents;
            for (MenuObject menuComponent:menuComponents.values()) {
                adopt(menuComponent);
            }
            return GameMenu.this;
        }

        protected class MenuBuilderException extends MenuObjectBuilderException{
            public MenuBuilderException(String s) {
                super(s);
            }
        }
    }
}
