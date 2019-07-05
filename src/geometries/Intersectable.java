package geometries;

import primitives.Coordinate;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.lang.Math.abs;

/**
 * An Intersectable
 */
public abstract class Intersectable {
    private Point3D _minimum;
    private Point3D _maximum;
    private Point3D _middle;
    private Boolean _optimised;

    public Intersectable() {

        _optimised=true;
    }

    /* ************* Getters/Setters *******/

    /**
     * get maximum point
     * @return 3d point
     */
    public Point3D getMax(){
        return _maximum;
    }

    /**
     * get minimum point
     * @return 3d point
     */
    public Point3D getMin(){
        return  _minimum;
    }

    /**
     * Set minimum
     * @param m point
     */
    public void setMin(Point3D m){
        _minimum = new Point3D(m);
    }

    /**
     * Set maximum
     * @param m point
     */
    public void setMax(Point3D m){
        _maximum = new Point3D(m);
    }

    /**
     * Get middle point
     * @return point
     */
    public Point3D getMiddle() {
        return _middle;
    }

    /**
     * set middle point
     * @param middle point
     */
    public void setMiddle(Point3D middle) {
        this._middle = new Point3D(middle);
    }

    /**
     * get if intersection is optimised
     * @return boolean
     */
    public Boolean getOptimised() {
        return _optimised;
    }

    /**
     * Set if intersection is optimised
     * @param optimised true if optimised
     */
    public void setOptimised(Boolean optimised) {
        _optimised = optimised;
    }

    /**
     * get Volume
     * @return the volume
     */
    public double getVolume(){
        Point3D p  = _maximum.subtract(_minimum).getPoint3D();
        return abs(p.getX().get()) * abs(p.getY().get()) * abs(p.getZ().get());
    }

    /* ************* Operators *******/

    /**
     * All intersections between the ray and the object
     * @param ray The ray
     * @return List of intersections (Points)
     * @see Point3D#Point3D(Coordinate, Coordinate, Coordinate)
     * @see Ray#Ray(Point3D, Vector)
     */
    public abstract List<GeoPoint> findIntersections(Ray ray);

    /**
     * All intersections between the ray and the object
     * @param ray The ray
     * @param distance2 max distance for intersections (squared)
     * @return List of intersections (Points)
     * @see Point3D#Point3D(Coordinate, Coordinate, Coordinate)
     * @see Ray#Ray(Point3D, Vector)
     */
    public List<GeoPoint> findIntersections(Ray ray, double distance2) {
        List<GeoPoint> intersections = findIntersections(ray);
        if (distance2 != -1) {
            Point3D p0 = ray.getPoint3D();
            intersections.removeIf(gp -> p0.distance2(gp.point) > distance2);
        }
        return intersections;
    }

    /**
     * Check if ray intersects box
     * @param ray the ray
     * @return boolean
     */
    public boolean intersects(Ray ray) {
        // algorithm for finding if there is an intersection with the box that is parallel to the axis
        double tmin, tmax, tymin, tymax, tzmin, tzmax;

        Point3D max = getMax();
        Point3D min = getMin();

        Point3D dir = ray.getVector().getPoint3D();
        Point3D origin = ray.getPoint3D();
        Point3D inv = ray.getInverted().getPoint3D();

        double divx = inv.getX().get();
        double divy = inv.getY().get();

        if (dir.getX().get()>=0){
            tmin = min.getX().subtract(origin.getX()).scale(divx).get();
            tmax = max.getX().subtract(origin.getX()).scale(divx).get();
        }else{
            tmax = min.getX().subtract(origin.getX()).scale(divx).get();
            tmin = max.getX().subtract(origin.getX()).scale(divx).get();
        }
        if (dir.getY().get()>=0){
            tymin = min.getY().subtract(origin.getY()).scale(divy).get();
            tymax = max.getY().subtract(origin.getY()).scale(divy).get();
        }else{
            tymax = min.getY().subtract(origin.getY()).scale(divy).get();
            tymin = max.getY().subtract(origin.getY()).scale(divy).get();
        }

        if ( (tmin > tymax) || (tymin > tmax) )
            return false;

        if (tymin > tmin)
            tmin = tymin;

        if (tymax < tmax)
            tmax = tymax;

        double divz = inv.getZ().get();

        if (dir.getZ().get()>=0){
            tzmin = min.getZ().subtract(origin.getZ()).scale(divz).get();
            tzmax = max.getZ().subtract(origin.getZ()).scale(divz).get();
        }else{
            tzmax = min.getZ().subtract(origin.getZ()).scale(divz).get();
            tzmin = max.getZ().subtract(origin.getZ()).scale(divz).get();
        }
        if ( (tmin > tzmax) || (tzmin > tmax) )
            return false;

        if (tzmax < tmax)
            tmax = tzmax;

        return (tmax >= 0);
    }

    /**
     * An empty list
     */
    List<GeoPoint> EMPTY_LIST = new ArrayList<>();

    public static class GeoPoint {
        public Geometry geometry;
        public Point3D point;

        /* ********* Constructors ***********/

        public GeoPoint(Geometry geometry,Point3D point){
            this.geometry = geometry;
            this.point = point;
        }

        /* ************** Admin *****************/

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return Objects.equals(geometry, geoPoint.geometry) &&
                    point.equals( geoPoint.point);
        }

        @Override
        public String toString() {
            return "GP{" +
                    "G=" + geometry +
                    ", P=" + point +
                    '}';
        }
    }

}
