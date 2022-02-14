package com.consoleui;

import com.banks.BankConditions;
import com.common.InterestRatesForDeposit;
import com.common.Percent;
import com.common.Range;
import com.services.BankSystem;
import com.tools.BanksException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class Application {
    private final BankSystem bankSystem = new BankSystem();
    private Map<String, Runnable> commands;
    private final Scanner scanner = new Scanner(System.in);

    public Application()
    {
        commands = new HashMap<>()
        {
            { "!ShowCommands", ShowCommands },
            { "!CreateBank", CreateBank },
            { "!ChangeBankConditions", ChangeBankConditions },
            { "!AddClientToBank", AddClientToBank },
            { "!ChangeClient", UpdateClient },
            { "!AddAccountToClient", AddAccountToClient },
            { "!MakeTransaction", MakeTransaction },
            { "!CancelTransaction", CancelTransaction },
            { "!LookInFuture", LookInFuture },
            { "!ShowBankList", ShowBankList },
            { "!ShowBankInfo", ShowBankInfo },
            { "!ShowTransactionHistory", ShowTransactionHistory },
        };
    }

    public void start() {
        System.out.println("Cringe Console Interface v1.0 is pleased to serve you! (All rights reserved)");
        ShowCommands();
        String command = scanner.nextLine();
        while (command != "!Exit") {
            while (command.isEmpty()) {
                System.out.println("-Enter correct command, dummy.");
                command = scanner.nextLine();
            }

            while (!commands.containsKey(command)) {
                System.out.println("-Incorrect command: " + command);
                command = scanner.nextLine();
            }

            commands[command]?.Invoke();
            command = scanner.nextLine();
        }
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

    private void ChangeBankConditions()
    {
        System.out.print("-Enter Bank name: ");
        String bankName = scanner.nextLine();

        while (.isEmpty(bankName))
        {
            System.out.println("-Very smart move, enter it again: ");
            bankName = scanner.nextLine();
        }

        System.out.println("-Choose condition: " + '\n' +
                "-1. Interest Rate For Credit" + '\n' +
                "-2. Interest Rate For Debit" + '\n' +
                "-3. Interest Rate For Deposit" + '\n' +
                "-4. Limit For Credit" + '\n' +
                "-5. Limit For Untrustworthy");
        int choice;
        while (int.TryParse(scanner.nextLine(), out choice))
            


        double inputValue;
        switch (choice)
        {
            case 1:
                System.out.println("-Interest Rate for Credit accounts: ");
                        while (inputValue == null) inputValue = parseDoubleOrNull(scanner.nextLine());

                    

                bankSystem.ChangeInterestRateForCredit(bankName, new Percent(inputValue));
                break;

            case 2:
                int rangeStart, rangeEnd;
                double interest;
                System.out.println("-Range starts at: ");
                while (!int.TryParse(scanner.nextLine(), out rangeStart))
                    

                System.out.println("-Range ends at: ");
                while (!int.TryParse(scanner.nextLine(), out rangeEnd))
                    

                System.out.println("-Interest rate: ");
                while (!double.TryParse(scanner.nextLine(), out interest))
                    

                bankSystem.ChangeInterestRateForDeposit(
                        bankName,
                        new Range(rangeStart, rangeEnd),
                        new Percent(interest));
                break;

            case 3:
                System.out.println("-Interest Rate for Debit accounts: ");
                        while (inputValue == null) inputValue = parseDoubleOrNull(scanner.nextLine());

                    

                bankSystem.ChangeInterestRateForDebit(bankName, new Percent(inputValue));
                break;

            case 4:
                System.out.println("-Limit for Credit accounts: ");
                        while (inputValue == null) inputValue = parseDoubleOrNull(scanner.nextLine());

                    

                bankSystem.ChangeLimitForCredit(bankName, inputValue);
                break;

            case 5:
                System.out.println("-Limit for Untrustworthy accounts: ");
                        while (inputValue == null) inputValue = parseDoubleOrNull(scanner.nextLine());

                    

                bankSystem.ChangeLimitForUntrustworthy(bankName, inputValue);
                break;

            default:
                System.out.println("-Incorrect number, dummy");
                return;
        }

        System.out.println("-Changed have been added successfully! Congratulations!");
    }

    private void AddClientToBank()
    {
        System.out.print("-Enter Bank name: ");
        String bankName = scanner.nextLine();

        while (.isEmpty(bankName))
        {
            System.out.println("-Very smart move, enter it again: ");
            bankName = scanner.nextLine();
        }

        System.out.println("---Creating client--");
        var builder = new ClientBuilder();
        System.out.println("-Enter Client's name: ");
        String input = scanner.nextLine();
        while (.isEmpty(input))
        {
            

            input = scanner.nextLine();
        }

        builder.SetName(input);

        System.out.println("-Enter Client's surname: ");
        input = scanner.nextLine();
        while (.isEmpty(input))
        {
            

            input = scanner.nextLine();
        }

        builder.SetSurname(input);

        System.out.println("-Enter Client's phone number: ");
        uint phoneNumber;
        while (!uint.TryParse(scanner.nextLine(), out phoneNumber))
        {
            

        }

        builder.SetPhoneNumber(phoneNumber);

        System.out.println("-Enter Client's Address(optional): ");
        input = scanner.nextLine();
        if (!.isEmpty(input))
        {
            builder.SetAddress(input);
        }

        System.out.println("-Enter Client's Passport(optional): ");
        input = scanner.nextLine();
        if (!.isEmpty(input))
        {
            builder.SetPassport(input);
        }

        bankSystem.AddClientToBank(builder.RetrieveResult(), bankName);
        System.out.println("-Client has been added successfully! Congratulations!");
    }

    private void UpdateClient()
    {
        System.out.print("-Enter Bank name: ");
        String bankName = scanner.nextLine();

        while (.isEmpty(bankName))
        {
            System.out.println("-Very smart move, enter it again: ");
            bankName = scanner.nextLine();
        }

        System.out.println("-Enter Client's phone number: ");
        uint phoneNumber;
        while (!uint.TryParse(scanner.nextLine(), out phoneNumber))
        {
            

        }

        IClient client = bankSystem.GetClient(bankName, phoneNumber);

        System.out.println("-Enter Client's Address(optional): ");
        String input = scanner.nextLine();
        if (!.isEmpty(input))
        {
            client.Address = input;
        }

        System.out.println("-Enter Client's Passport(optional): ");
        input = scanner.nextLine();
        if (!.isEmpty(input))
        {
            client.Passport = input;
        }

        System.out.println("-Client has been changed successfully!");
    }

    private void AddAccountToClient()
    {
        System.out.print("-Enter Client's _Bank_ name: ");
        String bankName = scanner.nextLine();

        while (.isEmpty(bankName))
        {
            System.out.println("-Very smart move, enter it again: ");
            bankName = scanner.nextLine();
        }

        uint phoneNumber;
        System.out.print("-Enter Client's phone number: ");
        while (!uint.TryParse(scanner.nextLine(), out phoneNumber))
            System.out.println("-Incorrect phone number, dummy");

        System.out.println("-Which type of account to create(number): " + '\n' +
                "-1. Deposit" + '\n' +
                "-2. Debit" + '\n' +
                "-3. Credit");
        int choice;
        while (!int.TryParse(scanner.nextLine(), out choice))
            


        AccountType accountType = choice switch
        {
            1 => AccountType.Deposit,
                2 => AccountType.Debit,
                3 => AccountType.Credit,
                _ => throw new BanksException("-Incorrect input"),
        };
        bankSystem.CreateAccount(accountType, bankName, phoneNumber);
        System.out.println("-Account has been added successfully!");
    }

    private void MakeTransaction()
    {
        System.out.println("-Which type of transaction to make: " + '\n' +
                "-1. Transfer" + '\n' +
                "-2. Replenish" + '\n' +
                "-3. Withdraw");
        int choice;
        while (!int.TryParse(scanner.nextLine(), out choice))
            


        System.out.println("-Enter transaction value: ");
        double value;
        while (!double.TryParse(scanner.nextLine(), out value))
            


        Guid sender, receiver;
        switch (choice)
        {
            case 1:
                System.out.println("-Enter Id of Account-Sender");
                while (!Guid.TryParse(scanner.nextLine(), out sender))
                    


                System.out.println("-Enter Id of Account-Receiver");
                while (!Guid.TryParse(scanner.nextLine(), out receiver))
                    


                bankSystem.MakeTransferTransaction(sender, receiver, value);
                break;
            case 2:
                System.out.println("-Enter Id of Account-Receiver");
                while (!Guid.TryParse(scanner.nextLine(), out receiver))
                    


                bankSystem.MakeReplenishTransaction(receiver, value);
                break;
            case 3:
                System.out.println("-Enter Id of Account-Sender");
                while (!Guid.TryParse(scanner.nextLine(), out sender))
                    


                bankSystem.MakeWithdrawTransaction(sender, value);
                break;
            default:
                System.out.println("-Incorrect type, dummy");
                return;
        }

        System.out.println("-Transaction completed!");
    }

    private void CancelTransaction()
    {
        System.out.println("-Enter transaction Id: ");
        Guid id;
        while (!Guid.TryParse(scanner.nextLine(), out id))
            


        bankSystem.CancelTransaction(id);

        System.out.println("-Transaction has been canceled successfully!");
    }

    private void LookInFuture()
    {
        System.out.println("-Enter how much days to skip");
        uint days;
        while (!uint.TryParse(scanner.nextLine(), out days))
            


        System.out.println("-Enter account id");
        Guid id;
        while (!Guid.TryParse(scanner.nextLine(), out id))
            


        System.out.println($"Account balance after {days} days: {bankSystem.GetFutureAccountBalance(id, days)}");
    }

    private void ShowBankList()
    {
        foreach (IBank bank in bankSystem.Banks)
        {
            System.out.println(bank.Name);
        }
    }

    private void ShowBankInfo()
    {
        System.out.print("-Enter Bank name: ");
        String bankName = scanner.nextLine();

        while (.isEmpty(bankName))
        {
            System.out.println("-Very smart move, enter it again: ");
            bankName = scanner.nextLine();
        }

        IBank bank = bankSystem.Banks.FirstOrDefault(bank => bank.Name == bankName);
        if (bank == null)
        {
            System.out.println($"-{bankName} doesn't exist :(");
            return;
        }

        System.out.println($"----Bank '{bank.Name}'---");
        System.out.println("-Bank conditions:" + '\n' +
                $"-Interest rate for Credit accounts - {bank.Conditions.InterestRateForCredit.Value}" + '\n' +
                $"-Limit for Credit accounts - {bank.Conditions.LimitForCredit}" + '\n' +
                $"-Interest rate for Debit accounts - {bank.Conditions.InterestRateForDebit.Value}" + '\n' +
                $"-Limit for Untrustworthy accounts - {bank.Conditions.LimitForUntrustworthy}" + '\n' +
                "-Interest rates for Deposit accounts:");
        foreach ((Range key, double value) in bank.Conditions.InterestRatesForDeposit.Content)
        {
            System.out.println($"    {key} - {value}");
        }

        System.out.println("----Clients:");
        int counter = 0;
        foreach ((IClient client, List<IAccount> accounts) in bank.Accounts)
        {
            counter++;
            System.out.println($"-{counter}) {client.Name} {client.Surname}" + '\n' +
                    $" Phone Number - {client.PhoneNumber}");
            if (client.Address != default)
            System.out.println($" Address - {client.Address}");
            if (client.Passport != default)
            System.out.println($" Passport - {client.Passport}");

            System.out.println(" -Accounts:");
            accounts.ForEach(acc => System.out.println(" " + acc.Id + $" Balance: {acc.CurrentBalance}"));
        }
    }

    private void ShowTransactionHistory()
    {
        System.out.println("------------------------------");
        foreach (ITransaction transaction in bankSystem.TransactionsHistory.Transactions)
        {
            System.out.println($"{transaction.DateTime} : {transaction.Id}");
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
