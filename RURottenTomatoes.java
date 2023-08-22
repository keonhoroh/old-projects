/*************************************************************************
 *  Compilation:  javac RURottenTomatoes.java
 *  Execution:    java RURottenTomatoes
 *
 *  @author: Nathan Roh, nkr30, nkr30@scarletmail.rutgers.edu
 *
 * RURottenTomatoes creates a 2 dimensional array of movie ratings 
 * from the command line arguments and displays the index of the movie 
 * that has the highest sum of ratings.
 *
 *  java RURottenTomatoes 3 2 5 2 3 3 4 1
 *  0
 *************************************************************************/

public class RURottenTomatoes {

    public static void main(String[] args) {
		int r = Integer.parseInt(args[0]);
		int c = Integer.parseInt(args[1]);
		int[][] matrix = new int[r][c];
		int row = 0;
		int[] sums = new int[c];

		for (int i = 2; i < args.length; i += c){
			for(int k = 0; k < c; k++){
				matrix[row][k] = Integer.parseInt(args[i + k]);
			}
			row ++;
		}

		for(int i = 0; i < r; i++){
			for(int k = 0; k < c; k++){
				sums[k] = sums[k] + matrix[i][k];
			}
		}

		int max = 0;
		for(int i = 0; i < sums.length; i++){
			if (sums[i] > sums[max]){
				max = i;
			}
		}
		System.out.println(max);
	}
}
