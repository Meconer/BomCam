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
    Vector3D point;
    Vector3D normal;

    public Plane3D(Vector3D point, Vector3D normal) {
        this.point = point;
        this.normal = normal;
    }
    
    
}
