package server;

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
public class Server {

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

    private String MSG_HELLO = "SMTP server ready";

    /**
     * Properties
     */
    private int port;
    private String state;
    private String checksumSent;
    private String timestampSent;
    private ServerSocket myconnex;

    // couche qui simplifie la gestion des échanges de message avec le client
    private Message messageUtils;

}
