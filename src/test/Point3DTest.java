package test;

import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Point3DTest {

    /**
     * check add vector ot point,
     * test for {@link Point3D#add add} method
     */
    @Test
    public void add() {
        Point3D p = new Point3D(1, 1, 1);
        Vector vec = new Vector(3, -2, 5);
        assertEquals(new Point3D(4, -1, 6), p.add(vec));
    }

    /**
     * check subtract vector from point,
     * test for {@link Point3D#subtract(Vector) subtract(Vector)} method
     */
    @Test
    public void subtractVector() {
        Point3D p = new Point3D(1, 1, 1);
        Vector vec = new Vector(3, -2, 5);
        assertEquals(new Point3D(-2, 3, -4), p.subtract(vec));
    }

    /**
     * check subtract point from point,
     * test for {@link Point3D#subtract(Point3D) subtract(Point3D)} method
     */
    @Test
    public void subtractPoint3D() {
        Point3D p1 = new Point3D(1, 1, 1);
        Point3D p2 = new Point3D(3, -2, 5);
        assertEquals(new Vector(-2, 3, -4), p1.subtract(p2));
    }

    /**
     * check distance between two points in pow 2,
     * test for {@link Point3D#distance2 distance2} method
     */
    @Test
    public void distance2() {
        Point3D p1 = new Point3D(8, 6, 5);
        Point3D p2 = new Point3D(4, 3, 5);
        Point3D p3 = new Point3D(-8, 77, -6);
        Point3D p4 = new Point3D(-4, 77, -3);
        Point3D p5 = new Point3D(0, 3, 4);
        Point3D p6 = new Point3D(0, 6, 8);
        assertEquals(25, p1.distance2(p2));
        assertEquals(25, p3.distance2(p4));
        assertEquals(25, p5.distance2(p6));
    }

    /**
     * check distance between two points,
     * test for {@link Point3D#distance distance} method
     */
    @Test
    public void distance() {
        Point3D p1 = new Point3D(8, 6, 5);
        Point3D p2 = new Point3D(4, 3, 5);
        Point3D p3 = new Point3D(-8, 77, -6);
        Point3D p4 = new Point3D(-4, 77, -3);
        Point3D p5 = new Point3D(0, 3, 4);
        Point3D p6 = new Point3D(0, 6, 8);
        assertEquals(5, p1.distance(p2));
        assertEquals(5, p3.distance(p4));
        assertEquals(5, p5.distance(p6));
    }
}
