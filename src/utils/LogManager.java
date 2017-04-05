package utils;


import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Date;

public class LogManager {

    private final String LOG_PATH = System.getProperty("user.dir") + "logs.txt";

    public LogManager(String errorName, String error){
        storeLog(errorName, error);
    }


    public void storeLog(String errorName, String error){

        try {
            PrintWriter writer = new PrintWriter(this.LOG_PATH);
            LocalDateTime now = LocalDateTime.now();
            writer.append(errorName + " " + error + " " + now.toString());
        }
        catch (IOException exc){
            exc.printStackTrace();
        }
    }
}
