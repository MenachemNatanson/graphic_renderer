package test;

import geometries.Cylinder;
import geometries.Intersectable;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CylinderTest {

    /**
     * check cylinder normal,
     * test for {@link Cylinder#getNormal getNormal} method
     */
    @Test
    public void getNormal() {
        Cylinder cy = new Cylinder(2,new Ray(new Point3D(2, 0, 0), new Vector(0, 1, 0)),10);
        assertEquals(new Vector(-1,0,0).normal(),cy.getNormal(new Point3D(0,3,0)));
        assertEquals(new Vector(0,1,0).normal(),cy.getNormal(new Point3D(1,5,0)));
        assertEquals(new Vector(0,-1,0).normal(),cy.getNormal(new Point3D(3,-5,0)));
    }

    /**
     * check findIntersections
     * test for {@link Cylinder#findIntersections(Ray)} method
     */
    @Test
    public void findIntersections(){
        List<Intersectable.GeoPoint> list =new ArrayList<>();

        Ray r = new Ray(new Point3D(0,0,0),new Vector(0,0,-1));

        r = new Ray(new Point3D(0,0,0),new Vector(0,0,-1));


        Cylinder c = new Cylinder(1,new Ray(new Point3D(0,0,-3),new Vector(0,1,0)),1);
        r = new Ray(new Point3D(0,0,0),new Vector(0,0,-1));
        list.add(new Intersectable.GeoPoint(c,new Point3D(0,0,-2)));
        list.add(new Intersectable.GeoPoint(c,new Point3D(0,0,-4)));
        //ray goes in the middle of cylinder
        assertEquals(list,c.findIntersections(r));


        //ray goes on button of cylinder
        c = new Cylinder(1,new Ray(new Point3D(0,1,-3),new Vector(0,1,0)),2);
        assertEquals(list,c.findIntersections(r));

        r =  new Ray(new Point3D(0,-3,-3),new Vector(0,1,0));
        list.clear();
        list.add(new Intersectable.GeoPoint(c,new Point3D(0,2,-3)));
        list.add(new Intersectable.GeoPoint(c,new Point3D(0,0,-3)));
        //ray goes thorough middle of cylinder top and button
        assertEquals(list,c.findIntersections(r));


        r =  new Ray(new Point3D(0,-3,-2),new Vector(0,1,0));
        list.clear();
        list.add(new Intersectable.GeoPoint(c,new Point3D(0,2,-2)));
        list.add(new Intersectable.GeoPoint(c,new Point3D(0,0,-2)));
        //ray goes thorough middle of cylinder top and button
        assertEquals(list,c.findIntersections(r));

    }

    /**
     * check 0 radius,
     * test for {@link Cylinder#Cylinder Cylinder} constructor
     */
    @Test(expected = IllegalArgumentException.class)
    public void radiusZeroExc(){
        Cylinder cy = new Cylinder(0,new Ray(new Point3D(1, 2, 3), new Vector(10, 10, 10)),10);
    }

    /**
     * check negative radius,
     * test for {@link Cylinder#Cylinder Cylinder} constructor
     */
    @Test(expected = IllegalArgumentException.class)
    public void radiusNegativeExc(){
        Cylinder cy = new Cylinder(-1,new Ray(new Point3D(1, 2, 3), new Vector(10, 10, 10)),10);
    }

    /**
     * check 0 height,
     * test for {@link Cylinder#Cylinder Cylinder} constructor
     */
    @Test(expected = IllegalArgumentException.class)
    public void heightZeroExc(){
        Cylinder cy = new Cylinder(5,new Ray(new Point3D(1, 2, 3), new Vector(10, 10, 10)),0);
    }

    /**
     * check negative height,
     * test for {@link Cylinder#Cylinder Cylinder} constructor
     */
    @Test(expected = IllegalArgumentException.class)
    public void heightNegativeExc(){
        Cylinder cy = new Cylinder(5,new Ray(new Point3D(1, 2, 3), new Vector(10, 10, 10)),-10);
    }
}