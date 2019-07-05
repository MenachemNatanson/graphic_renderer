package test;

import geometries.Box;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.*;

public class BoxTest {

    @Test
    public void intersectsBox() {
        Box box = new Box();
        box.setMiddle(new Point3D(0,0,-100));
        box.setMax(new Point3D(50,50,-50));
        box.setMin(new Point3D(-50,-50,-150));
        Ray ray = new Ray(new Point3D(0,0,0),new Vector(0,0,-1));
        assertTrue("Box in front of ray",box.intersects(ray));

        ray = new Ray(new Point3D(0,0,0),new Vector(1,1,-1));
        assertTrue("Box in front of ray",box.intersects(ray));

        ray = new Ray(new Point3D(0,0,0),new Vector(2,1,-1));
        assertFalse("Box in front of ray",box.intersects(ray));

        ray = new Ray(new Point3D(0,0,0),new Vector(0,0,1));
        assertFalse("Box in the back of ray",box.intersects(ray));

        ray = new Ray(new Point3D(0,0,-100),new Vector(0,0,-1));
        assertTrue("ray starts inside box",box.intersects(ray));

        ray = new Ray(new Point3D(0,50,0),new Vector(0,0,-1));
        assertTrue("ray tangent to box",box.intersects(ray));

        ray = new Ray(new Point3D(0,0,-150),new Vector(0,0,-1));
        assertTrue("ray starts on box",box.intersects(ray));
    }
}