package server.handlers.exceptions;

public class AlreadyTakenException extends Exception{

    public AlreadyTakenException(String message){
        super(message);
    }
}
