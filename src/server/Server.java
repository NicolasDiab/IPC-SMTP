package server;

import java.io.File;
import java.io.IOException;
import java.net.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sun.rmi.runtime.Log;
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
public class Server implements Runnable {


    private Thread thread;
    private String threadName = "ThreadInstance";

    private final String SERVER_DOMAIN = "univ-lyon1.fr";
    private final String SERVER_WAREHOUSE = System.getProperty("user.dir") + "/tmp/warehouse/";

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
    private final String CMD_RCPT = "RCPT TO";
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

    private String MSG_HELLO = CODE_220 + " " + SERVER_DOMAIN + " SMTP READY";

    /**
     * Properties
     */
    private int port;
    private String state;
    private List<String> forwardPaths;
    private List<String> mailLines;

    /**
     * Connections
     */
    private Socket connexion;
    private ServerSocket myconnex;

    // couche qui simplifie la gestion des échanges de message avec le client
    private Message messageUtils;

    public Server(int port, String threadName) {

        this.port = port;
        this.threadName = threadName;
    }

    public void run() {

        System.out.println("Server started on " + threadName);
        this.state = STATE_LISTENING;
        forwardPaths = new ArrayList<>();
        mailLines = new ArrayList<>();

        try {
            System.out.println("Waiting for client");
            myconnex = new ServerSocket(port, 6);
            connexion = myconnex.accept();

            this.messageUtils = new Message(connexion);


            this.messageUtils.write(MSG_HELLO);

            this.state = STATE_AUTHORIZATION;

            while (!connexion.isClosed()) {
                String messageReceived = this.messageUtils.read("\r\n");
                String command = messageReceived.split("\\s+")[0].toUpperCase();
                String[] parameters = messageReceived.split("\\s+");
                String[] parameterArray = Arrays.copyOfRange(parameters, 1, parameters.length);
                System.out.println("Command " + command);

                switch (command) {
                    case CMD_EHLO:
                    case CMD_HELO:
                        switch (this.state) {
                            //TODO voir les bons codes d'erreurs et les traiter de manière exhaustive
                            case STATE_AUTHORIZATION:
                                if (parameterArray.length <= 0) {
                                    this.messageUtils.write(CODE_500 + " Incorrect parameters (lacking server.domain)");
                                    new ErrorManager(CODE_500 + "", "Incorrect parameters (lacking server.domain)");
                                } else {
                                    if (!parameterArray[0].equals(SERVER_DOMAIN)) {
                                        this.messageUtils.write(CODE_500 + " Incorrect server.domain");
                                        new ErrorManager(CODE_500 + "", "Incorrect server.domain");
                                    } else {
                                        this.messageUtils.write(CODE_250 + " " + SERVER_DOMAIN + " says hello");
                                        this.state = STATE_AUTHENTICATED;
                                    }
                                }
                                break;
                            case STATE_AUTHENTICATED:
                                this.messageUtils.write(CODE_500 + " Already authenticated");
                                new ErrorManager(CODE_500 + "", "Already authenticated");
                                break;
                            case STATE_MAIL_RECIPIENTS:
                                this.messageUtils.write(CODE_500 + " Already authenticated");
                                new ErrorManager(CODE_500 + "", "Already authenticated");
                                break;
                            case STATE_MAIL_BODY:
                                this.messageUtils.write(CODE_500 + " Already authenticated");
                                new ErrorManager(CODE_500 + "", "Already authenticated");
                                break;
                        }
                        break;
                    case CMD_MAIL:
                        switch (this.state) {
                            //TODO voir les bons codes d'erreurs et les traiter de manière exhaustive
                            case STATE_AUTHORIZATION:
                                break;
                            case STATE_AUTHENTICATED:
                                if (parameterArray.length <= 1) {
                                    // incorrect message size
                                    this.messageUtils.write(CODE_500 + " Incorrect parameters (lacking from and/or username)");
                                    new ErrorManager(CODE_500 + "",
                                            "Incorrect parameters (lacking from and/or username)");
                                } else {
                                    // Correct message size -> correct parameters ?
                                    if (!parameterArray[0].toUpperCase().equals("FROM")) {
                                        messageUtils.write(CODE_500 + parameterArray[0].toUpperCase() + " doesn't exist");
                                        /** @TODO set right code **/
                                        new ErrorManager(CODE_500 + "Wrong command",
                                                parameterArray[0].toUpperCase() + " doesn't exist");
                                        break;
                                    }

                                    // rigthly-formated command -> does the typed user exist ?
                                    if (!userExists(parameterArray[1])) {
                                        messageUtils.write(CODE_500 + " Unknown user");
                                        /** @TODO set right code **/
                                        new ErrorManager(CODE_500 + "", "Unknown user");
                                    } else {
                                        messageUtils.write("HELLO !");
                                        state = STATE_MAIL_RECIPIENTS;
                                        /** @TODO go to RCPT TO and mail transaction **/
                                    }
                                }
                                break;
                            case STATE_MAIL_RECIPIENTS:
                                this.messageUtils.write(CODE_500 + " You must be authenticated first");
                                new ErrorManager(CODE_500 + "", "You must be authenticated first");
                                break;
                            case STATE_MAIL_BODY:
                                this.messageUtils.write(CODE_500 + " You must be authenticated first");
                                new ErrorManager(CODE_500 + "", "You must be authenticated first");
                                break;
                        }
                        break;
                    case CMD_RCPT:
                        switch (this.state) {
                            // @TODO voir les bons codes d'erreurs et les traiter de manière exhaustive
                            case STATE_AUTHORIZATION:
                                this.messageUtils.write(CODE_500 + " You must be authenticated first");
                                new ErrorManager(CODE_500 + "", "You must be authenticated first");
                                break;
                            case STATE_AUTHENTICATED:
                                this.messageUtils.write(CODE_500 + " You must be authenticated first");
                                new ErrorManager(CODE_500 + "", "You must be authenticated first");
                                break;
                            case STATE_MAIL_RECIPIENTS:
                                if (parameterArray.length <= 1) {
                                    // incorrect message size
                                    this.messageUtils.write(CODE_500 + " Incorrect parameters (lacking from and/or username)");
                                    new ErrorManager(CODE_500 + "",
                                            "Incorrect parameters (lacking from and/or username)");
                                } else {
                                    // Correct message size -> correct parameters ?
                                    if (!parameterArray[0].toUpperCase().equals("TO"))
                                        break;
                                    if (!userExists(parameterArray[1])) {
                                        messageUtils.write(CODE_500 + " Unknown user");
                                        /** @TODO set right code **/
                                        new ErrorManager(CODE_500 + "", "Unknown user");
                                        break;
                                    } else {
                                        forwardPaths.add(parameterArray[1]);
                                        state = STATE_MAIL_RECIPIENTS;
                                        /** @TODO go to DATA when all recipients are set **/
                                    }
                                }
                                break;
                            case STATE_MAIL_BODY:
                                break;
                        }
                        break;
                    case CMD_DATA:
                        switch (this.state) {
                            //TODO voir les bons codes d'erreurs et les traiter de manière exhaustive
                            case STATE_AUTHORIZATION:
                                break;
                            case STATE_AUTHENTICATED:
                                break;
                            case STATE_MAIL_RECIPIENTS:
                                break;
                            case STATE_MAIL_BODY:
                                /** @TODO Data logic **/
                                if (parameterArray[1] == null) {
                                    messageUtils.write(CODE_500 + " Missing data");
                                    /** @TODO set right code **/
                                    new ErrorManager(CODE_500 + "", "Missing data");
                                    break;
                                } else {
                                    mailLines.add(parameterArray[1]);
                                }
                                break;
                        }
                        break;
                    case CMD_RSET:
                        break;
                    case CMD_QUIT:
                        switch (this.state) {
                            case STATE_AUTHORIZATION:
                                //this.messageUtils.write(MSG_OK + " SMTP server signing off");
                                // close the TCP connection
                                connexion.close();
                                // wait for a new client
                                //this.run();
                                break;
                        }
                        break;
                    default:
                        this.messageUtils.write(CODE_500 + " invalid command");
                        new ErrorManager(CODE_500 + "", "invalid command");
                        break;
                }
            }
        } catch (IOException ex) {
            System.out.println("Une exception inatendue est survenue !!!!!!!!!!!!!!!");
            ex.printStackTrace();
        }
    }

    public void start() {
        System.out.println("Starting " + threadName);
        if (thread == null) {
            thread = new Thread(this, threadName);
            thread.start();
        }
    }

    public boolean userExists(String userAddress) {

        /** Get username from user address **/
        String userName = userAddress.replaceAll("[<]", "");
        userName = userName.replaceAll("[>]", "");

        if (!Utils.emailValidator(userName))
            return false;

        userName = userAddress.substring(0, userAddress.indexOf('@'));

        String userStoragePath = this.SERVER_WAREHOUSE + userName + ".txt";

        File f = new File(userStoragePath);
        if (f.exists() && !f.isDirectory()) {
            return true;
        }

        return false;
    }
}
