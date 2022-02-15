package com.banks;

import java.util.ArrayList;
import java.util.List;

public class ConditionChangeEvent {
    private final List<ConditionChangeEventListener> listeners = new ArrayList<>();

    public void subscribe(ConditionChangeEventListener listener) {
        listeners.add(listener);
    }

    public void unsubscribe(ConditionChangeEventListener listener) {
        listeners.remove(listener);
    }

    public void invoke(String message) {
        for (var listener : listeners) {
            listener.onConditionChanged(message);
        }
    }
}
