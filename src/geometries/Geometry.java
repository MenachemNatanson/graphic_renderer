package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;

/**
 * A Geometry Shape (interface)
 */
public abstract class Geometry extends Intersectable{
    private Color _emission;
    private Material _material;

    /* ************* Getters/Setters *******/

    /**
     * Get the normal from the point in the shape
     * @param p the point
     * @return the normal
     */
    abstract public Vector getNormal(Point3D p);

    /**
     * Get object emission
     * @return emission color
     */
    public Color getEmission(){
        return _emission;
    }

    /**
     * Get object material
     * @return material
     */
    public Material getMaterial(){
        return _material;
    }


    /********** Constructors ***********/
    // for the classes who derived from this abstract class

    public Geometry(){
        _emission = new Color(0,0,0);
        _material = new Material();
    }

    public Geometry(Material material, Color emission){
        _material = new Material(material);
        _emission = new Color(emission);
    }

}
