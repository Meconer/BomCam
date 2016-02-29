package Toolpkg;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import org.apache.commons.math3.geometry.euclidean.threed.Line;
import org.apache.commons.math3.geometry.euclidean.threed.Plane;
import org.apache.commons.math3.geometry.euclidean.threed.Rotation;
import org.apache.commons.math3.geometry.euclidean.threed.RotationConvention;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

/**
 *
 * @author Mats
 */
public class Borr {

    private class XRadiusAngleTriplet {
        private final double x;
        private final double radius;
        private final double angle;

        public XRadiusAngleTriplet(double x, double radius, double angle) {
            this.x = x;
            this.radius = radius;
            this.angle = angle;
        }

        public double getX() {
            return x;
        }

        public double getRadius() {
            return radius;
        }
        
        public double getAngle() {
            return angle;
        }
    }
    
    private final ArrayList<XRadiusAngleTriplet> drillList = new ArrayList<>();
    private final ArrayList<Plane> planeList = new ArrayList<>();

    private final DoubleProperty tipThickness;   
    private final DoubleProperty angle;
    private final DoubleProperty stockDiameter;
    
    private String specText = "";
    
    public Borr() {
        tipThickness = new SimpleDoubleProperty(1.0);
        angle = new SimpleDoubleProperty(2.0);
        stockDiameter = new SimpleDoubleProperty(12.0);
    }

    public DoubleProperty getTipThicknessProperty() {
        return tipThickness;
    }

    public DoubleProperty getAngleProperty() {
        return angle;
    }

    public DoubleProperty getStockDiameterProperty() {
        return stockDiameter;
    }
    
    
    public void setSpecText(String specText) {
        this.specText = specText;
    }


    private void buildTripletListFromSpecText() {
        String[] specList = specText.split("\n");
        String doubleRegex = "[-+]?[0-9]*\\.?[0-9]*";
        String zRegex = "[zZ](" + doubleRegex + ")";
        String radiusRegex = "[rR](" + doubleRegex + ")";
        String angleRegex = "[aA](" + doubleRegex + ")";
        Pattern zPattern = Pattern.compile(zRegex);
        Pattern radiusPattern = Pattern.compile(radiusRegex);
        Pattern anglePattern = Pattern.compile(angleRegex);
        
        double radius = 0;
        double z = 0;
        double angle = 0;

        for ( String s : specList) {
            Matcher zMatcher = zPattern.matcher(s);
            if ( zMatcher.find() ) z = Double.parseDouble(zMatcher.group(1));
            Matcher angleMatcher = anglePattern.matcher(s);
            if ( angleMatcher.find() ) angle = Double.parseDouble(angleMatcher.group(1));
            Matcher radiusMatcher = radiusPattern.matcher(s);
            if ( radiusMatcher.find() ) radius = Double.parseDouble(radiusMatcher.group(1));
            drillList.add( new XRadiusAngleTriplet(z, radius, angle));
        }
    }

    
    
    // Returns thickness of blank at x
    private double halfBlankThickness( double x ) {
        return tipThickness.get() + x * Math.tan(Math.toRadians(angle.get()));
    }
    
    
    
    private double calculateY(double r, double t) {
        if ( r < Constants.TOLERANCE ) return 0;
        return -Math.sqrt( r * r - t * t );
    }

