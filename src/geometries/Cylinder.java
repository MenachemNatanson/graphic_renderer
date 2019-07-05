package geometries;

import primitives.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static primitives.Util.isZero;

/**
 * Cylinder
 */
public class Cylinder extends Tube   {
    private double _height;

    /* ********* Constructors ***********/

    /**
     * A new Cylinder
     *
     * @param radius the radius
     * @param ray    the direction vector and middle point
     * @param height the height
     */
    public Cylinder(double radius, Ray ray, double height) {
        super(radius, ray);
        if (isZero(height) || height < 0)
            throw new IllegalArgumentException("Height is zero or negative");
        this._height = height;
        setBorders();
    }

    /**
     * A new Cylinder
     *
     * @param radius the radius
     * @param ray    the direction vector and middle point
     * @param height the height
     * @param material material
     * @param emission emission
     */
    public Cylinder(double radius, Ray ray, double height,Material material,Color emission) {
        super(radius, ray,material,emission);
        if (isZero(height) || height < 0)
            throw new IllegalArgumentException("Height is zero or negative");
        this._height = height;
        setBorders();
    }

    /**
     * A new Cylinder
     *
     * @param radius the radius
     * @param material material
     * @param emission emission
     */
    public Cylinder(double radius,Point3D p1,Point3D p2,Material material,Color emission) {
        super(radius, new Ray(p1.add(p2.subtract(p1).normal().scale(p1.distance(p2)/2)),p2.subtract(p1)),material,emission);
        double height = p1.distance(p2);
        if (isZero(height) || height < 0)
            throw new IllegalArgumentException("Height is zero or negative");
        this._height = height;
        setBorders();
    }

    /**
     * set the middle max and min points
     */
    private void setBorders(){
        Point3D rayP = _ray.getPoint3D();
        Vector rayV = _ray.getVector();
        Point3D upperPoint = rayP.add(rayV.scale(_height / 2));
        Point3D underPoint = rayP.subtract(rayV.scale(_height / 2));

        double maxX = upperPoint.add(new Vector(1,0,0).scale(_radius)).getX().get();
        maxX = Math.max(maxX,underPoint.add(new Vector(1,0,0).scale(_radius)).getX().get());
        double maxY = upperPoint.add(new Vector(0,1,0).scale(_radius)).getY().get();
        maxY =Math.max( maxY,underPoint.add(new Vector(0,1,0).scale(_radius)).getY().get());
        double maxZ = upperPoint.add(new Vector(0,0,1).scale(_radius)).getZ().get();
        maxZ = Math.max(maxZ,underPoint.add(new Vector(0,0,1).scale(_radius)).getZ().get());

        double minX = upperPoint.add(new Vector(-1,0,0).scale(_radius)).getX().get();
        minX = Math.min(minX,underPoint.add(new Vector(-1,0,0).scale(_radius)).getX().get());
        double minY = upperPoint.add(new Vector(0,-1,0).scale(_radius)).getY().get();
        minY = Math.min(minY,underPoint.add(new Vector(0,-1,0).scale(_radius)).getY().get());
        double minZ = upperPoint.add(new Vector(0,0,-1).scale(_radius)).getZ().get();
        minZ = Math.min(minZ,underPoint.add(new Vector(0,0,-1).scale(_radius)).getZ().get());

        setMax(new Point3D(maxX,maxY,maxZ));
        setMin(new Point3D(minX,minY,minZ));
        setMiddle(new Point3D((maxX+minX)/2,(maxY+minY)/2,(maxZ+minZ)/2));
    }

    /* ************* Getters/Setters *******/

    /**
     * Get Height
     *
     * @return height
     */
    public double getHeight() {
        return _height;
    }

    /* ************** Admin *****************/

    @Override
    public String toString() {
        return "Cylinder{" + super.toString() +
                " ,h=" + _height +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Cylinder cylinder = (Cylinder) o;
        return Double.compare(cylinder._height, _height) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(_height);
    }
    /* ************* Operations ***************/

    /**
     * Get the normal from the point in the shape
     *
     * @param p the point
     * @return the normal
     */
    @Override
    public Vector getNormal(Point3D p) {
        double d = _ray.getVector().dotProduct(p.subtract(_ray.getPoint3D()));

        if (isZero(d - _height / 2)) // p is on the top base (d>0)
            return new Vector(_ray.getVector());
        if (isZero(d + _height / 2)) // p is on the bottom base (d<0)
            return _ray.getVector().scale(-1);

        return super.getNormal(p);
    }

    /**
     *  Return All intersections with ray
     *
     * @param ray The ray
     * @return List of intersections (Points)
     * @see Point3D#Point3D(Coordinate, Coordinate, Coordinate)
     * @see Ray#Ray(Point3D, Vector)
     */
    @Override
    public List<GeoPoint> findIntersections(Ray ray) {
        if(!getOptimised()||intersects(ray)) {
            List<GeoPoint> list = new ArrayList<>();
            Point3D rayP = _ray.getPoint3D();
            Vector rayV = _ray.getVector();

            //get tube intersections
            for (GeoPoint p : super.findIntersections(ray)) {
                double d = Math.abs(rayV.dotProduct(p.point.subtract(rayP)));
                //if point is in the range
                if (Util.usubtract(_height / 2, d) >= 0.0)
                    list.add(new GeoPoint(this, p.point));
            }

            //get upper plane intersections
            Point3D upperPoint = rayP.add(rayV.scale(_height / 2));
            Plane upperPlane = new Plane(upperPoint, rayV);
            for (GeoPoint p : upperPlane.findIntersections(ray)) {
                //if point is in the range
                if (Util.usubtract(_radius, upperPoint.distance(p.point)) >= 0)
                    list.add(new GeoPoint(this, p.point));
            }

            //get under plane intersections
            Point3D underPoint = rayP.subtract(rayV.scale(_height / 2));
            Plane underPlane = new Plane(underPoint, rayV);
            for (GeoPoint p : underPlane.findIntersections(ray)) {
                //if point is in the range
                if (Util.usubtract(_radius, underPoint.distance(p.point)) >= 0)
                    list.add(new GeoPoint(this, p.point));
            }

            return list;
        }else{
            return EMPTY_LIST;
        }
    }

}
