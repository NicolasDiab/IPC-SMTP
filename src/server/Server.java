package server;

import java.io.IOException;
import java.net.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import utils.*;

import javax.net.SocketFactory;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 * @author NicolasDiab
 * @author GregoirePiat <gregoire.piat@etu.univ-lyon1.fr>
 */
public class Server implements Runnable{


    private Thread thread;
    private String threadName = "ThreadInstance";

    private final String SERVER_DOMAIN = "univ-lyon1.fr";

    /**
     * State constants
     */
    private final String STATE_LISTENING = "LISTENING";
    private final String STATE_AUTHORIZATION = "AUTHORIZATION";
    private final String STATE_AUTHENTICATED = "AUTHENTICATED";
    private final String STATE_MAIL_RECIPIENTS = "MAIL_RECIPIENTS";
    private final String STATE_MAIL_BODY = "MAIL_BODY";

    /**
     * Client's commands constants
     */
    private final String CMD_HELO = "HELO";
    private final String CMD_EHLO = "EHLO";
    private final String CMD_MAIL = "MAIL";
    private final String CMD_RCPT = "RCPT";
    private final String CMD_RSET = "RSET";
    private final String CMD_DATA = "DATA";
    private final String CMD_QUIT = "QUIT";

    /**
     * Success codes
     */
    private int CODE_220 = 220;
    private int CODE_250 = 250;
    private int CODE_354 = 354;

    /**
     * Error codes
     */
    private int CODE_500 = 500;
    private int CODE_501 = 501;
    private int CODE_502 = 502;

    private String MSG_HELLO = CODE_220 + " " + SERVER_DOMAIN + " SMTP";

    /**
     * Properties
     */
    private int port;
    private String state;
    private ServerSocket myconnex;

    // couche qui simplifie la gestion des échanges de message avec le client
    private Message messageUtils;

    public Server (int port, String threadName){

        this.port = port;
        this.threadName = threadName;

        //try {
            //ServerSocket myconnex = new ServerSocket(port,6);
            //SSLServerSocket secureSocket = null;
            //SSLServerSocketFactory factory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            //secureSocket = (SSLServerSocket) factory.createServerSocket(port);
            //secureSocket.setEnabledCipherSuites(factory.getSupportedCipherSuites());
            //myconnex = secureSocket;
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}
    }

    public void run(){

        System.out.println("Server started on " + threadName);
        this.state = STATE_LISTENING;

        try {
            System.out.println("Waiting for client");
            ServerSocket myconnex = new ServerSocket(port,6);
            Socket connexion = myconnex.accept();
            //SSLSocket connexion = null;
            //connexion = (SSLSocket) this.myconnex.accept();

            this.messageUtils = new Message(connexion);

            // @TODO connect user

            this.messageUtils.write(MSG_HELLO);
            System.out.println(MSG_HELLO);

            this.state = STATE_AUTHORIZATION;

            while (!connexion.isClosed()){
                String messageReceived = this.messageUtils.read("\r\n");
                String command = messageReceived.split("\\s+")[0].toUpperCase();
                String[] parameters = messageReceived.split("\\s+");
                String[] parameterArray = Arrays.copyOfRange(parameters, 1, parameters.length);
                System.out.println("Command " + command);

                switch(command) {
                    case CMD_EHLO:
                    case CMD_HELO:
                        switch(this.state){
                            //TODO voir les bons codes d'erreurs et les traiter de manière exhaustive
                            case STATE_AUTHORIZATION:
                                if (parameterArray.length <= 0)
                                    this.messageUtils.write(CODE_500 + " Incorrect parameters (lacking server.domain)");
                                else {
                                    if (!parameterArray[0].equals(SERVER_DOMAIN))
                                        this.messageUtils.write(CODE_500 + " Incorrect server.domain");
                                    else {
                                        this.messageUtils.write(CODE_250 + " server.domain says hello");
                                        this.state = STATE_AUTHENTICATED;
                                    }
                                }
                                break;
                            case STATE_AUTHENTICATED:
                                this.messageUtils.write(CODE_500 + " Already authenticated");
                                break;
                            case STATE_MAIL_RECIPIENTS:
                                this.messageUtils.write(CODE_500 + " Already authenticated");
                                break;
                            case STATE_MAIL_BODY:
                                this.messageUtils.write(CODE_500 + " Already authenticated");
                                break;
                        }
                        break;
                    case CMD_MAIL:
                        break;
                    case CMD_RCPT:
                        break;
                    case CMD_DATA:
                        break;
                    case CMD_RSET:
                        break;
                    case CMD_QUIT:
                        switch(this.state){
                            case STATE_AUTHORIZATION:
                                //this.messageUtils.write(MSG_OK + " SMTP server signing off");
                                // close the TCP connection
                                connexion.close();
                                // wait for a new client
                                this.run();
                                break;
                        }
                        break;
                    default:
                        this.messageUtils.write(CODE_500 + " invalid command");
                        break;
                }
            }

        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public void start () {
        System.out.println("Starting " +  threadName );
        if (thread == null) {
            thread = new Thread (this, threadName);
            thread.start ();
        }
    }
}
