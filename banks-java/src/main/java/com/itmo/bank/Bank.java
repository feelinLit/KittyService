package com.itmo.bank;

import com.itmo.account.BaseAccount;
import com.itmo.client.DefaultClient;
import com.itmo.common.InterestRatesForDeposit;
import com.itmo.common.Percent;
import com.itmo.common.Range;
import com.itmo.tool.BanksException;

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
