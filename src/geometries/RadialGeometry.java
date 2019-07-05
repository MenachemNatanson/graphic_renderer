package geometries;

import primitives.*;

import static primitives.Util.*;

/**
 * Radial Geometry
 */
public abstract class RadialGeometry extends Geometry{
    double _radius;

    /* ********* Constructors ***********/

    /**
     * A new RadialGeometry
     *
     * @param radius the radius
     */
    RadialGeometry(double radius) {
        if(Util.isZero(radius) || radius < 0)
            throw new IllegalArgumentException("Zero or negative radius");
        _radius = radius;
    }

    /**
     * A new RadialGeometry
     *
     * @param radius the radius
     * @param material the material
     * @param emission the emission
     */
    RadialGeometry(double radius, Material material, Color emission) {
        super(material,emission);
        if(Util.isZero(radius) || radius < 0)
            throw new IllegalArgumentException("Zero or negative radius");
        _radius = radius;
    }


    /*************** Admin *****************/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RadialGeometry)) return false;
        RadialGeometry other = (RadialGeometry) o;
        return usubtract(other._radius, _radius) == 0.0;
    }

    @Override
    public String toString() {
        return "R=" + _radius;
    }

    /* ************* Getters/Setters *******/

    /**
     * Get radius
     *
     * @return radius
     */
    public double getRadius() {
        return _radius;
    }
}
