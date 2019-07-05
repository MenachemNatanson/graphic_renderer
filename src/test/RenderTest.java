package test;

import elements.AmbientLight;
import elements.DirectionalLight;
import elements.SpotLight;
import org.junit.Test;

import elements.Camera;
import geometries.*;
import org.xml.sax.SAXException;
import primitives.*;
import renderer.ImageWriter;
import renderer.Render;
import renderer.loadScene;
import scene.Scene;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.fail;

public class RenderTest {

	/**
	 * Render all tests in folder Exc6
	 *
	 */
	@Test
	public void renderEx6() {
		String message = "";
		System.out.println("-----Ex6 render-----");
		File folder = new File("xml\\Exc6");
		File[] listOfFiles = folder.listFiles();
		assert listOfFiles != null;
		for (int i = 0; i < listOfFiles.length; i++) {
			try {
				if (listOfFiles[i].isFile()) {
					Render render = loadScene.loadFromXML("xml\\Exc6\\" + listOfFiles[i].getName(),false);
					render.renderImage();
					if(render.getImageWriter().getGrid())
						render.printGrid(50);
					render.getImageWriter().writeToimage();
					System.out.println("Successfully rendered picture: " + render.getImageWriter().getImageName());
					System.out.println("Completed: " + (int)(((double) i+1) / listOfFiles.length * 100.0) + "%");
				}
			} catch (Exception ex) {
				message = "\n" + "Error in: " + listOfFiles[i].getName()+"\n"+"Error details: " + ex.toString();
			}
		}
		if(message != ""){
			fail(message);
		}
	}

	/**
	 * Render all tests in folder Exc5

	 */
	@Test
	public void renderEx5() {
		String message ="";
		System.out.println("-----Ex5 render-----");
		File folder = new File("xml\\Exc5");
		File[] listOfFiles = folder.listFiles();
		assert listOfFiles != null;
		for (int i = 0; i < listOfFiles.length; i++) {
			try {
				if (listOfFiles[i].isFile()) {
					Render render = loadScene.loadFromXML("xml\\Exc5\\" + listOfFiles[i].getName(),true);
					render.renderImage();
					if(render.getImageWriter().getGrid())
						render.printGrid(50);
					render.getImageWriter().writeToimage();
					System.out.println("Successfully rendered picture: " + render.getImageWriter().getImageName());
					System.out.println("Completed: " + (int)(((double) i+1) / listOfFiles.length * 100.0) + "%");
				}
			} catch (Exception ex) {
				message = "\n" + "Error in: " + listOfFiles[i].getName()+"\n"+"Error details: " + ex.toString();
			}
		}
		if(message != ""){
			fail(message);
		}
	}

	/**
	 * Render all tests in folder Exc7

	 */
	@Test
	public void renderEx7() {
		String message ="";
		System.out.println("-----Ex7 render-----");
		File folder = new File("xml\\Exc7");
		File[] listOfFiles = folder.listFiles();
		assert listOfFiles != null;
		for (int i = 0; i < listOfFiles.length; i++) {
			try {
				if (listOfFiles[i].isFile()) {
					Render render = loadScene.loadFromXML("xml\\Exc7\\" + listOfFiles[i].getName(),false);
					render.renderImage();
					if(render.getImageWriter().getGrid())
						render.printGrid(50);
					render.getImageWriter().writeToimage();
					System.out.println("Successfully rendered picture: " + render.getImageWriter().getImageName());
					System.out.println("Completed: " + (int)(((double) i+1) / listOfFiles.length * 100.0) + "%");
				}
			} catch (Exception ex) {
				message = "\n" + "Error in: " + listOfFiles[i].getName()+"\n"+"Error details: " + ex.toString();
			}
		}
		if(message != ""){
			fail(message);
		}
	}

