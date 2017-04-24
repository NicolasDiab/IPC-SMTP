package utils;

public class ErrorManager {


    public ErrorManager(String errorType, String errorMessage){
        new LogManager(errorType, errorMessage);
    }
}
