package com.itmo.banksjava.account;

import com.itmo.banksjava.common.Percent;
import com.itmo.banksjava.tool.BanksException;

import java.util.UUID;

public abstract class BaseAccount implements Account {

    protected double transactionLimit = Double.MAX_VALUE;
    protected double currentBalance;
    protected double notAccruedInterest = 0;
    protected Percent interest;
    protected boolean isTrustworthy;
    protected UUID Id = UUID.randomUUID();

    protected BaseAccount(boolean isTrustworthy, double transactionLimit) throws BanksException {
        setTrustworthy(isTrustworthy);
        setTransactionLimit(transactionLimit);
    }

    public abstract double getCurrentBalance();

    public abstract void setCurrentBalance(double currentBalance) throws BanksException;

    public void addMoneyToBalance(double money) throws BanksException {
        setCurrentBalance(getCurrentBalance() + money);
    }

    public double getNotAccruedInterest() {
        return notAccruedInterest;
    }

    public void setNotAccruedInterest(double notAccruedInterest) {
        this.notAccruedInterest = notAccruedInterest;
    }

    public abstract Percent getInterest() throws BanksException;

    public abstract void setInterest(Percent interest);

    public double getTransactionLimit() {
        return isTrustworthy ? Double.MAX_VALUE : transactionLimit;
    }

    public void setTransactionLimit(double transactionLimit) throws BanksException {
        if (transactionLimit < 0) throw new BanksException("Transaction limit can't be negative");
        this.transactionLimit = transactionLimit;
    }

    public UUID getId() {
        return Id;
    }

    public abstract void chargeInterest() throws BanksException;

    public abstract void topUpInterest();

    @Override
    public void onTrustworthinessRaised() {
        isTrustworthy = true;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean isTrustworthy() {
        return isTrustworthy;
    }

    protected void setTrustworthy(boolean trustworthy) {
        isTrustworthy = trustworthy;
    }
}
