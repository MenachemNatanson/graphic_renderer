package test;

import elements.Camera;
import geometries.*;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CameraTest {

    @Test
    public void constructRayThroughPixel() {
        //Test 3x3 plane
        Camera cam = new Camera(new Point3D(0, 0, 0), new Vector(0, 1, 0), new Vector(0, 0, -1));
        Ray r1 = new Ray(new Point3D(-3.0,3.0,-1.0), new Vector(-3, 3, -1));
        Ray r2 = new Ray(new Point3D(0.0,3.0,-1.0), new Vector(0, 3, -1));
        Ray r3 = new Ray(new Point3D(3.0,3.0,-1.0), new Vector(3, 3, -1));
        Ray r4 = new Ray(new Point3D(-3.0,0.0,-1.0), new Vector(-3, 0, -1));
        Ray r5 = new Ray(new Point3D(0.0,0.0,-1.0), new Vector(0, 0, -1));
        Ray r6 = new Ray(new Point3D(3.0,0.0,-1.0), new Vector(3, 0, -1));
        Ray r7 = new Ray(new Point3D(-3.0,-3.0,-1.0), new Vector(-3, -3, -1));
        Ray r8 = new Ray(new Point3D(0.0,-3.0,-1.0), new Vector(0, -3, -1));
        Ray r9 = new Ray(new Point3D(3.0,-3.0,-1.0), new Vector(3, -3, -1));

        assertEquals("Test 3x3 plane 0,0", r1, cam.constructRayThroughPixel(3, 3, 0, 0, 1, 9, 9));
        assertEquals("Test 3x3 plane 1,0", r2, cam.constructRayThroughPixel(3, 3, 1, 0, 1, 9, 9));
        assertEquals("Test 3x3 plane 2,0", r3, cam.constructRayThroughPixel(3, 3, 2, 0, 1, 9, 9));
        assertEquals("Test 3x3 plane 0,1", r4, cam.constructRayThroughPixel(3, 3, 0, 1, 1, 9, 9));
        assertEquals("Test 3x3 plane 1,1", r5, cam.constructRayThroughPixel(3, 3, 1, 1, 1, 9, 9));
        assertEquals("Test 3x3 plane 2,1", r6, cam.constructRayThroughPixel(3, 3, 2, 1, 1, 9, 9));
        assertEquals("Test 3x3 plane 0,2", r7, cam.constructRayThroughPixel(3, 3, 0, 2, 1, 9, 9));
        assertEquals("Test 3x3 plane 1,2", r8, cam.constructRayThroughPixel(3, 3, 1, 2, 1, 9, 9));
        assertEquals("Test 3x3 plane 2,2", r9, cam.constructRayThroughPixel(3, 3, 2, 2, 1, 9, 9));

        //test 4x4 plane
        cam = new Camera(new Point3D(0, 0, 0), new Vector(0, 1, 0), new Vector(0, 0, -1));
        r1 = new Ray(new Point3D(-4.5,4.5,-1.0), new Vector(-4.5, 4.5, -1));
        r2 = new Ray(new Point3D(-1.5,1.5,-1.0), new Vector(-1.5, 1.5, -1));
        r3 = new Ray(new Point3D(1.5,-1.5,-1.0), new Vector(1.5, -1.5, -1));
        r4 = new Ray(new Point3D(4.5,-4.5,-1.0), new Vector(4.5, -4.5, -1));

        assertEquals("Test 4x4 plane 0,0", r1, cam.constructRayThroughPixel(4, 4, 0, 0, 1, 12, 12));
        assertEquals("Test 4x4 plane 1,1", r2, cam.constructRayThroughPixel(4, 4, 1, 1, 1, 12, 12));
        assertEquals("Test 4x4 plane 2,2", r3, cam.constructRayThroughPixel(4, 4, 2, 2, 1, 12, 12));
        assertEquals("Test 4x4 plane 3,3", r4, cam.constructRayThroughPixel(4, 4, 3, 3, 1, 12, 12));

    }

    @Test
    public void checkSphereIntersections() {
        Camera cam = new Camera(new Point3D(0, 0, 0), new Vector(0, 1, 0), new Vector(0, 0, -1));
        List<Intersectable.GeoPoint> list = new ArrayList<>();

        //Create 9x9 plane with 3x3 pixels
        Ray[] rays = new Ray[9];
        int in = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                rays[in] = cam.constructRayThroughPixel(3, 3, i, j, 1, 9, 9);
                in++;
            }
        }

        //Check all rays intersect Sphere
        Sphere sp = new Sphere(100, new Point3D(0, 0, -101));
        for (int i = 0; i < 9; i++) {
            list.addAll(sp.findIntersections(rays[i]));
        }
        assertEquals("Check all rays intersect Sphere", 18, list.size());

        //Check 5 rays intersect Sphere
        list.clear();
        sp = new Sphere(19, new Point3D(0, 0, -20));
        for (int i = 0; i < 9; i++) {
            list.addAll(sp.findIntersections(rays[i]));
        }
        assertEquals("Check 5 rays intersect Sphere", 10, list.size());

        //Check only middle ray intersects Sphere
        list.clear();
        sp = new Sphere(4.5, new Point3D(0, 0, -5.5));
        for (int i = 0; i < 9; i++) {
            list.addAll(sp.findIntersections(rays[i]));
        }
        assertEquals("Check only middle ray intersects Sphere", 2, list.size());

        //Check view plane inside Sphere
        list.clear();
        sp = new Sphere(20, new Point3D(0, 0, -0.5));
        for (int i = 0; i < 9; i++) {
            list.addAll(sp.findIntersections(rays[i]));
        }
        assertEquals("Check view plane inside Sphere", 9, list.size());

        //Check view plane inside Sphere 2
        list.clear();
        sp = new Sphere(1.5, new Point3D(0, 0, -2));
        for (int i = 0; i < 9; i++) {
            list.addAll(sp.findIntersections(rays[i]));
        }
        assertEquals("Check view plane inside Sphere 2", 1, list.size());

        //Check Sphere behind view plane
        list.clear();
        sp = new Sphere(5, new Point3D(0, 0, 10));
        for (int i = 0; i < 9; i++) {
            list.addAll(sp.findIntersections(rays[i]));
        }
        assertEquals("Check Sphere behind view plane", 0, list.size());

    }

    @Test
    public void checkTriangleIntersections() {
        Camera cam = new Camera(new Point3D(0, 0, 0), new Vector(0, 1, 0), new Vector(0, 0, -1));
        List<Intersectable.GeoPoint> list = new ArrayList<>();

        //Create 9x9 plane with 3x3 pixels
        Ray[] rays = new Ray[9];
        int in = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                rays[in] = cam.constructRayThroughPixel(3, 3, i, j, 1, 9, 9);
                in++;
            }
        }

        //Check only middle ray intersects Triangle
        Triangle tri = new Triangle(new Point3D(-1, -1, -2), new Point3D(1, -1, -2), new Point3D(0, 1, -2));
        list.clear();
        for (int i = 0; i < 9; i++) {
            list.addAll(tri.findIntersections(rays[i]));
        }
        assertEquals("Check only middle ray intersects Triangle", 1, list.size());

        //Check 2 rays intersects Triangle
        tri = new Triangle(new Point3D(-1, -1, -2), new Point3D(1, -1, -2), new Point3D(0, 20, -2));
        list.clear();
        for (int i = 0; i < 9; i++) {
            list.addAll(tri.findIntersections(rays[i]));
        }
        assertEquals("Check 2 rays intersects Triangle", 2, list.size());

    }

    @Test
    public void checkPlaneIntersections() {
        Camera cam = new Camera(new Point3D(0, 0, 0), new Vector(0, 1, 0), new Vector(0, 0, -1));
        List<Intersectable.GeoPoint> list = new ArrayList<>();

        //Create 9x9 plane with 3x3 pixels
        Ray[] rays = new Ray[9];
        int in = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                rays[in] = cam.constructRayThroughPixel(3, 3, i, j, 1, 9, 9);
                in++;
            }
        }

        //Check Plane 1 intersection
        Plane pl = new Plane(new Point3D(0, 0, -2), new Vector(0, 0, -2));
        list.clear();
        for (int i = 0; i < 9; i++) {
            list.addAll(pl.findIntersections(rays[i]));
        }
        assertEquals("Check Plane 1 intersection", 9, list.size());

        //Check Plane 2 intersection
        pl = new Plane(new Point3D(0, 0, -2), new Vector(1, 1, -20));
        list.clear();
        for (int i = 0; i < 9; i++) {
            list.addAll(pl.findIntersections(rays[i]));
        }
        assertEquals("Check Plane 2 intersection", 9, list.size());

        //Check Plane 3 intersection
        pl = new Plane(new Point3D(0, 0, -2), new Vector(-1, -2, -200));
        list.clear();
        for (int i = 0; i < 9; i++) {
            list.addAll(pl.findIntersections(rays[i]));
        }
        assertEquals("Check Plane 3 intersection", 9, list.size());

    }

    @Test
    public void checkTubeIntersections() {
        Camera cam = new Camera(new Point3D(0, 0, 0), new Vector(0, 1, 0), new Vector(0, 0, -1));
        List<Intersectable.GeoPoint> list = new ArrayList<>();

        //Create 9x9 plane with 3x3 pixels
        Ray[] rays = new Ray[9];
        int in = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                rays[in] = cam.constructRayThroughPixel(3, 3, i, j, 1, 9, 9);
                in++;
            }
        }

        //Check vertical Tube 3 intersection
        Tube tb = new Tube(3, new Ray(new Point3D(0, 0, -4), new Vector(0, 1, 0)));
        list.clear();
        for (int i = 0; i < 9; i++) {
            list.addAll(tb.findIntersections(rays[i]));
        }
        assertEquals("Check vertical Tube 3 intersection", 6, list.size());

        //Check Horizontal Tube 3 intersection
        tb = new Tube(3, new Ray(new Point3D(0, 0, -4), new Vector(1, 0, 0)));
        list.clear();
        for (int i = 0; i < 9; i++) {
            list.addAll(tb.findIntersections(rays[i]));
        }
        assertEquals("Check Horizontal Tube 3 intersection", 6, list.size());

        //Check diagonal Tube 3 intersection
        tb = new Tube(3, new Ray(new Point3D(0, 0, -4), new Vector(1, 1, 0)));
        list.clear();
        for (int i = 0; i < 9; i++) {
            list.addAll(tb.findIntersections(rays[i]));
        }
        assertEquals("Check diagonal Tube 3 intersection", 6, list.size());

        //Check diagonal2 Tube 3 intersection
        tb = new Tube(3, new Ray(new Point3D(0, 0, -40), new Vector(1, 1, 0.1)));
        list.clear();
        for (int i = 0; i < 9; i++) {
            list.addAll(tb.findIntersections(rays[i]));
        }
        assertEquals("Check diagonal2 Tube 3 intersection", 6, list.size());

        //Check Plane inside Tube 9 intersection
        tb = new Tube(20, new Ray(new Point3D(0, 0, -1), new Vector(0, 1, 0)));
        list.clear();
        for (int i = 0; i < 9; i++) {
            list.addAll(tb.findIntersections(rays[i]));
        }
        assertEquals("Check Plane inside Tube 9 intersection", 9, list.size());

        //Check Plane behind Tube 0 intersection
        tb = new Tube(20, new Ray(new Point3D(0, 0, 50), new Vector(0, 1, 0)));
        list.clear();
        for (int i = 0; i < 9; i++) {
            list.addAll(tb.findIntersections(rays[i]));
        }
        assertEquals("Check Plane behind Tube 0 intersection", 0, list.size());

    }

    @Test
    public void checkCylinderIntersections() {
        Camera cam = new Camera(new Point3D(0, 0, 0), new Vector(0, 1, 0), new Vector(0, 0, -1));
        List<Intersectable.GeoPoint> list = new ArrayList<>();

        //Create 9x9 plane with 3x3 pixels
        Ray[] rays = new Ray[9];
        int in = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                rays[in] = cam.constructRayThroughPixel(3, 3, i, j, 1, 9, 9);
                in++;
            }
        }

        //Check Cylinder 2 intersection
        Cylinder cy = new Cylinder(1, new Ray(new Point3D(0, 0, -4), new Vector(0, 1, 0)), 1);
        list.clear();
        for (int i = 0; i < 9; i++) {
            list.addAll(cy.findIntersections(rays[i]));
        }
        assertEquals("Check Cylinder 2 intersection 1", 2, list.size());

        //Check Cylinder 2 intersection 2
        cy = new Cylinder(1, new Ray(new Point3D(0, 0, -4), new Vector(1, 0, 0)), 1);
        list.clear();
        for (int i = 0; i < 9; i++) {
            list.addAll(cy.findIntersections(rays[i]));
        }
        assertEquals("Check Cylinder 2 intersection 2", 2, list.size());

        //Check Cylinder 2 intersection 3
        cy = new Cylinder(1, new Ray(new Point3D(0, 0, -4), new Vector(1, 1, 0)), 1);
        list.clear();
        for (int i = 0; i < 9; i++) {
            list.addAll(cy.findIntersections(rays[i]));
        }
        assertEquals("Check Cylinder 2 intersection 3", 2, list.size());

        //Check Cylinder 2 intersection 4
        cy = new Cylinder(1, new Ray(new Point3D(0, 0, -4), new Vector(1, 1, 0.1)), 1);
        list.clear();
        for (int i = 0; i < 9; i++) {
            list.addAll(cy.findIntersections(rays[i]));
        }
        assertEquals("Check Cylinder 2 intersection 4", 2, list.size());

        //Check Cylinder 2 intersection 5
        cy = new Cylinder(1, new Ray(new Point3D(0, 0, -4), new Vector(0, 0, -1)), 1);
        list.clear();
        for (int i = 0; i < 9; i++) {
            list.addAll(cy.findIntersections(rays[i]));
        }
        assertEquals("Check Cylinder 2 intersection 5", 2, list.size());
    }

    @Test
    public void ctor() {
        Camera cam = new Camera(new Point3D(0, 0, 0), new Vector(1, 0, 0), new Vector(0, 0, -1));
        assertEquals("Check the third vector", cam.getVRight(), new Vector(0, -1, 0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void ctorExc() {
        Camera cam = new Camera(new Point3D(0, 0, 0), new Vector(1, 1, 1), new Vector(0, 0, -1));
    }
}