package server;

import utils.Utils;

import javax.rmi.CORBA.Util;

/**
 * @author NicolasDiab
 * @author GregoirePiat <gregoire.piat@etu.univ-lyon1.fr>
 */
public class Main {
    public static void main(String [ ] args)
    {
        Server server = new Server(2222);
        server.run();
    }
}