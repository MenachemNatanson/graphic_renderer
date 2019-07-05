package test;

import geometries.*;
import org.junit.Test;
import org.omg.PortableServer.POA;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GeometriesTest {

    /**
     * Check add(...) func
     */
    @Test
    public void add() {
        Geometries g = new Geometries();

        //Check add no geometry
        List<Intersectable.GeoPoint> list=new ArrayList<>();
        assertEquals("Check add function. no Geometry",list,g.findIntersections(new Ray(new Point3D(0,0,0),new Vector(0,0,-1))));

        Sphere geo = new Sphere(1,new Point3D(0,0,-3));
        g.add(geo);

        //Check add one geometry
        list=new ArrayList<>();
        list.add(new Intersectable.GeoPoint(geo,new Point3D(0,0,-2)));
        list.add(new Intersectable.GeoPoint(geo,new Point3D(0,0,-4)));
        assertEquals("Check add function. one Geometry",list,g.findIntersections(new Ray(new Point3D(0,0,0),new Vector(0,0,-1))));
        Sphere sp1 = new Sphere(1,new Point3D(0,0,-3));
        Sphere sp2 = new Sphere(1,new Point3D(0,0,-3));
        g.add(sp1,sp2);

        //Check add two geometries at the same place
        list.add(new Intersectable.GeoPoint(sp1,new Point3D(0,0,-2)));
        list.add(new Intersectable.GeoPoint(sp1,new Point3D(0,0,-4)));
        list.add(new Intersectable.GeoPoint(sp1,new Point3D(0,0,-2)));
        list.add(new Intersectable.GeoPoint(sp1,new Point3D(0,0,-4)));
        List<Intersectable.GeoPoint> l = g.findIntersections(new Ray(new Point3D(0,0,0),new Vector(0,0,-1)));
        assertEquals("Check add two geometries at the same place",list,l);

        g = new Geometries();
        g.add(new Sphere(1,new Point3D(0,0,-3)),new Sphere(1,new Point3D(0,0,-2)));

        //Check add two geometries not at the same place
        list.clear();
        list.add(new Intersectable.GeoPoint(sp1,new Point3D(0,0,-2)));
        list.add(new Intersectable.GeoPoint(sp1,new Point3D(0,0,-4)));
        list.add(new Intersectable.GeoPoint(sp1,new Point3D(0,0,-1)));
        list.add(new Intersectable.GeoPoint(sp1,new Point3D(0,0,-3)));
        l = g.findIntersections(new Ray(new Point3D(0,0,0),new Vector(0,0,-1)));
        assertEquals("Check add two geometries not at the same place",list,l);

    }

    /**
     * Check constractor
     */
    @Test
    public void ctor() {
        Geometries g = new Geometries();

        //Check add no geometry
        List<Intersectable.GeoPoint> list=new ArrayList<>();
        assertEquals("Check add function. no Geometry",list,g.findIntersections(new Ray(new Point3D(0,0,0),new Vector(0,0,-1))));

        Sphere sp1 = new Sphere(1,new Point3D(0,0,-3));
        Sphere sp2 = new Sphere(1,new Point3D(0,0,-3));
        g.add(sp1,sp2);
        //Check add two geometries at the same place
        list.add(new Intersectable.GeoPoint(sp1,new Point3D(0,0,-2)));
        list.add(new Intersectable.GeoPoint(sp1,new Point3D(0,0,-4)));
        list.add(new Intersectable.GeoPoint(sp1,new Point3D(0,0,-2)));
        list.add(new Intersectable.GeoPoint(sp1,new Point3D(0,0,-4)));
        List<Intersectable.GeoPoint> l = g.findIntersections(new Ray(new Point3D(0,0,0),new Vector(0,0,-1)));
        assertEquals("Check add two geometries at the same place",list,l);

        g = new Geometries(new Sphere(1,new Point3D(0,0,-3)),new Sphere(1,new Point3D(0,0,-2)));
        list.clear();
        //Check add two geometries not at the same place
        list.add(new Intersectable.GeoPoint(sp1,new Point3D(0,0,-2)));
        list.add(new Intersectable.GeoPoint(sp1,new Point3D(0,0,-4)));
        list.add(new Intersectable.GeoPoint(sp1,new Point3D(0,0,-1)));
        list.add(new Intersectable.GeoPoint(sp1,new Point3D(0,0,-3)));
        l = g.findIntersections(new Ray(new Point3D(0,0,0),new Vector(0,0,-1)));
        assertEquals("Check add two geometries not at the same place",list,l);


    }


}