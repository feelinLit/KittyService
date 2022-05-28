package com.itmo.kotiki.personservice.tool;

public class DomainException extends RuntimeException {

    public DomainException() {
    }

    public DomainException(String message) {
        super(message);
    }
}
