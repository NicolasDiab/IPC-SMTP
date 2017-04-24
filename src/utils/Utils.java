package utils;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.regex.Pattern;

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

    public static boolean emailValidator(String address){
        return Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
                .matcher(address)
                .matches();
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
