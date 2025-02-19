import java.io.*;

public class Simulation
{
    //Class containing one function for creating a shop, adding snacks and customers from text files
    // and another function to run said shop with a list of varying shop actions
    // (purchase, add customer, add funds) from a text file
    // also containing main method, demonstrating the two functions.
    public static SnackShop initialiseShop(String shopName, File snackFile, File customerFile) throws IOException,
            InvalidCustomerException, InvalidSnackException
    {
        SnackShop snackShop = new SnackShop(shopName);
        //turn every line read from customers.txt into a string array,
        // sort by what customer the line is, add it to snackshop
        BufferedReader reader = new BufferedReader(new FileReader(customerFile));
        String line;
        while ((line = reader.readLine()) != null){ //loops while the line isn't empty
            String[] lineToArray = line.split("#");

            if (lineToArray.length == 3){
                Customer customer = new Customer(lineToArray[0], lineToArray[1], Integer.valueOf(lineToArray[2]));
                snackShop.addCustomer(customer);
            }
            else if (lineToArray.length == 4) {
                //if the array length is 4, it's either a student or a staff from "other" departments
                if (lineToArray[lineToArray.length - 1].equals("STUDENT")){
                    //using .equals() since it checks string values itself instead of the memory pointer of string
                    StudentCustomer studentCustomer = new StudentCustomer(lineToArray[0], lineToArray[1],
                            Integer.valueOf(lineToArray[2]));
                    snackShop.addCustomer(studentCustomer);
                }
                else if (lineToArray[lineToArray.length - 1].equals("STAFF")) {
                    StaffCustomer staffCustomer = new StaffCustomer(lineToArray[0], lineToArray[1],
                            Integer.valueOf(lineToArray[2]), "");
                    snackShop.addCustomer(staffCustomer);
                }
            }
            else if (lineToArray.length == 5) {
                if (lineToArray[lineToArray.length - 1].equals("CMP"))
                {
                    StaffCustomer staffCustomer = new StaffCustomer(lineToArray[0], lineToArray[1],
                            Integer.valueOf(lineToArray[2]), "CMP");
                    snackShop.addCustomer(staffCustomer);
                }
                else if (lineToArray[lineToArray.length - 1].equals("BIO")) {
                    StaffCustomer staffCustomer = new StaffCustomer(lineToArray[0], lineToArray[1],
                            Integer.valueOf(lineToArray[2]), "BIO");
                    snackShop.addCustomer(staffCustomer);
                }
                else if (lineToArray[lineToArray.length - 1].equals("MTH")) {
                    StaffCustomer staffCustomer = new StaffCustomer(lineToArray[0], lineToArray[1],
                            Integer.valueOf(lineToArray[2]), "MTH");
                    snackShop.addCustomer(staffCustomer);
                }
            }
        }

        //same process repeated for snacks.
        BufferedReader reader2 = new BufferedReader(new FileReader(snackFile));
        while ((line = reader2.readLine()) != null) {
            String[] lineToArray = line.split("@");
            //every line array is 3 strings long so only need to sort by food and drink
            char[] IDcharArray = lineToArray[0].toCharArray();
            char typeIdentifier = IDcharArray[0];
            if (typeIdentifier == 'F'){ //have to use == because char is a primitive datatype
                Food food = new Food(lineToArray[0], lineToArray[1], lineToArray[2], Integer.valueOf(lineToArray[3]));
                snackShop.addSnack(food);
            }
            else if (typeIdentifier == 'D')
            {
                Drink drink = new Drink(lineToArray[0], lineToArray[1], lineToArray[2],
                        Integer.valueOf(lineToArray[3]));
                snackShop.addSnack(drink);
            }
        }
        return snackShop;
    };

