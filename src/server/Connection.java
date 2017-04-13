package server;


/**
 * @author NicolasDiab
 * @author GregoirePiat <gregoire.piat@etu.univ-lyon1.fr>
 */
public class Connection {

    public static void main(String args[]) {
        Server S1 = new Server(2222, "Thread 1");
        S1.start();

        Server S2 = new Server(2223, "Thread 2");
        S2.start();

        Server S3 = new Server(2224, "Thread 3");
        S3.start();

        Server S4 = new Server(2225, "Thread 4");
        S4.start();


    }

}
