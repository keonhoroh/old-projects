/*************************************************************************
 *  Compilation:  javac PolygonTransform.java
 *  Execution:    java PolygonTransform
 *
 *  @author: Nathan Roh, nkr30, nkr30@scarletmail.rutgers.edu
 *
 *************************************************************************/

public class PolygonTransform {


    // Returns a new array that is an exact copy of the given array. 
    // The given array is not mutated. 
    public static double[] copy(double[] array) {
        double[] duplicateArray = new double[array.length];
        for(int i = 0; i < array.length; i++) {
            duplicateArray[i] = array[i];
        }
        return duplicateArray;
	
    }
    
    // Scales the given polygon by the factor alpha. 
    public static void scale(double[] x, double[] y, double alpha) {
        for (int i = 0; i < x.length; i++) {
            x[i] *= alpha;
            y[i] *= alpha;
        }
	
    }
    
    // Translates the given polygon by (dx, dy). 
    public static void translate(double[] x, double[] y, double dx, double dy) {
        for (int i = 0; i < x.length; i++) {
            x[i] += dx;
            y[i] += dy;
        }
	
    }
    
    // Rotates the given polygon theta degrees counterclockwise, about the origin. 
    public static void rotate(double[] x, double[] y, double theta) {
        //theta = theta * ((Math.PI)/180);
        theta *= (Math.PI)/180;
        for (int i = 0; i < y.length; i++) {
            double prevX = x[i];
            x[i] = x[i] * Math.cos(theta) - y[i] * Math.sin(theta);
            y[i] = y[i] * Math.cos(theta) + prevX * Math.sin(theta);
        }
	
    }

    // Tests each of the API methods by directly calling them. 
    public static void main(String[] args) {
            
    }
}
