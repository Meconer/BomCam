/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Toolpkg;

import SodickSickelProgram.CNCCodeLine;
import static Toolpkg.Util.cncRound;

/**
 *
 * @author Mats
 */
public class Point extends Geometry {

    private final double xPoint, yPoint;

    public Point(double x, double y ) {
        this.xPoint = x;
        this.yPoint = y;
    }

    public double getxPoint() {
        return xPoint;
    }

    public double getyPoint() {
        return yPoint;
    }

    @Override
    public Point getStartPoint() {
        return new Point( xPoint,yPoint);
    }

    @Override
    public Point getEndPoint() {
        return getStartPoint();
    }

    public String toCNCString(String pref1, String pref2) {
        String s = pref1 + cncRound( xPoint ) + " " + pref2 + cncRound( yPoint );
        return s;
    }

    @Override
    public CNCCodeLine geoToCNCCode(Point lastPoint, Util.GCode lastGCode ) {
        return new CNCCodeLine( "Point kan inte kodas", Util.GCode.G01, this);
    }

    public double squaredDistanceToPoint( Point otherPoint ) {
        return ( Math.pow(xPoint - otherPoint.xPoint, 2 ) + Math.pow(yPoint - otherPoint.yPoint, 2) );
    }
}
