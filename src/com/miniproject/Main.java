package com.miniproject;

import elements.AmbientLight;
import elements.Camera;
import elements.DirectionalLight;
import geometries.*;
import org.junit.Test;
import org.xml.sax.SAXException;
import primitives.*;
import renderer.ImageWriter;
import renderer.Render;
import renderer.RenderController;
import renderer.loadScene;
import scene.Scene;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Home Work 3.
 * Written by:
 * Elisha Mayer ,319185997 ,elisja.mayer@gmail.com .
 * Menachem Natanson , 207134859, menachem.natanson@gmail.com
 */
public class Main {
    private static boolean optimised;
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
            if(args!=null&& args.length>0&& "xml".equals(args[0])) {
                renderTests();
                return;
            }
            if(args!=null&& args.length>0&& "test1".equals(args[0])) {
                optimised=true;
                proTests();
                return;
            }
            if(args!=null&& args.length>0&& "test2".equals(args[0])) {
                optimised=true;
                proTestsNew();
                return;
            }
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter 1 to run from xml, enter 2 to run custom tests.\n>");
            String decision = scanner.nextLine();
            if (decision.equals("1"))
                renderTests();
            else {
                System.out.print("Do you want to optimise the test? (y/n)\n>");
                decision = scanner.nextLine();
                optimised = decision.toLowerCase().equals("y");
                System.out.print("Choose a test to run:\n>");
                decision = scanner.nextLine();
                if ("1".equals(decision)) {
                    System.out.println("Running Balls Test");
                    proTests();
                } else if("2".equals(decision)) {
                    System.out.println("Running New Balls Test");
                    proTestsNew();
                }else{
                    System.out.println("Test don't exist!");
                }
            }

    }

    /**
     * Render all tests in folder Tests
     */
    private static void renderTests() throws IOException, SAXException, ParserConfigurationException {
        File folder = new File("xml\\Tests");
        File[] listOfFiles = folder.listFiles();
        assert listOfFiles != null;
        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile()) {
                Render render = loadScene.loadFromXML("xml\\Tests\\" + listOfFile.getName(), false);
                System.out.println("rendering picture: " + render.getImageWriter().getImageName());
                if (render.getOptimised()) {
                    RenderController renderController = new RenderController(render.getImageWriter(), render.getScene(), render.getImageWriter().getGrid(), render.getRayBeam());
                    renderController.renderImage();
                } else {
                    render.renderImage();
                    if (render.getImageWriter().getGrid())
                        render.printGrid(50);
                    render.getImageWriter().writeToimage();
                }
            }
        }
    }


    /**
     * render special tests
     */
    private static void proTestsNew(){
        Scene scene = new Scene("");
        scene.setCamera(new Camera(new Point3D(-400,-1800,2000),new Vector(0,-1,0),new Vector(0,0,-1)),500);
        scene.getCamera().rotateXYZ(0,0,30);
        scene.setBackground(new Color(10,20,100).scale(0.8));
        scene.setLight(new AmbientLight(new Color(20,20,20),1));
        scene.addLight(new DirectionalLight(new Color(200,200,180),new Vector(0,1,-1)));
        //scene.addLight(new SpotLight(new Color(655,655,655),new Point3D(200,-400,0),0.05,0.00005,0.000008,new Vector(5,1,-1)));
        Cube cube=		new Cube(
                new Square(
                        new Point3D(600,200,-400),
                        new Point3D(-1400,200,-400),
                        new Point3D(-1400,200,-2320),
                        new Point3D(600,200,-2320)),
                new Point3D(0,-2000,1000),
                50,
                new Material(0.5,0.5,100,0.7,0)
                ,new Color(0,0,0)) ;
        cube.setOptimised(optimised);
        scene.addGeometries(cube);
        Cylinder c1 = (new Cylinder(25,new Point3D(600,225,-400), new Point3D(-1400,225,-400),new Material(0.5,0.5,100,0,0),new Color(0,0,0)));
        Cylinder c2 = (new Cylinder(25,new Point3D(600,225,-400), new Point3D(600,225,-2320),new Material(0.5,0.5,100,0,0),new Color(0,0,0)));
        Cylinder c3 = (new Cylinder(25,new Point3D(-1400,225,-2320), new Point3D(-1400,225,-400),new Material(0.5,0.5,100,0,0),new Color(0,0,0)));

        Sphere sp1 = (new Sphere(25,new Point3D(600,225,-400),new Material(0.5,0.5,100,0,0),new Color(0,0,0)));
        Sphere sp2 = (new Sphere(25,new Point3D(-1400,225,-400),new Material(0.5,0.5,100,0,0),new Color(0,0,0)));
        Square sq1 = (new Square(new Point3D(670,200,200),new Point3D(670,200,-2320),new Point3D(670,-1000,-2320),new Point3D(670,-1000,200),new Material(0.1,0.1,100,0.67,0,0.005,0),new Color(20,20,20)));
        Square sq2 = (new Square(new Point3D(-1470,200,200),new Point3D(-1470,200,-2320),new Point3D(-1470,-1000,-2320),new Point3D(-1470,-1000,200),new Material(0.1,0.1,100,0.67,0,0.005,0),new Color(20,20,20)));

        c1.setOptimised(optimised);
        c2.setOptimised(optimised);
        c3.setOptimised(optimised);
        sp1.setOptimised(optimised);
        sp2.setOptimised(optimised);
        sq1.setOptimised(optimised);
        sq2.setOptimised(optimised);
        scene.addGeometries(c1,c2,c3,sp1,sp2,sq1,sq2);


        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                if(!((j>=4&&j<=5)&&(i>=4&&i<=5)) ){
                    Sphere sp = new Sphere(100, new Point3D(500 - i * 200, 100, -(500 + j * 200)), new Material(0.5, 0.5, 1000, 0.2, 0), new Color(Math.random() * 100, Math.random() * 100, Math.random() * 100));
                    sp.setOptimised(optimised);
                    scene.addGeometries(sp);
                }
            }
        }
        Sphere sp = new Sphere(400,new Point3D(500-900,-300,-(500+900)),new Material(0.1,0.5,2000,0.68,0),new Color(	10,10,10));
        sp.setOptimised(optimised);
        scene.addGeometries(sp);
        ImageWriter imw = new ImageWriter("images\\IMG_0051_NewBalls",500,300,6000,3600);
        imw.setGrid(false);
        if(optimised) {
            RenderController rn = new RenderController(imw, scene,10);
            rn.renderImage();
        }else{
            Render render = new Render(imw,scene);
            render.renderImage();
            imw.writeToimage();
        }
    }

    /**
     * 100 Balls on plane
     */
    @Test
    private static void proTests(){
        Scene scene = new Scene("");
        scene.setCamera(new Camera(new Point3D(-400,-1800,2000),new Vector(0,-1,0),new Vector(0,0,-1)),500);
        scene.getCamera().rotateXYZ(0,0,30);
        scene.setBackground(Color.BLACK);
        scene.setLight(new AmbientLight(new Color(20,20,20),1));
        scene.addLight(new DirectionalLight(new Color(200,200,180),new Vector(0,1,-1)));
        //scene.addLight(new SpotLight(new Color(655,655,655),new Point3D(200,-400,0),0.05,0.00005,0.000008,new Vector(5,1,-1)));
        scene.addGeometries(
                new Cube(
                        new Square(
                                new Point3D(600,200,-400),
                                new Point3D(-1400,200,-400),
                                new Point3D(-1400,200,-2320),
                                new Point3D(600,200,-2320)),
                        new Point3D(0,-2000,1000),
                        50,
                        new Material(0.5,0.5,100,0.7,0)
                        ,new Color(0,0,0)) );
        scene.addGeometries(new Cylinder(25,new Point3D(600,225,-400), new Point3D(-1400,225,-400),new Material(0.5,0.5,100,0,0),new Color(0,0,0)));
        scene.addGeometries(new Cylinder(25,new Point3D(600,225,-400), new Point3D(600,225,-2320),new Material(0.5,0.5,100,0,0),new Color(0,0,0)));
        scene.addGeometries(new Cylinder(25,new Point3D(-1400,225,-2320), new Point3D(-1400,225,-400),new Material(0.5,0.5,100,0,0),new Color(0,0,0)));
        scene.addGeometries(new Sphere(25,new Point3D(600,225,-400),new Material(0.5,0.5,100,0,0),new Color(0,0,0)));
        scene.addGeometries(new Sphere(25,new Point3D(-1400,225,-400),new Material(0.5,0.5,100,0,0),new Color(0,0,0)));
        //	scene.addGeometries(new Plane(new Point3D(670,225,-400),new Vector(1,0,0),new Material(0.5,0.5,100,0.8,0,0.015,0),new Color(20,20,20)));
        //	scene.addGeometries(new Plane(new Point3D(-1470,225,-400),new Vector(1,0,0),new Material(0.5,0.5,100,0.8,0,0.015,0),new Color(20,20,20)));
        //	scene.addGeometries(new Plane(new Point3D(-1400,225,-2390),new Vector(0,0,1),new Material(0,0,100,0,0),new Color(0,0,0)));
        scene.addGeometries(new Square(new Point3D(670,200,200),new Point3D(670,200,-2320),new Point3D(670,-1000,-2320),new Point3D(670,-1000,200),new Material(0.1,0.1,100,0.67,0,0.01,0),new Color(20,20,20)));
        scene.addGeometries(new Square(new Point3D(-1470,200,200),new Point3D(-1470,200,-2320),new Point3D(-1470,-1000,-2320),new Point3D(-1470,-1000,200),new Material(0.1,0.1,100,0.67,0,0.01,0),new Color(20,20,20)));

        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                scene.addGeometries(new Sphere(100,new Point3D(500-i*200,100,-(500+j*200)),new Material(0.5,0.5,1000,0.2,0),new Color(	Math.random()*100,Math.random()*100,Math.random()*100)));
            }
        }
        ImageWriter imw = new ImageWriter("images\\IMG_0021_Balls",500,300,2000,1200);
        imw.setGrid(false);
        if(optimised) {
            RenderController rn = new RenderController(imw, scene,10);
            rn.renderImage();
        }else{
            Render render = new Render(imw,scene);
            render.renderImage();
            imw.writeToimage();
        }
    }
}
