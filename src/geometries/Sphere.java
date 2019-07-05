package geometries;

import primitives.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Sphere
 */
public class Sphere extends RadialGeometry  {
    private Point3D _point;

    /* ********* Constructors ***********/

    /**
     * A new Sphere
     *
     * @param radius the radius
     * @param point  the middle point
     */
    public Sphere(double radius, Point3D point) {
        super(radius);
        _point = new Point3D(point);
        setBorders();
    }

    /**
     * A new Sphere
     *
     * @param radius the radius
     * @param point  the middle point
     * @param material material
     * @param emission emission     */
    public Sphere(double radius, Point3D point,Material material,Color emission) {
        super(radius,material,emission);
        _point = new Point3D(point);
        setBorders();
    }

    /**
     * set the middle max and min points
     */
    private void setBorders(){
        double maxX = _point.add(new Vector(1,0,0).scale(_radius)).getX().get();
        double maxY = _point.add(new Vector(0,1,0).scale(_radius)).getY().get();
        double maxZ = _point.add(new Vector(0,0,1).scale(_radius)).getZ().get();
        double minX = _point.add(new Vector(-1,0,0).scale(_radius)).getX().get();
        double minY = _point.add(new Vector(0,-1,0).scale(_radius)).getY().get();
        double minZ = _point.add(new Vector(0,0,-1).scale(_radius)).getZ().get();
        setMax(new Point3D(maxX,maxY,maxZ));
        setMin(new Point3D(minX,minY,minZ));
        setMiddle(new Point3D((maxX+minX)/2,(maxY+minY)/2,(maxZ+minZ)/2));
    }
    /* ************* Getters/Setters *******/

    /**
     * Get Point
     *
     * @return point
     */
    public Point3D getPoint() {
        return _point;
    }

    /* ************** Admin *****************/

    @Override
    public String toString() {
        return "Sphere{" + super.toString() +
                " ,P=" + _point +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Sphere sphere = (Sphere) o;
        return _point.equals(sphere._point);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_point);
    }

    /**
     * Get the normal from the point in the shape
     *
     * @param p the point
     * @return the normal
     */
    @Override
    public Vector getNormal(Point3D p) {
        return p.subtract(_point).normal();
    }

    /**
     * Returns All intersections with ray
     *
     * @param ray The ray
     * @return List of intersections (Points)
     * @see Point3D#Point3D(Coordinate, Coordinate, Coordinate)
     * @see Ray#Ray(Point3D, Vector)
     */
    @Override
    public List<GeoPoint> findIntersections(Ray ray) {
        if(!getOptimised()||intersects(ray)) {
            //list to return
            List<GeoPoint> list;

            //get ray point and vector
            Point3D rayP = ray.getPoint3D();
            Vector rayV = ray.getVector();

            //vector between ray start and sphere center
            Vector l;
            try {
                l = _point.subtract(rayP);
            } catch (Exception ex) {
                //if Sphere is on ray start point then return p0 + r*V
                list = new ArrayList<>();
                list.add(new GeoPoint(this, rayP.add(rayV.scale(_radius))));
                return list;
            }

            //the scale for the ray in order to get parallel to the sphere center
            double tm = l.dotProduct(rayV);

            double lengthL = l.length();

            //get the distance between the ray and the sphere center
            double d2 = Util.usubtract(lengthL * lengthL, tm * tm);
            double d = Math.sqrt(d2);

            //the ray doesn't cross the sphere
            if (Util.usubtract(d, _radius) > 0.0)
                return EMPTY_LIST;

            //the ray tangent the sphere
            if (Util.usubtract(d, _radius) == 0.0) {
                list = new ArrayList<>();
                if (Util.alignZero(tm) >= 0.0)
                    try {
                        list.add(new GeoPoint(this, rayP.add(rayV.scale(tm))));
                    } catch (Exception ex) {
                        list.add(new GeoPoint(this, rayP));
                    }
                return list;
            }
            //the ray crosses the sphere is two places
            double th = Math.sqrt(Util.usubtract(_radius * _radius, d2));
            //get the distance to the two points
            double t1 = Util.usubtract(tm, th);
            double t2 = Util.uadd(tm, th);

            //return the points that are after the ray
            list = new ArrayList<>();
            if (Util.alignZero(t1) >= 0.0)
                try {
                    list.add(new GeoPoint(this, rayP.add(rayV.scale(t1))));
                } catch (Exception ex) {
                    list.add(new GeoPoint(this, rayP));
                }
            if (Util.alignZero(t2) >= 0.0)
                try {
                    list.add(new GeoPoint(this, rayP.add(rayV.scale(t2))));
                } catch (Exception ex) {
                    list.add(new GeoPoint(this, rayP));
                }
            return list;
        }else{
            return EMPTY_LIST;
        }
    }

    /* ************* Operations ***************/
}
