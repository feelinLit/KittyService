package com.tests;

import com.itmo.banksjava.account.AccountType;
import com.itmo.banksjava.bank.BankConditions;
import com.itmo.banksjava.client.ClientBuilder;
import com.itmo.banksjava.common.InterestRatesForDeposit;
import com.itmo.banksjava.common.Percent;
import com.itmo.banksjava.common.Range;
import com.itmo.banksjava.service.BankSystem;
import com.itmo.banksjava.tool.BanksException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class BanksJavaTests {
    private BankSystem bankSystem;

    private static Stream<Arguments> makeTransaction_BalanceChanged() {
        return Stream.of(
                Arguments.of(0, 0, 0),
                Arguments.of(30, 0, 0),
                Arguments.of(30, 5, 0),
                Arguments.of(123, 41, 20));
    }

    private static Stream<Arguments> MakeTransaction_CancelTransaction_BalanceNotChanged() {
        return Stream.of(
                Arguments.of(0, 0, 0, 0, 0),
                Arguments.of(30, 30, 10, 40, 20),
                Arguments.of(20, 0, 0, 20, 20));
    }

    @BeforeEach
    public void setUp() throws BanksException {
        bankSystem = new BankSystem();

        var interestRatesForDeposit = new InterestRatesForDeposit();
        interestRatesForDeposit.addInterestRate(new Range(0, 20), new Percent(0));
        interestRatesForDeposit.addInterestRate(new Range(40, 100), new Percent(3.65));

        var bankConditions =
                new BankConditions(50, 100, new Percent(5), interestRatesForDeposit, new Percent(10));

        bankSystem.addBank("BankName", bankConditions);
    }

    @Test
    public void addUntrustworthyAccount_MakeItsClientTrustworthy_LimitHasChanged()
            throws BanksException {
        final int clientPhoneNumber = 1337;
        BankConditions bankConditions = bankSystem.getBankConditions("BankName");
        var builder = new ClientBuilder();
        builder.setName("Name");
        builder.setSurname("Surname");
        builder.SetPhoneNumber(clientPhoneNumber);
        var client = builder.RetrieveResult();
        bankSystem.addClientToBank(client, "BankName");
        var account = bankSystem.createAccount(AccountType.Debit, "BankName", client.getPhoneNumber());

        Assertions.assertFalse(client.isTrustworthy());
        Assertions.assertFalse(account.isTrustworthy());
        Assertions.assertEquals(
                bankConditions.getLimitForUntrustworthy(), account.getTransactionLimit());

        client.setAddress("Address");
        client.setPassport("Passport");

        Assertions.assertTrue(client.isTrustworthy());
        Assertions.assertTrue(account.isTrustworthy());
        Assertions.assertEquals(Double.MAX_VALUE, account.getTransactionLimit());
    }

    @ParameterizedTest
    @MethodSource
    public void makeTransaction_BalanceChanged(
            double senderAmountToReplenish, double amountToTransfer, double receiverAmountToWithdraw)
            throws BanksException {
        var builder = new ClientBuilder();
        builder.setName("Name");
        builder.setSurname("Surname");
        builder.SetPhoneNumber(991);
        builder.setAddress("Address");
        builder.SetPassport("Passport");
        var client = builder.RetrieveResult();
        bankSystem.addClientToBank(client, "BankName");
        var sender = bankSystem.createAccount(AccountType.Debit, "BankName", client.getPhoneNumber());
        var receiver = bankSystem.createAccount(AccountType.Debit, "BankName", client.getPhoneNumber());

        bankSystem.makeReplenishTransaction(sender.getId(), senderAmountToReplenish);
        bankSystem.makeTransferTransaction(sender.getId(), receiver.getId(), amountToTransfer);
        bankSystem.makeWithdrawTransaction(receiver.getId(), receiverAmountToWithdraw);

        Assertions.assertEquals(senderAmountToReplenish - amountToTransfer, sender.getCurrentBalance());
        Assertions.assertEquals(
                amountToTransfer - receiverAmountToWithdraw, receiver.getCurrentBalance());
    }

    @ParameterizedTest
    @MethodSource
    public void MakeTransaction_CancelTransaction_BalanceNotChanged(
            double senderStartAmount,
            double receiverStartAmount,
            double senderAmountToReplenish,
            double amountToTransfer,
            double receiverAmountToWithdraw)
            throws BanksException {
        var builder = new ClientBuilder();
        builder.setName("Name");
        builder.setSurname("Surname");
        builder.SetPhoneNumber(991);
        builder.setAddress("Address");
        builder.SetPassport("Passport");
        var client = builder.RetrieveResult();
        bankSystem.addClientToBank(client, "BankName");
        var sender = bankSystem.createAccount(AccountType.Debit, "BankName", client.getPhoneNumber());
        var receiver = bankSystem.createAccount(AccountType.Debit, "BankName", client.getPhoneNumber());

        bankSystem.makeReplenishTransaction(sender.getId(), senderStartAmount);
        bankSystem.makeReplenishTransaction(receiver.getId(), receiverStartAmount);

        var replenishTransactionId =
                bankSystem.makeReplenishTransaction(sender.getId(), senderAmountToReplenish);
        var transferTransactionId =
                bankSystem.makeTransferTransaction(sender.getId(), receiver.getId(), amountToTransfer);
        var withdrawTransactionId =
                bankSystem.makeWithdrawTransaction(receiver.getId(), receiverAmountToWithdraw);

        bankSystem.cancelTransaction(withdrawTransactionId);
        bankSystem.cancelTransaction(transferTransactionId);
        bankSystem.cancelTransaction(replenishTransactionId);

        Assertions.assertEquals(senderStartAmount, sender.getCurrentBalance());
        Assertions.assertEquals(receiverStartAmount, receiver.getCurrentBalance());
    }
}
