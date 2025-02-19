import java.io.IOException;

public class Customer
{
    //Class for Customer account objects and its variations of Student and Staff.
    // This base class represents standard acccounts.
    private String accountID, accountName;
    protected int accountBalance;

    public Customer(String accountID, String accountName) throws InvalidCustomerException
    {
        this.accountID = accountID;
        this.accountName = accountName;
        accountBalance = 0;

        if (accountID.length() != 6){
            throw new InvalidCustomerException("Input invalid - account ID length error");
        }
        char[] charArr = accountID.toCharArray();
        for (int i = 0; i < 5; i++)
        {
            if (!(Character.isLetterOrDigit(charArr[i]))){
                throw new InvalidCustomerException("Input invalid - ID contains non-alphanumeric values");
            }
        }
    }

    public Customer(String accountID, String accountName, int accountBalance) throws InvalidCustomerException
    {
        this.accountID = accountID;
        this.accountName = accountName;
        this.accountBalance = accountBalance;

        if (accountID.length() != 6){
            throw new InvalidCustomerException("Input invalid - account ID length error");
        } else if (accountBalance < 0) {
            throw new InvalidCustomerException("Input invalid - account balance negative");
        }

        char[] charArr = accountID.toCharArray();
        for (int i = 0; i < 5; i++)
        {
            if (!(Character.isLetterOrDigit(charArr[i]))){
                throw new InvalidCustomerException("Input invalid - ID contains non-alphanumeric values");
            }
        }
    }


    public String getAccountID()
    {
        return accountID;
    }

    public String getAccountName()
    {
        return accountName;
    }

    public int getAccountBalance()
    {
        return accountBalance;
    }

    public void addFunds(int amount){
        if (amount > 0){
            accountBalance = accountBalance + amount;
        }
        else {
            System.out.println("Error: cannot add negative funds to an account");
            //(would be cool as a penalty)
        }
    }

    public int chargeAccount(int snackPrice) throws InvalidBalanceException
    {
        if (accountBalance - snackPrice >= 0){
            accountBalance = accountBalance - snackPrice;
            return snackPrice;
        }
        else{
            throw new InvalidBalanceException("Balance error: insufficient balance");
        }
    }

    @Override
    public String toString()
    {
        return "Account ID: " + this.getAccountID() + "\n" + "Account type: Standard" + "\n" +
                "Account Name = " + this.getAccountName() + "\n" +
                "Account Balance = " + this.getAccountBalance();
    }

    public static void main(String[] args)
    {
        //Test 1
        System.out.println("Test 1\n");
        try
        {
            Customer customer = new Customer("662421", "Barath The Goat", 0);
            System.out.println(customer);
            System.out.println("\nSuccess\n");
        }catch (Exception e){
            System.out.println("\nFail\n");
        }

        //Test 2
        System.out.println("Test 2\n");
        try
        {
            //negative balance
            Customer customer = new Customer("662421", "Barath The Goat", -400);
            System.out.println(customer);
            System.out.println("\nFail\n");
        }catch (Exception e){
            System.out.println(e);
            System.out.println("\nSuccess\n");
        }

        //Test 3
        System.out.println("Test 3\n");
        try
        {
            //Overcharge
            Customer c = new Customer("364525", "Troll Face", 1);
            c.chargeAccount(2);
            System.out.println(c);
            System.out.println("\nFail\n");
        }catch (Exception e){
            System.out.println(e);
            System.out.println("\nSuccess\n");
        }
    }
}
