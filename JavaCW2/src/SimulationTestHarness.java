import java.io.File;

public class SimulationTestHarness
{
    //Simple test harness class for Simulation, containing a main and then carrying out tests
    // only class for test harness because simulation already contains a main.
    public static void main(String[] args)
    {
        //Test 1
        System.out.println("Test 1\n");
        try
        {
            File snackFile = new File("snacks.txt");
            File customerFile = new File("customers.txt");
            File transactionFile = new File("transactions.txt");
            SnackShop shop = Simulation.initialiseShop("BSG", snackFile, customerFile);
            Simulation.simulateShopping(shop, transactionFile);
            System.out.println(shop);
            System.out.println("\nSuccess\n");
        }catch (Exception e){
            System.out.println(e);
            System.out.println("Fail\n");
        }

        //Test 2
        System.out.println("Test 2\n");
        try
        {
            //feed it wrong file
            File customerFile = new File("customers.txt");
            File transactionFile = new File("transactions.txt");
            SnackShop shop = Simulation.initialiseShop("JasonLinesMegaCorp", customerFile, customerFile);
            Simulation.simulateShopping(shop, transactionFile);
            System.out.println(shop);
            System.out.println("Fail\n");
        }catch (Exception e){
            System.out.println("\n" + e);
            System.out.println("Success\n");
        }
    }
}
