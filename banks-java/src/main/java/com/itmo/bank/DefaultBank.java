package com.itmo.bank;

import com.itmo.account.BaseAccount;
import com.itmo.account.CreditAccount;
import com.itmo.account.DebitAccount;
import com.itmo.account.DepositAccount;
import com.itmo.client.DefaultClient;
import com.itmo.common.InterestRatesForDeposit;
import com.itmo.common.Percent;
import com.itmo.common.Range;
import com.itmo.tool.BanksException;

import java.util.ArrayList;
import java.util.List;

public class DefaultBank extends BaseBank {
    public DefaultBank(String name, BankConditions conditions) {
        super(name, conditions);
    }

    public void addClient(DefaultClient client) throws BanksException {
        if (client == null) throw new BanksException("Client can't be null");
        if (accounts.keySet().stream().anyMatch(c -> client.getPhoneNumber() == c.getPhoneNumber()))
            throw new BanksException("Adding client already exists in this bank: " + getName());

        accounts.put(client, new ArrayList<>());
    }

    public void addAccount(DefaultClient client, BaseAccount account) throws BanksException {
        if (client == null) throw new BanksException("Client can't be null");
        if (account == null) throw new BanksException("Account can't be null");

        if (accounts.keySet().stream().allMatch(c -> c.getPhoneNumber() != client.getPhoneNumber()))
            throw new BanksException("Bank doesn't contain given client");

        if (accounts.values().stream().flatMap(List::stream).filter(account::equals).findFirst().orElse(null) != null)
            throw new BanksException("Bank already contains this account");

        accounts.get(client).add(account);
        client.TrustworthinessRaiseEvent.subscribe(account);
    }

    public void changeInterestRateForDebit(Percent value) {
        conditions.setInterestRateForDebit(value);
        for (BaseAccount account : accounts.values().stream().flatMap(List::stream).toList()) {
            if (account instanceof DebitAccount debitAccount)
                debitAccount.setInterest(value);
        }
    }

    public void setInterestRateForDeposit(InterestRatesForDeposit interestRates) throws BanksException {
        conditions.setInterestRatesForDeposit(interestRates);
        if (interestRates == null) throw new BanksException("Interest rates can't be null");
        for (BaseAccount account : accounts.values().stream().flatMap(List::stream).toList()) {
            if (account instanceof DepositAccount)
                ((DepositAccount) account).setInterestRatesForDeposit(interestRates);
        }
    }

    public void changeInterestRateForDeposit(Range range, Percent interest) throws BanksException {
        conditions.getInterestRatesForDeposit().addInterestRate(range, interest);
    }

    public void changeInterestRateForCredit(Percent value) {
        conditions.setInterestRateForCredit(value);
        for (BaseAccount account : accounts.values().stream().flatMap(List::stream).toList()) {
            if (account instanceof CreditAccount creditAccount)
            creditAccount.setInterest(value);
        }
    }

    public void changeLimitForCredit(double value) {
        conditions.setLimitForCredit(value);
        for (BaseAccount account : accounts.values().stream().flatMap(List::stream).toList()) {
            if (account instanceof CreditAccount creditAccount)
            creditAccount.setCreditLimit(value);
        }
    }

    public DefaultClient getClient(int phoneNumber) throws BanksException {
        DefaultClient found = accounts.keySet().stream().filter(c -> c.getPhoneNumber() == phoneNumber).findFirst().orElse(null);
        if (found == null) {
            throw new BanksException(getName() + " doesn't contain the client with phone number {phoneNumber}");
        }
        return found;
    }

    public void changeLimitForUntrustworthy(double value) throws BanksException {
        conditions.setLimitForUntrustworthy(value);
        for (BaseAccount account : accounts.values().stream().flatMap(List::stream).toList()) {
            account.setTransactionLimit(value);
        }
    }

    public void subscribeClientToNotifications(DefaultClient client) throws BanksException {
        if (client == null) throw new BanksException("Subscribing client can't be null");
        if (!accounts.containsKey(client)) throw new BanksException("Subscriber isn't a client of this bank");

        conditions.ConditionChangeEvent.subscribe(client);
    }
}