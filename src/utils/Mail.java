package utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author GregoirePiat
 */
public class Mail {

    private int id;
    private User from;
    private List<User> to;
    private String subject;
    private Date date;
    private String body;

    /**
     * Mail constructor
     *
     */
    public Mail(int id, User from, List<User> to, String subject, Date date, String body){
        this.id = id;
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.date = date;
        this.body = body;
    }

    public String toString(){
        StringBuilder mail = new StringBuilder();
        mail.append("Date: " + date.toString()+ "\r\n");
        mail.append("From: " + from.getName() + " <" + from.getMailAddress()+">\r\n");
        mail.append("Subject: "+ subject +"\r\n");
        mail.append("To: ");
        to.forEach((user)->mail.append(user.getName()+" <"+user.getMailAddress()+">, "));
        mail.substring(0,mail.length()-1);
        mail.append("\r\n"+body+"\r\n.\r\n");
        return mail.toString();
    }

    public User getFrom() {
        return from;
    }

    public List<User> getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }

    public Date getDate() {
        return date;
    }

    public String getBody() {
        return body;
    }

    public int getSize(){
        return this.toString().getBytes().length;
    }

    public int getId() {
        return id;
    }

    /**
     * Save message in local files foreach recipient
     */
    public void send() {
        // Just to be safe !
        if (this.getTo().isEmpty()) return;

        FileManager.storeMail(this);
    }
}