    public static void simulateShopping(SnackShop shop, File transactionFile) throws IOException,
            InvalidCustomerException
    {
        BufferedReader reader = new BufferedReader(new FileReader(transactionFile));
        String line;
        while ((line = reader.readLine()) != null){
            //copy every line and turn into string array, sort by the first word
            String[] lineToArray = line.split(",");
            if (lineToArray[0].equals("PURCHASE")){
                try
                {
                    if (shop.processPurchase(lineToArray[1], lineToArray[2])){
                        System.out.println("Transaction - " +
                                shop.customerCollection.get(lineToArray[1]).getAccountName() +
                                " has bought " + shop.snackCollection.get(lineToArray[2]).getSnackName());
                    }
                    else {
                        System.out.println("Transaction - " +
                                shop.customerCollection.get(lineToArray[1]).getAccountName() +
                                " could not buy " + shop.snackCollection.get(lineToArray[2]).getSnackName());
                    }
                }catch (InvalidTransactionException e){
                    if (shop.customerCollection.get(lineToArray[1]) == null)
                    {
                        System.out.println("Transaction unsuccessful - " + lineToArray[1] + " could not buy " +
                                shop.snackCollection.get(lineToArray[2]).getSnackID() + " - customer doesn't exist");
                    } else if (shop.snackCollection.get(lineToArray[2]) == null)
                    {
                        System.out.println("Transaction unsuccessful - " +
                                shop.customerCollection.get(lineToArray[1]).getAccountName() + " could not buy " +
                                lineToArray[2] + " - snack doesn't exist");
                    }
                } catch (InvalidBalanceException e)
                {
                    System.out.println("Transaction unsuccessful - " +
                            shop.customerCollection.get(lineToArray[1]).getAccountName() +
                            " doesn't have enough money");
                }
            } else if (lineToArray[0].equals("ADD_FUNDS"))
            {
                try
                {
                    Customer customer = shop.customerCollection.get(lineToArray[1]);
                    customer.addFunds(Integer.valueOf(lineToArray[2]));
                    System.out.println("Funds added: " + customer.getAccountName() + " | Amount: " + lineToArray[2]);
                }catch (NullPointerException e){
                    System.out.println("Funds couldn't be added to account:" + lineToArray[1] +
                            " - account doesn't exist");
                }
            } else if (lineToArray[0].equals("NEW_CUSTOMER"))
            {
                if (shop.customerCollection.containsKey(lineToArray[1])){ //NINE NINE!
                    System.out.println("Customer could't be added - " + lineToArray[2] + " ID already found");
                }
                else {
                    if (lineToArray.length == 4){ //normal customer
                        Customer customer = new Customer(lineToArray[1], lineToArray[2], Integer.valueOf(lineToArray[3]));
                        shop.addCustomer(customer);
                        System.out.println("Customer added - " + lineToArray[2] + " | Type: standard");
                    } else if (lineToArray.length == 5) //student or no-dept staff
                    {
                        if (lineToArray[3].equals("STUDENT")){
                            StudentCustomer studCust = new StudentCustomer(lineToArray[1], lineToArray[2],
                                    Integer.valueOf(lineToArray[4]));
                            shop.addCustomer(studCust);
                            System.out.println("Customer added - " + lineToArray[2] + " | Type: student");
                        }else {
                            StaffCustomer staffCust = new StaffCustomer(lineToArray[1], lineToArray[2],
                                    Integer.valueOf(lineToArray[4]), lineToArray[3]);
                            shop.addCustomer(staffCust);
                            System.out.println("Customer added - " + lineToArray[2] +
                                    " | Type: staff " + "| school: other");
                        }
                    } else if (lineToArray.length == 6)
                    {
                        StaffCustomer staffCust = new StaffCustomer(lineToArray[1], lineToArray[2],
                                Integer.valueOf(lineToArray[5]), lineToArray[4]);
                        shop.addCustomer(staffCust);
                        System.out.println("Customer added - " + lineToArray[2] + " | Type: staff " + "| School: " +
                                lineToArray[4]);
                    }
                }
            }
        }
    };

    public static void main(String[] args) throws InvalidSnackException, InvalidCustomerException, IOException
    {
        File snackFile = new File("snacks.txt");
        File customerFile = new File("customers.txt");
        File transactionFile = new File("transactions.txt");
        SnackShop shop = Simulation.initialiseShop("SimpleSnack Waterside Cafe", snackFile, customerFile);
        Simulation.simulateShopping(shop, transactionFile);
        System.out.println(shop);
    }
}
