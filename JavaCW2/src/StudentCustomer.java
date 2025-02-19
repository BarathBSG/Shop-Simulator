import java.io.IOException;

public class StudentCustomer extends Customer
{
    //Subclass of Customer representing Students with overdraft and 5% discount with overridden methods from
    // Customer to represent this.
    public static double discountAmount = 0.95;
    public StudentCustomer(String accountID, String accountName) throws InvalidCustomerException
    {
        super(accountID, accountName);
    }

    public StudentCustomer(String accountID, String accountName, int accountBalance) throws InvalidCustomerException
    {
        super(accountID, accountName, accountBalance);
        if (accountBalance < -500) {
            throw new InvalidCustomerException("ID invalid - student account balance at negative overdraft");
        }
    }

    @Override
    public int chargeAccount(int snackPrice) throws InvalidBalanceException
    {
        snackPrice = (int) Math.ceil(snackPrice * discountAmount); //apply 5% discount

        if (this.getAccountBalance() - snackPrice >= -500){
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
        return "Account ID: " + this.getAccountID() + "\n" + "Account type: Student" + "\n" +
                "Account Name = " + this.getAccountName() + "\n" +
                "Account Balance = " + this.getAccountBalance() + "\n" + "Student discount: " +
                getDiscountAmount(); //don't need 'this.' since it's a static method so doesn't change
    }

    public static double getDiscountAmount()
    {
        return discountAmount;
    }

    public static void main(String[] args)
    {
        //Test 1
        System.out.println("Test 1\n");
        try
        {
            StudentCustomer sc = new StudentCustomer("364525", "seniL nosaJ");
            sc.chargeAccount(526);
            System.out.println(sc);
            System.out.println("\nSuccess\n");
        }catch (Exception e){
            System.out.println(e);
            System.out.println("\nFail\n");
        }

        //Test 2
        System.out.println("Test 2\n");
        try
        {
            //Overcharge beyond overdraft
            StudentCustomer sc = new StudentCustomer("364525", "seniL nosaJ");
            sc.chargeAccount(527);
            System.out.println(sc);
            System.out.println("\nFail\n");
        }catch (Exception e){
            System.out.println(e);
            System.out.println("\nSuccess\n");
        }
    }
}
