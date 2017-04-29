package utils;

import java.io.*;
import java.util.ArrayList;

/**
 * @author GregoirePiat
 */
public class FileManager {

    public static final String CLIENT_STORAGE = System.getProperty("user.dir") + "/ressource/client/";
    public static final String SERVER_STORAGE = System.getProperty("user.dir") + "/tmp/warehouse/";


    /**
     * Store mail into user file
     * @param mail Mail
     */
    public static void storeMail(Mail mail){

        for(User u : mail.getTo()){
            // store in a different file whether it's for the client or for the server
            String filePath = SERVER_STORAGE + u.getName() + ".txt"; // path + file name + extension
            String mailString = "";

            File file = new File(filePath);
            file.getParentFile().mkdirs();

            mailString = mail.toString();

            try (Writer writer = new BufferedWriter(new FileWriter(filePath, true))) {
                writer.write(mailString);
            }
            catch (IOException e){
                System.out.println(e.getMessage());
            }
        }
    }
}
