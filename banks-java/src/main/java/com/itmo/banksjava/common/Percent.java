package com.itmo.banksjava.common;

import com.itmo.banksjava.tool.BanksException;

public class Percent {
    private final double value;

    public Percent(double value) throws BanksException {
        if (value < 0 || value > 100) throw new BanksException("Incorrect percent value");
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}
