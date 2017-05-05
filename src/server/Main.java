package server;

import utils.LogManager;

import javax.rmi.CORBA.Util;
import java.io.IOException;

/**
 * @author NicolasDiab
 * @author GregoirePiat <gregoire.piat@etu.univ-lyon1.fr>
 */
public class Main {
    public static void main(String [ ] args)
    {
        Server S1 = new Server(2222, "Thread 1");
        Thread thread1 = new Thread(S1);
        thread1.start();
    }
}