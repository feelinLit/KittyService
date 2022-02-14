package com.clients;

import com.banks.ConditionChangeEventListener;

public interface Client extends ConditionChangeEventListener {
    boolean isTrustworthy();
}
