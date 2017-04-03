package utils;

import javafx.scene.input.DataFormat;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author GregoirePiat
 */
public class Mail {

    private User fromHeader;
    private ArrayList<User> toHeader;
    private String subjectHeader;
    private Date dateHeader;
    private String body;

    /**
     * Mail constructor
     *
     */
    public Mail(User from, ArrayList<User> to, String subject, Date date, String body){
        this.fromHeader = from;
        this.toHeader = to;
        this.subjectHeader = subject;
        this.dateHeader = date;
        this.body = body;
    }

    public String toString(){
        StringBuilder mail = new StringBuilder();
        mail.append("Date: " + dateHeader.toString()+ "\r\n");
        mail.append("From: " + fromHeader.getName() + " <" + fromHeader.getMailAddress()+">\r\n");
        mail.append("Subject: "+ subjectHeader+"\r\n");
        toHeader.forEach((user)->mail.append("To: "+user.getName()+" <"+user.getMailAddress()+"> ,"));
        mail.substring(0,mail.length()-1);
        mail.append("\r\n"+body+"\r\n.\r\n");
        return mail.toString();
    }
}
