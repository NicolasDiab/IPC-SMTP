package utils;


import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogManager {

    private final String LOG_PATH = System.getProperty("user.dir") + "/" + "logs.txt";


    public LogManager(String errorName, String error){
        storeLog(errorName, error);
    }

    public LogManager(String errorName){
        storeLog(errorName, "");
    }

    public void storeLog(String errorName, String error){

        try {
            PrintWriter writer = new PrintWriter(new FileOutputStream(
                    new File(this.LOG_PATH),
                    true));
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            writer.append(dateFormat.format(date) + " ---- " + errorName + " -- " + error + "\n");
            writer.close();
        }
        catch (IOException exc){
            exc.printStackTrace();
        }
    }
}