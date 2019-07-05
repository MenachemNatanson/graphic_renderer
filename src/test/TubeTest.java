package test;

import geometries.Intersectable;
import geometries.Sphere;
import geometries.Tube;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TubeTest {

    /**
     * check tube normal,
     * test for {@link Tube#getNormal getNormal} method
     */
    @Test
    public void getNormal() {
        Tube tb = new Tube(1,new Ray(new Point3D(1, 0, 0), new Vector(0, 1, 0)));
        assertEquals(new Vector(-1,0,0).normal(),tb.getNormal(new Point3D(0,3,0)));
        assertEquals(new Vector(1,0,0).normal(),tb.getNormal(new Point3D(2,3,0)));
        assertEquals(new Vector(0,0,1).normal(),tb.getNormal(new Point3D(1,-1,1)));
        assertEquals(new Vector(0,0,-1).normal(),tb.getNormal(new Point3D(1,0,-1)));
    }

    /**
     * check 0 radius,
     * test for {@link Tube#Tube Tube} constructor
     */
    @Test(expected = IllegalArgumentException.class)
    public void radiusZeroExc(){
        Tube tb = new Tube(0,new Ray(new Point3D(1, 2, 3), new Vector(10, 10, 10)));
    }

    /**
     * check negative radius,
     * test for {@link Tube#Tube Tube} constructor
     */
    @Test(expected = IllegalArgumentException.class)
    public void radiusNegativeExc(){
        Tube tb = new Tube(-1,new Ray(new Point3D(1, 2, 3), new Vector(10, 10, 10)));
    }

    /**
     * check findIntersections
     */
    @Test
    public void findIntersections(){
        Tube tb;
        Ray r;
        List<Intersectable.GeoPoint> list;

        /*Check 3 ray going through a plane of 9x9 with 3x3 pixels*
         *  The ray go through the 'R'
         *  * * * * * * *
         *  * R *   *   *
         *  * * * * * * *
         *  *   * R *   *
         *  * * * * * * *
         *  *   *   * R *
         *  * * * * * * *
         * */
        //The 3 rays
        Ray middle =  new Ray(new Point3D(0,0,0),new Vector(0,0,-1));
        Ray leftUp =  new Ray(new Point3D(0,0,0),new Vector(-3,3,-1));
        Ray rightDown = new Ray(new Point3D(0,0,0),new Vector(3,-3,-1));


        //Tube on (0,0,-3) and radius 1 (Tube in front of the plane)
        tb = new Tube(1,new Ray(new Point3D(0,0,-3),new Vector(0,1,0)));

        //ray going through middle pixel
        list = new ArrayList<>();
        list.add(new Intersectable.GeoPoint(tb,new Point3D(0,0,-2)));
        list.add(new Intersectable.GeoPoint(tb,new Point3D(0,0,-4)));
        assertEquals("ray going through middle pixel",list,tb.findIntersections(middle));

        //ray going through upper left pixel
        list.clear();
        assertEquals("ray going through upper left pixel",list,tb.findIntersections(leftUp));

        //ray going through under right pixel
        list.clear();
        assertEquals("ray going through under right pixel",list,tb.findIntersections(rightDown));


        //Tube at (0,0,-3) and going upwards
        tb = new Tube(1,new Ray(new Point3D(0,0,-3),new Vector(0,1,0)));

        //ray going strait up with the z
        list = new ArrayList<>();
        list.add(new Intersectable.GeoPoint(tb,new Point3D(0,1,-2)));
        list.add(new Intersectable.GeoPoint(tb,new Point3D(0,3,-4)));
        assertEquals("ray going strait up with the z",list,tb.findIntersections(new Ray(new Point3D(0,0,-1),new Vector(0,1,-1))));


        //ray starts from tube ray start
        list = new ArrayList<>();
        list.add(new Intersectable.GeoPoint(tb,new Point3D(0,1,-4)));
        assertEquals("ray starts from tube ray start",list,tb.findIntersections(new Ray(new Point3D(0,0,-3),new Vector(0,1,-1))));

        //ray tangent to tube
        list = new ArrayList<>();
        list.add(new Intersectable.GeoPoint(tb,new Point3D(1,0,-3)));
        assertEquals("ray tangent to tube",list,tb.findIntersections(new Ray(new Point3D(1,0,0),new Vector(0,0,-1))));

        //ray tangent to tube and starts there
        list = new ArrayList<>();
        list.add(new Intersectable.GeoPoint(tb,new Point3D(1,0,-3)));
        assertEquals("ray tangent to tube and starts there",list,tb.findIntersections(new Ray(new Point3D(1,0,-3),new Vector(0,0,-1))));

        //ray tangent to tube and starts after there
        list = new ArrayList<>();
        assertEquals("ray tangent to tube and starts after there",list,tb.findIntersections(new Ray(new Point3D(1,0,-4),new Vector(0,0,-1))));

        //ray is going in a different direction
        list = new ArrayList<>();
        assertEquals("ray is going in a different direction",list,tb.findIntersections(new Ray(new Point3D(0,0,0),new Vector(0,0,1))));

        //ray is going in a different direction and starting at the middle
        list = new ArrayList<>();
        list.add(new Intersectable.GeoPoint(tb,new Point3D(0,0,-2)));
        assertEquals("ray is going in a different direction and starting at the middle",list,tb.findIntersections(new Ray(new Point3D(0,0,-3),new Vector(0,0,1))));

        //ray is going in a different direction and starting before the tube
        list = new ArrayList<>();
        list.add(new Intersectable.GeoPoint(tb,new Point3D(0,0,-4)));
        list.add(new Intersectable.GeoPoint(tb,new Point3D(0,0,-2)));
        assertEquals("ray is going in a different direction and starting before the tube",list,tb.findIntersections(new Ray(new Point3D(0,0,-5),new Vector(0,0,1))));
    }
}