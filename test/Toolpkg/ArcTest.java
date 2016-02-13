/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Toolpkg;

import SodickCNCProgram.CNCCodeLine;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mats
 */
public class ArcTest {
    
    public ArcTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getCenterX method, of class Arc.
     */
    @Test
    public void testGetCenterX() {
        System.out.println("getCenterX");
        Arc instance = new Arc(0, 0, 0, 0, 90, Util.ArcDirection.CW);
        double expResult = 0.0;
        double result = instance.getCenterX();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of getCenterY method, of class Arc.
     */
    @Test
    public void testGetCenterY() {
        System.out.println("getCenterY");
        Arc instance = new Arc(0, 0, 0, 0, 90, Util.ArcDirection.CW);
        double expResult = 0.0;
        double result = instance.getCenterY();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of getRadius method, of class Arc.
     */
    @Test
    public void testGetRadius() {
        System.out.println("getRadius");
        Arc instance = new Arc(0, 0, 5, 0, 90, Util.ArcDirection.CW);
        double expResult = 5.0;
        double result = instance.getRadius();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of getStartAngle method, of class Arc.
     */
    @Test
    public void testGetStartAngle() {
        System.out.println("getStartAngle");
        Arc instance = new Arc(0, 0, 5, 0, 90, Util.ArcDirection.CW);
        double expResult = 0.0;
        double result = instance.getStartAngle();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of getEndAngle method, of class Arc.
     */
    @Test
    public void testGetEndAngle() {
        System.out.println("getEndAngle");
        Arc instance = new Arc(0, 0, 5, 0, 90, Util.ArcDirection.CW);
        double expResult = 90.0;
        double result = instance.getEndAngle();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of getStartPoint method, of class Arc.
     */
    @Test
    public void testGetStartPoint() {
        System.out.println("getStartPoint");
        Arc instance = new Arc(0, 0, 5, 0, 90, Util.ArcDirection.CW);
        Point expResult = new Point( 5,0);
        Point result = instance.getStartPoint();
        // TODO review the generated test code and remove the default call to fail.
        assertEquals( Math.abs(result.getxPoint()-expResult.getxPoint()),0, 0.00000001);
    }

    /**
     * Test of getEndPoint method, of class Arc.
     */
    @Test
    public void testGetEndPoint() {
        System.out.println("getEndPoint");
        Arc instance = new Arc(0, 0, 5, 0, 90, Util.ArcDirection.CCW);
        Point expResult = new Point( 0,5);
        Point result = instance.getEndPoint();
        // TODO review the generated test code and remove the default call to fail.
        assertEquals( Math.abs(result.getyPoint()-expResult.getyPoint()),0, 0.00000001);
    }

    /**
     * Test of getDirection method, of class Arc.
     */
    @Test
    public void testGetDirection() {
        System.out.println("getDirection");
        Arc instance = new Arc(0, 0, 5, 0, 90, Util.ArcDirection.CW);
        Util.ArcDirection expResult = Util.ArcDirection.CW;
        Util.ArcDirection result = instance.getDirection();
        assertEquals(expResult, result);
    }

    /**
     * Test of getReversedArc method, of class Arc.
     */
    @Test
    public void testGetReversedArc() {
        System.out.println("getReversedArc");
        Arc instance = new Arc(0, 0, 5, 0, 90, Util.ArcDirection.CW);
        Arc result = instance.getReversedArc();
        
        assertEquals(instance.getCenterX(), result.getCenterX(), 0.000001);
        assertEquals(instance.getCenterY(), result.getCenterY(), 0.000001);
        assertEquals(instance.getRadius(), result.getRadius(), 0.000001);
        assertEquals(instance.getStartAngle(), result.getEndAngle(), 0.000001);
        assertEquals(instance.getEndAngle(), result.getStartAngle(), 0.000001);
    }

    /**
     * Test of getFillet method, of class Arc.
     */
    @Test
    public void testGetFillet() {
        System.out.println("getFillet");
        Line l1 = new Line(0, 0, 0, 5);
        Line l2 = new Line(0,5, -5, 5);
        double filletRadius = 1.0;
        Arc expResult = new Arc(-1, 4, 1.0, 0, 90, Util.ArcDirection.CCW); 
        Arc result = Arc.getFillet(l1, l2, filletRadius);
        assertEquals( Util.doubleEquals(expResult.getCenterX(), result.getCenterX()), true);
        assertEquals( Util.doubleEquals(expResult.getCenterY(), result.getCenterY()), true);
        assertEquals( Util.doubleEquals(expResult.getRadius(), result.getRadius()), true);
        assertEquals( Util.doubleEquals(expResult.getStartAngle(), result.getStartAngle()), true);
        assertEquals( Util.doubleEquals(expResult.getEndAngle(), result.getEndAngle()), true);
    }

    /**
     * Test of geoToCNCCode method, of class Arc.
     */
    @Test
    public void testGeoToCNCCode() {
        System.out.println("geoToCNCCode");
        Point lastPoint = new Point(0, 0);
        Point nextPoint = new Point(-5, 5);
        Util.GCode lastGCode = Util.GCode.G01;
        Arc instance = new Arc(-5, 0, 5, 0, 90, Util.ArcDirection.CCW);
        CNCCodeLine expResult = new CNCCodeLine("G03 X-5.0 Y5.0 I-5.0 J0.0", Util.GCode.G03, nextPoint);
        CNCCodeLine result = instance.geoToCNCCode(lastPoint, lastGCode);
        assertEquals(expResult.getLine().equals(result.getLine()), true);
        assertEquals(expResult.getgCode(), result.getgCode());
        assertEquals( Util.doubleEquals( expResult.getLastPoint().getxPoint(), result.getLastPoint().getxPoint()), true);
        assertEquals( Util.doubleEquals( expResult.getLastPoint().getyPoint(), result.getLastPoint().getyPoint()), true);
    }
    
}
