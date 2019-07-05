package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * Models point light source with direction
 */
public class SpotLight extends PointLight {
    private Vector _direction;

    /* ********* Constructors ***********/

    /**
     * a new spotlight
     *
     * @param color the color of the light
     * @param position the position of the light source
     * @param Kc kc
     * @param Kl kl
     * @param Kq kq
     * @param direction the direction of the light
     */
    public SpotLight(Color color, Point3D position, double Kc, double Kl, double Kq, Vector direction) {
        super(color, position, Kc, Kl, Kq);
        _direction = direction.normal();
    }

    /* ************* Getters/Setters *******/

    /**
     * get light intensity
     * @param p the point
     * @return light
     */
    @Override
    public Color getIntensity(Point3D p) {
        double pl = _direction.dotProduct(getL(p));
        if (pl <= 0)
            return Color.BLACK;
        return super.getIntensity(p).scale(pl);
    }

    /* ************* Administration *******/

    @Override
    public String toString() {
        return "SL{" +
                "D=" + _direction +
                ", C=" + super.toString() +
                '}';
    }
}
