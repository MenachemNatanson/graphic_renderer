package geometries;

import primitives.Ray;
import java.util.*;

/**
 * A container for Geometries (Intersectables)
 */
public class Geometries extends Intersectable {

    private List<Intersectable> _geometries = new ArrayList<>();

    /* ********* Constructors ***********/

    /**
     * A new Container
     * @param geometries the geometries
     * @see Intersectable
     */
    public Geometries(Intersectable ... geometries){
        _geometries.addAll(Arrays.asList(geometries));
    }

    /* ************* Operations ***************/

    /**
     * add geometry
     * @param geometries the geometry
     */
    public void add(Intersectable ... geometries){
        _geometries.addAll(Arrays.asList(geometries));
    }

    /**
     * get geometries
     * @return geometries list
     */
    public List<Intersectable> getGeometries(){
        return _geometries;
    }

    @Override
    public List<GeoPoint> findIntersections(Ray ray) {
        List<GeoPoint> list = new ArrayList<>();
        for (Intersectable item :_geometries) {
            list.addAll(item.findIntersections(ray));
        }
        return list;
    }

    /* ************** Admin *****************/
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("( ");
        for (Intersectable item:_geometries) {
            str.append(item.toString()).append(" ,");
        }
        str.append(" )");
        return str.toString();
    }
}
