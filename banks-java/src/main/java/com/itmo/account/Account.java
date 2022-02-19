package com.itmo.account;

import com.itmo.client.TrustworthinessRaiseEventListener;
import com.itmo.tool.BanksException;

public interface Account extends TrustworthinessRaiseEventListener, Cloneable {
  void chargeInterest() throws BanksException;

  void topUpInterest();

  boolean isTrustworthy();
}