package com.itmo.banksjava.account;

import com.itmo.banksjava.common.Percent;
import com.itmo.banksjava.tool.BanksException;

public class CreditAccount extends BaseAccount {

    private double creditLimit;

    public CreditAccount(
            boolean isTrustworthy,
            double transactionLimit,
            Percent interest,
            double creditLimit,
            double currentBalance)
            throws BanksException {
        super(isTrustworthy, transactionLimit);
        setInterest(interest);
        setCreditLimit(creditLimit);
        setCurrentBalance(currentBalance);
    }

    @Override
    public double getCurrentBalance() {
        return this.currentBalance;
    }

    @Override
    public void setCurrentBalance(double currentBalance) throws BanksException {
        if (this.currentBalance - currentBalance > getTransactionLimit())
            throw new BanksException("Transaction exceeds limit");
        if (currentBalance <= -getCreditLimit())
            throw new BanksException("Credit account balance can't be under credit limit");
        this.currentBalance = currentBalance;
    }

    @Override
    public Percent getInterest() {
        return this.interest;
    }

    @Override
    public void setInterest(Percent interest) {
        this.interest = interest;
    }

    @Override
    public void chargeInterest() throws BanksException {
        addMoneyToBalance(-getNotAccruedInterest());
        setNotAccruedInterest(0);
    }

    @Override
    public void topUpInterest() {
        if (getCurrentBalance() < 0)
            setNotAccruedInterest(
                    getNotAccruedInterest() + getCurrentBalance() * getInterest().getValue());
    }

    public double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(double creditLimit) {
        this.creditLimit = creditLimit;
    }
}
