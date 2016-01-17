/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Toolpkg;

/**
 *
 * @author Mats
 */
class Util {

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
    
}
