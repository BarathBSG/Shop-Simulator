import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SnackShop
{
    private String shopName;
    private int totalSalesSum;

    //using a hashmap because it is faster with larger datasets - quicker worst-case time complexity
    //the keys are the IDs
    HashMap<String, Snack> snackCollection = new HashMap<String, Snack>();
    HashMap<String, Customer> customerCollection = new HashMap<String, Customer>();

    public SnackShop(String shopName)
    {
        this.shopName = shopName;
    }

    public void addSnack(Snack snack){
        snackCollection.put(snack.getSnackID(), snack);
    }

    public void addCustomer(Customer customer){
        customerCollection.put(customer.getAccountID(), customer);
    }

    public boolean processPurchase(String customerID, String snackID) throws InvalidTransactionException,
            InvalidBalanceException
    {
        Customer customer = customerCollection.get(customerID);
        Snack snack = snackCollection.get(snackID);
        try
        {
            totalSalesSum = totalSalesSum + customer.chargeAccount(snack.calculatePrice());
            return true;

        }catch (InvalidBalanceException | NullPointerException e){
            //NullPointerException refers to snack/customer not existing
            // which is handled in simulation class
            if (customer == null || snack == null){
                System.out.print("");
            }else {
                System.out.println("Transaction unsuccessful - insufficient balance - "
                        + customer.getAccountName()
                        + " could not buy " + snack.getSnackName());
            }
            throw new InvalidTransactionException();
        }
    }

    //loop through hashmaps using for loop and entrySet() and overwrite maxValue if larger value is found
    public int largestBasePrice(){
        int maxValue = 0;
        for (Map.Entry<String, Snack> snacks : snackCollection.entrySet()) {
            Snack sn = snacks.getValue();
            int value = sn.getBasePrice();
            if (value > maxValue){
                maxValue = value;
            }
        }
        return maxValue;
    }

    public int countNegativeAccounts(){
        int count = 0;
        for (Map.Entry<String, Customer> customers : customerCollection.entrySet()) {
            Customer cs = customers.getValue();
            int balance = cs.getAccountBalance();
            if (balance < 0){
                count = count + 1;
            }
        }
        return count;
    }

    //requires a sorted list so add all values to an arraylist, sort it to then find median
    public int calculateMedianCustomerBalance(){
        ArrayList<Integer> balanceCollection = new ArrayList<Integer>();
        for (Map.Entry<String, Customer> customers : customerCollection.entrySet()) {
            Customer cs = customers.getValue();
            int balance = cs.getAccountBalance();
            balanceCollection.add(balance);
        }
        Collections.sort(balanceCollection);

        if (balanceCollection.size() % 2 == 0){ //if arraylist is even
            int middleIndex = (balanceCollection.size() / 2) + 1;
            int median = (balanceCollection.get(middleIndex - 1) + balanceCollection.get(middleIndex - 2)) / 2;
            return median;
        }else {
            return balanceCollection.get(balanceCollection.size() / 2);
        }
    }

    public String getShopName()
    {
        return shopName;
    }

    public void setShopName(String shopName)
    {
        this.shopName = shopName;
    }

    public int getTotalSalesSum()
    {
        return totalSalesSum;
    }

    @Override
    public String toString()
    {
        try
        {
            return "\nSummary of shop: " + this.getShopName() + "\n" + "Largest base price: " + this.largestBasePrice()
                    + " | Number of negative balances: " + this.countNegativeAccounts() + " | Median customer balance: "
                    + this.calculateMedianCustomerBalance() + "\n" + "Shop turnover: " + this.getTotalSalesSum();
        }catch (Exception e){
            return "Error: " + this.getShopName() + " is empty - cannot compute summary";
        }

    }

    public static void main(String[] args)
    {
        //Test 1
        System.out.println("Test 1\n");
        try
        {
            SnackShop sn = new SnackShop("BSG");

            Food myFood = new Food("F/1234567", "Oreos", "cold", 250);
            Customer myCustomer = new Customer("4D3J56", "Barath Sathish", 249);

            sn.processPurchase(myCustomer.getAccountID(), myFood.getSnackID());
            System.out.println("\nFail\n");
        }catch (Exception e){
            System.out.println(e);
            System.out.println("Success\n");
        }

        //Test 2
        System.out.println("Test 2\n");
        try
        {
            SnackShop sn = new SnackShop("BSG");

            Food myFood = new Food("F/1234567", "Oreos", "cold", 250);
            Drink myDrink = new Drink("D/1234567", "Fanta", "high", 275);
            Customer myCustomer = new Customer("4D3J56", "Barath Sathish", 7000);
            StudentCustomer studCust = new StudentCustomer("7F3T59", "Super Duper",
                    0);
            StaffCustomer myStaff = new StaffCustomer("58R526", "John-Paul Clay",
                    4000, "CMP");
            Customer customer2 = new Customer("4F3J86", "Jason Lines", 5000);

            sn.addSnack(myFood);
            sn.addSnack(myDrink);
            sn.addCustomer(myCustomer);
            sn.addCustomer(studCust);
            sn.addCustomer(myStaff);
            sn.addCustomer(customer2);

            System.out.println(sn);

            System.out.println("Success\n");
        }catch (Exception e){
            System.out.println(e);
            System.out.println("Fail\n");
        }

        //Test 3
        System.out.println("Test 3\n");
        try
        {
            SnackShop sm = new SnackShop("BSG");
            sm.setShopName("Jasoncorp");
            System.out.println(sm);
            System.out.println("Success\n");
        }catch (Exception e){
            System.out.println(e);
            System.out.println("Fail\n");
        }
    }
}
