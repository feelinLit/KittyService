package com.transactions;

import com.accounts.BaseAccount;
import com.tools.BanksException;

import java.util.UUID;

public class WithdrawTransaction extends BaseTransaction {
    private final BaseAccount account;
    private final double transactionValue;

    public WithdrawTransaction(BaseAccount account, double transactionValue) {
        this.account = account;
        this.transactionValue = transactionValue;
    }

    @Override
    public void execute() throws BanksException {
        account.addMoneyToBalance(-transactionValue);
    }

    @Override
    public void cancel() throws BanksException {
        account.addMoneyToBalance(transactionValue);
    }
}
