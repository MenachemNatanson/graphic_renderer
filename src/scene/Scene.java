package scene;

import elements.AmbientLight;
import elements.Camera;
import elements.LightSource;
import geometries.*;
import primitives.Color;
import primitives.Point3D;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;

/**
 * A Scene with geometries ,camera and light
 */
public class Scene {
    private static final int START_DISTANCE = 10;
    private static final int TREE_DEPTH = 10;
    private static final double EMPTY_VOLUME = 0.75;
    //variables
    private String _name;
    private Color _background;
    private AmbientLight _light;
    private Geometries _geometries;
    private Camera _camera;
    private double _cameraDistance;
    private List<LightSource> _lights;

    /* ********* Constructors ***********/
    /**
     * A new Scene
     * @param name Scene name
     */
    public Scene(String name) {
        _name = name;
        _geometries = new Geometries();
        _lights = new ArrayList<>();
    }

    /* ************* Getters/Setters *******/
    /**
     * get scene name
     * @return name
     */
    public String getName() {
        return _name;
    }

    /**
     * get scene background
     * @return background
     */
    public Color getBackground() {
        return _background;
    }

    /**
     * get scene light
     * @return light
     */
    public AmbientLight getAmbient() {
        return _light;
    }


    /**
     * get scene geometries
     * @return geometries
     */
    public Geometries getGeometries() {
        return _geometries;
    }

    /**
     * get scene camera
     * @return camera
     */
    public Camera getCamera() {
        return _camera;
    }

    /**
     * get camera distance
     * @return camera distance
     */
    public double getCameraDistance() {
        return _cameraDistance;
    }

    /**
     * get light sources
     * @return list of light sources
     */
    public List<LightSource> getLights() {
        return _lights;
    }

    /**
     * set scene background
     * @param background the background color
     */
    public void setBackground(Color background) {
        _background = background;
    }

    /**
     * set scene light
     * @param light the light
     */
    public void setLight(AmbientLight light) {
        _light = light;
    }

    /**
     * set scene camera and distance
     * @param camera the camera
     * @param cameraDistance camera distance
     */
    public void setCamera(Camera camera, double cameraDistance) {
        _camera = camera;
        _cameraDistance = cameraDistance;
    }

    /**
     * set light sources
     * @param light list of light sources
     */
    public void addLight(LightSource light) {
        _lights.add(light);
    }

    /**
     * add one or more geometries
     * @param geometries geometries to add
     */
    public void addGeometries(Intersectable... geometries) {
        _geometries.add(geometries);
    }

    /* ************** Admin *****************/
    @Override
    public String toString() {
        return "Scene{" +
                "Name='" + _name + '\'' +
                ", Background=" + _background +
                ", light=" + _light +
                ", geometries=" + _geometries +
                ", camera=" + _camera +
                ", Distance=" + _cameraDistance +
                '}';
    }

    /**
     *  build hierarchy tree of boxes in the scene
     */
    public void buildBoxes(){
        List<Intersectable> geoList = _geometries.getGeometries();
        List<Intersectable> infinite = new ArrayList<>();

        // add all infinite geometries into list
        for(int i=0;i<geoList.size();i++){
            Intersectable x =geoList.get(i);
            if(((x instanceof Plane) && !(x instanceof Triangle) && !(x instanceof Square))||
                    (x instanceof Tube && !(x instanceof Cylinder))){
                infinite.add(x);
            }
        }
        // remove all of the infinite geometries
        geoList.removeIf(x->((x instanceof Plane)&& !(x instanceof Triangle) && !(x instanceof Square))||
                (x instanceof Tube && !(x instanceof Cylinder)));


        if(!geoList.isEmpty()) {
            // make the outside big box, and find what the distance between geometries should probably be
            Box box = new Box(geoList);
            Point3D maxP = box.getMax();
            Point3D minP = box.getMin();
            Point3D difference = maxP.subtract(minP).getPoint3D();
            double distance = (abs(difference.getX().get()) + abs(difference.getY().get()) + abs(difference.getZ().get()));
            // split the main box insides to hierarchy of boxes
            box = splitBoxes(box, distance / START_DISTANCE, distance);

            // clear the list of geometries and add the root box of the hierarchy
            geoList.clear();
            _geometries.add(box);
        }
        // add the infinite geometries
        for(Intersectable intersectable:infinite)
            _geometries.add(intersectable);
    }

    /**
     * recursive function to combine the geometries inside the box into more boxes if expedient
     * @param box the outside box who contains geometries and other boxes
     * @param distance the maximum distance for combining geometries into a box
     * @param maxDistance the distance where it possible for no more combination
     * @return the root box of the hierarchy
     */
    private Box splitBoxes(Box box,double distance,double maxDistance){
        List<Intersectable> boxList = box.getGeometries();

        if(boxList.size()==1)
            return box;

        int boxSize = boxList.size();
        List<Intersectable> newBoxList = new ArrayList<>();
        List<Intersectable> temp = new ArrayList<>();
        Intersectable first;
        List<Intersectable> tempBox;
        Box original;
        Box added;
        do {
            first = boxList.get(0);
            temp.add(first);
            boxList.remove(first);
            // check if the first geometry has other geometries in a good distance between them
            for (int i=0;i<boxList.size();i++) {
                if(first.getMiddle().distance(boxList.get(i).getMiddle())<distance){
                    tempBox = new ArrayList<>(temp);
                    tempBox.add(boxList.get(i));
                    original = new Box(temp);
                    added = new Box(tempBox);
                    // check if when we add the new geometry to the box, we don't have too much empty volume
                    if((original.getVolume()+boxList.get(i).getVolume())/added.getVolume()>EMPTY_VOLUME)
                        temp.add(boxList.get(i));
                }
            }
            if(temp.size()>1) {
                boxList.removeAll(temp);
                newBoxList.add(new Box(temp));
            }
            else
                newBoxList.add(first);
            temp.clear();
        }while (!boxList.isEmpty());
        Box returnBox = new Box(newBoxList);

        // check if the the amount of the geometries in the box hasn't changed (no combination happened)
        // and we are over the distance where geometries should have been combined
        if(returnBox.getGeometries().size()==boxSize && distance>maxDistance)
            return returnBox;
        return splitBoxes(returnBox,distance*TREE_DEPTH,maxDistance);
    }
}
