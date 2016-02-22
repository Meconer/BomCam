/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Toolpkg;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

/**
 *
 * @author Mats
 */
public class Plane3D {
    private final Vector3D point;
    private final Vector3D normal;

    public Plane3D(Vector3D point, Vector3D normal) {
        this.point = point;
        this.normal = normal;
    }

    public Vector3D getPoint() {
        return point;
    }

    public Vector3D getNormal() {
        return normal;
    }
    
    @Override
    public String toString() {
        return point.toString() + " " + normal.toString();
    }
}
