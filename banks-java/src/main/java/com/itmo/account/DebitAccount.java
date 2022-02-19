package com.itmo.account;

import com.itmo.common.Percent;
import com.itmo.tool.BanksException;

public class DebitAccount extends BaseAccount {
    public DebitAccount(
            boolean isTrustworthy, double transactionLimit, Percent interest, double currentBalance)
            throws BanksException {
        super(isTrustworthy, transactionLimit);
        setInterest(interest);
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
    public Percent getInterest() {
        return interest;
    }

    @Override
    public void setInterest(Percent interest) {
        this.interest = interest;
    }

    @Override
    public void chargeInterest() throws BanksException {
        addMoneyToBalance(getNotAccruedInterest());
        setNotAccruedInterest(0);
    }

    @Override
    public void topUpInterest() {
        notAccruedInterest += currentBalance * interest.getValue();
    }
}
