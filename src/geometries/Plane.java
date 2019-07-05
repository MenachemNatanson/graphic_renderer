package geometries;

import primitives.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static primitives.Util.isZero;

/**
 * Plane
 */
public class Plane extends Geometry {

    Point3D _point;
    private Vector _vector;

    /* ********* Constructors ***********/

    /**
     * A new Plane
     *
     * @param p  a point on the plane
     * @param vec a vector in the plane
     */
    public Plane(Point3D p, Vector vec) {
        _point = new Point3D(p);
        _vector = vec.normal();
    }

    /**
     * A new Plane
     *
     * @param p  a point on the plane
     * @param vec a vector in the plane
     * @param material material
     * @param emission emission
     * */
    public Plane(Point3D p, Vector vec,Material material,Color emission) {
        super(material,emission);
        _point = new Point3D(p);
        _vector = vec.normal();
    }

    /**
     * A new Plane
     * @param p1 Point 1
     * @param p2 Point 2
     * @param p3 Point 3
     */
    public Plane(Point3D p1 , Point3D p2 ,Point3D p3){
        _point = new Point3D(p1);
        Vector v1 = p2.subtract(p3);
        Vector v2 = p2.subtract(p1);
        try {
            _vector = v1.crossProduct(v2).normal();
        }catch (IllegalArgumentException exc){
            throw new IllegalArgumentException("This is not a Plane/Triangle");
        }
    }

    /**
     * A new Plane
     * @param p1 Point 1
     * @param p2 Point 2
     * @param p3 Point 3
     * @param material material
     * @param emission emission
     */
    public Plane(Point3D p1 , Point3D p2 ,Point3D p3,Material material,Color emission){
        super(material,emission);
        _point = new Point3D(p1);
        Vector v1 = p2.subtract(p3);
        Vector v2 = p2.subtract(p1);
        try {
            _vector = v1.crossProduct(v2).normal();
        }catch (IllegalArgumentException exc){
            throw new IllegalArgumentException("This is not a Plane/Triangle");
        }
    }
    /* ************** Admin *****************/

    @Override
    public String toString() {
        return "Plane{" + _point + ", " + _vector + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plane plane = (Plane) o;
        return _point.equals(plane._point) &&
                _vector.equals(plane._vector);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_point, _vector);
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

    /**
     * Get Vector
     *
     * @return vector
     */
    public Vector getVector() {
        return _vector;
    }

    /**
     * Get the normal from the point in the shape
     *
     * @param p the point
     * @return the normal
     */
    @Override
    public Vector getNormal(Point3D p) {
        return new Vector(_vector);
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
        //list to return
        List<GeoPoint> list = new ArrayList<>();

        //get ray point and vector
        Point3D rayP = ray.getPoint3D();
        Vector rayV = ray.getVector();

        // check if the ray is parallel to the plane
        if (isZero(_vector.dotProduct(rayV))) // dotProduct = 0 => parallel
            return EMPTY_LIST;

        try {
            /*
            Ray points: P=P0+t∙v, , t≥0
            Plane points: Plane points: N∙(Q0−P)=0
            N∙(Q0−t∙v−P0)=0
            N∙(Q0−P0)−t∙N∙v=0
            t=N∙(Q0−P0)/(N∙v)

            t is the distance between the ray starting point and the plane
            */
            double t = (_vector.dotProduct(_point.subtract(rayP))) / (_vector.dotProduct(rayV));

            if(isZero(t)) // the ray starts on the plane
                list.add(new GeoPoint(this,rayP));
            else if(t > 0.0) // the ray crosses the plane
                list.add(new GeoPoint(this,rayP.add(rayV.scale(t))));
            else // the ray doesn't cross the plane
                return EMPTY_LIST;

        } catch(Exception ex){
            // _point.subtract(rayP) is vector zero, which means the ray point is equal to the plane point (ray start on plane)
            list.add(new GeoPoint(this,_point));
        }

        return list;
    }

    /* ************* Operations ***************/

}
