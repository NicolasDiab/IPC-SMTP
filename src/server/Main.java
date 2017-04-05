package server;

import utils.LogManager;

import javax.rmi.CORBA.Util;

/**
 * @author NicolasDiab
 * @author GregoirePiat <gregoire.piat@etu.univ-lyon1.fr>
 */
public class Main {
    public static void main(String [ ] args)
    {
        LogManager logManager = new LogManager("Test Name", "Test error content");
        //Server server = new Server(2222);
        //server.run();
    }
}