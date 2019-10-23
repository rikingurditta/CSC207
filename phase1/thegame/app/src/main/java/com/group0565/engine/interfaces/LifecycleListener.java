package com.group0565.engine.interfaces;

public interface LifecycleListener {

    default void init() {
    }

    default void stop() {
    }

    default void pause() {
    }

    default void wake() {
    }
}
