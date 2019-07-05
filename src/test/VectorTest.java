package test;

import org.junit.Test;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VectorTest {

    /**
     * check add vector to vector,
     * test for {@link Vector#add add} method
     */
    @Test
    public void add() {
        Vector vec1 = new Vector(1, 1, 1);
        Vector vec2 = new Vector(3, -2, 5);
        assertEquals(new Vector(4, -1, 6), vec1.add(vec2));
    }

    /**
     * check exception in add,
     * test for {@link Vector#add add} method
     */
    @Test(expected = IllegalArgumentException.class)
    public void addExc() {
        Vector vec1 = new Vector(1, 1, 1);
        Vector vec2 = new Vector(-1, -1, -1);
        vec1.add(vec2);
    }

    /**
     * check subtract vector,
     * test for {@link Vector#subtract subtract} method
     */
    @Test
    public void subtract() {
        Vector vec1 = new Vector(1, 1, 1);
        Vector vec2 = new Vector(3, -2, 5);
        assertEquals(new Vector(-2, 3, -4), vec1.subtract(vec2));
    }

    /**
     * check zero exception thrown in subtract,
     * test for {@link Vector#subtract subtract} method
     */
    @Test(expected = IllegalArgumentException.class)
    public void subtractExc() {
        Vector vec1 = new Vector(1, 1, 1);
        Vector vec2 = new Vector(1, 1, 1);
        vec1.subtract(vec2);
    }

    /**
     * check scale,
     * test for {@link Vector#scale scale} method
     */
    @Test
    public void scale() {
        Vector vec1 = new Vector(1, 1, 1);
        assertEquals(new Vector(2, 2, 2), vec1.scale(2));
        assertEquals(new Vector(-2, -2, -2), vec1.scale(-2));
    }

    /**
     * check zero exception in scale,
     * test for {@link Vector#scale scale} method
     */
    @Test(expected = IllegalArgumentException.class)
    public void scaleExc() {
        Vector vec1 = new Vector(1, 1, 1);
        vec1.scale(0);
    }

    /**
     * check dot product,
     * test for {@link Vector#dotProduct dotProduct} method
     */
    @org.junit.Test
    public void dotProduct() {
        Vector vec1 = new Vector(1, 1, 1);
        Vector vec2 = new Vector(3, -2, 5);
        assertEquals(6, vec1.dotProduct(vec2));
    }

    /**
     * check cross product,
     * test for {@link Vector#crossProduct crossProduct} method
     */
    @org.junit.Test
    public void crossProduct() {
        Vector vec1 = new Vector(1, 1, 1);
        Vector vec2 = new Vector(3, -2, 5);
        assertEquals(new Vector(7, -2, -5), vec1.crossProduct(vec2));
    }

    /**
     * check vector zero in cross product,
     * test for {@link Vector#crossProduct crossProduct} method
     */
    @Test(expected = IllegalArgumentException.class)
    public void crossProductExc() {
        Vector vec1 = new Vector(1, 1, 1);
        Vector vec2 = new Vector(2, 2, 2);
        vec1.crossProduct(vec2);
    }

    /**
     * check vector length,
     * test for {@link Vector#length length} method
     */
    @org.junit.Test
    public void length() {
        Vector vec1 = new Vector(0, -3, 4);
        Vector vec2 = new Vector(3, 0, -4);
        Vector vec3 = new Vector(-3, 4, 0);
        assertEquals(5, vec1.length());
        assertEquals(5, vec2.length());
        assertEquals(5, vec3.length());
    }

    /**
     * check vector length in pow 2,
     * test for {@link Vector#length2 length2} method
     */
    @org.junit.Test
    public void length2() {
        Vector vec1 = new Vector(0, -3, 4);
        Vector vec2 = new Vector(3, 0, -4);
        Vector vec3 = new Vector(-3, 4, 0);
        assertEquals(25, vec1.length2());
        assertEquals(25, vec2.length2());
        assertEquals(25, vec3.length2());
    }

    /**
     * check vector normal,
     * test for {@link Vector#normal normal} method
     */
    @org.junit.Test
    public void normal() {
        Vector vec1 = new Vector(0, -3, 4);
        Vector vec2 = new Vector(3, 0, -4);
        Vector vec3 = new Vector(-3, 4, 0);
        assertEquals(new Vector(0, -3.0 / 5.0, 4.0 / 5.0), vec1.normal());
        assertEquals(new Vector(3.0 / 5.0, 0, -4.0 / 5.0), vec2.normal());
        assertEquals(new Vector(-3.0 / 5.0, 4.0 / 5.0, 0), vec3.normal());
        assertEquals(1,vec1.normal().length());
        assertEquals(1,vec2.normal().length());
        assertEquals(1,vec3.normal().length());
    }
}