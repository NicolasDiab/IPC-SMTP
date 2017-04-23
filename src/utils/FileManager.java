package utils;

import java.io.*;
import java.util.ArrayList;

/**
 * @author GregoirePiat
 */
public class FileManager {

    public static final String CLIENT_STORAGE = System.getProperty("user.dir") + "/ressource/client/";
    public static final String SERVER_STORAGE = System.getProperty("user.dir") + "/ressource/server/";


    /**
     * Store mail into user file
     * @param mail Mail
     */
    public static void storeMail(Mail mail, boolean forTheClient){

        for(User u : mail.getTo()){
            // store in a different file whether it's for the client or for the server
            String filePath = (forTheClient ? CLIENT_STORAGE : SERVER_STORAGE) + u.getName() + ".mail";
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

    /**
     * @param user User
     * @return a list of messages
     */
    public static ArrayList<Mail> retrieveMails(User user, boolean forTheClient) {
        String filePath = (forTheClient ? CLIENT_STORAGE : SERVER_STORAGE) + user.getName() + ".mail";
        return Utils.readMailsFromFile(filePath);
    }
}
