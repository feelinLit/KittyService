package com.itmo.client;

import com.itmo.bank.ConditionChangeEventListener;

public interface Client extends ConditionChangeEventListener {
    boolean isTrustworthy();
}
