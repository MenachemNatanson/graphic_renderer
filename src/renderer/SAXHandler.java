package renderer;
import elements.*;
import geometries.*;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import primitives.*;
import scene.Scene;

/**
 * a handler for xml extract
 */
public class SAXHandler extends DefaultHandler {
    //variables
    Scene _scene = null;
    ImageWriter _imageWriter =null;
    String version=null;
    Boolean _ignoreResolution=false;
    Boolean _optimised=false;
    int _beamRay = 10;

    //Triggered when the start of tag is found.
    @Override
    public void startElement(String uri, String localName,
                             String qName, Attributes attributes) {

        switch(qName){
            //Create new scene
            case "scene": {
                _scene = new Scene(attributes.getValue("name"));
                version = attributes.getValue("version");
                String color = attributes.getValue("background-color");
                double[] colors = parse3Numbers(color);
                _scene.setBackground(new Color(colors));
                int width = Integer.parseInt(attributes.getValue("screen-width"));
                int heigth = Integer.parseInt(attributes.getValue("screen-height"));
                _imageWriter = new ImageWriter(_scene.getName(), width, heigth, width, heigth);
            }
            break;

            //Create new light
            case "ambient-light":{
                double[] colors = parse3Numbers(attributes.getValue("color"));
                _scene.setLight(new AmbientLight(new Color(colors),Double.parseDouble(attributes.getValue("ka"))));
                break;}
            case "directional-light":{
                Color color = new Color(parse3Numbers(attributes.getValue("color")));
                double[] points = parse3Numbers(attributes.getValue("direction"));
                Vector direction = new Vector(points[0],points[1],points[2]);
                _scene.addLight(new DirectionalLight(color,direction));
                break;}
            case "point-light":{
                Color color = new Color(parse3Numbers(attributes.getValue("color")));
                double[] points = parse3Numbers(attributes.getValue("position"));
                Point3D position = new Point3D(points[0],points[1],points[2]);
                double kc = Double.parseDouble(attributes.getValue("kc"));
                double kl = Double.parseDouble(attributes.getValue("kl"));
                double kq = Double.parseDouble(attributes.getValue("kq"));
                _scene.addLight(new PointLight(color,position,kc,kl,kq));
                break;}
            case "spot-light":{
                Color color = new Color(parse3Numbers(attributes.getValue("color")));
                double[] points = parse3Numbers(attributes.getValue("position"));
                Point3D position = new Point3D(points[0],points[1],points[2]);
                double kc = Double.parseDouble(attributes.getValue("kc"));
                double kl = Double.parseDouble(attributes.getValue("kl"));
                double kq = Double.parseDouble(attributes.getValue("kq"));
                points = parse3Numbers(attributes.getValue("direction"));
                Vector direction = new Vector(points[0],points[1],points[2]);
                _scene.addLight(new SpotLight(color,position,kc,kl,kq,direction));
                break;}
            //Create new camera
            case "camera": {
                double[] points = parse3Numbers(attributes.getValue("p0"));
                Point3D p0 = new Point3D(points[0], points[1], points[2]);
                points = parse3Numbers(attributes.getValue("vTo"));
                Vector vTo = new Vector(points[0], points[1], points[2]);
                points = parse3Numbers(attributes.getValue("vUp"));
                Vector vUp = new Vector(points[0], points[1], points[2]);
                Camera cam = new Camera(p0, vUp, vTo);
                _scene.setCamera(cam, Double.parseDouble(attributes.getValue("Screen-dist")));
            }
            break;
            //Create new sphere
            case "sphere": {
                double[] points = parse3Numbers(attributes.getValue("center"));
                Point3D center = new Point3D(points[0], points[1], points[2]);
                Color color = new Color(parse3Numbers(attributes.getValue("emission")));
                Material material = getMaterail(attributes.getValue("material"));
                Sphere sp = new Sphere(Double.parseDouble(attributes.getValue("radius")), center,material,color);
                sp.setOptimised(_optimised);
                _scene.addGeometries(sp);
            }
            break;
            //Create new triangle
            case "triangle":
            {
                double[] points = parse3Numbers(attributes.getValue("p0"));
                Point3D p0 = new Point3D(points[0], points[1], points[2]);
                points = parse3Numbers(attributes.getValue("p1"));
                Point3D p1 = new Point3D(points[0], points[1], points[2]);
                points = parse3Numbers(attributes.getValue("p2"));
                Point3D p2 = new Point3D(points[0], points[1], points[2]);
                Color color = new Color(parse3Numbers(attributes.getValue("emission")));
                Material material = getMaterail(attributes.getValue("material"));
                Triangle t = new Triangle(p0,p1,p2,material,color);
                t.setOptimised(_optimised);
                _scene.addGeometries(t);
            }
            break;
            //create new cylinder
            case "cylinder2p":
            {
                double[] points = parse3Numbers(attributes.getValue("p1"));
                Point3D p1 = new Point3D(points[0], points[1], points[2]);
                points = parse3Numbers(attributes.getValue("p2"));
                Point3D p2 = new Point3D(points[0], points[1], points[2]);
                double radius = Double.parseDouble(attributes.getValue("radius"));
                Color color = new Color(parse3Numbers(attributes.getValue("emission")));
                Material material = getMaterail(attributes.getValue("material"));
                Cylinder c =new Cylinder(radius,p1,p2,material,color);
                c.setOptimised(_optimised);
                _scene.addGeometries(c);
            }
            break;
            //create new cylinder
            case "cylinder":
            {
                double[] points = parse3Numbers(attributes.getValue("Ray-p"));
                Point3D rayP = new Point3D(points[0], points[1], points[2]);
                points = parse3Numbers(attributes.getValue("Ray-v"));
                Vector rayV = new Vector(points[0], points[1], points[2]);
                double radius = Double.parseDouble(attributes.getValue("radius"));
                double heigth = Double.parseDouble(attributes.getValue("heigth"));
                Color color = new Color(parse3Numbers(attributes.getValue("emission")));
                Material material = getMaterail(attributes.getValue("material"));
                Cylinder c = new Cylinder(radius,new Ray(rayP,rayV),heigth,material,color);
                c.setOptimised(_optimised);
                _scene.addGeometries(c);
            }
            break;
            //create new tube
            case "tube":
            {
                double[] points = parse3Numbers(attributes.getValue("Ray-p"));
                Point3D rayP = new Point3D(points[0], points[1], points[2]);
                points = parse3Numbers(attributes.getValue("Ray-v"));
                Vector rayV = new Vector(points[0], points[1], points[2]);
                double radius = Double.parseDouble(attributes.getValue("radius"));
                Color color = new Color(parse3Numbers(attributes.getValue("emission")));
                Material material = getMaterail(attributes.getValue("material"));
                _scene.addGeometries(new Tube(radius,new Ray(rayP,rayV),material,color));
            }
            break;
            //create new plane
            case "plane":
            {
                double[] points = parse3Numbers(attributes.getValue("p"));
                Point3D p = new Point3D(points[0], points[1], points[2]);
                points = parse3Numbers(attributes.getValue("v"));
                Vector v = new Vector(points[0], points[1], points[2]);
                Color color = new Color(parse3Numbers(attributes.getValue("emission")));
                Material material = getMaterail(attributes.getValue("material"));
                _scene.addGeometries(new Plane(p,v,material,color));
            }
            break;
            //create new Square
            case "square":
            {
                double[] points = parse3Numbers(attributes.getValue("p0"));
                Point3D p1 = new Point3D(points[0], points[1], points[2]);
                points = parse3Numbers(attributes.getValue("p1"));
                Point3D p2 = new Point3D(points[0], points[1], points[2]);
                points = parse3Numbers(attributes.getValue("p2"));
                Point3D p3 = new Point3D(points[0], points[1], points[2]);
                points = parse3Numbers(attributes.getValue("p3"));
                Point3D p4 = new Point3D(points[0], points[1], points[2]);
                Color color = new Color(parse3Numbers(attributes.getValue("emission")));
                Material material = getMaterail(attributes.getValue("material"));
                Square sq=new Square(p1,p2,p3,p4,material,color);
                sq.setOptimised(_optimised);
                _scene.addGeometries(sq);
            }
            break;
            //create new Square
            case "cube":
            {
                double[] points = parse3Numbers(attributes.getValue("p0"));
                Point3D p1 = new Point3D(points[0], points[1], points[2]);
                points = parse3Numbers(attributes.getValue("p1"));
                Point3D p2 = new Point3D(points[0], points[1], points[2]);
                points = parse3Numbers(attributes.getValue("p2"));
                Point3D p3 = new Point3D(points[0], points[1], points[2]);
                points = parse3Numbers(attributes.getValue("p3"));
                Point3D p4 = new Point3D(points[0], points[1], points[2]);
                points = parse3Numbers(attributes.getValue("bp"));
                Point3D bp = new Point3D(points[0], points[1], points[2]);
                double height = Double.parseDouble(attributes.getValue("height"));
                Color color = new Color(parse3Numbers(attributes.getValue("emission")));
                Material material = getMaterail(attributes.getValue("material"));
                Square front = new Square(p1,p2,p3,p4);
                Cube cb=new Cube(front,bp,height,material,color);
                cb.setOptimised(_optimised);
                _scene.addGeometries(cb);
            }
            break;
            //set grid
            case "grid":
            {
                _imageWriter.setGrid(true);
            }
            break;
            //set grid
            case "optimised":
            {
                _optimised=true;
            }
            break;

            //set resolution
            case "resolution":
            {
                if(!_ignoreResolution) {
                    int nx = Integer.parseInt(attributes.getValue("screen-width"));
                    int ny = Integer.parseInt(attributes.getValue("screen-height"));
                    _imageWriter.setNx(nx);
                    _imageWriter.setNy(ny);
                }
            }
            break;

            //get rotation
            case "rotate": {
                double x = Double.parseDouble(attributes.getValue("x"));
                double y = Double.parseDouble(attributes.getValue("y"));
                double z = Double.parseDouble(attributes.getValue("z"));
                _scene.getCamera().rotateXYZ(x,y,z);
            }
            break;
            // set number of rays in beam (default 20)
            case "rays-beam":{
                _beamRay = Integer.parseInt(attributes.getValue("num"));
            }
            break;

        }
    }

