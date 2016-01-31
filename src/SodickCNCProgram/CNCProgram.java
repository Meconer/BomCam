/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SodickCNCProgram;


import SodickBomProgram.BomProgram;
import SodickBomProgram.Constants;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.prefs.Preferences;
import javafx.stage.FileChooser;

/**
 *
 * @author matsandersson
 */
public class CNCProgram {

    protected String fileName;
    protected ArrayList<String> program = new ArrayList<>();
    
    Preferences prefs = Preferences.userNodeForPackage(BomProgram.class);

    public boolean save(  ) {
        FileChooser fc = new FileChooser();

        File file;
        fc.setInitialFileName(fileName);
        String workingDir = prefs.get(Constants.DIRECTORY_PREFS_KEY, "");
        if ( !workingDir.equals("") ) {
            file = new File( workingDir );
            fc.setInitialDirectory(file);
        }
        //Show save file dialog
        file = fc.showSaveDialog(null);

        if (file != null) {
            try {
                FileWriter fileWriter;

                fileWriter = new FileWriter(file);
                Iterator<String> pIter = program.iterator();

                while (pIter.hasNext()) {
                    fileWriter.write(pIter.next() + "\r\n");
                }

                fileWriter.close();
                String filePath = file.getAbsolutePath();
                prefs.put(Constants.DIRECTORY_PREFS_KEY, filePath.substring(0, filePath.lastIndexOf(File.separator)));
                return true;
            } catch (IOException ex) {
                System.err.println(" Kan inte spara filen " + fileName);
                System.err.println(ex.getMessage());
            }

        }
        return false;
    }
}
