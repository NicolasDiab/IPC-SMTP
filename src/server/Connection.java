package server;

/**
 * @author NicolasDiab
 * @author GregoirePiat <gregoire.piat@etu.univ-lyon1.fr>
 */
public class Connection {

    public static void main(String args[]) {

        Server S1 = new Server(2222, "Thread 1");
        Thread thread1 = new Thread(S1);
        thread1.start();

        Server S2 = new Server(2223, "Thread 2");
        Thread thread2 = new Thread(S2);
        thread2.start();

        Server S3 = new Server(2224, "Thread 3");
        Thread thread3 = new Thread(S3);
        thread3.start();

        Server S4 = new Server(2225, "Thread 4");
        Thread thread4 = new Thread(S4);
        thread4.start();

    }
}