    // Calculate the chains for the drill cutting toolpath.
    public void calculate() {
        
        buildTripletListFromSpecText();
        
        XRadiusAngleTriplet prevTriplet;
        XRadiusAngleTriplet nextTriplet;

        // Calculate a series of planes that make up the drill edges.
        Iterator<XRadiusAngleTriplet> tripletIterator = drillList.iterator();
        if ( !tripletIterator.hasNext() ) throw new IndexOutOfBoundsException("borrList har inga element");
        prevTriplet = tripletIterator.next();
        while ( tripletIterator.hasNext() ) {
            
            // Calculate the first 3D point on this part of the drill
            double xPrev = prevTriplet.getX();
            double zPrev = halfBlankThickness(xPrev);
            double yPrev = calculateY( prevTriplet.getRadius(), zPrev);
            Vector3D startPoint = new Vector3D( xPrev, yPrev, zPrev);
        
            // The same for the second point
            nextTriplet = tripletIterator.next();
            double xNext = nextTriplet.getX();
            double zNext = halfBlankThickness(xNext);
            double yNext = calculateY(nextTriplet.getRadius(), zNext);
            Vector3D nextPoint = new Vector3D(xNext, yNext, zNext);
            
            // Build a vertical plane through the two calculated points.
            // First a vector between the points.
            Vector3D diffVector = nextPoint.subtract(startPoint);
            
            // The normal vector of the vertical plane through these two points
            // The z part is 0 for a vertical plane
            Vector3D normalVector = new Vector3D( diffVector.getY(), -diffVector.getX(),0);
            // Make the plane
            Plane verticalPlane = new Plane(startPoint, normalVector, Constants.TOLERANCE);
            
            // Now rotate the plane around the diff vector
            // The plane will still go through both start and end points but not vertical.
            Rotation rotation = new Rotation(diffVector, Math.toRadians(nextTriplet.getAngle()), RotationConvention.VECTOR_OPERATOR);
            Plane nextPlane = verticalPlane.rotate(startPoint, rotation);
            planeList.add( nextPlane);
            
            // Use the nextPoint as startPoint for the next loop.
            prevTriplet = nextTriplet;
        }
        
        // We now have the planes that make up the drill edges. 
        // Now we will calculate the intersection lines between all consecutive planes.
        // For the first intersection we will use the first plane but rotated 180 degrees
        // around the x axis.
        Rotation rotation = new Rotation(new Vector3D(1,0,0), Math.PI, RotationConvention.VECTOR_OPERATOR);
        Plane prevPlane = planeList.get(0).rotate(new Vector3D(0,0,0), rotation);

        ArrayList<Line> lineList = new ArrayList<>();
        
        Iterator<Plane> planeIterator = planeList.iterator();
        while ( planeIterator.hasNext() ) {
            Plane nextPlane = planeIterator.next();
            Line line = prevPlane.intersection(nextPlane);
            lineList.add(line);
            prevPlane = nextPlane;
        }
        // Add a last line that is the intersection of the last plane and a 
        // vertical line at the negative radius of the blank.
        
        // First make the vertical plane at the negative radius
        Plane radVerticalPlane = new Plane( new Vector3D( 0, -stockDiameter.get()/2, 0), new Vector3D(0,-1,0), Constants.TOLERANCE);
        Line line = prevPlane.intersection(radVerticalPlane);
        lineList.add(line);
        
        // We now have a list of 3D lines through the vertices of the drill edges.
        // We get the intersection of each line with a plane that is 2 mm above
        // the XY plane and another plane that is 2 mm below.
        Plane abovePlane = new Plane( new Vector3D(0,0,2), new Vector3D( 0,0,1), Constants.TOLERANCE);
        Plane belowPlane = new Plane( new Vector3D(0,0,-2), new Vector3D( 0,0,1), Constants.TOLERANCE);
        
        // Now make two lists of intersection points with the lines in the line lists
        ArrayList<Vector3D> abovePointList = new ArrayList<>();
        ArrayList<Vector3D> belowPointList = new ArrayList<>();
        Iterator<Line> lineIterator = lineList.iterator();
        while ( lineIterator.hasNext() ) {
            line = lineIterator.next();
            Vector3D aboveIntersection = abovePlane.intersection(line);
            Vector3D belowIntersection = belowPlane.intersection(line);
            Vector3D mirroredBelow = new Vector3D(belowIntersection.getX(), -belowIntersection.getY(), -belowIntersection.getZ());
            abovePointList.add(aboveIntersection);
            belowPointList.add(mirroredBelow);
            System.out.println("Point : " + aboveIntersection.toString());
            System.out.println("BelowPoint : " + mirroredBelow.toString());
        }
    }
    
}
