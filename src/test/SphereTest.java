package test;

import geometries.Intersectable;
import geometries.Sphere;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SphereTest {

    /**
     * check sphere normal,
     * test for {@link Sphere#getNormal getNormal} method
     */
    @Test
    public void getNormal() {
        Sphere sp = new Sphere(Math.sqrt(3),new Point3D(1,1,1));
        assertEquals(new Vector(1,1,1).normal(),sp.getNormal(new Point3D(2,2,2)));
    }

    /**
     * check 0 radius,
     * test for {@link Sphere#Sphere Sphere} constructor
     */
    @Test(expected = IllegalArgumentException.class)
    public void radiusZeroExc(){
        Sphere sp = new Sphere(0,new Point3D(1,1,1));
    }

    /**
     * check negative radius,
     * test for {@link Sphere#Sphere Sphere} constructor
     */
    @Test(expected = IllegalArgumentException.class)
    public void radiusNegativeExc(){
        Sphere sp = new Sphere(-1,new Point3D(1,1,1));
    }

    /**
     * Check findIntersections
     */
    @Test
    public void findIntersections(){
        Sphere sp;
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


        //Sphere on (0,0,-3) and radius 1 (Sphere in front of the plane)
        sp = new Sphere(1,new Point3D(0,0,-3));

        //ray going through middle pixel
        list = new ArrayList<>();
        list.add(new Intersectable.GeoPoint(sp,new Point3D(0,0,-2)));
        list.add(new Intersectable.GeoPoint(sp,new Point3D(0,0,-4)));
        assertEquals("ray going through middle pixel",list,sp.findIntersections(middle));

        //ray going through upper left pixel
        list.clear();
        assertEquals("ray going through upper left pixel",list,sp.findIntersections(leftUp));

        //ray going through under right pixel
        list.clear();
        assertEquals("ray going through under right pixel",list,sp.findIntersections(rightDown));

        //Sphere on (0,0,0) and radius 5 (Sphere on 0,0,0 and plane inside)
        sp = new Sphere(5,new Point3D(0,0,0));

        //ray going through middle pixel
        list.clear();
        list.add(new Intersectable.GeoPoint(sp,(new Vector(0,0,-1)).normal().scale(5).getPoint3D()));
        assertEquals("ray going through middle pixel",list,sp.findIntersections(middle));

        //ray going through upper left pixel
        list.clear();
        list.add(new Intersectable.GeoPoint(sp,(new Vector(-3,3,-1)).normal().scale(5).getPoint3D()));
        assertEquals("ray going through upper left pixel",list,sp.findIntersections(leftUp));

        //ray going through under right pixel
        list.clear();
        list.add(new Intersectable.GeoPoint(sp,(new Vector(3,-3,-1)).normal().scale(5).getPoint3D()));
        assertEquals("ray going through under right pixel",list,sp.findIntersections(rightDown));

        //Sphere on (0,0,-0.5) and radius 5 (Plane inside the Sphere)
        sp = new Sphere(5,new Point3D(0,0,-0.5));

        //ray going through middle pixel
        list.clear();
        list.add(new Intersectable.GeoPoint(sp,new Point3D(0,0,-5.5)));
        assertEquals("ray going through middle pixel",list,sp.findIntersections(middle));

        //ray going through upper left pixel
        list.clear();
        list.add(new Intersectable.GeoPoint(sp,new Point3D(-3.503843994234294,3.503843994234294,-1.167947998078098)));
        assertEquals("ray going through upper left pixel",list,sp.findIntersections(leftUp));

        //ray going through under right pixel
        list.clear();
        list.add(new Intersectable.GeoPoint(sp,new Point3D(3.503843994234294,-3.503843994234294,-1.167947998078098)));
        assertEquals("ray going through under right pixel",list,sp.findIntersections(rightDown));

        //Sphere on (0,0,5) and radius 3 (Sphere behind the plane)
        sp = new Sphere(3,new Point3D(0,0,5));

        //ray going through middle pixel
        list.clear();
        assertEquals("ray going through middle pixel",list,sp.findIntersections(middle));

        //ray going through upper left pixel
        list.clear();
        assertEquals("ray going through upper left pixel",list,sp.findIntersections(leftUp));

        //ray going through under right pixel
        list.clear();
        assertEquals("ray going through under right pixel",list,sp.findIntersections(rightDown));

        //** Other Checks***/
        sp = new Sphere(1,new Point3D(2,0,0));

        //Check when the ray is tangent sphere
        r = new Ray(new Point3D(0,0,0),new Vector(3/2d,0,Math.sqrt(3)/2));
        list = new ArrayList<>();
        list.add(new Intersectable.GeoPoint(sp,new Point3D(3/2d,0,Math.sqrt(3)/2)));
        assertEquals("Check when the ray is tangent sphere",list,sp.findIntersections(r));

        //Check when the ray is tangent sphere and starts from there
        r = new Ray(new Point3D(3/2d,0,Math.sqrt(3)/2),new Vector(3/2d,0,Math.sqrt(3)/2));
        list.clear();
        list.add(new Intersectable.GeoPoint(sp,new Point3D(3/2d,0,Math.sqrt(3)/2)));
        assertEquals("Check when the ray is tangent sphere and starts from there",list,sp.findIntersections(r));

        //Check when the ray is tangent sphere and starts after there
        r = new Ray((new Point3D(3/2d,0,Math.sqrt(3)/2)).add(new Vector(3/2d,0,Math.sqrt(3)/2)),new Vector(3/2d,0,Math.sqrt(3)/2));
        list.clear();
        assertEquals("Check when the ray is tangent sphere and starts after there",list,sp.findIntersections(r));

        //Check when the ray starts on the Sphere and crosses it again
        r = new Ray((new Point3D(1,0,0)),new Vector(1,1,0));
        list.clear();
        list.add(new Intersectable.GeoPoint(sp,new Point3D(1,0,0)));
        list.add(new Intersectable.GeoPoint(sp,new Point3D(2,1,0)));
        assertEquals("Check when the ray starts on the Sphere and crosses it again",list,sp.findIntersections(r));

        //Check when the ray starts on the Sphere and doesn't crosses it again
        r = new Ray((new Point3D(2,1,0)),new Vector(1,1,0));
        list.clear();
        list.add(new Intersectable.GeoPoint(sp,new Point3D(2,1,0)));
        assertEquals("Check when the ray starts on the Sphere and doesn't crosses it again",list,sp.findIntersections(r));

        //Check when the ray starts on the middle of Sphere
        r = new Ray((new Point3D(2,0,0)),new Vector(1,1,0));
        list.clear();
        list.add(new Intersectable.GeoPoint(sp,new Point3D(2,0,0).add((new Vector(1,1,0)).normal())));
        assertEquals("Check when the ray starts on the middle of Sphere",list,sp.findIntersections(r));

        //Check when the ray starts after the Sphere and is parallel to the Sphere middle
        r = new Ray((new Point3D(4,0,0)),new Vector(1,0,0));
        list.clear();
        assertEquals("Check when the ray starts after the Sphere and is parallel to the Sphere middle",list,sp.findIntersections(r));

        //Check when the ray starts at the and of the Sphere and is parallel to the Sphere middle
        r = new Ray((new Point3D(3,0,0)),new Vector(1,0,0));
        list.clear();
        list.add(new Intersectable.GeoPoint(sp,new Point3D(3,0,0)));
        assertEquals("Check when the ray starts at the and of the Sphere and is parallel to the Sphere middle",list,sp.findIntersections(r));

        //Check when the ray starts at the begging of the Sphere and is parallel to the Sphere middle
        r = new Ray((new Point3D(1,0,0)),new Vector(1,0,0));
        list.clear();
        list.add(new Intersectable.GeoPoint(sp,new Point3D(1,0,0)));
        list.add(new Intersectable.GeoPoint(sp,new Point3D(3,0,0)));
        assertEquals("Check when the ray starts at the begging of the Sphere and is parallel to the Sphere middle",list,sp.findIntersections(r));

        //Check when the ray parallel to a tangent plane to the Sphere
        sp = new Sphere(1,new Point3D(2,-2,0));
        r = new Ray((new Point3D(2,0,0)),new Vector(1,0,0));
        list.clear();
        assertEquals("Check when the ray parallel to a tangent plane to the Sphere",list,sp.findIntersections(r));
    }
}