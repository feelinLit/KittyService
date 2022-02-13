package com.notifications;

public class ConsoleNotificationDecorator extends BaseNotificationDecorator {

    public ConsoleNotificationDecorator(Notifier wrapped) {
        super(wrapped);
    }

    @Override
    public void send(String message) {
        System.out.printf(message);
    }
}
