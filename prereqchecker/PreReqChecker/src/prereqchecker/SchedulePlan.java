package prereqchecker;

import java.util.*;

public class SchedulePlan {
    public static void main(String[] args) {

        if ( args.length < 3 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.ValidPrereq <adjacency list INput file> <valid prereq INput file> <valid prereq OUTput file>");
            return;
        }
        StdIn.setFile(args[0]);
        ArrayList<String>[] arr = makeList();
        StdIn.setFile(args[1]);
        StdOut.setFile(args[2]);

        String target = StdIn.readString();
        int numCourses = StdIn.readInt();

        ArrayList<String> coursesTaken = new ArrayList<String>();
        ArrayList<String> semester = new ArrayList<String>();

        for(int i = 0; i < numCourses; i++)
            coursesTaken.add(StdIn.readString());
        for(int i = 0; i < coursesTaken.size(); i++)
            getPrereqs(coursesTaken.get(i), arr, coursesTaken);

        ArrayList<String> output = new ArrayList<String>();
        int count = 0;
        while(!coursesTaken.contains(target)){
            for(int i = 0; i < arr.length; i++){
                boolean elig = true;
                for(int j = 1; j < arr[i].size(); j++){
                    if(!coursesTaken.contains(arr[i].get(j)))
                        elig = false;
                }
                if(elig && !coursesTaken.contains(arr[i].get(0))){ 
                    semester.add(arr[i].get(0));
                    output.add(arr[i].get(0) + " ");
                }
            }
            for(int k = 0; k < semester.size(); k++)
                coursesTaken.add(semester.get(k));
            output.add(".");
            count++;
        }

        count--;


        StdOut.println(count);
        int i = 0;
        while(count > 0){
            if(!output.get(i).equals(".")){
                StdOut.print(output.get(i));
                i++;
            }
            else{
                i++;
                count--;
                if(count > 0)
                    StdOut.println();
            }
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
