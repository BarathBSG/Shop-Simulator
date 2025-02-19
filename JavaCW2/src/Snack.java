public abstract class Snack
{
    //Abstract class to represent the link between Food and Drink - different types of Snack.
    // Constructor contains simple input validation to be inherited by subclasses and to be expanded on.
    private String snackID;
    private String snackName;
    private int basePrice;

    public Snack(String snackID, String snackName, int basePrice) throws InvalidSnackException
    {
        this.snackID = snackID;
        this.snackName = snackName;
        this.basePrice = basePrice;


        char[] charArr = snackID.toCharArray();
        if (charArr[1] != '/'){
            throw new InvalidSnackException("input invalid - no '/' in ID for type");
        } else if (charArr.length != 9)
        {
            throw new InvalidSnackException("input invalid - ID length not 9");
        } else if (basePrice < 0){
            throw new InvalidSnackException("input invalid - base price negative");
        }
        for (int i = 2; i < 9; i++)
        {
            if (!(Character.isDigit(charArr[i]))){
                throw new InvalidSnackException("ID invalid - number sequence contains non-digits");
            }
        }
    }

    public String getSnackID()
    {
        return snackID;
    }

    public String getSnackName()
    {
        return snackName;
    }

    public int getBasePrice()
    {
        return basePrice;
    }

    public abstract int calculatePrice();
}
