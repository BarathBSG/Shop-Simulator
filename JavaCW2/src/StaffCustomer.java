import java.io.IOException;

public class StaffCustomer extends Customer
{
    //Subclass of Customer representing Staff and their varying discounts.
    // Overridden methods to represent this and methods to deduce departments in constructors.
    private enum accountSchool {CMP, BIO, MTH, other};
    private accountSchool AccountSchool;
    public StaffCustomer(String accountID, String accountName, String AccountSchool) throws InvalidCustomerException
    {
        super(accountID, accountName);
        //A switch can be used but not enough cases to make it more efficient than if statement
        switch (AccountSchool){
            case "CMP":
                this.AccountSchool = accountSchool.CMP;
                break;
            case "BIO":
                this.AccountSchool = accountSchool.BIO;
                break;
            case "MTH":
                this.AccountSchool = accountSchool.MTH;
                break;
            default:
                this.AccountSchool = accountSchool.other;
        }
    }

    public StaffCustomer(String accountID, String accountName, int accountBalance, String AccountSchool)
            throws InvalidCustomerException
    {
        super(accountID, accountName, accountBalance);
        switch (AccountSchool){
            case "CMP":
                this.AccountSchool = accountSchool.CMP;
                break;
            case "BIO":
                this.AccountSchool = accountSchool.BIO;
                break;
            case "MTH":
                this.AccountSchool = accountSchool.MTH;
                break;
            default:
                this.AccountSchool = accountSchool.other;
        }
    }

    @Override
    public int chargeAccount(int snackPrice) throws InvalidBalanceException
    {
        if (AccountSchool == accountSchool.CMP){
            snackPrice = (int) Math.ceil(snackPrice * 0.9); //apply 10% discount
        } else if (AccountSchool == accountSchool.MTH || AccountSchool == accountSchool.BIO)
        {
            snackPrice = (int) Math.ceil(snackPrice * 0.98); //apply 2% discount
        }

        if (this.getAccountBalance() - snackPrice >= 0){
            accountBalance = this.getAccountBalance() - snackPrice;
            return snackPrice;
        }
        else{
            throw new InvalidBalanceException("Balance error: insufficient balance");
        }
    }

    @Override
    public String toString()
    {
        return "Account ID: " + this.getAccountID() + "\n" +
                "Account type: Staff" + "\n" +
                "Staff School: " + AccountSchool + "\n" +
                "Account Name = " + this.getAccountName() + "\n" +
                "Account Balance = " + this.getAccountBalance();
    }

    public static void main(String[] args)
    {
        //Test 1
        System.out.println("Test 1\n");
        try
        {
            StaffCustomer st = new StaffCustomer("420696", "illuminati", "");
            StaffCustomer st2 = new StaffCustomer("420696", "illuminati", "CMP");
            System.out.println(st);
            System.out.println("");
            System.out.println(st2);
            System.out.println("\nSuccess\n");
        }catch (Exception e){
            System.out.println(e);
            System.out.println("\nFail\n");
        }
    }
}