	/**
	 * Render all tests in folder Tests
	 */
	@Test
	public void renderTests() throws IOException, SAXException, ParserConfigurationException {
		String message ="";
		System.out.println("-----Tests render-----");
		File folder = new File("xml\\Tests");
		File[] listOfFiles = folder.listFiles();
		assert listOfFiles != null;
		for (int i = 0; i < listOfFiles.length; i++) {
			//	try {
			if (listOfFiles[i].isFile()) {
				Render render = loadScene.loadFromXML("xml\\Tests\\" + listOfFiles[i].getName(),false);
				render.renderImage();
				if(render.getImageWriter().getGrid())
					render.printGrid(50);
				render.getImageWriter().writeToimage();
				System.out.println("Successfully rendered picture: " + render.getImageWriter().getImageName());
				System.out.println("Completed: " + (int)(((double) i+1) / listOfFiles.length * 100.0) + "%");
			}
			//	} catch (Exception ex) {
			//		message = "\n" + "Error in: " + listOfFiles[i].getName()+"\n"+"Error details: " + ex.toString();
			//	}
		}
		if(message != ""){
			fail(message);
		}
	}


	/**
	 * 100 Balls on plane
	 */
	@Test
	public void proTests(){
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		System.out.println(dtf.format(now));
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
		scene.buildBoxes();
		Render rn = new Render(imw,scene);
		rn.renderImage();
		imw.writeToimage();
		now = LocalDateTime.now();
		System.out.println(dtf.format(now));
	}

	/**
	 * 100 Balls on plane
	 */
	@Test
	public void proTests2(){
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		System.out.println(dtf.format(now));
		Scene scene = new Scene("");
		scene.setCamera(new Camera(new Point3D(-400,-1800,2000),new Vector(0,-1,0),new Vector(0,0,-1)),500);
		scene.getCamera().rotateXYZ(0,0,30);
		scene.setBackground(Color.BLACK);
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
		cube.setOptimised(true);
		scene.addGeometries(cube);
		Cylinder c1 = (new Cylinder(25,new Point3D(600,225,-400), new Point3D(-1400,225,-400),new Material(0.5,0.5,100,0,0),new Color(0,0,0)));
		Cylinder c2 = (new Cylinder(25,new Point3D(600,225,-400), new Point3D(600,225,-2320),new Material(0.5,0.5,100,0,0),new Color(0,0,0)));
		Cylinder c3 = (new Cylinder(25,new Point3D(-1400,225,-2320), new Point3D(-1400,225,-400),new Material(0.5,0.5,100,0,0),new Color(0,0,0)));
		Sphere sp1 = (new Sphere(25,new Point3D(600,225,-400),new Material(0.5,0.5,100,0,0),new Color(0,0,0)));
		Sphere sp2 = (new Sphere(25,new Point3D(-1400,225,-400),new Material(0.5,0.5,100,0,0),new Color(0,0,0)));
		//	scene.addGeometries(new Plane(new Point3D(670,225,-400),new Vector(1,0,0),new Material(0.5,0.5,100,0.8,0,0.015,0),new Color(20,20,20)));
		//	scene.addGeometries(new Plane(new Point3D(-1470,225,-400),new Vector(1,0,0),new Material(0.5,0.5,100,0.8,0,0.015,0),new Color(20,20,20)));
		//	scene.addGeometries(new Plane(new Point3D(-1400,225,-2390),new Vector(0,0,1),new Material(0,0,100,0,0),new Color(0,0,0)));
		Square sq1 = (new Square(new Point3D(670,200,200),new Point3D(670,200,-2320),new Point3D(670,-1000,-2320),new Point3D(670,-1000,200),new Material(0.1,0.1,100,0.67,0,0.01,0),new Color(20,20,20)));
		Square sq2 = (new Square(new Point3D(-1470,200,200),new Point3D(-1470,200,-2320),new Point3D(-1470,-1000,-2320),new Point3D(-1470,-1000,200),new Material(0.1,0.1,100,0.67,0,0.01,0),new Color(20,20,20)));
		c1.setOptimised(true);
		c2.setOptimised(true);
		c3.setOptimised(true);
		sp1.setOptimised(true);
		sp2.setOptimised(true);
		sq1.setOptimised(true);
		sq2.setOptimised(true);
		scene.addGeometries(c1,c2,c3,sp1,sp2,sq1,sq2);


		for(int i=0;i<10;i++){
			for(int j=0;j<10;j++){
				Sphere sp = new Sphere(100,new Point3D(500-i*200,100,-(500+j*200)),new Material(0.5,0.5,1000,0.2,0),new Color(	Math.random()*100,Math.random()*100,Math.random()*100));
				sp.setOptimised(true);
				scene.addGeometries(sp);
			}
		}
		scene.buildBoxes();
		ImageWriter imw = new ImageWriter("images\\IMG_0021_Balls",500,300,2000,1200);
		Render rn = new Render(imw,scene);
		rn.renderImage();
		imw.writeToimage();
		now = LocalDateTime.now();
		System.out.println(dtf.format(now));
	}

