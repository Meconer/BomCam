/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SodickSickelProgram;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javafx.stage.FileChooser;

/**
 *
 * @author matsandersson
 */
public class CNCProgram {

    String fileName;
    ArrayList<String> program = new ArrayList<>();

    public void save() {
        FileChooser fc = new FileChooser();

        fc.setInitialFileName(fileName);
        File file = new File(CurrentSettings.getInstance().getCurrentDirectory());
        fc.setInitialDirectory(file);
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
                CurrentSettings.getInstance().setCurrentDirectory( filePath.substring(0, filePath.lastIndexOf(File.separator)));
            } catch (IOException ex) {
                System.err.println(" Kan inte spara filen " + fileName);
                System.err.println(ex.getMessage());
            }

        }
    }
}
