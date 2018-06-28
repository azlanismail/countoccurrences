import mpi.Datatype;
import mpi.MPI;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HybriWordCount extends Thread {
	
	private Thread t;
    private String threadName;
    private String gcs = null;
    private String tt = null;
    private int ind ;
    private static int noOfThread = 8;
    private static MultiThreadWordCount[] th = new MultiThreadWordCount[noOfThread];
    static Map<String, Integer> sum = new HashMap<>();
    Map<String, Integer> a0 = new HashMap<>();
    Map<String, Integer> a1 = new HashMap<>();
    Map<String, Integer> a2 = new HashMap<>();
    Map<String, Integer> a3 = new HashMap<>();
    Map<String, Integer> a4 = new HashMap<>();
    Map<String, Integer> a5 = new HashMap<>();
    Map<String, Integer> a6 = new HashMap<>();
    Map<String, Integer> a7 = new HashMap<>();
	
	static int min = 1;
	
	public static void main(String[] args) throws Exception{
		long startTime = System.nanoTime();
		MPI.Init(args);  
        int rank = MPI.COMM_WORLD.Rank();
//        int size = MPI.COMM_WORLD.Size();    
        String fileName = "C:/sample/500.txt";
        ArrayList<String> strings = new ArrayList<String>();
        readFile(fileName,strings);
        ArrayList<Integer> lineForEachProcess = PreMapping(strings.size(),Integer.parseInt(args[1]));
        int r = Integer.parseInt(args[1]);
//        System.out.println("Line For Each Process : " + lineForEachProcess);
//    	System.out.println("Rank : " + rank);
    	
    	Map<String, Integer> a1 = new HashMap<>();
    	for(int i = 0 ; i < r ; i++) {
    		ArrayList<String> chunkString = new ArrayList<String>();
    		if(rank==i) {
        		int endRange = lineForEachProcess.get(i)-1;
        		int startRange = lineForEachProcess.get(i + 1);
        		int forEachthread = endRange-startRange;
        		ArrayList<Integer> lineForEachThread = PreMapping(forEachthread,noOfThread);
        		System.out.println(forEachthread);
        		MappingAndStartThread(strings,lineForEachThread,noOfThread);
        		for(int se = startRange; se <= endRange; se++) {
        			chunkString.add(strings.get(se));
        		}
        		String listString = "";
            	for (String s : chunkString)
                {
                    listString += s;
                }
        		System.out.println("Rank "+i + ": " + "start " + startRange + " end " + endRange);
        		String chunkS = listString;
        		System.out.println(chunkS);
            	String[] splitStr = chunkS.split(" ");
            	for (String words: splitStr) {
                	int endOfLine = words.length() - 1;
                    if (a1.containsKey(words)) {
                        a1.put(words, a1.get(words) + 1);
                    } else {
                    	boolean word = false;
                    	for (int c = 0; c < words.length(); c++) {
                    		char sntCat = words.charAt(c);
                    		if (Character.isLetter(sntCat) && c != endOfLine) {
                      			word = true;
                      		} else if (!Character.isLetter(sntCat) && word) {
                      			a1.put(words, 1);
                      			word = false;
                      		} else if (Character.isLetter(sntCat) && c == endOfLine) {
                      			a1.put(words, 1);
                      		}
                    	}
                    }
                }
    		}
    	}Map<String, Integer> globalWordCount = new HashMap<>();
        for (String words : a1.keySet()) {
            int count = a1.get(words);
            if(globalWordCount.containsKey(words)) {
            	globalWordCount.put(words, globalWordCount.get(words)+count);
                MPI.COMM_WORLD.Reduce(a1, 0, globalWordCount, 0, 1, MPI.OBJECT, MPI.SUM, 0);
            }else {
            	globalWordCount.put(words, count);
            }        
        }
//        a1.forEach((k, v) -> globalWordCount.merge(k, v, Integer::sum));
        
        
        if (rank == 0) {
               System.out.println("I am process "+rank+" with the total data");
//               System.out.println(globalWordCount.toString());
               for (String words : globalWordCount.keySet()) {
                   int count = globalWordCount.get(words);
                   if (count >= min) {
                   	System.out.print(count + "\t|");
                   	System.out.println(words);
                   }
               }
        }else{
//            System.out.println("I am process "+rank+" with the total data ");
//            System.out.println(a1.toString());
//            System.out.println(globalWordCount.toString());
        }
        MPI.Finalize();

        long endTime = System.nanoTime();
        long duration = (endTime - startTime)/1000000;
        System.out.println("\nExecution time : "+ duration+" ms");
	}
	
	/*
     * @Read Text File
     */
    public static void readFile(String fileName,ArrayList<String> strings) {
    	String line = null;

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null) {
            	strings.add(line);
            }   
            bufferedReader.close();         
        } catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");                
        } catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }
    }
    
    /*
     * @Convert List to String
     */
    public static String listToString(ArrayList<String> strings) {
    	String listString = "";
    	for (String s : strings)
        {
            listString += s;
        }
    	return listString;
    }
    
    /*
     * @Convert List To String
     */
    public static ArrayList<Integer> PreMapping(int ln,int tN) {
    	int lineNumPerThread = ln / tN;
    	int cntr = ln / lineNumPerThread;
    	ArrayList<Integer> lineList = new ArrayList<Integer>();
    	lineList.add(Integer.valueOf(ln));
    	int chkLine = ln - lineNumPerThread;
    	for(int i = 0 ; i < cntr ; i++) {
    		if (chkLine !=1) {
    			lineList.add(Integer.valueOf(chkLine));
    		} else {
    			chkLine = + 1;
    			lineList.add(Integer.valueOf(chkLine));
    		}
    		chkLine = chkLine - lineNumPerThread;
    	}
    	return lineList;
    }
    
  //MappingAndStartThread(strings,lineForEachThread,noOfThread);
    public static void MappingAndStartThread(ArrayList<String> strings,ArrayList<Integer> ints,int noOT) {
    	System.out.println("No Of Line : " + strings.size());
    	System.out.println("Line For Each Thread : " + ints);
    	System.out.println("No of Thread : " + noOT);
    	ArrayList<String> chunkString = new ArrayList<String>();
    	String tType = "sub";
    	for(int i = 0 ; i < noOT ; i++) {
    		chunkString.clear();
    		String threadName = "Thread-" + (i+1); 
    		int endRange = ints.get(i)-1;
    		int startRange = ints.get(i + 1);
    		for(int se = startRange; se <= endRange; se++) {
    			chunkString.add(strings.get(se));
    		}
    		String listString = "";
        	for (String s : chunkString)
            {
                listString += s;
            }
    		System.out.println(threadName + ": " + "start " + startRange + " end " + endRange);
    		th[i] = new MultiThreadWordCount(i,tType,listString,threadName);
    		th[i].start();
    	}
    	for(int i = 0 ; i < noOT ; i++) {
    		th[i].chkTS();
    	}
    }
}
