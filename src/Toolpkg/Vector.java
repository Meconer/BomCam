/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Toolpkg;

/**
 *
 * @author Mats
 */
public class Vector {

    private final double a;
    private final double b;

    public Vector(double a, double b) {
        this.a = a;
        this.b = b;
    }

    // Return the a composant of this vector
    public double getA() {
        return a;
    }

    // Return the b composant of this vector
    public double getB() {
        return b;
    }

    // Return the dot product of this and v.
    public double dotProd(Vector v) {
        return (a * v.getB() + b * v.getA());
    }

    // return vector scaled by scaleFactor
    public Vector getScaled(double scaleFactor) {
        return new Vector(scaleFactor * a, scaleFactor * b);
    }

    // return resulting vector from this + v
    public Vector add( Vector v ) {
        double newA = a + v.getA();
        double newB = b + v.getB();
        return new Vector(newA , newB);
    }
    
    // return resulting vector from this - v
    public Vector subtract( Vector v ) {
        double newA = a - v.getA();
        double newB = b - v.getB();
        return new Vector(newA , newB);
    }
    
    
    
    // Returns length of vector
    public double getLength() {
        return Math.sqrt(a * a + b * b);
    }

    // Returns normalized vector
    public Vector getUnityVector() {
        Vector retV;
        try {
            retV = getScaled(1 / getLength());
        } catch (ArithmeticException e) {
            retV = new Vector(0, 0);
        }
        return retV;
    }

    // Returns angle of this vector
    public double getAngle() {
        return Math.atan2(b, a);
    }

    // Returns reversed vector
    public Vector getReversed() {
        return new Vector(-a, -b);
    }
    
    // Returns the z-axis component of this vector and v
    public double crossProd2D( Vector v ) {
        return ( a* v.getB()- b*v.getA());
    }
    
    public Point toPoint() {
        return new Point(a, b);
    }
}
