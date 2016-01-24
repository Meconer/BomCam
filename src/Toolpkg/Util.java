/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Toolpkg;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 *
 * @author Mats
 */
public class Util {

    static String gCodeToString(GCode gCode) {
        String s;
        switch (gCode) {
            case G01 :
                s = "G01";
                break;
            case G02 : 
                s = "G02";
                break;
            case G03 : 
                s = "G02";
                break;
            default:
                s = "";
                break;
        }
        return s;
    }
    
    public enum GCode  {
        G01, G02, G03
    }
    
    public enum ArcDirection {
        CW, CCW
    }
    


    static double checkDouble(double origDouble, String newText ) {
        try {
            double d = Double.parseDouble(newText);
            return d;
        } catch ( Exception e ) {
            return origDouble;
        }
    }

    static int countMatches(String value, String string) {
        return string.length() - string.replace(value, "").length();
        
    }
    
    // Rounds the double value to n decimals
    public static String cncRound(double value, int n) {
        String format = "0.0" ;
        for ( int i = 1; i<n; i++ ) format += "#";
        DecimalFormat df = new DecimalFormat(format);
        df.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));
        try {
            String s = df.format(value);
            return s;
        } catch (Exception e) {
            return "cncRound Error";
        }
    }

    
}
