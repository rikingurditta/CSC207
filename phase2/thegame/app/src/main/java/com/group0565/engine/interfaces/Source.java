package com.group0565.engine.interfaces;

/** Functional Interface To wrap a value */
public interface Source<T> {
  /**
   * Get the underlying value
   *
   * @return The value
   */
  T getValue();
}
