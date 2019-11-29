package com.group0565.engine.interfaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface Observable {
    String REGISTERED_EVENT = "Observer Registered";
    HashMap<Observable, List<Observer>> observers = new HashMap<>();
    HashMap<Observable, List<EventObserver>> eventObservers = new HashMap<>();

    default void registerObserver(Observer observer) {
        if (observers.get(this) == null)
            observers.put(this, new ArrayList<>());
        observers.get(this).add(observer);
        observer.observe(this);
    }

    default void registerObserver(EventObserver eventObserver) {
        if (eventObservers.get(this) == null)
            eventObservers.put(this, new ArrayList<>());
        eventObservers.get(this).add(eventObserver);
        eventObserver.observe(this, new ObservationEvent(REGISTERED_EVENT));
    }

    default void notifyObservers() {
        if (observers.get(this) == null)
            observers.put(this, new ArrayList<>());
        for (Observer observer : observers.get(this)) {
            observer.observe(this);
        }
    }

    default void notifyObservers(ObservationEvent event) {
        if (observers.get(this) == null)
            observers.put(this, new ArrayList<>());
        if (eventObservers.get(this) == null)
            eventObservers.put(this, new ArrayList<>());
        for (Observer observer : observers.get(this)) {
            observer.observe(this);
        }
        for (EventObserver eventObserver : eventObservers.get(this)){
            eventObserver.observe(this, event);
        }
    }

    default void unregisterObserver(Observer observer){
        if (observers.get(this) == null)
            observers.put(this, new ArrayList<>());
        observers.get(this).remove(observer);
    }

    default void unregisterObserver(EventObserver observer){
        if (eventObservers.get(this) == null)
            eventObservers.put(this, new ArrayList<>());
        eventObservers.get(this).remove(observer);
    }
}
