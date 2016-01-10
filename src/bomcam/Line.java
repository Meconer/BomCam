/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bomcam;

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

    
}
