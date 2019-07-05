package primitives;

/**
 * Vector
 */
public class Vector {
    private Point3D _point3D;

    public static final Vector X = new Vector(1,0,0);
    public static final Vector Y = new Vector(0,1,0);
    public static final Vector Z = new Vector(0,0,1);
    public static final Vector MINUS_X = new Vector(-1,0,0);
    public static final Vector MINUS_Y = new Vector(0,-1,0);
    public static final Vector MINUS_Z = new Vector(0,0,-1);

    /* ********* Constructors ***********/
    /**
     * A new vector
     *
     * @param p the end of the vector
     * @see Point3D#Point3D(Coordinate, Coordinate, Coordinate)
     */
    public Vector(Point3D p) {
        if (Point3D.ZERO.equals(p)) {
            throw new IllegalArgumentException("Vector Zero");
        }
        _point3D = new Point3D(p);
    }

    /**
     * A new Vector
     *
     * @param x x
     * @param y y
     * @param z z
     */
    public Vector(double x, double y, double z) {
        Point3D p = new Point3D(new Coordinate(x), new Coordinate(y), new Coordinate(z));
        if (Point3D.ZERO.equals(p)) {
            throw new IllegalArgumentException("Vector Zero");
        }
        _point3D = new Point3D(p);
    }

    /**
     * Copy vector
     *
     * @param other other coordinate
     */
    public Vector(Vector other) {
        _point3D = new Point3D(other._point3D);
    }

    /* ************* Getters/Setters *******/
    /**
     * Get point3D
     *
     * @return the value
     * @see Point3D#Point3D(Coordinate, Coordinate, Coordinate)
     */
    public Point3D getPoint3D() {
        return _point3D;
    }

    /* ************** Admin *****************/
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Vector)) return false;
        Vector other = (Vector) obj;
        return _point3D.equals(other._point3D);
    }

    @Override
    public String toString() {
        return "V=" + _point3D;
    }

    /* ************* Operations ***************/
    /**
     * Add vector to the vector
     *
     * @param other the vector to add
     * @return a new vector
     */
    public Vector add(Vector other) {
        return new Vector(_point3D.add(other));
    }

    /**
     * Subtract vector from the vector
     *
     * @param other the vector to sub
     * @return a new vector
     */
    public Vector subtract(Vector other) {
        return new Vector(_point3D.subtract(other));
    }

    /**
     * Scale the vector
     *
     * @param num value to scale
     * @return a new vector
     */
    public Vector scale(double num) {
        return new Vector(new Point3D(
                _point3D.getX().scale(num),
                _point3D.getY().scale(num),
                _point3D.getZ().scale(num))
        );
    }

    /**
     * dot-product with the vector
     *
     * @param other the vector to dot-product with
     * @return the value
     */
    public double dotProduct(Vector other) {
        return (_point3D.getX().multiply(other.getPoint3D().getX())).get() +
                (_point3D.getY().multiply(other.getPoint3D().getY())).get() +
                (_point3D.getZ().multiply(other.getPoint3D().getZ())).get();
    }

    /**
     * cross-product with the vector
     *
     * @param other the vector to cross-product with
     * @return a new vector
     */
    public Vector crossProduct(Vector other) {
        Coordinate thisX = _point3D.getX(), thisY = _point3D.getY(), thisZ = _point3D.getZ(),
                otherX = other.getPoint3D().getX(), otherY = other.getPoint3D().getY(), otherZ = other.getPoint3D().getZ();

        return new Vector(new Point3D(
                (thisY.multiply(otherZ)).subtract(thisZ.multiply(otherY)),
                (thisZ.multiply(otherX)).subtract(thisX.multiply(otherZ)),
                (thisX.multiply(otherY)).subtract(thisY.multiply(otherX))));
    }

    /**
     * the length of the vector
     *
     * @return the value
     */
    public double length() {
        return Math.sqrt(length2());
    }

    /**
     * the length in the pow 2
     *
     * @return the value
     */
    public double length2() {
        Coordinate x = _point3D.getX(), y = _point3D.getY(), z = _point3D.getZ();

        return (x.multiply(x)).get()+(y.multiply(y)).get()+(z.multiply(z)).get();
    }

    /**
     * get the normalization of the vector
     *
     * @return a new vector
     */
    public Vector normal() {
        double len = length();
        return new Vector(
                _point3D._x._coord / len,
                _point3D._y._coord / len,
                _point3D._z._coord / len);
    }

    /**
     * normalize the vector
     *
     * @return the vector itself
     */
    public Vector normalize() {
        double len = length();
        _point3D = new Point3D(_point3D._x._coord / len,
                _point3D._y._coord / len,
                _point3D._z._coord / len);
        return this;
    }

    /**
     * multiply vector with matrix
     * @param matrix the matrix
     * @return vector
     */
    public Vector multiply(Matrix matrix){
        Coordinate[][] numbers = matrix.getMatrix();
        Coordinate x= _point3D.getX();
        Coordinate y = _point3D.getY();
        Coordinate z= _point3D.getZ();
        return new Vector(new Point3D(
                numbers[0][0].multiply(x).add(numbers[0][1].multiply(y)).add(numbers[0][2].multiply(z)),
                numbers[1][0].multiply(x).add(numbers[1][1].multiply(y)).add(numbers[1][2].multiply(z)),
                numbers[2][0].multiply(x).add(numbers[2][1].multiply(y)).add(numbers[2][2].multiply(z))
        ));
    }

    /**
     * Builds an orthogonal vector of size 1
     * @return new orthogonal vector
     */
    public Vector buildOrthogonalVector() {
        double x = _point3D.getX().get();
        double y = _point3D.getY().get();
        double z = _point3D.getZ().get();
        if (x <= y && x <= z) { // x is the smallest
            return new Vector(0, z, -y).normalize();
        } else if (y <= z) {
            return new Vector(z, 0, -x).normalize();
        } else {
            return new Vector(y, -x, 0).normalize();
        }
    }
}
