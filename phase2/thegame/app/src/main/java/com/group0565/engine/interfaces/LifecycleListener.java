package com.group0565.engine.interfaces;

/** An interface for a LifeCycle aware object */
public interface LifecycleListener {

  /** A method called on LifeCycleOwner init */
  default void init() {}

  /** A method called on LifeCycleOwner stop */
  default void stop() {}

  /** A method called on LifeCycleOwner pause */
  default void pause() {}

  /** A method called on LifeCycleOwner resume */
  default void resume() {}
}
