package com.accounts;

import com.common.InterestRatesForDeposit;
import com.common.Percent;
import com.tools.BanksException;

public class DepositAccount extends BaseAccount {
    private InterestRatesForDeposit interestRatesForDeposit;

    public DepositAccount(
            boolean isTrustworthy,
            double transactionLimit,
            InterestRatesForDeposit interestRatesForDeposit,
            double currentBalance)
            throws BanksException {
        super(isTrustworthy, transactionLimit);
        setInterestRatesForDeposit(interestRatesForDeposit);
        setCurrentBalance(currentBalance);
    }

    @Override
    public double getCurrentBalance() {
        return currentBalance;
    }

    @Override
    public void setCurrentBalance(double currentBalance) throws BanksException {
        if (this.currentBalance - currentBalance > this.transactionLimit)
            throw new BanksException("Transaction exceeds limit");
        if (currentBalance < 0) throw new BanksException("Balance of debit account can't be under 0");
        this.currentBalance = currentBalance;
    }

    @Override
    public Percent getInterest() throws BanksException {
        return interestRatesForDeposit.getInterestRateForCurrentBalance(currentBalance);
    }

    @Override
    public void setInterest(Percent interest) {}

    @Override
    public void chargeInterest() throws BanksException {
        addMoneyToBalance(getNotAccruedInterest());
        setNotAccruedInterest(0);
    }

    @Override
    public void topUpInterest() {
        notAccruedInterest += currentBalance * interest.getValue();
    }

    public InterestRatesForDeposit getInterestRatesForDeposit() {
        return interestRatesForDeposit;
    }

    public void setInterestRatesForDeposit(InterestRatesForDeposit interestRatesForDeposit) {
        this.interestRatesForDeposit = interestRatesForDeposit;
    }
}
