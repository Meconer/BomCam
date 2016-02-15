/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SodickCNCProgram;

import Geometry.Point;
import Toolpkg.Util;

/**
 *
 * @author Mats
 */
public class CNCCodeLine {
    private final String line;
    private final Util.GCode gCode;
    private final Point lastPoint;

    public CNCCodeLine(String line, Util.GCode gCode, Point lastPoint ) {
        this.line = line;
        this.gCode = gCode;
        this.lastPoint = lastPoint;
    }

    public String getLine() {
        return line;
    }

    public Util.GCode getgCode() {
        return gCode;
    }
    
    public Point getLastPoint() {
        return lastPoint;
    }
    
    
}
