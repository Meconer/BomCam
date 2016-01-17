/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Toolpkg;

import java.awt.geom.Point2D;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author mats
 */
class DxfFile {

    List<String> dxfStringList = new LinkedList();
    Path path;
    BufferedWriter bufferedWriter;

    private boolean createFile() {
        JFileChooser jfc = new JFileChooser();
        boolean okToCreate = false;
        int returnVal = jfc.showSaveDialog(null);
        if (returnVal == JFileChooser.CANCEL_OPTION) {
            return false;
        } else {
            path = Paths.get(jfc.getSelectedFile().toURI());
            if (Files.exists(path)) {
                int result = JOptionPane.showConfirmDialog(null, "Filen finns. Skriva över?", "Filen finns", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    okToCreate = true;
                }
            } else {
                okToCreate = true;
            }
            if (okToCreate) {
                try {
                    bufferedWriter = Files.newBufferedWriter(path, StandardCharsets.UTF_8);
                    return true;
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, ex.getLocalizedMessage());
                    return false;
                }

            }
        }
        return false;
    }


    void addArc(Point2D.Double center, double arcStart, double arcEnd, double radie) {
        dxfStringList.add("ARC");
        dxfStringList.add("  8");
        dxfStringList.add("0");
        dxfStringList.add(" 10");
        dxfStringList.add(String.format( Locale.US, " %f", center.x));
        dxfStringList.add(" 20");
        dxfStringList.add(String.format( Locale.US, " %f", center.y));
        dxfStringList.add(" 40");
        dxfStringList.add(String.format( Locale.US, " %f", radie));
        dxfStringList.add(" 50");
        dxfStringList.add(String.format( Locale.US, " %f", arcStart));
        dxfStringList.add(" 51");
        dxfStringList.add(String.format( Locale.US, " %f", arcEnd));
        dxfStringList.add(" 0");
    }

    void addLine(Point2D.Double startPoint, Point2D.Double endPoint) {
        dxfStringList.add("LINE");
        dxfStringList.add("  8");
        dxfStringList.add("0");
        dxfStringList.add(" 10");
        dxfStringList.add(String.format( Locale.US, " %f", startPoint.x));
        dxfStringList.add(" 20");
        dxfStringList.add(String.format( Locale.US, " %f", startPoint.y));
        dxfStringList.add(" 11");
        dxfStringList.add(String.format( Locale.US, " %f", endPoint.x));
        dxfStringList.add(" 21");
        dxfStringList.add(String.format( Locale.US, " %f", endPoint.y));
        dxfStringList.add(" 0");
    }

    void addHeader() {
        dxfStringList.add("  0");
        dxfStringList.add("SECTION");
        dxfStringList.add("  2");
        dxfStringList.add("ENTITIES");
        dxfStringList.add("  0");
    }

    void saveFile() {
        if (createFile()) {
            try {
                Iterator<String> i = dxfStringList.iterator();
                while (i.hasNext()) {
                    bufferedWriter.write(i.next() + "\r\n");
                }
            } catch (Exception e) {

            } finally {
                //Close the BufferedWriter
                try {
                    if (bufferedWriter != null) {
                        bufferedWriter.flush();
                        bufferedWriter.close();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    void addEnd() {
        dxfStringList.add("ENDSEC");
        dxfStringList.add("  0");
        dxfStringList.add("EOF");
    }
}
