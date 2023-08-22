/*************************************************************************
 *  Compilation:  javac RecursiveAppend.java
 *  Execution:    java RecursiveAppend
 *
 *  @author: Nathan Roh, nkr30, nkr30@scarletmail.rutgers.edu
 *
 *************************************************************************/

public class RecursiveAppend {

    // Returns the original string appended to the original string n times 
    public static String appendNTimes (String original, int n) {
        if (n == 0) {
            return original;
        }
        else {
            return appendNTimes(original, n-1) + original;
        }
    }

    public static void main (String[] args) {
        System.out.println(appendNTimes("cat", 0));
    }
}
