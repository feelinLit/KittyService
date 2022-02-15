import com.banks.BankConditions;
import com.common.InterestRatesForDeposit;
import com.common.Percent;
import com.common.Range;
import com.services.BankSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BanksJavaTests {
    private BankSystem bankSystem;

    @BeforeEach
    public void setUp()
    {
        bankSystem = new BankSystem();

        var interestRatesForDeposit = new InterestRatesForDeposit();
        interestRatesForDeposit.addInterestRate(new Range(0, 20), new Percent(0));
        interestRatesForDeposit.addInterestRate(new Range(40, 100), new Percent(3.65));

        var bankConditions = new BankConditions(
                50,
                100,
                new Percent(new double(5)),
                new Percent(new double(10)),
                interestRatesForDeposit);

        bankSystem.AddBank("BankName", bankConditions);
    }

    @Test
    public void addUntrustworthyAccount_MakeItsClientTrustworthy_LimitHasChanged()
    {
            const int clientPhoneNumber = 1337;
        BankConditions bankConditions = bankSystem.GetBankConditions("BankName");
        var builder = new ClientBuilder();
        builder.SetName("Name");
        builder.SetSurname("Surname");
        builder.SetPhoneNumber(clientPhoneNumber);
        IClient client = builder.RetrieveResult();
        bankSystem.AddClientToBank(client, "BankName");
        IAccount account = bankSystem.CreateAccount(AccountType.Debit, "BankName", client.PhoneNumber);

        Assert.IsFalse(client.Trustworthy);
        Assert.IsFalse(account.Trustworthy);
        Assert.AreEqual(bankConditions.LimitForUntrustworthy, account.TransactionLimit);

        client.Address = "Address";
        client.Passport = "Passport";

        Assert.IsTrue(client.Trustworthy);
        Assert.IsTrue(account.Trustworthy);
        Assert.AreEqual(double.MaxValue, account.TransactionLimit);
    }

        [TestCase(0,0,0)]
            [TestCase(30,0,0)]
            [TestCase(30,5,0)]
            [TestCase(123,41,20)]
    public void MakeTransaction_BalanceChanged(
            double senderAmountToReplenish,
            double amountToTransfer,
            double receiverAmountToWithdraw)
    {
        var builder = new ClientBuilder();
        builder.SetName("Name");
        builder.SetSurname("Surname");
        builder.SetPhoneNumber(991);
        builder.SetAddress("Address");
        builder.SetPassport("Passport");
        IClient client = builder.RetrieveResult();
        bankSystem.AddClientToBank(client, "BankName");
        IAccount sender = bankSystem.CreateAccount(AccountType.Debit, "BankName", client.PhoneNumber);
        IAccount receiver = bankSystem.CreateAccount(AccountType.Debit, "BankName", client.PhoneNumber);

        bankSystem.MakeReplenishTransaction(sender.Id, senderAmountToReplenish);
        bankSystem.MakeTransferTransaction(sender.Id, receiver.Id, amountToTransfer);
        bankSystem.MakeWithdrawTransaction(receiver.Id, receiverAmountToWithdraw);

        Assert.AreEqual(senderAmountToReplenish - amountToTransfer, sender.CurrentBalance);
        Assert.AreEqual(amountToTransfer - receiverAmountToWithdraw, receiver.CurrentBalance);
    }

        [TestCase(0, 0, 0, 0, 0)]
            [TestCase(30, 30, 10, 40, 20)]
            [TestCase(20, 0, 0, 20, 20)]
    public void MakeTransaction_CancelTransaction_BalanceNotChanged(
            double senderStartAmount,
            double receiverStartAmount,
            double senderAmountToReplenish,
            double amountToTransfer,
            double receiverAmountToWithdraw)
    {
        var builder = new ClientBuilder();
        builder.SetName("Name");
        builder.SetSurname("Surname");
        builder.SetPhoneNumber(991);
        builder.SetAddress("Address");
        builder.SetPassport("Passport");
        IClient client = builder.RetrieveResult();
        bankSystem.AddClientToBank(client, "BankName");
        IAccount sender = bankSystem.CreateAccount(AccountType.Debit, "BankName", client.PhoneNumber);
        IAccount receiver = bankSystem.CreateAccount(AccountType.Debit, "BankName", client.PhoneNumber);

        bankSystem.MakeReplenishTransaction(sender.Id, senderStartAmount);
        bankSystem.MakeReplenishTransaction(receiver.Id, receiverStartAmount);

        UUID replenishTransactionId =
                bankSystem.MakeReplenishTransaction(sender.Id, senderAmountToReplenish);
        UUID transferTransactionId =
                bankSystem.MakeTransferTransaction(sender.Id, receiver.Id, amountToTransfer);
        UUID withdrawTransactionId =
                bankSystem.MakeWithdrawTransaction(receiver.Id, receiverAmountToWithdraw);

        bankSystem.CancelTransaction(withdrawTransactionId);
        bankSystem.CancelTransaction(transferTransactionId);
        bankSystem.CancelTransaction(replenishTransactionId);


        Assert.AreEqual(senderStartAmount, sender.CurrentBalance);
        Assert.AreEqual(receiverStartAmount, receiver.CurrentBalance);
    }
}
