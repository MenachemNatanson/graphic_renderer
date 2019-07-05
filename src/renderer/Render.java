package renderer;

import elements.Camera;
import elements.LightSource;
import primitives.*;
import scene.Scene;

import static primitives.Util.*;
import static geometries.Intersectable.GeoPoint;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.LinkedList;
import java.util.Random;


/**
 * Render class (makes a bitmap picture from the scene)
 */
public class Render implements Runnable {
    private static final double MIN_CALC_COLOR_K = 0.001;
    private static final int RECURSIVE_L = 7;
    // variables
    private int _rayBeam = 10;
    private static Random rand = new Random();
    private Boolean _optimised;
    private ImageWriter _imageWriter;
    private int _id=-1;
    private int _minI;
    private int _maxI;
    private int _minJ;
    private int _maxJ;
    private RenderController _controller;

    /* ********* Constructors ***********/
    private Scene _scene;

    /* ************* Getters/Setters *******/

    /**
     * a new Render
     *
     * @param imageWriter image writer
     * @param scene       scene
     */
    public Render(ImageWriter imageWriter, Scene scene) {
        _imageWriter = imageWriter;
        _scene = scene;
        _optimised=false;
        _minI=0;
        _minJ=0;
        _maxI=imageWriter.getNx();
        _maxJ=imageWriter.getNy();
    }

    /**
     * a new Render
     *
     * @param imageWriter image writer
     * @param scene       scene
     */
    public Render(ImageWriter imageWriter, Scene scene,Boolean opt,int br) {
        _imageWriter = imageWriter;
        _scene = scene;
        _optimised=opt;
        _rayBeam = br;
        _minI=0;
        _minJ=0;
        _maxI=imageWriter.getNx();
        _maxJ=imageWriter.getNy();
    }


    /**
     *
     * @param i id of the thread
     * @param renderController class for controlling the render process with thread pool
     * @param imageWriter image writer
     * @param scene scene
     * @param minI start X axis of render
     * @param maxI end X axis of render
     * @param minJ start Y axis of render
     * @param maxJ end Y axis of render
     * @param br amount of rays in beam
     */
    public Render(int i, RenderController renderController, ImageWriter imageWriter, Scene scene, int minI, int maxI, int minJ, int maxJ, int br) {
        _minI=minI;
        _minJ=minJ;
        _maxI=maxI;
        _maxJ=maxJ;
        _id=i;
        _controller=renderController;
        _imageWriter=imageWriter;
        _scene=scene;
        _optimised=false;
        _rayBeam=br;
    }

    /**
     * Get image writer
     *
     * @return image writer
     */
    public ImageWriter getImageWriter() {
        return _imageWriter;
    }

    /**
     * Get if it is optimised
     * @return optimised
     */
    public Boolean getOptimised() {
        return _optimised;
    }

    /**
     * Get ray beam number
     * @return int
     */
    public int getRayBeam() {
        return _rayBeam;
    }

    /* ************* Operations ***************/

    /**
     * Get scene
     *
     * @return scene
     */
    public Scene getScene() {
        return _scene;
    }

    /**
     * render the scene into the imageWriter
     */
    public void renderImage() {
        //variables
        int nx = _imageWriter.getNx();
        int ny = _imageWriter.getNy();
        double width = _imageWriter.getWidth();
        double height = _imageWriter.getHeight();
        Camera camera = _scene.getCamera();
        double distance = _scene.getCameraDistance();
        java.awt.Color background = _scene.getBackground().getColor();
        double steps = 0;
        int whole = nx * ny;
        int previous = 0;
        LocalDateTime start = LocalDateTime.now();
        if(_optimised)
            _scene.buildBoxes();

        //render image
        for (int i = _minI; i < _maxI; i++) {
            for (int j = _minJ; j < _maxJ; j++) {
                int percentage = (int) ((++steps / whole) * 100000);
                if (percentage != previous) {
                    if(_controller==null)
                        System.out.print("\r"+percentage / 1000.0 + "%");
                    else
                        _controller.progress(percentage/1000.0,_id);
                    previous = percentage;
                }
                Ray ray = camera.constructRayThroughPixel(nx, ny, i, j, distance, width, height);
                GeoPoint intersection = getClosestPoint(ray);
                _imageWriter.writePixel(i, j,
                        intersection == null ? background : // no intersection = background color
                                calcColor(intersection, ray).getColor()); // intersection = calculate the right color
            }
        }
        if(_controller!=null)
            _controller.finish();
        else {
            LocalDateTime end = LocalDateTime.now();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            System.out.println("\rStart time: "+dtf.format(start));
            System.out.println("End time: "+dtf.format(end));
            //String reset = "\u001B[0m";
            //String red = "\u001B[31m";
            String reset = "";
            String red = "";
            System.out.println("Duration: "+red+Duration.between(start,end).toMillis()/1000.0+reset+" Seconds");
        }
    }

