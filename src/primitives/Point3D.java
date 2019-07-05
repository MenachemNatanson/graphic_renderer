package primitives;

/**
 * Point in 3D
 */
public class Point3D {
    /**
     * Point Zero
     */
    public static final Point3D ZERO = new Point3D(0,0,0);

    Coordinate _x;
    Coordinate _y;
    Coordinate _z;

    /* ********* Constructors ***********/
    /**
     * A new Point in 3D
     *
     * @param x X coordinate
     * @param y Y coordinate
     * @param z Z coordinate
     * @see Coordinate#Coordinate(double)
     */
    public Point3D(Coordinate x, Coordinate y, Coordinate z) {
        _x = new Coordinate(x);
        _y = new Coordinate(y);
        _z = new Coordinate(z);
    }

    /**
     * A new Point3D
     *
     * @param x x
     * @param y y
     * @param z z
     */
    public Point3D(double x, double y, double z) {
        _x = new Coordinate(x);
        _y = new Coordinate(y);
        _z = new Coordinate(z);
    }

    /**
     * Copy Point
     *
     * @param other other point
     */
    public Point3D(Point3D other) {
        _x = new Coordinate(other._x);
        _y = new Coordinate(other._y);
        _z = new Coordinate(other._z);
    }

    /* ************* Getters/Setters *******/
    /**
     * Get X coordinate
     *
     * @return the value
     * @see Coordinate
     */
    public Coordinate getX() {
        return _x;
    }

    /**
     * Get Y coordinate
     *
     * @return the value
     * @see Coordinate#Coordinate(double)
     */
    public Coordinate getY() {
        return _y;
    }

    /**
     * Get Z coordinate
     *
     * @return the value
     * @see Coordinate
     */
    public Coordinate getZ() {
        return _z;
    }

    /* ************** Admin *****************/
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Point3D)) return false;
        Point3D other = (Point3D) obj;
        return _z.equals(other._z) &&
                _y.equals(other._y) &&
                _x.equals(other._x);
    }

    @Override
    public String toString() {

        return "(" + _x + "," + _y + "," + _z + ")";
    }

    /* ************* Operations ***************/
    /**
     * Add a vector to a point in 3D
     *
     * @param vec the vector
     * @return a new point
     * @see Vector#Vector(Point3D)
     */
    public Point3D add(Vector vec) {
        return new Point3D(
                _x.add(vec.getPoint3D().getX()),
                _y.add(vec.getPoint3D().getY()),
                _z.add(vec.getPoint3D().getZ())
        );
    }

    /**
     * Subtract a vector from a point in 3D
     *
     * @param vec the vector
     * @return a new point
     * @see Vector#Vector(Point3D)
     */
    public Point3D subtract(Vector vec) {
        return new Point3D(
                _x.subtract(vec.getPoint3D().getX()),
                _y.subtract(vec.getPoint3D().getY()),
                _z.subtract(vec.getPoint3D().getZ())
        );
    }

    /**
     * Subtract two points
     *
     * @param other the second point ( start point )
     * @return a vector from the other point to the current point
     */
    public Vector subtract(Point3D other) {
        return new Vector(
                new Point3D(
                        _x.subtract(other.getX()),
                        _y.subtract(other.getY()),
                        _z.subtract(other.getZ())
                )
        );
    }

    /**
     * get the distance between the two points in the power of 2
     *
     * @param other the second point
     * @return the distance in the power of 2
     */
    public double distance2(Point3D other) {
        Coordinate x= other._x;
        Coordinate y= other._y;
        Coordinate z= other._z;

        return  (_x.subtract(x)).multiply(_x.subtract(x)).add(
                    (_y.subtract(y)).multiply(_y.subtract(y))).add(
                        (_z.subtract(z)).multiply(_z.subtract(z))).get();
    }

    /**
     * get the distance between the two points
     *
     * @param other the second point
     * @return the distance
     */
    public double distance(Point3D other) {
        return Math.sqrt(distance2(other));
    }
}