    /**
     * parse 3 number from string
     * @param s string
     * @return the 3 numbers
     */
    private double[] parse3Numbers(String s){
        String[] numbers = s.split(" ");
        double[] num = new double[3];
        num[0] = Double.parseDouble(numbers[0]);
        num[1] = Double.parseDouble(numbers[1]);
        num[2] = Double.parseDouble(numbers[2]);
        return num;
    }

    private Material getMaterail(String str){
        String [] nums = str.split(" ");
        switch (nums.length){
            case 3:
                return new Material(
                        Double.parseDouble(nums[0]),
                        Double.parseDouble(nums[1]),
                        Integer.parseInt(nums[2]));
            case 5:
                return new Material(
                        Double.parseDouble(nums[0]),
                        Double.parseDouble(nums[1]),
                        Integer.parseInt(nums[2]),
                        Double.parseDouble(nums[3]),
                        Double.parseDouble(nums[4]));
            case 7:
                return new Material(
                        Double.parseDouble(nums[0]),
                        Double.parseDouble(nums[1]),
                        Integer.parseInt(nums[2]),
                        Double.parseDouble(nums[3]),
                        Double.parseDouble(nums[4]),
                        Double.parseDouble(nums[5]),
                        Double.parseDouble(nums[6]));
        }
    return null;
    }
}


