package test;

import org.junit.Test;
import renderer.ImageWriter;

import java.awt.*;

import static org.junit.Assert.*;

public class ImageWriterTest {

    /**
     * Create Image with 500x500 pixel and a 10x10 grid
     */
    @Test
    public void createGrid(){
        ImageWriter imageWriter = new ImageWriter("images\\IMG_0002_Grid",500,500,500,500);
        for(int i=0;i<500;i++){
            for(int j=0;j<500;j++){
                if(isGrid(i,j,50,50) && i!=499&&j!=499)
                    imageWriter.writePixel(i,j, Color.WHITE);
                else
                    imageWriter.writePixel(i,j,Color.BLACK);
            }
        }
        imageWriter.writeToimage();
    }

    /**
     * Check if the Location is a grid line
     * 
     * @param x the x location
     * @param y the y location
     * @param columnWidth width of column
     * @param rowWidth width of row
     * @return true if the location is a grid line, and false otherwise
     */
    private static Boolean isGrid(int x, int y, int columnWidth, int rowWidth){
        return (x+1)%columnWidth == 0 || (y+1)%rowWidth == 0;

    }
}