/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Toolpkg;

import SodickCNCProgram.CNCCodeLine;

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
    
    public Line( Point start, Point end) {
        xStart = start.getxPoint();
        yStart = start.getyPoint();
        xEnd = end.getxPoint();
        yEnd = end.getyPoint();
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
            line += addSpace + "X" + Util.cncRound( xEnd, 4);
            addSpace = " ";
        }
        if ( Math.abs( yEnd - lastPoint.getyPoint() ) > 0.00008 ) line += addSpace + "Y" + Util.cncRound( yEnd, 4);
        
        return new CNCCodeLine(line, Util.GCode.G01, new Point( xEnd,yEnd ) );
    }

    double getAngle() {
        return Math.atan2( yEnd - yStart, xEnd - xStart );
    }

    public static Line getLineAtAngle( Point startPoint, double angle, double length) {
        Point endPoint = new Point(startPoint.getxPoint() + length * Math.cos(angle), startPoint.getyPoint() + length * Math.sin(angle));
        return new Line( startPoint, endPoint);
    }
    
    public Line getParallelLine( double distance, offsetSide side) {
        Line unityVector = getUnity();
        double rotationAngle = Math.PI/2;
        if (side == offsetSide.RIGHT ) rotationAngle = -rotationAngle;
        Line rotatedUnityVector = unityVector.getRotatedLine( rotationAngle );
        Line scaleLine = rotatedUnityVector.scaleLine(distance);
        return getLineAtAngle(scaleLine.getEndPoint(), getAngle(), getLength() );
    }
    
    public Line getUnity() {
        return getLineAtAngle(getStartPoint(), getAngle(), 1.0 );
    }
    
    public Line scaleLine( double scaleFactor ) {
        return getLineAtAngle(getStartPoint(), getAngle(), getLength() * scaleFactor );
    }
    
    public Line getRotatedLine( double rotAngle ) {
        return getLineAtAngle(getStartPoint(), rotAngle + getAngle(), getLength());
    }

    double getRevAngle() {
        return Math.atan2( yStart - yEnd, xStart - xEnd );
    }

    private double getLength() {
        return getStartPoint().pointDistance(getEndPoint());
    }

}
