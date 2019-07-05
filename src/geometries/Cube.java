package geometries;

import primitives.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * A Cube
 */
public class Cube extends Geometry {
    private Geometries _geometries;
    private Point3D _middle;

    /* ********* Constructors ***********/
    /**
     * A new Cube
     * @param front front square
     * @param back back square
     * @param material material
     * @param emission emission
     */
    public Cube(Square front,Square back,Material material, Color emission) {
        super(material, emission);
        _geometries = new Geometries(front,back);
        _geometries.add(new Square(front.getPoint1(),front.getPoint2(),back.getPoint2(),back.getPoint1()));
        _geometries.add(new Square(front.getPoint2(),front.getPoint3(),back.getPoint3(),back.getPoint2()));
        _geometries.add(new Square(front.getPoint3(),front.getPoint4(),back.getPoint4(),back.getPoint3()));
        _geometries.add(new Square(front.getPoint4(),front.getPoint1(),back.getPoint1(),back.getPoint4()));
        Vector cross = front.getPoint1().subtract(front.getPoint3());
        Point3D middleF = front.getPoint3().add(cross.scale(0.5));
        cross = back.getPoint1().subtract(back.getPoint3());
        Point3D middleB = back.getPoint3().add(cross.scale(0.5));
        cross = middleB.subtract(middleF);
        _middle = middleF.add(cross.scale(0.5));
        setBorders();
    }

    /**
     * A new Cube
     * @param front front square
     * @param bp any point at the back
     * @param height height of the box (distance between front and back
     * @param material material
     * @param emission emission
     */
    public Cube(Square front,Point3D bp,double height,Material material, Color emission) {
        super(material, emission);
        Vector normal = front.getNormal(null);
        Vector dir = bp.subtract(front.getPoint3());
        if(dir.dotProduct(normal)>0){
            normal = normal.scale(-1);
        }
        Vector h = normal.scale(height);
        Square back = new Square(front.getPoint1().add(h),front.getPoint2().add(h),front.getPoint3().add(h),front.getPoint4().add(h));
        _geometries = new Geometries(front,back);
        _geometries.add(new Square(front.getPoint1(),front.getPoint2(),back.getPoint2(),back.getPoint1()));
        _geometries.add(new Square(front.getPoint2(),front.getPoint3(),back.getPoint3(),back.getPoint2()));
        _geometries.add(new Square(front.getPoint3(),front.getPoint4(),back.getPoint4(),back.getPoint3()));
        _geometries.add(new Square(front.getPoint4(),front.getPoint1(),back.getPoint1(),back.getPoint4()));
        Vector cross = front.getPoint1().subtract(front.getPoint3());
        Point3D middleF = front.getPoint3().add(cross.scale(0.5));
        cross = back.getPoint1().subtract(back.getPoint3());
        Point3D middleB = back.getPoint3().add(cross.scale(0.5));
        cross = middleB.subtract(middleF);
        _middle = middleF.add(cross.scale(0.5));
        setBorders();
    }

    /**
     * set min max and middle
     */
    private void setBorders(){
        List<Geometry> geometries = new ArrayList<>();
        for(Intersectable intrs:_geometries.getGeometries())
            geometries.add((Geometry) intrs);
        Coordinate maxX = geometries.stream().max(Comparator.comparing(x-> x.getMax().getX().get())).get().getMax().getX();
        Coordinate maxY = geometries.stream().max(Comparator.comparing(x-> x.getMax().getY().get())).get().getMax().getY();
        Coordinate maxZ = geometries.stream().max(Comparator.comparing(x-> x.getMax().getZ().get())).get().getMax().getZ();
        setMax(new Point3D(maxX,maxY,maxZ));
        Coordinate minX = geometries.stream().min(Comparator.comparing(x-> x.getMax().getX().get())).get().getMax().getX();
        Coordinate minY = geometries.stream().min(Comparator.comparing(x-> x.getMax().getY().get())).get().getMax().getY();
        Coordinate minZ = geometries.stream().min(Comparator.comparing(x-> x.getMax().getZ().get())).get().getMax().getZ();
        setMin(new Point3D(minX,minY,minZ));
        setMiddle(new Point3D(
                maxX.add(minX).scale(0.5),
                maxY.add(minY).scale(0.5),
                maxZ.add(minZ).scale(0.5)
        ));
    }

    /* ************* Operations ***************/
    /**
     * Get normal
     * @param p the point
     * @return vector
     */
    @Override
    public Vector getNormal(Point3D p) {
        Geometry geometry = _geometries.findIntersections(new Ray(_middle,p.subtract(_middle))).get(0).geometry;
        return geometry.getNormal(p);
    }

    /**
     * find ray intersections
     * @param ray The ray
     * @return list of geo points
     */
    @Override
    public List<GeoPoint> findIntersections(Ray ray) {
        if(!getOptimised()||intersects(ray)) {
            List<GeoPoint> intersections = _geometries.findIntersections(ray);
            if (intersections.isEmpty())
                return intersections;
            List<GeoPoint> returnInts = new ArrayList<>();
            for (GeoPoint geoPoint : intersections) {
                returnInts.add(new GeoPoint(this, geoPoint.point));
            }
            return returnInts;
        }else{
            return EMPTY_LIST;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cube cube = (Cube) o;
        return _geometries.equals(cube._geometries) &&
                _middle.equals(cube._middle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_geometries, _middle);
    }
}
