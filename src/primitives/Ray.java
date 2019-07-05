package primitives;


/**
 * Ray
 */
public class Ray {
    private Point3D _point3D;
    private Vector _vector;
    private Vector _inverted;
    private static final double EPS = 0.1;

    /* ********* Constructors ***********/

    /**
     * A new Ray
     *
     * @param p the start point
     * @param vec  the vector
     * @see Point3D#Point3D(Coordinate, Coordinate, Coordinate)
     * @see Vector#Vector(Point3D)
     */
    public Ray(Point3D p, Vector vec) {
        _point3D = new Point3D(p);
        _vector = vec.normal();
        p = vec.getPoint3D();
        _inverted = new Vector(1/p.getX().get(),1/p.getY().get(),1/p.getZ().get());
    }

    /**
     * A new Ray where the ray start point is moved by EPS on the
     * normal's line in direction with vec
     *
     * @param p the start point
     * @param vec  the vector
     * @param normal  the vector for start point movement
     * @see Point3D#Point3D(Coordinate, Coordinate, Coordinate)
     * @see Vector#Vector(Point3D)
     */
    public Ray(Point3D p, Vector vec, Vector normal) {
        _vector = vec.normal();
        //add eps
        Vector epsVector = normal.scale((normal.dotProduct(vec) > 0) ? EPS : -EPS);
        _point3D = p.add(epsVector);
        p = vec.getPoint3D();
        _inverted = new Vector(1/p.getX().get(),1/p.getY().get(),1/p.getZ().get());

    }

    /**
     * A new Ray
     *
     * @param other Ray to copy from
     */
    public Ray(Ray other) {
        _point3D = new Point3D(other._point3D);
        _vector = new Vector(other._vector);
        _inverted = new Vector(other._inverted);
    }

    /* ************* Getters/Setters *******/

    /**
     * Get start point
     *
     * @return the point
     * @see Point3D#Point3D(Coordinate, Coordinate, Coordinate)
     */
    public Point3D getPoint3D() {
        return _point3D;
    }

    /**
     * Get Vector
     *
     * @return the vector
     * @see Vector#Vector(Point3D)
     */
    public Vector getVector() {
        return _vector;
    }

    /**
     * Get inverted vector
     * @return inverted vector
     */
    public Vector getInverted() {
        return _inverted;
    }

    /* ************** Admin *****************/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ray)) return false;
        Ray ray = (Ray) o;
        return _point3D.equals(ray._point3D) &&
                _vector.equals(ray._vector);
    }

    @Override
    public String toString() {
        return "Ray{" + _point3D + "," + _vector + "}";
    }
}
