package com.group0565.engine.interfaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/** An interface representation for an Observable object */
public interface Observable {
  /** Registration message */
  String REGISTERED_EVENT = "Observer Registered";
  /** A map of observables to their observers */
  HashMap<Observable, List<Observer>> observers = new HashMap<>();
  /** A map of observables to their event observers */
  HashMap<Observable, List<EventObserver>> eventObservers = new HashMap<>();

  /**
   * Register an observer to this observable, add this observable to map if not already there
   *
   * @param observer An observer object to add to observers map
   */
  default void registerObserver(Observer observer) {
    observers.putIfAbsent(this, new ArrayList<>());
    observers.get(this).add(observer);
    observer.observe(this);
  }

  /**
   * Register an event observer to this observable, add this observable to map if not already there
   *
   * @param eventObserver An observer object to add to event observers map
   */
  default void registerObserver(EventObserver eventObserver) {
    eventObservers.putIfAbsent(this, new ArrayList<>());
    eventObservers.get(this).add(eventObserver);
    eventObserver.observe(this, new ObservationEvent(REGISTERED_EVENT));
  }

  /** Notify observers of a change, add this observable to map if not already there */
  default void notifyObservers() {
    observers.putIfAbsent(this, new ArrayList<>());
    for (Observer observer : observers.get(this)) {
      observer.observe(this);
    }
  }

  /**
   * Notify observers of event, add this observable to map if not already there
   *
   * @param event The occurring observation event
   */
  default void notifyObservers(ObservationEvent event) {
    observers.putIfAbsent(this, new ArrayList<>());
    eventObservers.putIfAbsent(this, new ArrayList<>());
    for (Observer observer : observers.get(this)) {
      observer.observe(this);
    }
    for (EventObserver eventObserver : eventObservers.get(this)) {
      eventObserver.observe(this, event);
    }
  }

  /**
   * Unregister an observer from the map
   *
   * @param observer The observer to remove
   */
  default void unregisterObserver(Observer observer) {
    observers.putIfAbsent(this, new ArrayList<>());
    observers.get(this).remove(observer);
  }

  /**
   * Unregister an event observer from the map
   *
   * @param observer The observer to remove
   */
  default void unregisterObserver(EventObserver observer) {
    eventObservers.putIfAbsent(this, new ArrayList<>());
    eventObservers.get(this).remove(observer);
  }
}
