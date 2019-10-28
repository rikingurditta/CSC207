package com.group0565.engine.gameobjects;

import android.graphics.Canvas;

import com.group0565.engine.interfaces.FactoryObject;
import com.group0565.math.Vector;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

/**
 * Factory to generate FactoryObjects.
 * <p>
 * Bubbles that goes off screen are recycled.
 */
public class Factory<E extends GameObject & FactoryObject> extends GameObject {
    /**
     * Inactive Time before deleting a object
     */
    private static final long TIMEOUT = 5000;
    /**
     * A Map of inactive objects.
     * <p>
     * Since GameObjects are mutable, A HashMap hashing the UUID of the Objects is needed instead.
     */
    private HashMap<UUID, E> inactive = new HashMap<>();

    /**
     * A collection of child objects of this game object.
     * <p>
     * The parent is responsible for updating and rendering it's children.
     * <p>
     * A child's position is relative to it's parent's location.
     * <p>
     * The TreeMap with the ZComparator sorts the children by their z value in ascending order
     * to ensure rendering order.
     * <p>
     * A TreeMap is used as GameObjects are mutable, hence the immutable and hashable UUID of the object
     * is used as a key to the TreeMap.
     */
    private TreeMap<UUID, E> children = new TreeMap<>(new ZComparator());

    /**
     * The Class Object for E
     */
    private Class<E> cls;

    /**
     * Creates a new BubbleFactory with parent, at location position.
     *
     * @param position The position (relative or absolute) of this object.
     * @param cls      The Class Object for E
     */
    public Factory(Vector position, Class<E> cls) {
        super(position);
        this.cls = cls;
    }

    /**
     * Update this object and all children.
     * <p>
     * Any child that is a Bubble is check if it is active,
     * if not, they are added into the inactive queue.
     *
     * @param ms Milliseconds since last update
     */
    @Override
    public void updateAll(long ms) {
        this.update(ms);
        Iterator<E> childiter = this.children.values().iterator();
        while (childiter.hasNext()) {
            E child = childiter.next();
            child.updateAll(ms);
            if (!child.isActive()) {
                childiter.remove();
                this.inactive.put(child.getUUID(), child);
            }
        }
        Iterator<Map.Entry<UUID, E>> inactiveIter = inactive.entrySet().iterator();
        while (inactiveIter.hasNext()) {
            Map.Entry<UUID, E> entry = inactiveIter.next();
            E b = entry.getValue();
            if (b.getInactivity() > TIMEOUT) {
                inactive.remove(entry.getKey());
                inactiveIter.remove();
            }
        }
    }

    /**
     * Draw this object and Renders its children onto canvas
     *
     * @param canvas The Canvas on which to draw and render
     */
    @Override
    public void renderAll(Canvas canvas) {
        this.draw(canvas);
        for (GameObject child : this.children.values())
            child.renderAll(canvas);
    }

    /**
     * Spawns a Bubble at position.
     *
     * @param position The position to spawn the Bubble at
     * @param relative If true, the Bubble is relative to this manager. Otherwise, position is absolute.
     */
    public void spawnObject(Vector position, boolean relative) {
        if (this.inactive.isEmpty()) {
            try {
                E b = cls.getConstructor(GameObject.class, Vector.class, boolean.class).newInstance(this, position, relative);
                this.children.put(b.getUUID(), b);
            } catch (IllegalAccessException | InstantiationException | NoSuchMethodException e) {
                throw new IllegalConstructorException("Constructor for class " + cls.toString() + " is Illegal! See Javadoc of FactoryObject.");
            } catch (InvocationTargetException e) {
                throw new IllegalConstructorException("An exception occurred when trying to construct " + cls.toString(), e);
            }
        } else {
            Map.Entry<UUID, E> b = inactive.entrySet().iterator().next();
            inactive.remove(b.getKey());
            this.children.put(b.getKey(), b.getValue());
            b.getValue().init(position, relative);
        }
    }

    public boolean despawnObject(E obj) {
        if (!children.containsKey(obj.getUUID()))
            return false;
        children.remove(obj.getUUID());
        inactive.put(obj.getUUID(), obj);
        obj.deactivate();
        return true;
    }

    public Collection<E> getActiveObjects() {
        return this.children.values();
    }
}

/**
 * Exception thrown when Construct of a FactoryObject is Illegal.
 * <p>
 * For Javadoc see constructors of RuntimeException.
 */
class IllegalConstructorException extends RuntimeException {

    public IllegalConstructorException() {
    }


    public IllegalConstructorException(String message) {
        super(message);
    }


    public IllegalConstructorException(String message, Throwable cause) {
        super(message, cause);
    }


    public IllegalConstructorException(Throwable cause) {
        super(cause);
    }


    public IllegalConstructorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}