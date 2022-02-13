package com.common;

import com.sun.jdi.Value;
import com.tools.BanksException;

public class Percent {
    private double value;

    public Percent(double value) throws BanksException {
        if (value < 0 || value > 100) throw new BanksException("Incorrect percent value");
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}
