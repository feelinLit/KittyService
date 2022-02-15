package com.accounts;

import com.clients.TrustworthinessRaiseEventListener;
import com.clients.TrustworthinessRaiseEventListener;
import com.tools.BanksException;

public interface Account extends TrustworthinessRaiseEventListener, Cloneable {
  void chargeInterest() throws BanksException;

  void topUpInterest();

  boolean isTrustworthy();
}
