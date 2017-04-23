package utils;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author GregoirePiat
 */
public class Mail {

    private int id;
    private User from;
    private ArrayList<User> to;
    private String subject;
    private Date date;
    private String body;

    /**
     * Mail constructor
     *
     */
    public Mail(int id, User from, ArrayList<User> to, String subject, Date date, String body){
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

    public ArrayList<User> getTo() {
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
}
