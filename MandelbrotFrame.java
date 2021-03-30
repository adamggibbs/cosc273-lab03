import java.awt.Color;
import java.util.concurrent.*;
import java.lang.Runtime;

public class MandelbrotFrame {
    // bounds of the frame
    private double xMin;
    private double yMin;
    private double xMax;
    private double yMax;

    // width and height of the frame in pixels
    private int width;
    private int height;

    // height x width array of escape values
    private float[][] esc;

    public MandelbrotFrame (double[] bounds, int width, int height) {
	xMin = bounds[0];
	yMin = bounds[1];
	xMax = bounds[2];
	yMax = bounds[3];

	this.width = width;
	this.height = height;

	esc = new float[height][width];
    }

    // set the bounds of the frame
    public void setBounds (double[] bounds) {
	xMin = bounds[0];
	yMin = bounds[1];
	xMax = bounds[2];
	yMax = bounds[3];	
    }

    public void updateEscapeTimes() {

        double xDiff = (double)(xMax - xMin);
        double yDiff = (double)(yMax - yMin);
        
        int nThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService pool = Executors.newFixedThreadPool(nThreads);

        for(int a = 0; a < width; a++){
            for(int b = 0; b < height; b++){

                double x0 = ((double)a/(double)width)*xDiff + xMin;
                double y0 = ((double)b/(double)height)*yDiff + yMin;

                // create and execute a task
                MandelbrotTask task = new MandelbrotTask(esc, a, b, x0, y0);
                pool.execute(task);
   
            }
        }

        // shutdown the pool and wait for all pending tasks to complete
        pool.shutdown();
        try {
            pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            // do nothing
        }
    }

    private Color colorMap (float val) {
	// replace this with a more intersting color map
	if (val == 0.0)
	    return Color.BLACK;
	
    return new Color((float)(Math.log(1.5) / Math.log(val+1)), 1 / (float)(Math.log(val+2)), (float)0.5);
    }


    /*
     * return a 2d array of Colors depicting the Mandelbrot set
     */
    public Color[][] getBitmap () {
	updateEscapeTimes();
	Color[][] bitmap = new Color[height][width];
    
    for (int row = 0; row < height; ++row) {
        for (int col = 0; col < width; ++col) {
        bitmap[row][col] = colorMap(esc[row][col]);
        }
    }

	return bitmap;
    }
}
