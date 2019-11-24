package com.group0565.engine.interfaces;

import com.group0565.engine.gameobjects.MenuObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface Observable {
    HashMap<Observable, List<Observer>> observers = new HashMap<>();

    default void registerObserver(Observer observer) {
        if (observers.get(this) == null)
            observers.put(this, new ArrayList<>());
        observers.get(this).add(observer);
        observer.observe(this);
    }

    default void notifyObservers() {
        if (observers.get(this) == null)
            observers.put(this, new ArrayList<>());
        for (Observer observer : observers.get(this)) {
            observer.observe(this);
        }
    }

    default void unregisterObserver(Observer observer){
        if (observers.get(this) == null)
            observers.put(this, new ArrayList<>());
        observers.get(this).remove(observer);
    }
}
