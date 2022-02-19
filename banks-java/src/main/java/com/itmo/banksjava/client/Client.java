package com.itmo.banksjava.client;

import com.itmo.banksjava.bank.ConditionChangeEventListener;

public interface Client extends ConditionChangeEventListener {
    boolean isTrustworthy();
}
