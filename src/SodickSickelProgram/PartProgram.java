package SodickSickelProgram;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Program för att representera ett program för att dela ämnet på hälften.
 * @author Mats
 */
public class PartProgram extends StraightFineProgram {
    
    void build(String fileName) {
        this.fileName = fileName;
        
        addHeader();
        addMainProgram();
        try {
            addSubs();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

}