	/**
	 * 100 Balls on plane
	 */
	@Test
	public void proTests3(){
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		System.out.println(dtf.format(now));
		Scene scene = new Scene("");
		scene.setCamera(new Camera(new Point3D(-400,-1800,2000),new Vector(0,-1,0),new Vector(0,0,-1)),500);
		scene.getCamera().rotateXYZ(0,0,30);
		scene.setBackground(Color.BLACK);
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
		cube.setOptimised(true);
		scene.addGeometries(cube);
		Cylinder c1 = (new Cylinder(25,new Point3D(600,225,-400), new Point3D(-1400,225,-400),new Material(0.5,0.5,100,0,0),new Color(0,0,0)));
		Cylinder c2 = (new Cylinder(25,new Point3D(600,225,-400), new Point3D(600,225,-2320),new Material(0.5,0.5,100,0,0),new Color(0,0,0)));
		Cylinder c3 = (new Cylinder(25,new Point3D(-1400,225,-2320), new Point3D(-1400,225,-400),new Material(0.5,0.5,100,0,0),new Color(0,0,0)));
		Sphere sp1 = (new Sphere(25,new Point3D(600,225,-400),new Material(0.5,0.5,100,0,0),new Color(0,0,0)));
		Sphere sp2 = (new Sphere(25,new Point3D(-1400,225,-400),new Material(0.5,0.5,100,0,0),new Color(0,0,0)));
		//	scene.addGeometries(new Plane(new Point3D(670,225,-400),new Vector(1,0,0),new Material(0.5,0.5,100,0.8,0,0.015,0),new Color(20,20,20)));
		//	scene.addGeometries(new Plane(new Point3D(-1470,225,-400),new Vector(1,0,0),new Material(0.5,0.5,100,0.8,0,0.015,0),new Color(20,20,20)));
		//	scene.addGeometries(new Plane(new Point3D(-1400,225,-2390),new Vector(0,0,1),new Material(0,0,100,0,0),new Color(0,0,0)));
		Square sq1 = (new Square(new Point3D(670,200,200),new Point3D(670,200,-2320),new Point3D(670,-1000,-2320),new Point3D(670,-1000,200),new Material(0.1,0.1,100,0.67,0,0.01,0),new Color(20,20,20)));
		Square sq2 = (new Square(new Point3D(-1470,200,200),new Point3D(-1470,200,-2320),new Point3D(-1470,-1000,-2320),new Point3D(-1470,-1000,200),new Material(0.1,0.1,100,0.67,0,0.01,0),new Color(20,20,20)));
		c1.setOptimised(true);
		c2.setOptimised(true);
		c3.setOptimised(true);
		sp1.setOptimised(true);
		sp2.setOptimised(true);
		sq1.setOptimised(true);
		sq2.setOptimised(true);
		scene.addGeometries(c1,c2,c3,sp1,sp2,sq1,sq2);

		Intersectable list[][] =new Intersectable[20][5];
		List<Intersectable> list1 = new ArrayList<>();
		int w=0;
		for(int i=0;i<10;i++){
			for(int j=0;j<5;j++){
				list[w][j] = new Sphere(100,new Point3D(500-i*200,100,-(500+j*200)),new Material(0.5,0.5,1000,0.2,0),new Color(	Math.random()*100,Math.random()*100,Math.random()*100));
				list[w][j].setOptimised(true);
			}
			list1.add(new Box(Arrays.asList(list[w])));
			w++;
			for(int j=5;j<10;j++){
				list[w][j-5] = new Sphere(100,new Point3D(500-i*200,100,-(500+j*200)),new Material(0.5,0.5,1000,0.2,0),new Color(	Math.random()*100,Math.random()*100,Math.random()*100));
				list[w][j-5].setOptimised(true);
			}
			list1.add(new Box(Arrays.asList(list[w])));
			w++;
		}
		scene.addGeometries(new Box(list1));
		ImageWriter imw = new ImageWriter("images\\IMG_0021_Balls",500,300,2000,1200);
		Render rn = new Render(imw,scene);
		rn.renderImage();
		imw.writeToimage();
		now = LocalDateTime.now();
		System.out.println(dtf.format(now));
	}