    private Color calcBeamColor(double rad, Ray ray, Vector n, int level, double kk) {
        if (rad != 0) {
            Color sum = Color.BLACK;
            List<Ray> beam = getBeam(ray, n, rad);
            for (Ray beamRay : beam) {
                GeoPoint gp = getClosestPoint(beamRay);
                if (gp != null) {
                    sum = sum.add(calcColor(gp, beamRay, level - 1, kk));
                }else{
                    sum = sum.add(_scene.getBackground());
                }
            }
            return sum.scale(1.0 / beam.size());
        } else {
            GeoPoint gp = getClosestPoint(ray);
            return gp == null ? _scene.getBackground() : calcColor(gp, ray, level - 1, kk);
        }
    }

    /**
     * calculate the color of a point in the scene
     *
     * @param geopoint the point which we calculate the color from
     * @param inRay    the ray to the given point
     * @return the color of the requested point
     */
    private Color calcColor(GeoPoint geopoint, Ray inRay) {
        return calcColor(geopoint, inRay, RECURSIVE_L, 1.0).add(_scene.getAmbient().getIntensity());
    }

    /**
     * calculate the color of a point in the scene
     *
     * @param geopoint the point which we calculate the color from
     * @param inRay    the ray to the given point
     * @param level    the number of recursion levels
     * @param k        double num that if it is smaller than MIN_CALC_COLOR_K we will return BLACK
     * @return the color of the requested point
     */
    private Color calcColor(GeoPoint geopoint, Ray inRay, int level, double k) {
        if (level == 0 || k < MIN_CALC_COLOR_K) return Color.BLACK;

        //add emission
        Color color = geopoint.geometry.getEmission();

        //get variables
        Vector v = geopoint.point.subtract(_scene.getCamera().getP0()).normal();
        Vector n = geopoint.geometry.getNormal(geopoint.point);
        int nShininess = geopoint.geometry.getMaterial().getNShininess();
        double kd = geopoint.geometry.getMaterial().getKD();
        double ks = geopoint.geometry.getMaterial().getKS();
        //go over lights
        for (LightSource lightSource : _scene.getLights()) {
            Vector l = lightSource.getL(geopoint.point);
            if (n.dotProduct(l) * n.dotProduct(v) > 0) {
                double ktr = transparency(l, n, geopoint);
                if (ktr * k > MIN_CALC_COLOR_K) {
                    //add light
                    Color lightIntensity = lightSource.getIntensity(geopoint.point).scale(ktr);
                    color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                            calcSpecular(ks, l, n, v, nShininess, lightIntensity));
                }
            }
        }

        // Recursive call for a reflected ray
        double kr = geopoint.geometry.getMaterial().getKR();
        double rr = geopoint.geometry.getMaterial().getRR();
        Vector direction;
        Vector newV = inRay.getVector();
        double kkr = k * kr;
        if (kkr > MIN_CALC_COLOR_K) {
            try {
                // reflectedRay vector = v-2∙(v∙n)∙n
                direction = newV.subtract(n.scale(2 * newV.dotProduct(n)));
                Ray reflectedRay = new Ray(geopoint.point, direction, n);
                color = color.add(calcBeamColor(rr, reflectedRay, n, level, kkr).scale(kr));
            } catch (Exception ignored) {
            }
        }

        // Recursive call for a refracted ray
        double kt = geopoint.geometry.getMaterial().getKT();
        double rt = geopoint.geometry.getMaterial().getRT();
        double kkt = k * kt;
        if (kkt > MIN_CALC_COLOR_K) {
            direction = newV;
            Ray refractedRay = new Ray(geopoint.point, direction, n);
            color = color.add(calcBeamColor(rt, refractedRay, n, level, kkt).scale(kt));
        }

