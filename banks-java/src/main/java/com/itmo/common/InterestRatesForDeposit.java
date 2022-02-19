package com.itmo.common;

import com.itmo.tool.BanksException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class InterestRatesForDeposit {
    private final Map<Range, Percent> content = new HashMap<>();

    public Map<Range, Percent> getContent() {
        return Collections.unmodifiableMap(content);
    }

    public void addInterestRate(Range range, Percent interest) throws BanksException {
        for (var key : content.keySet()) {
            if (key.intersects(range))
                throw new BanksException("Adding range intersects with existing one");
        }

        content.put(range, interest);
    }

    public void changeInterestRate(Range existingRange, Percent interest) throws BanksException {
        if (!content.containsKey(existingRange))
            throw new BanksException("This interest rates doesn't have given range");

        content.replace(existingRange, interest);
    }

    public Percent getInterestRateForCurrentBalance(double balance) throws BanksException {
        var foundRange =
                content.keySet().stream().filter(range1 -> range1.contains(balance)).findAny().orElse(null);
        if (foundRange == null) throw new BanksException("No interest for balance: " + balance);

        return content.get(foundRange);
    }
}
