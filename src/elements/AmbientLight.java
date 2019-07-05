package elements;

import primitives.Color;

/**
 * Ambient light
 */
public class AmbientLight extends Light {
    /* ********* Constructors ***********/
    /**
     * A new ambient light
     * @param color the color
     * @param ka parameter
     */
    public AmbientLight(Color color, double ka) {
        super(color.scale(ka));
    }
}
