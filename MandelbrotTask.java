public class MandelbrotTask implements Runnable{

    float[][] esc;
    int a,b;
    double x0,y0;

    // int width, height; 
    // double xDiff, yDiff; 
    // double xMin, yMin;

    public MandelbrotTask(float[][] esc, int a, int b, double x0, double y0){
        this.esc = esc;
        this.a = a;
        this.b = b;
        this.x0 = x0;
        this.y0 = y0;
        // this.width = width;
        // this.height = height;
        // this.xDiff = xDiff;
        // this.yDiff = yDiff;
        // this.xMin = xMin;
        // this.yMin = yMin;
    }

    public void run(){

        // double x0 = ((double)a/(double)width)*xDiff + xMin;
        // double y0 = ((double)b/(double)height)*yDiff + yMin;

        double x = 0.0;
        double y = 0.0;
        double x2 = 0.0;
        double y2 = 0.0;

        int iteration = 0;
        int maxIteration = 1000;
                
        while(x2 + y2 <= 4 && iteration < maxIteration){

            y = 2 * x * y + y0;
            x = x2 - y2 + x0;
            x2 = x * x;
            y2 = y * y;

            iteration++;
        }

        if(iteration < maxIteration){
            esc[b][a] = iteration;
        } else {
            esc[b][a] = 0;
        }

    }
}