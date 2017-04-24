package utils;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

public class Utils {

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


    public static boolean emailValidator(String address){
        return Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
                .matcher(address)
                .matches();
    }
}
