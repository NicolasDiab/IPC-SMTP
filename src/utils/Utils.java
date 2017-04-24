package utils;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Utils {

    /**
     *
     * @param timestamp long
     * @return String
     */
    public static String computeChecksum(long timestamp){

        try {
            /** Compute base **/
            String s = Long.toString(timestamp) + getSharedSecret();

            System.out.println("SecretToHash:" + Long.toString(timestamp) + getSharedSecret());

            /** Compute MD5 checksum **/
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(s.getBytes(),0,s.length());
            String checksum = (new BigInteger(1, m.digest()).toString(16));
            return checksum;

        }
        catch (NoSuchAlgorithmException exc){
            System.out.println(exc.getMessage());
        }
        return null;
    }


    public static String getSharedSecret(){
        return "SharedSecretRandom";
    }

    public static void displayMessage(){
    System.out.println("\n" +
            " _  _  _  _  _  _  _       _  _  _                                          _  _  _  _    _           _  _  _  _  _  _  _  _  _  _   \n" +
            "(_)(_)(_)(_)(_)(_)(_)_  _ (_)(_)(_) _                                     _(_)(_)(_)(_)_ (_) _     _ (_)(_)(_)(_)(_)(_)(_)(_)(_)(_)_ \n" +
            "   (_)   (_)        (_)(_)         (_)                                   (_)          (_)(_)(_)   (_)(_)      (_)      (_)        (_)\n" +
            "   (_)   (_) _  _  _(_)(_)                       _  _  _  _  _           (_)_  _  _  _   (_) (_)_(_) (_)      (_)      (_) _  _  _(_)\n" +
            "   (_)   (_)(_)(_)(_)  (_)                      (_)(_)(_)(_)(_)            (_)(_)(_)(_)_ (_)   (_)   (_)      (_)      (_)(_)(_)(_)  \n" +
            "   (_)   (_)           (_)          _                                     _           (_)(_)         (_)      (_)      (_)           \n" +
            " _ (_) _ (_)           (_) _  _  _ (_)                                   (_)_  _  _  _(_)(_)         (_)      (_)      (_)           \n" +
            "(_)(_)(_)(_)              (_)(_)(_)                                        (_)(_)(_)(_)  (_)         (_)      (_)      (_) ");

    }
}
