import java.io.IOException;

public class Food extends Snack
{
    private boolean hotFood;
    public final Double heatSurcharge = 0.1;
    public Food(String snackID, String snackName, String hotFood, int basePrice) throws InvalidSnackException
    {
        super(snackID, snackName, basePrice);
        if (hotFood.equals("hot")){
            this.hotFood = true;
        } else if (hotFood.equals("cold"))
        {
            this.hotFood = false;
        }
        else{
            throw new InvalidSnackException("heat value invalid - only enter hot or cold");
        }

        char[] charArr = snackID.toCharArray();
        if (charArr[0] != 'F'){
            throw new InvalidSnackException("ID invalid - Food snack ID doesn't begin with F");
        }
    }

    public String isHotFood()
    {
        if (hotFood){
            return "hot";
        }
        else {
            return "cold";
        }
    }

    public Double getHeatSurcharge()
    {
        return heatSurcharge;
    }

    @Override
    public int calculatePrice()
    {
        if (hotFood){
            double tempVal = Math.round(getBasePrice() * (1 + heatSurcharge));
            //round used instead of ceil because ceil incorrectly rounds up for a few values containing
            // floating point precision errors (e.g. rounding 198.00000000000003 to 199)
            return (int) tempVal;
        }
        else{
            double tempVal = getBasePrice();
            return (int) tempVal;
        }
    }

    @Override
    public String toString()
    {
        return "Snack ID: " + this.getSnackID() + "\n" +
                "Snack name: " + getSnackName() + "\n" +
                //"BASE price: " + this.getBasePrice() + "\n" +
                "price: " + this.calculatePrice() + "\n" +
                "food heat: " + this.isHotFood() + "\n" +
                "heat surcharge: " + heatSurcharge * 100 + "%";
    }

    public static void main(String[] args)
    {
        //Test 1
        System.out.println("Test 1\n");
        try
        {
            Food food = new Food("F/2337652", "Pure Uranium", "cold", 1);
            System.out.println(food);
            System.out.println("\nSuccess\n");
        }catch (Exception e){
            System.out.println(e);
            System.out.println("\nFail\n");
        }

        //Test 2
        System.out.println("Test 2\n");
        try
        {
            //invalid type ID
            Food food = new Food("K/4837999", "Lava", "hot", 1);
            System.out.println(food);
            System.out.println("\nFail\n");
        }catch (Exception e){
            System.out.println(e);
            System.out.println("\nSuccess\n");
        }

        //Test 3
        System.out.println("Test 3\n");
        try
        {
            //invalid heat ID
            Food food = new Food("F/1011101", "Dark Matter", "don't know", 1);
            System.out.println(food);
            System.out.println("\nFail\n");
        }catch (Exception e){
            System.out.println(e);
            System.out.println("\nSuccess\n");
        }
    }
}
