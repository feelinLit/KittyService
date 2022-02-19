package com.itmo.transaction;

import com.itmo.account.BaseAccount;
import com.itmo.tool.BanksException;

public class TransferTransaction extends BaseTransaction {
    private final BaseAccount accountSender;
    private final BaseAccount accountReceiver;
    private final double transactionValue;

    public TransferTransaction(
            BaseAccount accountSender, BaseAccount accountReceiver, double transactionValue) {
        this.accountSender = accountSender;
        this.accountReceiver = accountReceiver;
        this.transactionValue = transactionValue;
    }

    @Override
    public void execute() throws BanksException {
        accountSender.addMoneyToBalance(-transactionValue);
        accountReceiver.addMoneyToBalance(transactionValue);
    }

    @Override
    public void cancel() throws BanksException {
        accountSender.addMoneyToBalance(transactionValue);
        accountReceiver.addMoneyToBalance(-transactionValue);
    }
}
