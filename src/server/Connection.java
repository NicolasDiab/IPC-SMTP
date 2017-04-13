package server;


/**
 * @author NicolasDiab
 * @author GregoirePiat <gregoire.piat@etu.univ-lyon1.fr>
 */
public class Connection {

    public static void main(String args[]) {
        Server S1 = new Server(2222);
        S1.start();

        Server S2 = new Server(2223);
        S1.start();

        Server S3 = new Server(2224);
        S1.start();

        Server S4 = new Server(2225);
        S1.start();


    }

}
