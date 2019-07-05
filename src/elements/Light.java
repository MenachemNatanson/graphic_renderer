package elements;

import primitives.Color;

/**
 * Abstract Light
 */
public abstract class Light {

    private Color _color;

    /**
     * A new Light
     */
    public Light(){
        _color = new Color();
    }

    /**
     * A new Light
     * @param color the color
     */
    public Light(Color color){
        _color = new Color(color);
    }

    /**
     * get Color Intensity
     * @return color Intensity
     */
    public Color getIntensity() {
        return _color;
    }

    /* ************* Administration *******/
    @Override
    public String toString() {
        return "color " + _color;
    }
}
