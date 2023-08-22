/*************************************************************************
 *  Compilation:  javac CheckDigit.java
 *  Execution:    java CheckDigit 020131452
 *
 *  @author: Nathan Roh, nkr30@scarletmail.rutgers.edu, nkr30
 *
 *  Takes a 12 or 13 digit integer as a command line argument, then computes
 *  and displays the check digit
 *
 *  java CheckDigit 048231312622
 *  0
 *
 *  java CheckDigit 9780470458310
 *  0
 * 
 *  java CheckDigit 9780470454310
 *  8
 * 
 *  Print only the check digit character, nothing else.
 *
 *************************************************************************/

public class CheckDigit {

    public static void main (String[] args) {
        long digit = Long.parseLong(args[0]);
        long secondDigit = digit / 10;
        int sumOne = 0;
        int sumTwo = 0;
        int endSum = 0;
        int digitLength = String.valueOf(digit).length();
    
        for(int i = 0; i < digitLength; i+=2){
            sumOne = sumOne + ((int)(digit%10));
            digit /= 100;
        }
        for(int i = 0; i < digitLength; i+=2){
            sumTwo = sumTwo + ((int)(secondDigit%10));
            secondDigit /= 100;
        }
        
        endSum = ((sumOne%10) + ((3 * (sumTwo%10))%10))%10;
        System.out.println(endSum);
    }
}