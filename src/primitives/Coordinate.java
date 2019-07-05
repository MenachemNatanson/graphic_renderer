package primitives;

import static primitives.Util.*;

/**
 * Coordinate
 */
public final class Coordinate {
    /**
     * Zero coordinate
     */
    public static Coordinate ZERO = new Coordinate(0.0);
    //private static final double EPSILON = 0.0000001;
    double _coord;

    /* ********* Constructors ***********/
    /**
     * A new coordinate
     *
     * @param coord the value
     */
    public Coordinate(double coord) {
        // if it too close to zero make it zero
        _coord = alignZero(coord);
    }

    /**
     * Copy coordinate
     *
     * @param other other coordinate
     */
    public Coordinate(Coordinate other) {
        _coord = other._coord;
    }

    /* ************* Getters/Setters *******/
    /**
     * Get coordinate
     *
     * @return the value
     */
    public double get() {
        return _coord;
    }

    /* ************** Admin *****************/
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Coordinate)) return false;
        return usubtract(_coord, ((Coordinate) obj)._coord) == 0.0;
    }

    @Override
    public String toString() {
        return "" + _coord;
    }

    /* ************* Operations ***************/
    /**
     * Subtract from the coordinate
     *
     * @param other the coordinate to sub
     * @return a new coordinate
     */
    public Coordinate subtract(Coordinate other) {
        return new Coordinate(usubtract(_coord, other._coord));
    }

    /**
     * Add from the coordinate
     *
     * @param other the coordinate to add
     * @return a new coordinate
     */
    public Coordinate add(Coordinate other) {
        return new Coordinate(uadd(_coord, other._coord));
    }

    /**
     * Scale the coordinate
     *
     * @param num value to scale
     * @return a new coordinate
     */
    public Coordinate scale(double num) {
        return new Coordinate(uscale(_coord, num));
    }

    /**
     * Multiply from the coordinate
     *
     * @param other the coordinate to multiply
     * @return a new coordinate
     */
    public Coordinate multiply(Coordinate other) {
        return new Coordinate(uscale(_coord, other._coord));
    }

}
