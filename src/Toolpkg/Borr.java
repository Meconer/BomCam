/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Toolpkg;

import java.util.ArrayList;
import org.apache.commons.math3.geometry.euclidean.threed.Rotation;
import org.apache.commons.math3.geometry.euclidean.threed.RotationConvention;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

/**
 *
 * @author Mats
 */
public class Borr {
    private class XRadiusPair {
        private final double x;
        private final double radius;

        public XRadiusPair(double x, double radius) {
            this.x = x;
            this.radius = radius;
        }

        public double getX() {
            return x;
        }

        public double getRadius() {
            return radius;
        }
    }
    
    private final ArrayList<XRadiusPair> pairList = new ArrayList<>();
    private double tipThickness = 0.5;
    private double angle = 2;
    
    public Borr() {
        pairList.add(new XRadiusPair( 0,0 ));
        pairList.add(new XRadiusPair( 1,3 ));
        pairList.add(new XRadiusPair( 5,3 ));
        pairList.add(new XRadiusPair( 7,5 ));
        pairList.add(new XRadiusPair( 10,5 ));
        pairList.add(new XRadiusPair( 11,6 ));
    }
    
    // Returns thickness of blank at x
    private double halfBlankThickness( double x ) {
        return tipThickness + x * Math.tan(Math.toRadians(angle));
    }
    
    public void calculate() {
        
        XRadiusPair prevPair = pairList.get(0);
        XRadiusPair nextPair = pairList.get(1);
        
        Vector3D startPoint = new Vector3D( 0, 0, 0 );
        
        // Next point is at a a radius line rotated upwards so it touches the
        // surface at the blank at this x.
        // We get the y value with pythagorean theorem by:
        
        double r = nextPair.getRadius();
        double x = nextPair.getX();
        double z = halfBlankThickness( x );
        double y = - Math.sqrt( r*r - z*z); // Y is on the negative side.
        Vector3D nextPoint = new Vector3D( x, y, z);
        System.out.println("nextpt : "+ nextPoint);
        System.out.println("nextpt.unit : "+ nextPoint.normalize());
        
        // Make a new plane x=0 and rotate it so it goes through the z axis and
        // nextPoint.
        
        Plane3D firstPlane = new Plane3D( startPoint, new Vector3D( y, -x, 0).normalize());
        System.out.println("firstPlane " + firstPlane);
        Vector3D rotationAxis = nextPoint.subtract(startPoint);
        System.out.println("RotAx : "+ rotationAxis);
        
        Rotation rot1 = new Rotation(rotationAxis, Math.toRadians(5), RotationConvention.VECTOR_OPERATOR);
        
        //Vector3D newNormal = rot1.applyTo(startPlane.normal);
        //System.out.println("Rot1 : "+ newNormal);
        
    }
    
}
