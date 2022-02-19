package com.itmo.banksjava.account;

import com.itmo.banksjava.client.TrustworthinessRaiseEventListener;
import com.itmo.banksjava.tool.BanksException;

public interface Account extends TrustworthinessRaiseEventListener, Cloneable {
    void chargeInterest() throws BanksException;

    void topUpInterest();

    boolean isTrustworthy();
}
