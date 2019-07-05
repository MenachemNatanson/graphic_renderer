package renderer;

import scene.Scene;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * class for controlling the render process with thread pool
 */
public class RenderController {
    private int _threadsFinish;
    private double[] _progress ;
    private ImageWriter _imageWriter;
    private Scene _scene;
    private boolean _grid;
    private LocalDateTime _start;
    private ExecutorService _pool;
    private int _rayBeam;

    /* ********* Constructors ***********/
    /**
     * build new RenderController
     * @param imageWriter imageWriter
     * @param scene scene
     * @param rb amount of rays in beam
     */
    public RenderController(ImageWriter imageWriter, Scene scene,int rb) {
        _imageWriter = imageWriter;
        _scene = scene;
        _grid = _imageWriter.getGrid();
        _rayBeam=rb;
    }

    /**
     * build new RenderController (with option for grid)
     *
     * @param imageWriter imageWriter
     * @param scene scene
     * @param grid apply grid if true
     * @param rb amount of rays in beam
     */
    public RenderController( ImageWriter imageWriter, Scene scene,boolean grid,int rb) {
        _imageWriter = imageWriter;
        _scene = scene;
        _grid=grid;
        _rayBeam=rb;
    }

    /* ************* Operations ***************/
    /**
     * render image
     */
    public void renderImage(){
        _scene.buildBoxes();
        _start =  LocalDateTime.now();
        int _cores = Runtime.getRuntime().availableProcessors();
        _pool = Executors.newFixedThreadPool(_cores);

        _threadsFinish = 0;
        _progress = new double[_imageWriter.getNx()-1];
        // make a thread for every row
        for(int i = 0; i< _imageWriter.getNx()-1; i++){
            Render rn = new Render(i,this,_imageWriter,_scene,i,i+1,0,_imageWriter.getNy(),_rayBeam);
            _pool.execute(rn);
        }
       _pool.shutdown();
        try {
          while (!_pool.awaitTermination(1, TimeUnit.HOURS));
       } catch (Exception ignored) {}
    }

    /**
     * gives information about the progress of a certain thread
     * @param progress percentage of how much it already rendered
     * @param id id of the thread
     */
    void progress(double progress, int id){
        _progress[id]=progress;
        double sum = 0;
        for(double p :_progress)
            sum+=p;
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        System.out.print("\r"+df.format(sum)+"%");
        System.out.flush();
    }

    /**
     * Save image and kill the thread pool after all threads are finished
     */
    void finish(){
        _threadsFinish++;
        if(_threadsFinish>=_imageWriter.getNx()-1) {
            if(_grid)
                new Render(_imageWriter,_scene).printGrid(50);
            _imageWriter.writeToimage();
            LocalDateTime end = LocalDateTime.now();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            System.out.println("\rStart time: "+dtf.format(_start));
            System.out.println("End time: "+dtf.format(end));
            //String reset = "\u001B[0m";
            //String red = "\u001B[31m";
            String reset = "";
            String red = "";
            System.out.println("Duration: "+red+Duration.between(_start,end).toMillis()/1000.0+reset+" Seconds");
            _pool.shutdownNow();
        }
    }
}
