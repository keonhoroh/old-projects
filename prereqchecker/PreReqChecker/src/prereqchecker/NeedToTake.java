package prereqchecker;

import java.util.*;

public class NeedToTake {
    public static void main(String[] args) {

        if ( args.length < 3 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.ValidPrereq <adjacency list INput file> <valid prereq INput file> <valid prereq OUTput file>");
            return;
        }
        StdIn.setFile(args[0]);
        ArrayList<String>[] arr = makeList();
        StdIn.setFile(args[1]);
        StdOut.setFile(args[2]);
        ArrayList<String> output = new ArrayList<String>();

        String target = StdIn.readString();
        int numCourses = StdIn.readInt();

        ArrayList<String> coursesTaken = new ArrayList<String>();
        ArrayList<String> targetPrereqs = new ArrayList<String>();

        for(int i = 0; i < numCourses; i++)
            coursesTaken.add(StdIn.readString());
        for(int i = 0; i < coursesTaken.size(); i++)
            getPrereqs(coursesTaken.get(i), arr, coursesTaken);

        getPrereqs(target, arr, targetPrereqs);
        for(int i = 0; i < targetPrereqs.size(); i++){
            if(!coursesTaken.contains(targetPrereqs.get(i)))
                output.add(targetPrereqs.get(i));
        }

        for(int i = 0; i < output.size(); i++){
            StdOut.println(output.get(i));
        }

        

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
