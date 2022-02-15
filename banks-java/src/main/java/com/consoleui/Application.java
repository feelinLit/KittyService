package com.consoleui;

import com.accounts.AccountType;
import com.banks.BankConditions;
import com.clients.ClientBuilder;
import com.clients.DefaultClient;
import com.common.InterestRatesForDeposit;
import com.common.Percent;
import com.common.Range;
import com.services.BankSystem;
import com.tools.BanksException;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Application {
    private final BankSystem bankSystem = new BankSystem();
    private final Map<String, Runnable> commands;
    private final Scanner scanner = new Scanner(System.in);

    public Application()
    {
        commands = Stream.of(new Object[][] {
            { "!ShowCommands", (Runnable) this::showCommands},
            { "!CreateBank", (Runnable) this::createBank},
            { "!ChangeBankConditions", (Runnable) this::changeBankConditions},
            { "!AddClientToBank", (Runnable) this::addClientToBank},
            { "!UpdateClient", (Runnable) this::updateClient},
            { "!AddAccountToClient", (Runnable) this::addAccountToClient},
            { "!MakeTransaction", (Runnable) this::makeTransaction},
            { "!CancelTransaction", (Runnable) this::cancelTransaction},
            { "!LookInFuture", (Runnable) this::lookInFuture},
            { "!ShowBankList", (Runnable) this::showBankList},
            { "!ShowBankInfo", (Runnable) this::showBankInfo},
            { "!ShowTransactionHistory", (Runnable) this::showTransactionHistory},
        }).collect(Collectors.toMap(data -> (String) data[0], data -> (Runnable) data[1]));;
    }

    public void start() {
        System.out.println("Cringe Console Interface v1.2 is pleased to serve you! (All rights reserved)");
        showCommands();
        String command = scanner.nextLine();
        while (!Objects.equals(command, "!Exit")) {
            while (command.isEmpty()) {
                System.out.println("-Enter correct command, dummy.");
                command = scanner.nextLine();
            }

            while (!commands.containsKey(command)) {
                System.out.println("-Incorrect command: " + command);
                command = scanner.nextLine();
            }

            commands.get(command).run();
            command = scanner.nextLine();
        }
        System.out.println("""
                 ~~~
                  ~~
                 _||_______________________
                /__________________________\\
                | Bye, have a great time!  |
                |__________________________|""");
    }

    private void showCommands() {
        System.out.println("==============================");

        for (var commandName : commands.keySet())
            System.out.println(commandName);

        System.out.println("!Exit");
        System.out.println("==============================");
    }

    private void createBank()
    {
        System.out.print("-Enter Bank name: ");
        String bankName = scanner.nextLine();

        while (bankName.isEmpty()) {
            System.out.println("-Very smart move, enter it again: ");
            bankName = scanner.nextLine();
        }

        System.out.println("---Set Bank Conditions--");
        Double inputValue = null;

        System.out.println("-Interest Rate for Debit accounts: ");
        while (inputValue == null) inputValue = parseDoubleOrNull(scanner.nextLine());
        Percent interestRateForDebit = null;
        try {
            interestRateForDebit = new Percent(inputValue);
        } catch (BanksException e) {
            e.printStackTrace();
            return;
        }

        inputValue = null;
        System.out.println("-Interest Rate for Credit accounts: ");
        while (inputValue == null) inputValue = parseDoubleOrNull(scanner.nextLine());

        inputValue = null;
        while(inputValue == null) inputValue = parseDoubleOrNull(scanner.nextLine());
        Percent interestRateForCredit = null;
        try {
            interestRateForCredit = new Percent(inputValue);
        } catch (BanksException e) {
            e.printStackTrace();
            return;
        }

        var interestRatesForDeposit = new InterestRatesForDeposit();
        System.out.println("-Interest Rates for Deposit accounts");
        System.out.println("-Add new ranges with interest rates (enter 'stop' to stop)");
        do
        {
            Integer rangeStart = null, rangeEnd = null;
            Double interest = null;
            System.out.println("-Range starts at: ");
            while (rangeStart == null) rangeStart = parseIntOrNull(scanner.nextLine());
            System.out.println("-Range ends at: ");
            while (rangeEnd == null) rangeEnd = parseIntOrNull(scanner.nextLine());
            System.out.println("-Interest rate: ");
            while (interest == null) interest = parseDoubleOrNull(scanner.nextLine());
            Percent interestValue = null;
            try {
                interestValue = new Percent(interest);
            } catch (BanksException e) {
                e.printStackTrace();
                return;
            }
            try {
                interestRatesForDeposit.addInterestRate(new Range(rangeStart, rangeEnd), interestValue);
            } catch (BanksException e) {
                e.printStackTrace();
                return;
            }
            System.out.println("-Stop?");
        }
        while (!Objects.equals(scanner.nextLine(), "stop"));

        inputValue = null;
        System.out.println("-Limit for Credit accounts: ");
        while (inputValue == null) inputValue = parseDoubleOrNull(scanner.nextLine());
        double limitForCredit = inputValue;

        inputValue = null;
        System.out.println("-Limit for Untrustworthy accounts: ");
        while (inputValue == null) inputValue = parseDoubleOrNull(scanner.nextLine());
        double limitForUntrustworthy = inputValue;

        var bankConditions = new BankConditions(
                limitForUntrustworthy,
                limitForCredit,
                interestRateForDebit,
                interestRatesForDeposit,
                interestRateForCredit);

        try {
            bankSystem.addBank(bankName, bankConditions);
        } catch (BanksException e) {
            e.printStackTrace();
            return;
        }
        System.out.println("-Bank has been added successfully! Congratulations!");
    }

    private void changeBankConditions() {
        System.out.print("-Enter Bank name: ");
        String bankName = scanner.nextLine();

        while (bankName.isEmpty()) {
            System.out.println("-Very smart move, enter it again: ");
            bankName = scanner.nextLine();
        }

        System.out.println("""
                -Choose condition:
                -1. Interest Rate For Credit
                -2. Interest Rate For Debit
                -3. Interest Rate For Deposit
                -4. Limit For Credit
                -5. Limit For Untrustworthy""");
        Integer choice = null;
        while (choice == null) choice = parseIntOrNull(scanner.nextLine());

        Double inputValue = null;
        switch (choice) {
            case 1 -> {
                System.out.println("-Interest Rate for Credit accounts: ");
                while (inputValue == null) inputValue = parseDoubleOrNull(scanner.nextLine());
                try {
                    bankSystem.changeInterestRateForCredit(bankName, new Percent(inputValue));
                } catch (BanksException e) {
                    e.printStackTrace();
                    return;
                }
            }
            case 2 -> {
                Integer rangeStart = null, rangeEnd = null;
                Double interest = null;
                System.out.println("-Range starts at: ");
                while (rangeStart == null) rangeStart = parseIntOrNull(scanner.nextLine());
                System.out.println("-Range ends at: ");
                while (rangeEnd == null) rangeEnd = parseIntOrNull(scanner.nextLine());
                System.out.println("-Interest rate: ");
                while (interest == null) interest = parseDoubleOrNull(scanner.nextLine());
                try {
                    bankSystem.changeInterestRateForDeposit(
                            bankName,
                            new Range(rangeStart, rangeEnd),
                            new Percent(interest));
                } catch (BanksException e) {
                    e.printStackTrace();
                    return;
                }
            }
            case 3 -> {
                System.out.println("-Interest Rate for Debit accounts: ");
                while (inputValue == null) inputValue = parseDoubleOrNull(scanner.nextLine());
                try {
                    bankSystem.changeInterestRateForDebit(bankName, new Percent(inputValue));
                } catch (BanksException e) {
                    e.printStackTrace();
                    return;
                }
            }
            case 4 -> {
                System.out.println("-Limit for Credit accounts: ");
                while (inputValue == null) inputValue = parseDoubleOrNull(scanner.nextLine());
                try {
                    bankSystem.changeLimitForCredit(bankName, inputValue);
                } catch (BanksException e) {
                    e.printStackTrace();
                }
            }
            case 5 -> {
                System.out.println("-Limit for Untrustworthy accounts: ");
                while (inputValue == null) inputValue = parseDoubleOrNull(scanner.nextLine());
                try {
                    bankSystem.changeLimitForUntrustworthy(bankName, inputValue);
                } catch (BanksException e) {
                    e.printStackTrace();
                    return;
                }
            }
            default -> {
                System.out.println("-Incorrect number, dummy");
                return;
            }
        }

        System.out.println("-Changed have been added successfully! Congratulations!");
    }

    private void addClientToBank() {
        System.out.print("-Enter Bank name: ");
        String bankName = scanner.nextLine();

        while (bankName.isEmpty()) {
            System.out.println("-Very smart move, enter it again: ");
            bankName = scanner.nextLine();
        }

        System.out.println("---Creating client--");
        var builder = new ClientBuilder();
        System.out.println("-Enter Client's name: ");
        String input = scanner.nextLine();
        while (input.isEmpty()) input = scanner.nextLine();

        try {
            builder.setName(input);
        } catch (BanksException e) {
            e.printStackTrace();
            return;
        }

        System.out.println("-Enter Client's surname: ");
        input = scanner.nextLine();
        while (input.isEmpty()) input = scanner.nextLine();

        try {
            builder.setSurname(input);
        } catch (BanksException e) {
            e.printStackTrace();
            return;
        }

        System.out.println("-Enter Client's phone number: ");
        Integer phoneNumber = null;
        while (phoneNumber == null) phoneNumber = parseIntOrNull(scanner.nextLine());

        try {
            builder.SetPhoneNumber(phoneNumber);
        } catch (BanksException e) {
            e.printStackTrace();
            return;
        }

        System.out.println("-Enter Client's Address(optional): ");
        input = scanner.nextLine();
        if (!input.isEmpty()) builder.setAddress(input);

        System.out.println("-Enter Client's Passport(optional): ");
        input = scanner.nextLine();
        if (!input.isEmpty()) builder.SetPassport(input);

        try {
            bankSystem.addClientToBank(builder.RetrieveResult(), bankName);
        } catch (BanksException e) {
            e.printStackTrace();
            return;
        }
        System.out.println("-Client has been added successfully! Congratulations!");
    }

    private void updateClient() {
        System.out.print("-Enter Bank name: ");
        String bankName = scanner.nextLine();

        while (bankName.isEmpty()) {
            System.out.println("-Very smart move, enter it again: ");
            bankName = scanner.nextLine();
        }

        System.out.println("-Enter Client's phone number: ");
        Integer phoneNumber = null;
        while (phoneNumber == null) phoneNumber = parseIntOrNull(scanner.nextLine());

        DefaultClient client = null;
        try {
            client = bankSystem.getClient(bankName, phoneNumber);
        } catch (BanksException e) {
            e.printStackTrace();
            return;
        }

        System.out.println("-Enter Client's Address(optional): ");
        String input = scanner.nextLine();
        if (!input.isEmpty()) client.setAddress(input);

        System.out.println("-Enter Client's Passport(optional): ");
        input = scanner.nextLine();
        if (!input.isEmpty()) client.setPassport(input);

        System.out.println("-Client has been changed successfully!");
    }

    private void addAccountToClient() {
        System.out.print("-Enter Client's _Bank_ name: ");
        String bankName = scanner.nextLine();

        while (bankName.isEmpty()) {
            System.out.println("-Very smart move, enter it again: ");
            bankName = scanner.nextLine();
        }

        Integer phoneNumber = null;
        System.out.print("-Enter Client's phone number: ");
        while (phoneNumber == null) phoneNumber = parseIntOrNull(scanner.nextLine());

        System.out.println("""
                -Which type of account to create(number):
                -1. Deposit
                -2. Debit
                -3. Credit""");
        Integer choice = null;
        while (choice == null) choice = parseIntOrNull(scanner.nextLine());

        AccountType accountType = switch(choice) {
            case 1 -> AccountType.Deposit;
            case 2 -> AccountType.Debit;
            case 3 -> AccountType.Credit;
            default -> null;
        };
        if (accountType == null) {
            System.out.println("Incorrect account type, lol");
            return;
        }
        try {
            bankSystem.createAccount(accountType, bankName, phoneNumber);
        } catch (BanksException e) {
            e.printStackTrace();
            return;
        }
        System.out.println("-Account has been added successfully!");
    }

    private void makeTransaction() {
        System.out.println("""
                -Which type of transaction to make:
                -1. Transfer
                -2. Replenish
                -3. Withdraw""");
        Integer choice = null;
        while (choice == null) choice = parseIntOrNull(scanner.nextLine());

        System.out.println("-Enter transaction value: ");
        Double value = null;
        while (value == null) value = parseDoubleOrNull(scanner.nextLine());

        UUID sender, receiver;
        switch (choice) {
            case 1 -> {
                System.out.println("-Enter Id of Account-Sender");
                sender = UUID.fromString(scanner.nextLine());
                System.out.println("-Enter Id of Account-Receiver");
                receiver = UUID.fromString(scanner.nextLine());
                try {
                    bankSystem.makeTransferTransaction(sender, receiver, value);
                } catch (BanksException e) {
                    e.printStackTrace();
                    return;
                }
            }
            case 2 -> {
                System.out.println("-Enter Id of Account-Receiver");
                receiver = UUID.fromString(scanner.nextLine());
                try {
                    bankSystem.makeReplenishTransaction(receiver, value);
                } catch (BanksException e) {
                    e.printStackTrace();
                    return;
                }
            }
            case 3 -> {
                System.out.println("-Enter Id of Account-Sender");
                sender = UUID.fromString(scanner.nextLine());
                try {
                    bankSystem.makeWithdrawTransaction(sender, value);
                } catch (BanksException e) {
                    e.printStackTrace();
                    return;
                }
            }
            default -> {
                System.out.println("-Incorrect type, dummy");
                return;
            }
        }

        System.out.println("-Transaction completed!");
    }

    private void cancelTransaction() {
        System.out.println("-Enter transaction Id: ");
        UUID id;
        id = UUID.fromString(scanner.nextLine());

        try {
            bankSystem.cancelTransaction(id);
        } catch (BanksException e) {
            e.printStackTrace();
            return;
        }

        System.out.println("-Transaction has been canceled successfully!");
    }

    private void lookInFuture() {
        System.out.println("-Enter how much days to skip");
        Integer days = null;
        while (days == null) days = parseIntOrNull(scanner.nextLine());

        System.out.println("-Enter account id");
        var id = UUID.fromString(scanner.nextLine());
        double futureBalance;
        try {
            futureBalance = bankSystem.getFutureAccountBalance(id, days);
        } catch (BanksException | CloneNotSupportedException e) {
            e.printStackTrace();
            return;
        }
        System.out.println("Account balance after " + days + " days: " + futureBalance);
    }

    private void showBankList() {
        for (var bank : bankSystem.getBanks())
            System.out.println(bank.getName());
    }

    private void showBankInfo() {
        System.out.print("-Enter Bank name: ");
        String bankName = scanner.nextLine();

        while (bankName.isEmpty()) {
            System.out.println("-Very smart move, enter it again: ");
            bankName = scanner.nextLine();
        }

        String finalBankName = bankName;
        var bank = bankSystem.getBanks().stream().filter(b -> Objects.equals(b.getName(), finalBankName)).findAny().orElse(null);
        if (bank == null) {
            System.out.printf("-%s doesn't exist :(\n", bankName);
            return;
        }

        System.out.println("----Bank '" + bank.getName() + "'---");
        System.out.printf("""
                        -Bank conditions:
                        -Interest rate for Credit accounts - %f
                        -Limit for Credit accounts - %f
                        -Interest rate for Debit accounts - %f
                        -Limit for Untrustworthy accounts - %f
                        -Interest rates for Deposit accounts:""",
                bank.getConditions().getInterestRateForCredit().getValue(),
                bank.getConditions().getLimitForCredit(),
                bank.getConditions().getInterestRateForDebit().getValue(),
                bank.getConditions().getLimitForUntrustworthy());
        for (var entry : bank.getConditions().getInterestRatesForDeposit().getContent().entrySet()) {
            System.out.printf("    %s - %f\n", entry.getKey(), entry.getValue().getValue());
        }

        System.out.println("----Clients:");
        Integer counter = 0;
        for (var entry : bank.getAccounts().entrySet()) {
            counter++;
            System.out.printf("""
                            -%d) %s %s
                             Phone Number - %d
                            """,
                    counter,
                    entry.getKey().getName(),
                    entry.getKey().getSurname(),
                    entry.getKey().getPhoneNumber());
            if (entry.getKey().getAddress() != null)
            System.out.println(" Address - " + entry.getKey().getAddress());
            if (entry.getKey().getPassport() != null)
            System.out.println(" Passport - " + entry.getKey().getPassport());

            System.out.println(" -Accounts:");
            entry.getValue().forEach(acc -> System.out.println(" " + acc.getId() + " Balance: " + acc.getCurrentBalance()));
        }
    }

    private void showTransactionHistory() {
        System.out.println("------------------------------");
        for (var transaction : bankSystem.getTransactionsHistory().getTransactions()) {
            System.out.printf("%s : %s\n", transaction.getDateTime(), transaction.getId());
        }

        System.out.println("------------------------------");
    }

    public Integer parseIntOrNull(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public Double parseDoubleOrNull(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
