public class InvalidSnackException extends Exception
{
    //Class that extends Exception to be thrown when there is an issue about Snack when being initialised.
    //leaving default constructor available but unused for usability - good practice..
    public InvalidSnackException(){}
    public InvalidSnackException(String message){
        super(message);
    }
}