	@Test
	public void testBeam(){
		Vector v = new Vector(1,1,1);
		v=v.normal();
		Vector n = new Vector(0,0,1);
		Render rn = new Render(null,null);
		List<Ray> beam = rn.getBeam(new Ray(new Point3D(0,0,0),v),n,0.002);
		for (Ray r:beam){
			if(v.getPoint3D().distance(r.getVector().getPoint3D())>0.2)
				fail();
		}

	}


	/**
	 * 100 Balls on plane
	 */
	@Test
	public void proTestsNew(){
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		System.out.println(dtf.format(now));
		Scene scene = new Scene("");
		scene.setCamera(new Camera(new Point3D(-400,-1800,2000),new Vector(0,-1,0),new Vector(0,0,-1)),500);
		scene.getCamera().rotateXYZ(0,0,30);
		scene.setBackground(Color.BLACK);
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
		cube.setOptimised(true);
		scene.addGeometries(cube);
		Cylinder c1 = (new Cylinder(25,new Point3D(600,225,-400), new Point3D(-1400,225,-400),new Material(0.5,0.5,100,0,0),new Color(0,0,0)));
		Cylinder c2 = (new Cylinder(25,new Point3D(600,225,-400), new Point3D(600,225,-2320),new Material(0.5,0.5,100,0,0),new Color(0,0,0)));
		Cylinder c3 = (new Cylinder(25,new Point3D(-1400,225,-2320), new Point3D(-1400,225,-400),new Material(0.5,0.5,100,0,0),new Color(0,0,0)));
		Sphere sp1 = (new Sphere(25,new Point3D(600,225,-400),new Material(0.5,0.5,100,0,0),new Color(0,0,0)));
		Sphere sp2 = (new Sphere(25,new Point3D(-1400,225,-400),new Material(0.5,0.5,100,0,0),new Color(0,0,0)));
		//	scene.addGeometries(new Plane(new Point3D(670,225,-400),new Vector(1,0,0),new Material(0.5,0.5,100,0.8,0,0.015,0),new Color(20,20,20)));
		//	scene.addGeometries(new Plane(new Point3D(-1470,225,-400),new Vector(1,0,0),new Material(0.5,0.5,100,0.8,0,0.015,0),new Color(20,20,20)));
		//	scene.addGeometries(new Plane(new Point3D(-1400,225,-2390),new Vector(0,0,1),new Material(0,0,100,0,0),new Color(0,0,0)));
		Square sq1 = (new Square(new Point3D(670,200,200),new Point3D(670,200,-2320),new Point3D(670,-1000,-2320),new Point3D(670,-1000,200),new Material(0.1,0.1,100,0.67,0,0.01,0),new Color(20,20,20)));
		Square sq2 = (new Square(new Point3D(-1470,200,200),new Point3D(-1470,200,-2320),new Point3D(-1470,-1000,-2320),new Point3D(-1470,-1000,200),new Material(0.1,0.1,100,0.67,0,0.01,0),new Color(20,20,20)));
		c1.setOptimised(true);
		c2.setOptimised(true);
		c3.setOptimised(true);
		sp1.setOptimised(true);
		sp2.setOptimised(true);
		sq1.setOptimised(true);
		sq2.setOptimised(true);
		scene.addGeometries(c1,c2,c3,sp1,sp2,sq1,sq2);


		for(int i=0;i<10;i++){
			for(int j=0;j<10;j++){
				if(!((j>=4&&j<=5)&&(i>=4&&i<=5)) ){
					Sphere sp = new Sphere(100, new Point3D(500 - i * 200, 100, -(500 + j * 200)), new Material(0.5, 0.5, 1000, 0.2, 0), new Color(Math.random() * 100, Math.random() * 100, Math.random() * 100));
					sp.setOptimised(true);
					scene.addGeometries(sp);
				}
			}
		}
		Sphere sp = new Sphere(400,new Point3D(500-900,-300,-(500+900)),new Material(0.1,0.5,1000,0.65,0),new Color(	10,10,10));
		sp.setOptimised(true);
		scene.addGeometries(sp);
		scene.buildBoxes();
		ImageWriter imw = new ImageWriter("images\\IMG_0051_NewBalls",500,300,2000,1200);
		Render rn = new Render(imw,scene);
		rn.renderImage();
		imw.writeToimage();
		now = LocalDateTime.now();
		System.out.println(dtf.format(now));
	}

