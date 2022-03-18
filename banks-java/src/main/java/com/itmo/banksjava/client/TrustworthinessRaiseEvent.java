package com.itmo.banksjava.client;

import java.util.ArrayList;
import java.util.List;

public class TrustworthinessRaiseEvent {
    private final List<TrustworthinessRaiseEventListener> listeners = new ArrayList<>();

    public void subscribe(TrustworthinessRaiseEventListener listener) {
        listeners.add(listener);
    }

    public void unsubscribe(TrustworthinessRaiseEventListener listener) {
        listeners.remove(listener);
    }

    public void invoke() {
        for (var listener : listeners) {
            listener.onTrustworthinessRaised();
        }
    }
}