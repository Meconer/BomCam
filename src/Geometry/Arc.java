package Geometry;

import SodickCNCProgram.CNCCodeLine;
import Toolpkg.Constants;
import Toolpkg.Util;
import Toolpkg.Vector;

/**
 *
 * @author Mats
 */
public class Arc extends Geometry {
    
    private final double centerX, centerY, radius, startAngle, endAngle;
    private final Util.ArcDirection direction;

    public Arc(double centerX, double centerY, double radius, double startAngle, double endAngle, Util.ArcDirection direction ) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
        this.startAngle = startAngle;
        this.endAngle = endAngle;
        this.direction = direction;
    }

    public double getCenterX() {
        return centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    public double getRadius() {
        return radius;
    }

    public double getStartAngle() {
        return startAngle;
    }

    public double getEndAngle() {
        return endAngle;
    }

    @Override
    public Point getStartPoint() {
        double x = centerX + radius * Math.cos( Math.toRadians(startAngle ));
        double y = centerY + radius * Math.sin( Math.toRadians(startAngle) );
        return new Point( x, y );
    }
    
    @Override
    public Point getEndPoint() {
        return new Point(
                centerX + radius * Math.cos( Math.toRadians(endAngle )), 
                centerY + radius * Math.sin( Math.toRadians(endAngle )));
    }

    public Util.ArcDirection getDirection() {
        return direction;
    }
    
    public Arc getReversedArc() {
        Util.ArcDirection newDirection = Util.ArcDirection.CCW;
        if ( direction == Util.ArcDirection.CCW ) newDirection = Util.ArcDirection.CW;
        return new Arc( centerX, centerY, radius, endAngle, startAngle, newDirection );
    }

    public static Arc getFillet( Line l1, Line l2, double filletRadius) {
        // The end of l1 must be the start of l2
        if ( l1.getEndPoint().pointDistance( l2.getStartPoint() ) > Constants.SAME_POINT_MAX_DISTANCE ) {
            return null;
        }
        // Get direction vectors of the two lines
        Vector v1 = l1.toVector();
        Vector v2 = l2.toVector();
        
        // find out if v2 turns left or right from v1 by taking the cross product. If
        // it is negative it turns right and positive turns left
        double sign = Math.signum( v1.crossProd2D(v2) );
        Util.ArcDirection dir = ( sign > 0 ) ? Util.ArcDirection.CCW : Util.ArcDirection.CW;
        
        // The fillets angles
        double startAngle = Math.toDegrees(l1.getAngle() - sign * Math.PI/2);
        double endAngle = Math.toDegrees(l2.getAngle() - sign * Math.PI/2);
        
        // Now get the lines parallell to l1 and l2 with a distance to the original
        // lines that is same as the filletRadius
        offsetSide side = ( dir == Util.ArcDirection.CCW) ? offsetSide.LEFT : offsetSide.RIGHT;
        Line l1Par = l1.getParallelLine(filletRadius, side );
        Line l2Par = l2.getParallelLine(filletRadius, side );
        Vector filletCenterPoint = l1Par.intersection( l2Par );
        
        if ( filletCenterPoint == null ) { 
            // No intersection suitable for fillet found
            return null;
        }
      
        Arc fillet = new Arc( filletCenterPoint.getA(), filletCenterPoint.getB(), filletRadius,startAngle,endAngle,dir );
        return fillet;
    }
    @Override
    public CNCCodeLine geoToCNCCode(Point lastPoint, Util.GCode lastGCode) {
        Util.GCode gCode;
        Point startPoint;
        Point endPoint;
        startPoint = getStartPoint();
        endPoint = getEndPoint();
        if ( direction == Util.ArcDirection.CCW ) {
            gCode = Util.GCode.G03;
        } else {
            gCode = Util.GCode.G02;
        }
        double iValue = centerX - startPoint.getxPoint();
        double jValue = centerY - startPoint.getyPoint();
        
        String line = "";
        if ( gCode != lastGCode ) {
            line += Util.gCodeToString( gCode) + " ";
        }
        if ( Math.abs( endPoint.getxPoint() - lastPoint.getxPoint() ) > 0.00005 ) line += "X" + Util.cncRound( endPoint.getxPoint(),4) + " ";
        if ( Math.abs( endPoint.getyPoint() - lastPoint.getyPoint() ) > 0.00005 ) line += "Y" + Util.cncRound( endPoint.getyPoint(),4) + " ";
        
        line += "I" + Util.cncRound(iValue,4) + " ";
        line += "J" + Util.cncRound(jValue,4);
        
        return new CNCCodeLine(line, gCode, endPoint);
       
    }
    
    
    
}
