package server;


import utils.Mail;
import utils.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author NicolasDiab
 * @author GregoirePiat <gregoire.piat@etu.univ-lyon1.fr>
 */
public class Connection {

    public static void main(String args[]) {

        //List<User> recipients = new ArrayList<>();
        //recipients.add(new User("gregoire", "gregoire.piat@etu.univ-lyon1.fr"));
//
        //Mail mail = new Mail(1, new User("gregoire", "gregoire.piat@etu.univ-lyon1.fr"), recipients,
        //        "Test", new Date(), "Something");
//
        //mail.send();

        Server S1 = new Server(2222, "Thread 1");
        S1.start();

        //Server S2 = new Server(2223, "Thread 2");
        //S2.start();
//
        //Server S3 = new Server(2224, "Thread 3");
        //S3.start();
//
        //Server S4 = new Server(2225, "Thread 4");
        //S4.start();


    }

}
