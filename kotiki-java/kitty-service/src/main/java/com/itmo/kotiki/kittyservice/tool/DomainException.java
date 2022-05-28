package com.itmo.kotiki.kittyservice.tool;

public class DomainException extends RuntimeException {

    public DomainException() {
    }

    public DomainException(String message) {
        super(message);
    }
}
