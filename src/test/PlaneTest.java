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

public class PlaneTest {

    /**
     * check plane normal,
     * test for {@link Plane#getNormal getNormal} method
     */
    @Test
    public void getNormal() {
        Plane plane = new Plane(new Point3D(2, 0, 0), new Point3D(0, 2, 0), new Point3D(0, 0, 0));
        assertEquals(new Vector(0, 0, 1), plane.getNormal(null));

        plane = new Plane(new Point3D(1, 1, 1), new Vector(1, 2, 3));
        assertEquals(new Vector(1, 2, 3).normal(), plane.getNormal(null));
    }

    /**
     * check invalid plane,
     * test for {@link Plane#Plane(Point3D, Point3D, Point3D) Plane(Point3D, Point3D, Point3D)} constructor
     */
    @Test(expected = IllegalArgumentException.class)
    public void InvalidPlane() {
        Plane plane = new Plane(new Point3D(0, 2, 0), new Point3D(2, 0, 0), new Point3D(2, 0, 0));
    }


    /**
     * Check findIntersections
     */
    @Test
    public void findIntersections() {
        Plane pl = new Plane(new Point3D(0, 0, -3), new Vector(0, 0, -1));
        Ray r;
        List<Intersectable.GeoPoint> list = new ArrayList<>();


        // EP: The Ray must be neither orthogonal nor parallel to the plane
        // the ray intersects the plane
        r = new Ray(new Point3D(1, 1, 0), new Vector(2, 1, -1));
        list.add(new Intersectable.GeoPoint(pl,new Point3D(7, 4, -3)));
        assertEquals("EP - the ray intersects the plane", list, pl.findIntersections(r));
        // the ray does not intersect the plane
        r = new Ray(new Point3D(1, 1, 0), new Vector(2, 1, 1));
        list.clear();
        assertEquals("EP - the ray does not intersect the plane", list, pl.findIntersections(r));


        // BVA: Ray is parallel to the plane
        // the ray is included in the plane
        r = new Ray(new Point3D(1, 2, -3), new Vector(2, 1, 0));
        assertEquals("BVA - the ray is parallel and included in the plane", list, pl.findIntersections(r));
        // the ray is not included in the plane
        r = new Ray(new Point3D(1, 2, -2), new Vector(2, 1, 0));
        assertEquals("BVA - the ray is parallel and not included in the plane", list, pl.findIntersections(r));


        // BVA: Ray is orthogonal to the plane
        // Ray starts before the plane
        r = new Ray(new Point3D(1, 1, 0), new Vector(0, 0, -1));
        list.add(new Intersectable.GeoPoint(pl,new Point3D(1, 1, -3)));
        assertEquals("BVA - the ray is orthogonal and starts before the plane", list, pl.findIntersections(r));
        // Ray starts in the plane
        r = new Ray(new Point3D(1, 1, -3), new Vector(0, 0, -1));
        list.clear();
        list.add(new Intersectable.GeoPoint(pl,new Point3D(1, 1, -3)));
        assertEquals("BVA - the ray is orthogonal and starts in the plane", list, pl.findIntersections(r));
        // Ray starts after the plane
        r = new Ray(new Point3D(1, 1, -4), new Vector(0, 0, -1));
        list.clear();
        assertEquals("BVA - the ray is orthogonal and starts after the plane", list, pl.findIntersections(r));


        // BVA: Starting point on the plane, but the rest of the ray is not
        r = new Ray(new Point3D(1, 1, -3), new Vector(2, 1, -1));
        list.add(new Intersectable.GeoPoint(pl,new Point3D(1, 1, -3)));
        assertEquals("BVA - Starting point on the plane, but the rest of the ray is not", list, pl.findIntersections(r));


        // BVA: Starting point is equal to the plane point
        r = new Ray(new Point3D(0, 0, -3), new Vector(2, 1, -1));
        list.clear();
        list.add(new Intersectable.GeoPoint(pl,new Point3D(0, 0, -3)));
        assertEquals("BVA - Starting point is equal to the plane point", list, pl.findIntersections(r));
    }
}