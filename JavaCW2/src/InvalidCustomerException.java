public class InvalidCustomerException extends Exception
{
    //Subclass of exception, used to be thrown when there is an issue with Customer, when Customer is
    // being initialised.
    public InvalidCustomerException(){}

    public InvalidCustomerException(String message){
        super(message);
    }
}
