package elements;

import primitives.*;

/**
 * A Camera
 */
public class Camera {
    private Point3D _p0;
    private Vector _vUp;
    private Vector _vRight;
    private Vector _vTo;
    /* ********* Constructors ***********/
    /**
     * A new Camera. vUp and vTo need to be orthogonal
     * @param p0 camera location
     * @param vUp vector up
     * @param vTo vector to
     */
    public Camera(Point3D p0, Vector vUp, Vector vTo) {
        if(!Util.isZero(vUp.dotProduct(vTo)))
            throw new IllegalArgumentException("Vectors are not orthogonal");
        _p0 = new Point3D(p0);
        _vUp = vUp.normal();
        _vTo = vTo.normal();
        _vRight = vTo.crossProduct(vUp).normal();
    }
    /* ************* Getters/Setters *******/

    /**
     * Get camera location
     * @return p0
     */
    public Point3D getP0() {
        return _p0;
    }

    /**
     * Get vector Up
     * @return vUP
     */
    public Vector getVUp() {
        return _vUp;
    }

    /**
     * Get vector right
     * @return vRight
     */
    public Vector getVRight() {
        return _vRight;
    }

    /**
     * Get vector to
     * @return vTo
     */
    public Vector getVTo() {
        return _vTo;
    }

    /* ************** Admin *****************/

    @Override
    public String toString() {
        return "Camera{" +
                "P0=" + _p0 +
                ", VUp=" + _vUp +
                ", VR=" + _vRight +
                ", vTo=" + _vTo +
                '}';
    }
    /* ************* Operations ***************/

    /**
     * Get a ray that goes through pixel in plane
     * @param nX Number of pixels on X
     * @param nY Number of pixels on Y
     * @param i Pixel location on the X
     * @param j Pixel location on the Y
     * @param screenDistance Distance of the plane from camera
     * @param screenWidth Screen Width
     * @param screenHeight Screen Height
     * @return Ray that goes through pixel
     */
    public Ray constructRayThroughPixel(int nX,int nY,int i,int j ,double screenDistance ,double screenWidth , double screenHeight){
        //check if the values are ok
        if(screenDistance <=0)
            throw new IllegalArgumentException("Zero or negative distance");
        if(nX <= 0 && nY <= 0)
            throw new IllegalArgumentException("Zero or negative pixels");
        if(i >= nX || i<0)
            throw new IllegalArgumentException("i out of range");
        if(j >= nY || j<0)
            throw new IllegalArgumentException("j out of range");

        Point3D pointC = _p0.add(_vTo.scale(screenDistance));
        double xToMove = (i - (nX - 1.0)/2.0);
        double yToMove = (j - (nY - 1.0)/2.0);
        Point3D pointIJ = pointC;

        //find the point on the plane
        if(!Util.isZero(xToMove))
            pointIJ = pointIJ.add(_vRight.scale(xToMove*screenWidth/nX));
        if(!Util.isZero(yToMove))
            pointIJ = pointIJ.add(_vUp.scale(-yToMove*screenHeight/nY));

        //create ray and return
        Vector vectorIJ = pointIJ.subtract(_p0);
        return  new Ray(pointIJ,vectorIJ);
    }

    /**
     * rotate the camera
     * @param x degrees on the x
     * @param y degrees on the y
     * @param z degrees on the z
     */
    public void rotateXYZ(double x,double y,double z){
        // convert from degrees to radians
        x = (x/180)*Math.PI;
        y = (y/180)*Math.PI;
        z = (z/180)*Math.PI;

        double cos = Math.cos(x);
        double sin = Math.sin(x);
        Matrix matrixX = new Matrix(new double[][]{{cos,-sin,0},{sin,cos,0},{0,0,1}});

        cos = Math.cos(y);
        sin = Math.sin(y);
        Matrix matrixY = new Matrix(new double[][]{{cos, 0, sin}, {0, 1, 0}, {-sin, 0, cos}});

        cos = Math.cos(z);
        sin =Math.sin(z);
        Matrix matrixZ = new Matrix(new double[][]{{1, 0, 0}, {0, cos, -sin}, {0, sin, cos}});

        Matrix matrix = matrixX.multiply(matrixY).multiply(matrixZ);

        _vRight = _vRight.multiply(matrix).normal();
        _vTo = _vTo.multiply(matrix).normal();
        _vUp = _vUp.multiply(matrix).normal();
    }
}

