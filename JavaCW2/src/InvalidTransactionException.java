public class InvalidTransactionException extends Exception
{
    //Subclass of Exception to handle errors in Transaction when processPurchase method and chargeAccount are used
    // message constructor not used and default used once because message not needed where it's thrown -
    // it's handled in a different class. Useful to have both available for scenarios like this
    public InvalidTransactionException(){}
    public InvalidTransactionException(String message){
        super(message);
    }
}
