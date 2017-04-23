package utils;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

public class Utils {

    public static ArrayList<Mail> readMailsFromFile(String filepath){
        ArrayList<Mail> mails = new ArrayList<Mail>();
        System.out.println(filepath);
        try {
            InputStream is = new FileInputStream(filepath);
            BufferedReader buf = new BufferedReader(new InputStreamReader(is));

            String line = buf.readLine();
            StringBuilder sb = new StringBuilder();

            while(line != null){
                sb.append(line).append("\n");
                line = buf.readLine();
            }

            String fileAsString = sb.toString();
            //System.out.println("Contents : " + fileAsString);

            /* Split each line */
            String[] splitMessage = fileAsString.split("\\r\\n|\\n|\\r");
            ArrayList<String> newMail = new ArrayList<String>();

            // REVOIR SI NECESSAIRE
            /*
            for (String split : splitMessage){
                if (!split.equals("")){
                    newMail.add(split);
                    if (split.startsWith(".")){
                    // Check that headers are enough
                        if (newMail.size() > 5){
                            ArrayList<String> headers = new ArrayList<String>();
                            headers.add(newMail.get(0));
                            headers.add(newMail.get(1));
                            headers.add(newMail.get(2));
                            headers.add(newMail.get(3));
                            headers.add(newMail.get(4));

                            String body = "";
                            for (int i = 5; i < newMail.size(); ++ i)
                                body += newMail.get(i) + "\n";

                        // Create a new message
                            Mail mail = new Mail();
                            mails.add(mail);
                            newMail.clear();

                        }
                    }
                }
            }
            */

        }
        catch (Exception e){
            e.printStackTrace();
        }

        return mails;
    }

    public static void displayMessage(){
    System.out.println("\n" +
            " _  _  _  _  _  _  _       _  _  _                                          _  _  _  _    _           _  _  _  _  _  _  _  _  _  _   \n" +
            "(_)(_)(_)(_)(_)(_)(_)_  _ (_)(_)(_) _                                     _(_)(_)(_)(_)_ (_) _     _ (_)(_)(_)(_)(_)(_)(_)(_)(_)(_)_ \n" +
            "   (_)   (_)        (_)(_)         (_)                                   (_)          (_)(_)(_)   (_)(_)      (_)      (_)        (_)\n" +
            "   (_)   (_) _  _  _(_)(_)                       _  _  _  _  _           (_)_  _  _  _   (_) (_)_(_) (_)      (_)      (_) _  _  _(_)\n" +
            "   (_)   (_)(_)(_)(_)  (_)                      (_)(_)(_)(_)(_)            (_)(_)(_)(_)_ (_)   (_)   (_)      (_)      (_)(_)(_)(_)  \n" +
            "   (_)   (_)           (_)          _                                     _           (_)(_)         (_)      (_)      (_)           \n" +
            " _ (_) _ (_)           (_) _  _  _ (_)                                   (_)_  _  _  _(_)(_)         (_)      (_)      (_)           \n" +
            "(_)(_)(_)(_)              (_)(_)(_)                                        (_)(_)(_)(_)  (_)         (_)      (_)      (_) ");

    }


    public static Mail test(){
        String userDir = System.getProperty("user.dir");
        System.out.println(userDir);
        ArrayList<User> to = new ArrayList<>();
        User user1 = new User("user1", "user1@test1.fr");
        User user2 = new User("user2", "user2@test1.fr");
        User user3 = new User("user3", "user3@test2.fr");
        User user4 = new User("user4", "user4@test2.fr");

        to.add(user2);
        to.add(user3);
        to.add(user4);

        Mail mail = new Mail(0, user1, to, "Hello", new Date(), "Hello server, \r\nThis is a message just to say hello.\r\nSo, \"Hello\".");
        return mail;
    }

    public static boolean emailValidator(String address){
        return Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
                .matcher(address)
                .matches();
    }
}
