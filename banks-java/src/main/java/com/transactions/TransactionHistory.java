package com.transactions;

import com.tools.BanksException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TransactionHistory {
    private final List<BaseTransaction> transactions = new ArrayList<>();

    public void push(BaseTransaction transaction) throws BanksException {
        if (transaction == null) throw new BanksException("Removing transaction is null");

        transactions.add(transaction);
    }

    public void remove(BaseTransaction transaction) throws BanksException {
        if (transaction == null) throw new BanksException("Removing transaction is null");

        transactions.remove(transaction);
    }

    public List<BaseTransaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }
}