        return color;
    }

    /**
     * calculate the Diffusive factor
     *
     * @param kd             kd
     * @param l              vector from light
     * @param n              normal
     * @param lightIntensity light intensity
     * @return diffusive light
     */
    private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
        // kd∙|l∙n|∙lightIntensity
        return lightIntensity.scale(kd * Math.abs(l.dotProduct(n)));
    }

    /**
     * calculate the Specular factor
     *
     * @param ks             ks
     * @param l              vector from light
     * @param n              normal
     * @param v              vector v
     * @param nShininess     material shininess
     * @param lightIntensity light intensity
     * @return specular light
     */
    private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        Vector r;
        //get vector r
        try {
            r = l.subtract(n.scale(2 * l.dotProduct(n))).normal(); // r = l - 2∙(l∙n)∙𝒏
        } catch (Exception ex) {
            r = l;
        }
        double minusvr = -v.dotProduct(r);
        if (minusvr <= 0)
            return Color.BLACK;
        // ks∙(max(0,-v∙r))^nShininess∙lightIntensity
        return lightIntensity.scale(ks * Math.pow(minusvr, nShininess));
    }

    /**
     * find the point with the minimal distance from the given ray
     *
     * @param ray the ray which make the intersection
     * @return the closet point to the ray, or null if there are no intersection points
     */
    private GeoPoint getClosestPoint(Ray ray) {
        List<GeoPoint> list = _scene.getGeometries().findIntersections(ray);
        Point3D p = ray.getPoint3D();

        if (list.isEmpty())
            return null;
        GeoPoint pToReturn = list.get(0);
        if (list.size() == 1)
            return pToReturn;

        double distance2 = p.distance2(pToReturn.point);

        for (GeoPoint point : list) {
            double temp = p.distance2(point.point);
            if (temp < distance2) {
                pToReturn = point;
                distance2 = temp;
            }
        }

        return pToReturn;
    }

    /**
     * add a grid on top of the image writer
     *
     * @param interval the distance between the lines of the grid
     */
    public void printGrid(int interval) {
        int nx = _imageWriter.getNx();
        int ny = _imageWriter.getNy();
        for (int i = 0; i < nx; i++) {
            for (int j = 0; j < ny; j++) {
                if (((i + 1) % interval == 0 || (j + 1) % interval == 0) && (i != nx - 1 && j != ny - 1))
                    _imageWriter.writePixel(i, j, java.awt.Color.WHITE);
            }
        }

    }

    /**
     * Check if is transparent
     *
     * @param l        vector from light
     * @param n        normal
     * @param geoPoint point
     * @return double
     */
    private double transparency(Vector l, Vector n, GeoPoint geoPoint) {
        Vector lightDirection = l.scale(-1); //from point to light source

        Ray lightRay = new Ray(geoPoint.point, lightDirection, n);
        List<GeoPoint> intersectionPoints = _scene.getGeometries().findIntersections(lightRay, l.getPoint3D().distance2(geoPoint.point));

        double ktr = 1;
        for (GeoPoint gp : intersectionPoints)
            ktr *= gp.geometry.getMaterial().getKT();
        return ktr;
    }

    /**
     * Get Beam around ray
     *
     * @param ray    the ray
     * @param normal normal of the object
     * @param radius radius of the top of the beam
     * @return list of rays (beam)
     */
    public List<Ray> getBeam(Ray ray, Vector normal, double radius) {
        Vector dir = ray.getVector();
        Point3D p0 = ray.getPoint3D();

        //check that the ray and the normal are at the same side
        if (dir.dotProduct(normal) < 0)
            normal = normal.scale(-1);


        Vector xV = dir.buildOrthogonalVector();
        Vector yV = dir.crossProduct(xV);

        Point3D pc = p0.add(dir); // point 1 size far from the beginning of the ray
        List<Ray> beam = new LinkedList<>(Collections.singletonList(ray));
        do {
            double xFactor = rand.nextDouble() * 2 - 1;
            double yFactor = Math.sqrt(1 - xFactor * xFactor);
            Point3D p = pc;
            if (!isZero(xFactor))
                p = p.add(xV.scale(xFactor));
            if (!isZero(yFactor))
                p = p.add(yV.scale(yFactor));
            p = pc.add(p.subtract(pc).scale((rand.nextDouble() * 2 - 1) * radius));
            Vector v = p.subtract(p0);
            //if it isn't under the geometry then add
            if (v.dotProduct(normal) > 0)
                beam.add(new Ray(p0, v));
        } while (beam.size() < _rayBeam);
        return beam;
    }

    @Override
    public void run() {
        renderImage();
    }
}

