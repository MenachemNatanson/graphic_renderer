package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * Models omni-directional point source
 */
public class PointLight extends Light implements LightSource {
    private Point3D _position;
    private double _Kc;
    private double _Kl;
    private double _Kq;

    /* ********* Constructors ***********/

    /**
     * a new point light
     *
     * @param color the color of the light
     * @param position the position of the light source
     * @param Kc kc
     * @param Kl kl
     * @param Kq kq
     */
    public PointLight(Color color, Point3D position, double Kc, double Kl, double Kq) {
        super(color);
        _position = new Point3D(position);
        _Kc = Kc;
        _Kl = Kl;
        _Kq = Kq;
    }

    /* ************* Getters/Setters *******/
    /**
     * get light intensity
     * @param p the point
     * @return light
     */
    @Override
    public Color getIntensity(Point3D p) {
        double distance2 = p.distance2(_position);
        return getIntensity().scale(1/(_Kc +_Kl*Math.sqrt(distance2) + _Kq*distance2));
    }

    /**
     * get vector from light
     * @param p the point
     * @return vector
     */
    @Override
    public Vector getL(Point3D p) {
        return p.subtract(_position).normal();
    }

    /**
     * Get light position
     * @return point
     */
    public Point3D getPosition() {
        return _position;
    }

    /* ************* Administration *******/
    @Override
    public String toString() {
        return "PL{" +
                "P=" + _position +
                ", Kc=" + _Kc +
                ", Kl=" + _Kl +
                ", Kq=" + _Kq +
                ", C=" + super.toString() +
                '}';
    }
}
