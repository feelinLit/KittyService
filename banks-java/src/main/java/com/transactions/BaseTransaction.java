package com.transactions;

import com.tools.BanksException;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public abstract class BaseTransaction {
    protected UUID Id = UUID.randomUUID();
    protected LocalDateTime DateTime = LocalDateTime.now();

    public UUID getId() {
        return Id;
    }

    public LocalDateTime getDateTime() {
        return DateTime;
    }

    public abstract void execute() throws BanksException;
    public abstract void cancel() throws BanksException;
}
