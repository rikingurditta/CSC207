package com.group0565.engine.interfaces;

public class ObservationEvent <T> {
    private String msg;
    private T payload = null;

    public ObservationEvent(String msg) {
        this.msg = msg;
    }

    public ObservationEvent(String msg, T payload) {
        this.msg = msg;
        this.payload = payload;
    }

    public String getMsg() {
        return msg;
    }

    public T getPayload() {
        return payload;
    }

    public boolean isEvent(String event) {
        return getMsg().equals(event);
    }
}
