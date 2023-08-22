package prereqchecker;

import java.util.*;
public class ValidPrereq {
    public static void main(String[] args) {

        if ( args.length < 3 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.ValidPrereq <adjacency list INput file> <valid prereq INput file> <valid prereq OUTput file>");
            return;
        }
        StdIn.setFile(args[0]);
        ArrayList<String>[] arr = makeList();
        StdIn.setFile(args[1]);
        StdOut.setFile(args[2]);

        String course1 = StdIn.readString();
        String course2 = StdIn.readString();
        ArrayList<String> prereqs = new ArrayList<String>(); 
        getPrereqs(course2, arr, prereqs);

        String output = "YES";
        for(int i = 0; i < prereqs.size(); i++){
 
            if(prereqs.get(i).equals(course1))
                output = "NO";
        }

        StdOut.print(output);
    }

    public static void getPrereqs(String course, ArrayList<String>[] arr, ArrayList<String> prereqs){

        for(int i = 0; i < arr.length; i++){
            if(arr[i].get(0).equals(course)){
                if(arr[i].size()>1){
                    for(int j = 1; j < arr[i].size(); j++){
                        prereqs.add(arr[i].get(j));
                        getPrereqs(arr[i].get(j), arr, prereqs);
                    }
                }
            }
        }
    }


    public static ArrayList<String>[] makeList(){
        int numClasses = StdIn.readInt();
        ArrayList<String>[] arr = new ArrayList[numClasses];
        for(int i = 0; i < arr.length; i++){
            arr[i] = new ArrayList<String>();
            arr[i].add(StdIn.readString());
        } 

        int numLinks = StdIn.readInt();
        for(int i = 0; i < numLinks; i++){
            String from = StdIn.readString();
            String to = StdIn.readString();
            for(int j = 0; j < numClasses; j++){
                if(arr[j].get(0).equals(from))
                    arr[j].add(to);
            }
        }
        
        return arr;
    }
}
