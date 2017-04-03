package client;

import utils.Console;
import utils.Message;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.net.InetAddress;

public class Client {
    private int port;
    private String address;

    private Message messageUtils;

    public Client(int port) {
        this.port = port;
    }

    /**
     * Connect to server
     * @return Socket
     */
    public boolean connectToServer() {
        System.out.println("Connecting to server");
        SSLSocket connexion = null;
        try {
            SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            connexion = (SSLSocket) sslsocketfactory.createSocket(InetAddress.getByName(this.address), this.port);
            connexion.setEnabledCipherSuites(sslsocketfactory.getSupportedCipherSuites());
            this.messageUtils = new Message(connexion);
            return connexion.isConnected();
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Launch Client
     *
     */
    public void launch(){
        String msgHello = this.messageUtils.read("\r\n");

        boolean connected = false;
        while(!connected) {
            connected = connection();
        }
        boolean exit = false;
        while (!exit) {
            exit = commandSelection();
        }
    }

    private boolean connection(){


        Console.display("Saisir l'adresse du serveur SMTP :");
        boolean connected = connectToServer();
        if(!connected)
            Console.display("La connexion a échoué.");
        return connected;
    }

    private boolean commandSelection(){
        boolean exit = false;
        int command = Console.displayInt("Entrer une fonctionnalité : \n"
                + "1. Envoyer un message\n"
                + "2. Quitter\n");
        switch (command) {
            case 1:
                sendMail();
                break;
            case 2:
                quit();
                break;
            default:
                Console.display("Fonction non disponible.");
                break;
        }
        Console.display("Appuyez sur la touche 'Entrée' pour continuer.");
        Console.read();
        return exit;
    }

    private void sendMail(){

    }

    private void quit(){

    }

}
