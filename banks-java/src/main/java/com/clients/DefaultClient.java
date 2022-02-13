package com.clients;

import main.notifications.Notifier;

public class DefaultClient extends BaseClient {
    @Override
    public void onConditionChanged(String message) {
        notifier.send(message);
    }


    private Notifier notifier;

    ov
}
