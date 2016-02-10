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

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double dotProd(Vector v) {
        return (a * v.getB() + b * v.getA());
    }

    public Vector getScaled(double scaleFactor) {
        return new Vector(scaleFactor * a, scaleFactor * b);
    }

    public Vector add( Vector v ) {
        return new Vector(a + v.getA(), b + getB());
    }
    
    public double getLength() {
        return Math.sqrt(a * a + b * b);
    }

    public Vector getUnityVector() {
        Vector retV;
        try {
            retV = getScaled(1 / getLength());
        } catch (ArithmeticException e) {
            retV = new Vector(0, 0);
        }
        return retV;
    }
}
