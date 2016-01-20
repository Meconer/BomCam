/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SodickSickelProgram;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mats
 */
class StraightFineProgram extends StraightProgram {
    
    private final static String HEADER_FILE_NAME = "SodickSickelProgram/straight1.txt";
    
    void addHeader() {
        String header;
        
        ClassLoader cl = this.getClass().getClassLoader();
        InputStream is = cl.getResourceAsStream(HEADER_FILE_NAME);
        BufferedReader br = new BufferedReader( new InputStreamReader(is));
        String line;
        try {
            while ( ( line = br.readLine() ) != null ) {
                program.add(line);
            }
        } catch (IOException ex) {
            System.err.println("Fel vid läsning av resource : " + HEADER_FILE_NAME);
        }
    }
}
