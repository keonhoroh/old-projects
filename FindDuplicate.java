/*************************************************************************
 *  Compilation:  javac FindDuplicate.java
 *  Execution:    java FindDuplicate
 *
 *  @author: Nathan Roh, nkr30, nkr30@scarletmail.rutgers.edu
 *
 * FindDuplicate that reads n integer arguments from the command line 
 * into an integer array of length n, where each value is between is 1 and n, 
 * and displays true if there are any duplicate values, false otherwise.
 *
 *  % java FindDuplicate 10 8 5 4 1 3 6 7 9
 *  false
 *
 *  % java FindDuplicate 4 5 2 1 
 *  true
 *************************************************************************/

public class FindDuplicate {

    public static void main(String[] args) {
		int[] numbers = new int[args.length];
		boolean condition = false;

		for(int i = 0; i < args.length; i++){
			numbers[i] = Integer.parseInt(args[i]);
		}
		for(int i = 0; i < args.length; i++){
			for(int k = i+1; k < args.length; k++){
				if (numbers[i] == numbers[k]){
					condition = true;
					break;
				}
			}
			
		}
		System.out.println(condition);
	}
}