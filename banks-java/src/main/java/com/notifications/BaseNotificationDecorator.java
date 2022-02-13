package com.notifications;

public abstract class BaseNotificationDecorator implements Notifier {
    private Notifier wrappedNotifier;

    public BaseNotificationDecorator(Notifier wrapped) {
        this.wrappedNotifier = wrapped;
    }

    @Override
    public void send(String message) {
        wrappedNotifier.send(message);
    }
}
