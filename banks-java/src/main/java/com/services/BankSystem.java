package com.services;

import com.accounts.*;
import com.banks.BankConditions;
import com.banks.BaseBank;
import com.banks.DefaultBank;
import com.clients.BaseClient;
import com.common.InterestRatesForDeposit;
import com.common.Percent;
import com.common.Range;
import com.tools.BanksException;
import com.transactions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class BankSystem {
    private final List<BaseBank> banks = new ArrayList<>();
    private final List<BaseClient> clients = new ArrayList<>();
    private final List<BaseAccount> accounts = new ArrayList<>();
    private final TransactionHistory transactionsHistory = new TransactionHistory();

    public  List<BaseBank> getBanks() { return Collections.unmodifiableList(banks); }
    public  List<BaseClient> getClients() { return Collections.unmodifiableList(clients); }
    public  List<BaseAccount> getAccounts() { return Collections.unmodifiableList(accounts); }


    public void addBank(String name, BankConditions bankConditions) throws BanksException {
        if (bankConditions == null) throw new BanksException("Bank Conditions can't be null");
        if (name.isEmpty()) throw new BanksException("Bank name can't be null or empty");
        
        if (this.banks.stream().anyMatch(bank -> bank.getName().equals(name))) {
            throw new BanksException("Bank with given name already exists");
        }

        banks.add(new DefaultBank(name, bankConditions));
    }

    public void addClientToBank(BaseClient client, String bankName) throws BanksException {
        if (client == null) throw new BanksException("Adding client can't be null");
        if (bankName.isEmpty()) throw new BanksException("Bank name can't be null or empty");

        BaseBank bank = getBank(bankName);
        bank.addClient(client);
        if (!this.clients.contains(client))
            this.clients.add(client);
    }

    public BaseAccount createAccount(AccountType accountType, String bankName, int phoneNumber) throws BanksException {
        BaseClient client = getClient(bankName, phoneNumber);
        BaseBank bank = getBank(bankName);

        BaseAccount account = switch (accountType) {
            case Debit ->new DebitAccount(
                    client.isTrustworthy(),
                    bank.getConditions().getLimitForUntrustworthy(),
                    bank.getConditions().getInterestRateForDebit(),
                    0);
            case Credit -> new CreditAccount(
                    client.isTrustworthy(),
                    bank.getConditions().getLimitForUntrustworthy(),
                    bank.getConditions().getInterestRateForCredit(),
                    bank.getConditions().getLimitForCredit(),
                    0);
            case Deposit -> new DepositAccount(
                    client.isTrustworthy(),
                    bank.getConditions().getLimitForUntrustworthy(),
                    bank.getConditions().getInterestRatesForDeposit(),
                    0);
        };
        bank.addAccount(client, account);
        if (!this.accounts.contains(account))
            this.accounts.add(account);

        return account;
    }

    public UUID makeTransferTransaction(UUID senderId, UUID receiverId, double value) throws BanksException {
        BaseAccount sender = getAccount(senderId);
        BaseAccount receiver = getAccount(receiverId);

        var transaction = new TransferTransaction(sender, receiver, value);
        transaction.execute();
        transactionsHistory.push(transaction);
        return transaction.getId();
    }

    public UUID makeReplenishTransaction(UUID accountId, double value) throws BanksException {
        BaseAccount account = getAccount(accountId);

        var transaction = new ReplenishTransaction(account, value);
        transaction.execute();
        transactionsHistory.push(transaction);
        return transaction.getId();
    }

    public UUID makeWithdrawTransaction(UUID accountId, double value) throws BanksException {
        BaseAccount account = getAccount(accountId);

        var transaction = new WithdrawTransaction(account, value);
        transaction.execute();
        transactionsHistory.push(transaction);
        return transaction.getId();
    }

    public void cancelTransaction(UUID id) throws BanksException {
        BaseTransaction foundTransaction = transactionsHistory.getTransactions().stream().filter(t -> t.getId() == id).findAny().orElse(null);
        if (foundTransaction == null)
            throw new BanksException("No such transaction with given id: " + id);
        foundTransaction.cancel();
        transactionsHistory.remove(foundTransaction);
    }

    public void chargeInterests() throws BanksException {
        for (BaseAccount account : this.accounts)
            account.chargeInterest();
    }

    public void topUpInterests() {
        for (BaseAccount account : this.accounts)
            account.topUpInterest();
    }

    public double getFutureAccountBalance(UUID id, int dateDifference) throws BanksException, CloneNotSupportedException {
        BaseAccount account = getAccount(id);
        if (!this.accounts.contains(account))
            throw new BanksException("Account doesn't exist");

        var accountClone = (BaseAccount) account.clone();

        for (int i = 0; i < dateDifference; i++)
            accountClone.topUpInterest();

        accountClone.chargeInterest();
        return accountClone.getCurrentBalance();
    }

    public void subscribeClientToNotifications(String bankName, int phoneNumber) throws BanksException {
        BaseClient client = getClient(bankName, phoneNumber);
        if (!this.clients.contains(client))
        {
            throw new BanksException("Subscribing client doesn't exist");
        }

        getBank(bankName).subscribeClientToNotifications(client);
    }

    public void changeInterestRateForDebit(String bankName, Percent value) throws BanksException {
        getBank(bankName).changeInterestRateForDebit(value);
    }

    public void setInterestRatesForDeposit(String bankName, InterestRatesForDeposit interestRates) throws BanksException {
        getBank(bankName).setInterestRateForDeposit(interestRates);
    }

    public void changeInterestRateForDeposit(String bankName, Range range, Percent interest) throws BanksException {
        getBank(bankName).changeInterestRateForDeposit(range, interest);
    }

    public void changeInterestRateForCredit(String bankName, Percent value) throws BanksException {
        getBank(bankName).changeInterestRateForCredit(value);
    }

    public void changeLimitForUntrustworthy(String bankName, double value) throws BanksException {
        getBank(bankName).changeLimitForUntrustworthy(value);
    }

    public void changeLimitForCredit(String bankName, double value) throws BanksException {
        getBank(bankName).changeLimitForCredit(value);
    }

    public BankConditions getBankConditions(String bankName) throws BanksException {
        return new BankConditions(getBank(bankName).getConditions());
    }

    public BaseClient getClient(String bankName, int phoneNumber) throws BanksException {
        return getBank(bankName).getClient(phoneNumber);
    }

    private BaseBank getBank(String bankName) throws BanksException {
        var foundBank = this.banks.stream().filter(bank -> bank.getName().equals(bankName)).findAny().orElse(null);
        if (foundBank == null)
            throw new BanksException("Bank with name " + bankName + " doesn't exist");

        return foundBank;
    }

    private BaseAccount getAccount(UUID id) throws BanksException {
        var foundAccount = this.accounts.stream().filter(acc -> acc.getId() == id).findAny().orElse(null);
        if (foundAccount == null)
            throw new BanksException("Account with given id doesn't exist");

        return foundAccount;
    }
}
