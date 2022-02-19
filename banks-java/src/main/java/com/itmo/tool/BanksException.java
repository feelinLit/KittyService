package com.itmo.tool;

public class BanksException extends RuntimeException {
    public BanksException() {}

    public BanksException(String message) {
        super(message);
    }
}
