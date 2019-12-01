package com.group0565.engine.interfaces;

/** An interface for an observer */
public interface Observer {
  /**
   * Observe an observable for notifications
   *
   * @param observable The notifying object
   */
  void observe(Observable observable);
}
