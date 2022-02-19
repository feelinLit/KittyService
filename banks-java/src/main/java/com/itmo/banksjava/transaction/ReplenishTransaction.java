package com.itmo.banksjava.transaction;

import com.itmo.banksjava.account.BaseAccount;
import com.itmo.banksjava.tool.BanksException;

public class ReplenishTransaction extends BaseTransaction {
    private final BaseAccount account;
    private final double transactionValue;

    public ReplenishTransaction(BaseAccount account, double transactionValue) {
        this.account = account;
        this.transactionValue = transactionValue;
    }

    @Override
    public void execute() throws BanksException {
        account.addMoneyToBalance(transactionValue);
    }

    @Override
    public void cancel() throws BanksException {
        account.addMoneyToBalance(-transactionValue);
    }
}
