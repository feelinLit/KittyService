package com.banks;

import com.accounts.BaseAccount;
import com.clients.BaseClient;
import com.common.InterestRatesForDeposit;
import com.common.Percent;
import com.common.Range;
import com.tools.BanksException;

public interface Bank {
    void addClient(BaseClient client) throws BanksException;

    void addAccount(BaseClient client, BaseAccount account) throws BanksException;

    void subscribeClientToNotifications(BaseClient client) throws BanksException;

    void changeInterestRateForDebit(Percent value);

    void setInterestRateForDeposit(InterestRatesForDeposit interestRates) throws BanksException;

    void changeInterestRateForDeposit(Range range, Percent percent) throws BanksException;

    void changeInterestRateForCredit(Percent value);

    void changeLimitForUntrustworthy(double value) throws BanksException;

    void changeLimitForCredit(double value);

    BaseClient getClient(int phoneNumber) throws BanksException;
}
