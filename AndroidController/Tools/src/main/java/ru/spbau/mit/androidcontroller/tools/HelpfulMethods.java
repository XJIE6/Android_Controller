package ru.spbau.mit.androidcontroller.tools;

/**
 * Created by n_buga on 13.03.16.
 */
public class HelpfulMethods {
    public static boolean isNumeric(String str)
    {
        for (char c : str.toCharArray())
        {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }
}
