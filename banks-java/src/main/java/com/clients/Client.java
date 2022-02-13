package com.clients;

import com.banks.ConditionChangeEventListener;

public interface Client extends ConditionChangeEventListener {
    void notify(Object sender, String message);
}
