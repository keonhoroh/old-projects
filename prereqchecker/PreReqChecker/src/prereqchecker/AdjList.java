package prereqchecker;

import java.util.*;
public class AdjList {
    public static void main(String[] args) {

        if ( args.length < 2 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.AdjList <adjacency list INput file> <adjacency list OUTput file>");
            return;
        }
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);

        int numClasses = StdIn.readInt();
        ArrayList<String>[] arr = new ArrayList[numClasses];
        for(int i = 0; i < arr.length; i++){
            arr[i] = new ArrayList<String>();
            arr[i].add(StdIn.readString());
        } 

        int numLinks = StdIn.readInt();
        for(int i = 0; i < numLinks; i++){
            String from = StdIn.readString();
            String onto = StdIn.readString();
            for(int j = 0; j < numClasses; j++){
                if(arr[j].get(0).equals(from))
                    arr[j].add(onto);
            }
        }

        String output = "";
        for(int i = 0; i< numClasses; i++){
            for(int j = 0; j<arr[i].size(); j++)
                output += arr[i].get(j) + " ";
            output += "\n";
        }

        StdOut.print(output);
    }
}