	/**
	 * 100 Balls on plane
	 */
	@Test
	public void proTestsNew2(){
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		System.out.println(dtf.format(now));
		Scene scene = new Scene("");
		scene.setCamera(new Camera(new Point3D(-400,-1800,2000),new Vector(0,-1,0),new Vector(0,0,-1)),500);
		scene.getCamera().rotateXYZ(0,0,30);
		scene.setBackground(Color.BLACK);
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
		cube.setOptimised(true);
		scene.addGeometries(cube);
		Cylinder c1 = (new Cylinder(25,new Point3D(600,225,-400), new Point3D(-1400,225,-400),new Material(0.5,0.5,100,0,0),new Color(0,0,0)));
		Cylinder c2 = (new Cylinder(25,new Point3D(600,225,-400), new Point3D(600,225,-2320),new Material(0.5,0.5,100,0,0),new Color(0,0,0)));
		Cylinder c3 = (new Cylinder(25,new Point3D(-1400,225,-2320), new Point3D(-1400,225,-400),new Material(0.5,0.5,100,0,0),new Color(0,0,0)));
		Sphere sp1 = (new Sphere(25,new Point3D(600,225,-400),new Material(0.5,0.5,100,0,0),new Color(0,0,0)));
		Sphere sp2 = (new Sphere(25,new Point3D(-1400,225,-400),new Material(0.5,0.5,100,0,0),new Color(0,0,0)));
		//	scene.addGeometries(new Plane(new Point3D(670,225,-400),new Vector(1,0,0),new Material(0.5,0.5,100,0.8,0,0.015,0),new Color(20,20,20)));
		//	scene.addGeometries(new Plane(new Point3D(-1470,225,-400),new Vector(1,0,0),new Material(0.5,0.5,100,0.8,0,0.015,0),new Color(20,20,20)));
		//	scene.addGeometries(new Plane(new Point3D(-1400,225,-2390),new Vector(0,0,1),new Material(0,0,100,0,0),new Color(0,0,0)));
		Square sq1 = (new Square(new Point3D(670,200,200),new Point3D(670,200,-2320),new Point3D(670,-1000,-2320),new Point3D(670,-1000,200),new Material(0.1,0.1,100,0.67,0,0.01,0),new Color(20,20,20)));
		Square sq2 = (new Square(new Point3D(-1470,200,200),new Point3D(-1470,200,-2320),new Point3D(-1470,-1000,-2320),new Point3D(-1470,-1000,200),new Material(0.1,0.1,100,0.67,0,0.01,0),new Color(20,20,20)));
		c1.setOptimised(true);
		c2.setOptimised(true);
		c3.setOptimised(true);
		sp1.setOptimised(true);
		sp2.setOptimised(true);
		sq1.setOptimised(true);
		sq2.setOptimised(true);
		scene.addGeometries(c1,c2,c3,sp1,sp2,sq1,sq2);


		for(int i=0;i<10;i++){
			for(int j=0;j<10;j++){
				if(!((j>=4&&j<=5)&&(i>=4&&i<=5)) ){
					Sphere sp = new Sphere(100, new Point3D(500 - i * 200, 100, -(500 + j * 200)), new Material(0.5, 0.5, 1000, 0.2, 0), new Color(Math.random() * 100, Math.random() * 100, Math.random() * 100));
					sp.setOptimised(true);
					scene.addGeometries(sp);
				}
			}
		}
		cube=		new Cube(
				new Square(
						new Point3D(-600,-200,-1600),
						new Point3D(-200,-200,-1600),
						new Point3D(-200,-200,-1200),
						new Point3D(-600,-200,-1200)),
				new Point3D(-600,-600,-1200),
				400,
				new Material(0.2,0.5,1000,0.6,0),
				new Color(	10,10,10)) ;
		cube.setOptimised(true);
		scene.addGeometries(cube);
		scene.buildBoxes();
		ImageWriter imw = new ImageWriter("images\\IMG_0052_NewBalls",500,300,2000,1200);
		Render rn = new Render(imw,scene);
		rn.renderImage();
		imw.writeToimage();
		now = LocalDateTime.now();
		System.out.println(dtf.format(now));
	}
}







