import java.io.IOException;

public class Drink extends Snack
{
    //Subclass of Snack representing Drinks, with unique implementation of calculatePrice to account for
    // sugar tax and toString to show inherited fields alongside specialised fields.
    private enum SugarLevel {high, low, none}
    public SugarLevel sugarLevel;

    public Drink(String snackID, String snackName, int basePrice) throws InvalidSnackException
    {
        super(snackID, snackName, basePrice);
        sugarLevel = SugarLevel.none;

        char[] charArr = snackID.toCharArray();
        if (charArr[0] != 'D'){
            throw new InvalidSnackException("ID invalid - Drink snack ID doesn't begin with D");
        }
    }
    public Drink(String snackID, String snackName, String sl, int basePrice) throws InvalidSnackException
    {
        super(snackID, snackName, basePrice);
        this.sugarLevel = SugarLevel.valueOf(sl);

        char[] charArr = snackID.toCharArray();
        if (charArr[0] != 'D'){
            throw new InvalidSnackException("ID invalid - Drink snack ID doesn't begin with D");
        }
    }

    public SugarLevel getSugarLevel()
    {
        return sugarLevel;
    }

    @Override
    public int calculatePrice()
    {
        if (sugarLevel == SugarLevel.high){
            int tempVal = getBasePrice() + 24;
            return tempVal;
        }
        else if (sugarLevel == SugarLevel.low){
            int tempVal = getBasePrice() + 18;
            return tempVal;
        }
        else {
            int tempVal = getBasePrice();
            return tempVal;
        }
    }

    @Override
    public String toString()
    {
        return "Snack ID: " + this.getSnackID() + "\n" +
                "Snack name: " + this.getSnackName() + "\n" +
                "Sugar level: " + sugarLevel + "\n" +
                //"BASE price: " + this.getBasePrice() + "\n" +
                "price: " + this.calculatePrice() + "\n";
    }

    public static void main(String[] args)
    {
        //Test 1
        System.out.println("Test 1\n");
        try
        {
            Drink drink = new Drink("D/2337652", "Liquid Nitrogen", "high", 999999);
            System.out.println(drink);
            System.out.println("\nSuccess\n");
        }catch (Exception e){
            System.out.println(e);
            System.out.println("\nFail\n");
        }

        //Test 2
        System.out.println("Test 2\n");
        try
        {
            //invalid snack type char for ID
            Drink drink = new Drink("X/2337652", "Diesel", "low", 999999);
            System.out.println(drink);
            System.out.println("\nFail\n");
        }catch (Exception e){
            System.out.println(e);
            System.out.println("\nSuccess\n");
        }
    }
}
