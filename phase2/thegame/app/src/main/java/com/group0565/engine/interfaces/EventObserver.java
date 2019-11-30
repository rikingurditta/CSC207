package com.group0565.engine.interfaces;

/** An interface for event observers */
public interface EventObserver {
  /**
   * Register to observable event
   *
   * @param observable The host observable
   * @param event The event to listen to
   */
  void observe(Observable observable, ObservationEvent event);
}
