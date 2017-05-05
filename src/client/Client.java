package client;

import utils.*;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;

public class Client {
    private int port;
    private String address;

    private Message messageUtils;

    private SSLSocket connexion;

    public Client(int port) {
        this.port = port;
    }

    /**
     * Connect to server
     * @return Socket
     */
    private boolean connectToServer() {
        System.out.println("Connecting to server");
        connexion = null;
        try {
            SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            connexion = (SSLSocket) sslsocketfactory.createSocket(InetAddress.getByName(this.address), this.port);
            connexion.setEnabledCipherSuites(sslsocketfactory.getSupportedCipherSuites());
            this.messageUtils = new Message(connexion);
            String answer = this.messageUtils.read("\r\n");
            Console.display(answer);
            return isSuccessful(answer);
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
        boolean connected = false;
        while(!connected) {
            connected = connection() && ehlo();
        }
        Console.display("Connexion effectuée avec succès.");
        boolean exit = false;
        while (!exit) {
            exit = commandSelection();
        }
    }

    private boolean connection(){
        Console.display("Saisir l'adresse du serveur SMTP :");
        this.address = Console.read();
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
                boolean validEmail = false;
                User uSender;
                ArrayList<User> uRecipients;
                do {
                    Console.display("Saisir l'adresse mail de l'envoyeur :");
                    String sender = Console.read().trim();
                    validEmail = Utils.emailValidator(sender);
                    uSender = new User(sender.split("@")[0],sender);
                    if(!validEmail)
                        Console.display("L'adresse mail <"+sender+"> n'est pas valide.");
                }while(!validEmail);
                do {
                    uRecipients = new ArrayList<>();
                    Console.display("Saisir l'adresse mail des destinataires (séparés d'une virgule) :");
                    String recipients = Console.read().trim();
                    String[] receptientsList = recipients.split(",");
                    for (String recepient : receptientsList) {
                        validEmail = Utils.emailValidator(recepient.trim());
                        uRecipients.add(new User(recepient.split("@")[0],recepient));
                        if(!validEmail)
                            Console.display("L'adresse mail <"+recepient+"> n'est pas valide.");
                    }
                }while(!validEmail);
                Console.display("Sujet du mail :");
                String subjet = Console.read();
                Console.display("Corps du mail :");
                String body = Console.read();
                Mail mail = new Mail(0,uSender, uRecipients,subjet,new Date(), body);
                //Mail mail = Utils.test();
                sendMail(mail);
                break;
            case 2:
                exit = quit();
                break;
            default:
                Console.display("Fonctionnalité non disponible.");
                break;
        }
        Console.display("Appuyez sur la touche 'Entrée' pour continuer.");
        Console.read();
        return exit;
    }

    private boolean ehlo(){
        messageUtils.write("EHLO univ-lyon1.fr");
        //for(int i = 0;i<5; i++){
            String answer = this.messageUtils.read("\r\n");
            Console.display(answer);
            if(!isSuccessful(answer))
                return false;
        //}
        return true;
    }

    private void sendMail(Mail mail){
        messageUtils.write("MAIL FROM: <"+mail.getFrom().getMailAddress().trim()+">");
        String answer = this.messageUtils.read("\r\n");
        Console.display(answer);
        if(!isSuccessful(answer)) {
            Console.display("Error sending the mail (MAIL FROM)");
            return;
        }
        for(User u : mail.getTo()) {
            messageUtils.write("RCPT TO: <" + u.getMailAddress().trim() + ">");
            answer = this.messageUtils.read("\r\n");
            Console.display(answer);
            if(!isSuccessful(answer)) {
                Console.display("Error sending the mail (RCPT TO <" + u.getMailAddress().trim() + ">)");
                //return;
            }
        }
        messageUtils.write("DATA");
        answer = messageUtils.read("\r\n");
        Console.display(answer);
        if(!isSuccessful(answer)) {
            Console.display("Error sending the mail (DATA)");
            return;
        }
        messageUtils.write(mail.getBody() + "\r\n.\r\n");
    }

    private boolean quit(){
        messageUtils.write("QUIT");
        String answer = this.messageUtils.read("\r\n");
        Console.display(answer);
        if(!isSuccessful(answer))
            return false;
        return true;
    }

    private boolean isSuccessful(String message){
        if(message=="") return false;
        String sub = message.substring(0,3);
        return sub.equals("250")||sub.equals("220")||sub.equals("221")||sub.equals("252")||sub.equals("251")||sub.equals("354");
    }

}
