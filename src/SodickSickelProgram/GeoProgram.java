/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SodickSickelProgram;

import Toolpkg.Chain;
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
public class GeoProgram {

    Chain chain;
    String fileName;
    ArrayList<String> program = new ArrayList<>();

    public void setChain(Chain chain) {
        this.chain = chain;
    }

    public void save() {
        FileChooser fc = new FileChooser();

        fc.setInitialFileName(fileName);
        String currentDir = System.getProperty("user.home") + File.separator;
        File file = new File(currentDir);
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
            } catch (IOException ex) {
                System.err.println(" Kan inte spara filen " + fileName);
                System.err.println(ex.getMessage());
            }

        }
    }
}
