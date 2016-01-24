/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Toolpkg;

import SodickSickelProgram.CNCCodeLine;

/**
 *
 * @author Mats
 */
public class Line extends Geometry {

    private final double xStart, yStart, xEnd, yEnd;

    public Line(double xStart, double yStart, double xEnd, double yEnd) {
        this.xStart = xStart;
        this.yStart = yStart;
        this.xEnd = xEnd;
        this.yEnd = yEnd;
    }

    public double getxStart() {
        return xStart;
    }

    public double getyStart() {
        return yStart;
    }

    public double getxEnd() {
        return xEnd;
    }

    public double getyEnd() {
        return yEnd;
    }

    @Override
    public Point getStartPoint() {
        return new Point( xStart, yStart );
    }

    @Override
    public Point getEndPoint() {
        return new Point( xEnd, yEnd );
    }

    Geometry getReversedLine() {
        return new Line(xEnd,yEnd,xStart,yStart);
    }

    @Override
    public CNCCodeLine geoToCNCCode(Point lastPoint, Util.GCode lastGCode) {
        String line = "";
        String addSpace = "";
        if (lastGCode != Util.GCode.G01 ) { 
            line += "G01";
            addSpace = " ";
        }
        if ( Math.abs( xEnd - lastPoint.getxPoint() ) > 0.00008 ) {
            line += addSpace + "X" + Util.cncRound( xEnd);
            addSpace = " ";
        }
        if ( Math.abs( yEnd - lastPoint.getyPoint() ) > 0.00008 ) line += addSpace + "Y" + Util.cncRound( yEnd);
        
        return new CNCCodeLine(line, Util.GCode.G01, new Point( xEnd,yEnd ) );
    }


}
