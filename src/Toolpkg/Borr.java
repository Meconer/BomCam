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
    private class ZRadiusPair {
        private final double z;
        private final double radius;

        public ZRadiusPair(double z, double radius) {
            this.z = z;
            this.radius = radius;
        }

        public double getZ() {
            return z;
        }

        public double getRadius() {
            return radius;
        }
    }
    
    private final ArrayList<ZRadiusPair> pairList = new ArrayList<>();
    private double tipThickness = 0.5;
    private double angle = 2;
    
    public Borr() {
        pairList.add(new ZRadiusPair( 0,0 ));
        pairList.add(new ZRadiusPair( 1,3 ));
        pairList.add(new ZRadiusPair( 5,3 ));
        pairList.add(new ZRadiusPair( 7,5 ));
        pairList.add(new ZRadiusPair( 10,5 ));
        pairList.add(new ZRadiusPair( 11,6 ));
    }
    
    // Returns thickness of blank at z
    private double blankThickness( double z ) {
        return tipThickness + z * Math.tan(Math.toRadians(angle));
    }
    
    public void calculate() {
        
        ZRadiusPair prevPair = pairList.get(0);
        ZRadiusPair nextPair = pairList.get(1);
        
        
        Plane3D startPlane = new Plane3D(new Vector3D(prevPair.z, prevPair.radius, blankThickness(prevPair.z)), new Vector3D(-1,0,0));
        
        System.out.println("StartPt : "+ startPlane.point);
        
        Vector3D nextPoint = new Vector3D(nextPair.z, nextPair.radius, blankThickness(nextPair.z));
        System.out.println("nextpt : "+ nextPoint);
        
        Vector3D rotationAxis = nextPoint.subtract(startPlane.point);
        System.out.println("RotAx : "+ rotationAxis);
        
        Rotation rot1 = new Rotation(rotationAxis, Math.toRadians(5), RotationConvention.VECTOR_OPERATOR);
        
        Vector3D newNormal = rot1.applyTo(startPlane.normal);
        System.out.println("Rot1 : "+ newNormal);
        
    }
    
}
