package geometries;

import primitives.*;
import java.util.List;
import java.util.Objects;

/**
 * Triangle
 */
public class Triangle extends Plane {
    private Point3D _point2;
    private Point3D _point3;

    /* ********* Constructors ***********/

    /**
     * A new Triangle
     *
     * @param p1 point 1
     * @param p2 point 2
     * @param p3 point 3
     */
    public Triangle(Point3D p1, Point3D p2, Point3D p3) {
        super(p1,p2,p3);
        _point2 = new Point3D(p2);
        _point3 = new Point3D(p3);
        setBorders();
    }

    /**
     * A new Triangle
     *
     * @param p1 point 1
     * @param p2 point 2
     * @param p3 point 3
     * @param material material
     * @param emission emission
     * */
    public Triangle(Point3D p1, Point3D p2, Point3D p3,Material material,Color emission) {
        super(p1,p2,p3,material,emission);
        _point2 = new Point3D(p2);
        _point3 = new Point3D(p3);
        setBorders();
    }

    /**
     * set the min and max and middle points
     */
    private void setBorders(){
        double maxX = Math.max(_point.getX().get(),_point2.getX().get());
        maxX = Math.max(maxX,_point3.getX().get());
        double maxY = Math.max(_point.getY().get(),_point2.getY().get());
        maxY = Math.max(maxY,_point3.getY().get());
        double maxZ = Math.max(_point.getZ().get(),_point2.getZ().get());
        maxZ = Math.max(maxZ,_point3.getZ().get());

        double minX = Math.min(_point.getX().get(),_point2.getX().get());
        minX = Math.min(minX,_point3.getX().get());
        double minY = Math.min(_point.getY().get(),_point2.getY().get());
        minY = Math.min(minY,_point3.getY().get());
        double minZ = Math.min(_point.getZ().get(),_point2.getZ().get());
        minZ = Math.min(minZ,_point3.getZ().get());

        setMax(new Point3D(maxX,maxY,maxZ));
        setMin(new Point3D(minX,minY,minZ));
        setMiddle(new Point3D((maxX+minX)/2,(maxY+minY)/2,(maxZ+minZ)/2));

    }

    /* ************* Getters/Setters *******/

    /**
     * Get First point
     *
     * @return the point
     */
    public Point3D getPoint1() {
        return super._point;
    }

    /**
     * Get Second Point
     *
     * @return the point
     */
    public Point3D getPoint2() {
        return _point2;
    }

    /**
     * Get the Third point
     *
     * @return the point
     */
    public Point3D getPoint3() {
        return _point3;
    }

    /* ************** Admin *****************/

    @Override
    public String toString() {
        return "Tri{" +
                "P1=" + super._point +
                ", P2=" + _point2 +
                ", P3=" + _point3 +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Triangle triangle = (Triangle) o;
        return _point2.equals(triangle._point2) &&
                _point3.equals(triangle._point3);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), _point2, _point3);
    }

    /* ************** Operations ***************/
    /**
     * Returns all intersections with ray
     *
     * @param ray The ray
     * @return List of intersections (Points)
     * @see Point3D#Point3D(Coordinate, Coordinate, Coordinate)
     * @see Ray#Ray(Point3D, Vector)
     */
    @Override
    public List<GeoPoint> findIntersections(Ray ray) {
        if(!getOptimised()||intersects(ray)) {
            // get the point from the plane intersection
            List<GeoPoint> list = super.findIntersections(ray);

            // check if there is any intersection with the plane of the triangle
            if (list.isEmpty())
                return EMPTY_LIST;

            Point3D planeP = list.get(0).point; // intersection point with the plane of the triangle
            Point3D rayP = ray.getPoint3D().subtract(ray.getVector()); //get a point before the original ray point

            // we will never get vector zero, because we took a point that located before the original ray point

        /*
        check if the intersection point is inside the triangle

        V1 = P1−P0
        V2 = P2−P0
        V3 = P3−P0
        N1 = normalize(V1×V2)
        N2 = normalize(V2×V3)
        N3 = normalize(V3×V1)
        */
            Vector v1, v2, v3, n1, n2, n3;

            v1 = _point.subtract(rayP);
            v2 = _point2.subtract(rayP);
            v3 = _point3.subtract(rayP);

            n1 = v1.crossProduct(v2).normal();
            n2 = v2.crossProduct(v3).normal();
            n3 = v3.crossProduct(v1).normal();

            // The point is inside if all (P−P0)∙N𝒊 have the same sign (+/-)
            // checking if got the same sign (+/-)
            // Constraint compromise: if one or more are 0.0 – no intersection
            double n1Sign = planeP.subtract(rayP).dotProduct(n1),
                    n2Sign = planeP.subtract(rayP).dotProduct(n2),
                    n3Sign = planeP.subtract(rayP).dotProduct(n3);

            if (Util.alignZero(n1Sign) > 0.0 && Util.alignZero(n2Sign) > 0.0 && Util.alignZero(n3Sign) > 0.0 ||
                    Util.alignZero(n1Sign) < 0.0 && Util.alignZero(n2Sign) < 0.0 && Util.alignZero(n3Sign) < 0.0) {
                list.clear();
                list.add(new GeoPoint(this, planeP));
                return list;
            } else
                return EMPTY_LIST;
        }else{
            return EMPTY_LIST;
        }
    }
}
