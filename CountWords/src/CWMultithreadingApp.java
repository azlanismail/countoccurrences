import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class CWMultithreadingApp extends Thread{
          private static Thread t;
          private String threadName;
          private String[] perkataan;
          private int start,end;
         // private static long startTime;  // = System.curr    nanoTime();
        //prepare the hashmap
          public static HashMap<String, Integer> wordMap = new LinkedHashMap<String, Integer>();
          //public static HashMap<String, Integer> wordMap1 = new LinkedHashMap<String, Integer>();
          //public static HashMap<String, Integer> wordMap2 = new LinkedHashMap<String, Integer>();
        
                      
          CWMultithreadingApp(String name, String[] words, int start, int end) {
             this.threadName = name;
             System.out.println("Creating " +  threadName );
             this.perkataan = words;
             this.start = start;
             this.end = end;
             
          }
          public void start () {
               System.out.println("Starting " +  threadName );
               if (t == null) {
                   t = new Thread (this, threadName);
                   t.start ();
               }
          }
         
          public void run() {
        	 //long startTime = System.nanoTime();
               System.out.println("Running " +  threadName );
               //if(thread i)
               for (int i = start; i<=end;i++)
               {
               //System.out.println(threadName + perkataan[i] );
               
         		//start counting the occurences
               }for (String word : perkataan) {
         		         wordMap.put(word,(wordMap.get(word) == null ? 1 : (wordMap.get(word) + 1)));
         		    }
         		    
              
             
         		
               System.out.println("Thread " +  threadName + " exiting.");
               
             }
          
          public static void main(String[] args) {
      		// TODO Auto-generated method stub

      		//set the path for input file
      		//String inputfile = "C:\\Users\\AkashiLegacy\\workspace\\Parallel Project\\parallelWord\\inputfile.txt";
      	
      	    //set the path for input file
    		String inputfile = "inputfile1000.txt";
      		
      	//prepare the lists
          ArrayList<String> inline = new ArrayList<String>();
      		
      		
      		
      		//prepare object for reading
      		Scanner read = null;
      		try {
      			read = new Scanner(new BufferedReader(new FileReader(inputfile)));
      		} catch (FileNotFoundException e) {
      			// TODO Auto-generated catch block
      			e.printStackTrace();
      		}
      		
      		//prepare the string variable
      		String dataIn = null;
      		
      		//read line by line from the file and store into lists
      		while (read.hasNextLine()) {
      			dataIn = read.nextLine();
      			inline.add(dataIn);
      			
      		}
      		
      		//close the file
      		read.close();
      		
      		String[] words= new String[inline.size()];
     		
     		for(int i=0; i < inline.size(); i++) {
    			//to read line by line
    			String input = inline.get(i);
    			//vSystem.out.println(" line"+(i)+ " = "+ inline.get(i));
    			//to split the string on whitespace using regular expression "\\s+"
    			words = input.split("\\s+");
     		
     		}
      		
     		
     		for(int i=0;i<words.length;i++){
     		//System.out.println(words[i]);
     		
     		}
      		//partition
     		
     		
           //looping
     		CWMultithreadingApp [] th = new CWMultithreadingApp[8];
            long startTime = System.currentTimeMillis();
            
            for(int i=0; i < th.length; i++)  {
                th[i] = new CWMultithreadingApp("Threads "+i+"  = ",words, i*(words.length/th.length), (i+1)*(words.length/th.length));
                th[i].start();
            }
            
            
            try {
                t.join();
            } catch (InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            long endTime   = System.currentTimeMillis(); // nanoTime();
            long totalTime = endTime - startTime;
          //display the result
     		
     		System.out.println("The results :");
     		for (String name: wordMap.keySet()){
     			String key =name.toString();
     			String value = wordMap.get(name).toString();  
     			System.out.println(key + " = " + value);  
     		} 
            
            System.out.println("Total time taken for program to run is = "+totalTime+" millisecond"); 
          }
}