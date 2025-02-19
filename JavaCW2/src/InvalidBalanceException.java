public class InvalidBalanceException extends Exception
{
    //A class that extends Exception to be thrown for balance problems when initialising customer accounts
    // or during transactions
    public InvalidBalanceException(){}

    public InvalidBalanceException(String message){
        super(message);
    }
}
