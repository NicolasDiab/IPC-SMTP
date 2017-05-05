package server;

import sun.misc.MessageUtils;
import utils.Message;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author NicolasDiab
 * @author GregoirePiat <gregoire.piat@etu.univ-lyon1.fr>
 */
public class Connection {

    public static void main(String args[]) {


        //final int SERVER_PORT = 2222;
        //Socket connexion;
        //ServerSocket myconnex;
        //Message messageUtils;
        //Random rand = new Random();
//
        //List ports = new ArrayList<Integer>();
        //ports.add(2223);
        //ports.add(2224);
        //ports.add(2225);
        //ports.add(2226);





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

        Server S5 = new Server(2226, "Thread 4");
        Thread thread5 = new Thread(S5);
        thread5.start();



        //try {
        //    SSLServerSocket secureSocket = null;
        //    SSLServerSocketFactory factory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        //    secureSocket = (SSLServerSocket) factory.createServerSocket(SERVER_PORT);
        //    secureSocket.setEnabledCipherSuites(factory.getSupportedCipherSuites());
        //    myconnex = secureSocket;
        //    System.out.println("Waiting for client");
        //    connexion = secureSocket.accept();
        //    messageUtils = new Message(connexion);
//
        //    while (!connexion.isClosed()) {
        //        messageUtils.write((String) ports.get(rand.nextInt(ports.size())));
        //    }
//
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}

    }
}
