package com.banks;

import com.accounts.BaseAccount;
import com.clients.DefaultClient;
import com.common.InterestRatesForDeposit;
import com.common.Percent;
import com.common.Range;
import com.tools.BanksException;

public interface Bank {
    void addClient(DefaultClient client) throws BanksException;

    void addAccount(DefaultClient client, BaseAccount account) throws BanksException;

    void subscribeClientToNotifications(DefaultClient client) throws BanksException;

    void changeInterestRateForDebit(Percent value);

    void setInterestRateForDeposit(InterestRatesForDeposit interestRates) throws BanksException;

    void changeInterestRateForDeposit(Range range, Percent percent) throws BanksException;

    void changeInterestRateForCredit(Percent value);

    void changeLimitForUntrustworthy(double value) throws BanksException;

    void changeLimitForCredit(double value);

    DefaultClient getClient(int phoneNumber) throws BanksException;
}
