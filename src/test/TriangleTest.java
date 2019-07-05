package test;

import geometries.Intersectable;
import geometries.Plane;
import geometries.Triangle;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TriangleTest {

    /**
     * check triangle normal,
     * test for {@link Triangle#getNormal getNormal} method
     */
    @Test
    public void getNormal() {
        Triangle tri = new Triangle(new Point3D(2,0,0),new Point3D(0,2,0),new Point3D(0,0,0));
        assertEquals(new Vector(0,0,1),tri.getNormal(null));
    }

    /**
     * Check findIntersections
     */
    @Test
    public void findIntersections() {
        Triangle tr = new Triangle(new Point3D(0, 3, -3),new Point3D(3, 0, -3),new Point3D(-3, 0, -3));
        Ray r;
        List<Intersectable.GeoPoint> list = new ArrayList<>();


        // EP:

        // the ray goes through the triangle
        r = new Ray(new Point3D(1, 1, -2), new Vector(-2, 0.5, -1));
        list.add(new Intersectable.GeoPoint(tr,new Point3D(-1, 1.5, -3)));
        assertEquals("EP - the ray goes through the triangle", list, tr.findIntersections(r));
        // the ray is outside the triangle between 2 far sides
        r = new Ray(new Point3D(4, 4, -2), new Vector(1, 1, -4));
        list.clear();
        assertEquals("EP - the ray is outside the triangle between 2 far sides", list, tr.findIntersections(r));
        // the ray is outside the triangle between 2 close sides
        r = new Ray(new Point3D(-4, -1, -2), new Vector(-1, -1, -1));
        assertEquals("EP - the ray is outside the triangle between 2 close sides", list, tr.findIntersections(r));


        // BVA:

        // the ray begins anywhere on the plane
        r = new Ray(new Point3D(1, 1, -3), new Vector(-2, 0.5, -1));
        list.add(new Intersectable.GeoPoint(tr,new Point3D(1, 1, -3)));
        assertEquals("BVA - the ray begins anywhere at the plane", list, tr.findIntersections(r));

        // ray goes through the continuation of side 1
        r = new Ray(new Point3D(-1, 4, -2), new Vector(0, 0, -1));
        list.clear();
        assertEquals("BVA - ray goes through the continuation of side 1", list, tr.findIntersections(r));
        // ray goes through the continuation of side 2
        r = new Ray(new Point3D(1, 4, -2), new Vector(0, 0, -1));
        assertEquals("BVA - ray goes through the continuation of side 2", list, tr.findIntersections(r));
        // ray goes through the continuation of side 3
        r = new Ray(new Point3D(4, 0, -2), new Vector(0, 0, -1));
        assertEquals("BVA - ray goes through the continuation of side 3", list, tr.findIntersections(r));
        // ray goes through the continuation of side 4
        r = new Ray(new Point3D(4, -1, -2), new Vector(0, 0, -1));
        assertEquals("BVA - ray goes through the continuation of side 4", list, tr.findIntersections(r));
        // ray goes through the continuation of side 5
        r = new Ray(new Point3D(-4, -1, -2), new Vector(0, 0, -1));
        assertEquals("BVA - ray goes through the continuation of side 5", list, tr.findIntersections(r));
        // ray goes through the continuation of side 6
        r = new Ray(new Point3D(-4, 0, -2), new Vector(0, 0, -1));
        assertEquals("BVA - ray goes through the continuation of side 6", list, tr.findIntersections(r));

        // ray through side 1, start before
        r = new Ray(new Point3D(-2, 1, -1), new Vector(0, 0, -1));
        assertEquals("BVA - ray through side 1, start before", list, tr.findIntersections(r));
        // ray through side 1, start on
        r = new Ray(new Point3D(-2, 1, -3), new Vector(0, 0, -1));
        assertEquals("BVA - ray through side 1, start on", list, tr.findIntersections(r));
        // ray through side 2, start before
        r = new Ray(new Point3D(2, 1, -1), new Vector(0, 0, -1));
        assertEquals("BVA - ray through side 2, start before", list, tr.findIntersections(r));
        // ray through side 2, start on
        r = new Ray(new Point3D(2, 1, -3), new Vector(0, 0, -1));
        assertEquals("BVA - ray through side 2, start on", list, tr.findIntersections(r));
        // ray through side 3, start before
        r = new Ray(new Point3D(1, 0, -1), new Vector(0, 0, -1));
        assertEquals("BVA - ray through side 3, start before", list, tr.findIntersections(r));
        // ray through side 3, start on
        r = new Ray(new Point3D(-1, 0, -3), new Vector(0, 0, -1));
        assertEquals("BVA - ray through side 3, start on", list, tr.findIntersections(r));

        // ray through vertex 1, start before
        r = new Ray(new Point3D(0, 3, -2), new Vector(0, 0, -1));
        assertEquals("BVA - ray through vertex 1, start before", list, tr.findIntersections(r));
        // ray through vertex 1, start on
        r = new Ray(new Point3D(0, 3, -3), new Vector(0, 0, -1));
        assertEquals("BVA - ray through vertex 1, start on", list, tr.findIntersections(r));
        // ray through vertex 2, start before
        r = new Ray(new Point3D(3, 0, -2), new Vector(0, 0, -1));
        assertEquals("BVA - ray through vertex 2, start before", list, tr.findIntersections(r));
        // ray through vertex 2, start on
        r = new Ray(new Point3D(3, 0, -3), new Vector(0, 0, -1));
        assertEquals("BVA - ray through vertex 2, start on", list, tr.findIntersections(r));
        // ray through vertex 3, start before
        r = new Ray(new Point3D(-3, 0, -2), new Vector(0, 0, -1));
        assertEquals("BVA - ray through vertex 3, start before", list, tr.findIntersections(r));
        // ray through vertex 3, start on
        r = new Ray(new Point3D(-3, 0, -3), new Vector(0, 0, -1));
        assertEquals("BVA - ray through vertex 3, start on", list, tr.findIntersections(r));
    }
}