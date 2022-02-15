package com.banks;

import com.common.*;

public class BankConditions {
    public final ConditionChangeEvent ConditionChangeEvent = new ConditionChangeEvent();
    private double limitForUntrustworthy;
    private double limitForCredit;
    private Percent interestRateForDebit;
    private InterestRatesForDeposit interestRatesForDeposit;
    private Percent interestRateForCredit;

    public BankConditions(
            double limitForUntrustworthy,
            double limitForCredit,
            Percent interestRateForDebit,
            InterestRatesForDeposit interestRatesForDeposit,
            Percent interestRateForCredit) {
        this.limitForUntrustworthy = limitForUntrustworthy;
        this.limitForCredit = limitForCredit;
        this.interestRateForDebit = interestRateForDebit;
        this.interestRatesForDeposit = interestRatesForDeposit;
        this.interestRateForCredit = interestRateForCredit;
    }

    public BankConditions(BankConditions bankConditions) {
        limitForUntrustworthy = bankConditions.limitForUntrustworthy;
        limitForCredit = bankConditions.limitForCredit;
        interestRateForDebit = bankConditions.interestRateForDebit;
        interestRateForCredit = bankConditions.interestRateForCredit;
        interestRatesForDeposit = bankConditions.interestRatesForDeposit;
    }

    public double getLimitForUntrustworthy() {
        return limitForUntrustworthy;
    }

    public void setLimitForUntrustworthy(double limitForUntrustworthy) {
        this.limitForUntrustworthy = limitForUntrustworthy;
        ConditionChangeEvent.invoke(
                "Interest Rate For Untrustworthy has been changed to " + limitForUntrustworthy);
    }

    public double getLimitForCredit() {
        return limitForCredit;
    }

    public void setLimitForCredit(double limitForCredit) {
        this.limitForCredit = limitForCredit;
        ConditionChangeEvent.invoke("Limit for Credit has been changed to " + limitForCredit);
    }

    public Percent getInterestRateForDebit() {
        return interestRateForDebit;
    }

    public void setInterestRateForDebit(Percent interestRateForDebit) {
        this.interestRateForDebit = interestRateForDebit;
        ConditionChangeEvent.invoke(
                "Interest Rate For Debit has been changed to " + interestRateForDebit);
    }

    public InterestRatesForDeposit getInterestRatesForDeposit() {
        return interestRatesForDeposit;
    }

    public void setInterestRatesForDeposit(InterestRatesForDeposit interestRatesForDeposit) {
        this.interestRatesForDeposit = interestRatesForDeposit;
        ConditionChangeEvent.invoke(
                "Interest Rates For Deposit has been changed to " + interestRatesForDeposit);
    }

    public Percent getInterestRateForCredit() {
        return interestRateForCredit;
    }

    public void setInterestRateForCredit(Percent interestRateForCredit) {
        this.interestRateForCredit = interestRateForCredit;
        ConditionChangeEvent.invoke(
                "Interest Rate For Credit has been changed to " + interestRateForCredit);
    }
}
