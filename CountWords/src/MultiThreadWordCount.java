
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MultiThreadWordCount extends Thread{
	
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
    
    public static void main(String [] args) {
    	long startTime = System.nanoTime();
        String fileName = "inputfile10000.txt";
        ArrayList<String> strings = new ArrayList<String>();
        readFile(fileName,strings);
        String listString = listToString(strings);
        int wc = countWords(listString);
        ArrayList<Integer> lineForEachThread = PreMapping(strings.size(),noOfThread);
        MappingAndStartThread(strings,lineForEachThread,noOfThread);
        System.out.println("Global Word Count : "+wc);
        chkArr();
        long endTime = System.nanoTime();
        long duration = (endTime - startTime)/1000000;
        System.out.println("\nExecution time : "+ duration+" ms");
    }
    
    MultiThreadWordCount(int in,String ttype,String strings,String tName) {
    	this.gcs = strings;
    	this.ind = in;
    	this.tt = ttype;
        this.threadName = tName;
        System.out.println("Creating " +  threadName );
     }
    public static void chkArr() {
    	int nOt = noOfThread - 1;
    	for (int r = nOt ; r >= 0 ; r--) {
    			if(!th[r].a7.isEmpty()) {
        			for (String words : th[r].a7.keySet()) {
        	            int count = th[r].a7.get(words);
        	            if (sum.containsKey(words)) {
        	            	sum.put(words, sum.get(words) + count);
        	            } else {
        	            	sum.put(words,count);
                        }
        	        }
        		} else if(!th[r].a6.isEmpty()) {
        			for (String words : th[r].a6.keySet()) {
        	            int count = th[r].a6.get(words);
        	            if (sum.containsKey(words)) {
        	            	sum.put(words, sum.get(words) + count);
        	            } else {
        	            	sum.put(words,count);
                        }
        	        }
        		} else if(!th[r].a5.isEmpty()) {
        			for (String words : th[r].a5.keySet()) {
        	            int count = th[r].a5.get(words);
        	            if (sum.containsKey(words)) {
        	            	sum.put(words, sum.get(words) + count);
        	            } else {
        	            	sum.put(words,count);
                        }
        	        }
        		} else if(!th[r].a4.isEmpty()) {
        			for (String words : th[r].a4.keySet()) {
        	            int count = th[r].a4.get(words);
        	            if (sum.containsKey(words)) {
        	            	sum.put(words, sum.get(words) + count);
        	            } else {
        	            	sum.put(words,count);
                        }
        	        }
        		} else if(r <= 3) {
    			if(!th[r].a3.isEmpty()) {
        			for (String words : th[r].a3.keySet()) {
        	            int count = th[r].a3.get(words);
        	            if (sum.containsKey(words)) {
        	            	sum.put(words, sum.get(words) + count);
        	            } else {
        	            	sum.put(words,count);
                        }
        	        }
        		} else if(!th[r].a2.isEmpty()) {
        			for (String words : th[r].a2.keySet()) {
        	            int count = th[r].a2.get(words);
        	            if (sum.containsKey(words)) {
        	            	sum.put(words, sum.get(words) + count);
        	            } else {
        	            	sum.put(words,count);
                        }
        	        }
        		} else if(!th[r].a1.isEmpty()) {
        			for (String words : th[r].a1.keySet()) {
        	            int count = th[r].a1.get(words);
        	            if (sum.containsKey(words)) {
        	            	sum.put(words, sum.get(words) + count);
        	            } else {
        	            	sum.put(words,count);
                        }
        	        }
        		} else if(!th[r].a0.isEmpty()) {
        			for (String words : th[r].a0.keySet()) {
        	            int count = th[r].a0.get(words);
        	            if (sum.containsKey(words)) {
        	            	sum.put(words, sum.get(words) + count);
        	            } else {
        	            	sum.put(words,count);
                        }
        	        }
        		}
    		}
    	}
        System.out.println("=======================\n"+"= For each word count = \n"+"=======================\n");
        System.out.println("COUNT "+"  |WORD\n"+"------------------------\n");
    	for (String words : sum.keySet()) {
            int count = sum.get(words);
            System.out.print(count + "\t|");
            System.out.println(words);
        }
    }
    
    public void chkTS() {
    	for(int i = 0 ; i < 1 ; i++) {
    		if (t.isAlive()== true) {
    			i = i - 1;
    		} 
    	}
     }
    
    public void start () {
        System.out.println("Starting " +  threadName );
        if (t == null) {
            t = new Thread (this, threadName);
            t.start ();
        }
    }
    
    public void run() {
    	if (tt=="sub") {
    		String chunkS = gcs;
    		int indi = ind;
    		String arT = "a"+indi;
    		System.out.println(arT);
        	System.out.println("Running " +  threadName );
        	String[] splitStr = chunkS.split(" ");
            for (String words : splitStr) {
            	int endOfLine = words.length() - 1;
                if (arT.equals("a0")) {
                	if (a0.containsKey(words)) {
                		a0.put(words, a0.get(words) + 1);
                	} else {
                    	boolean word = false;
                    	for (int i = 0; i < words.length(); i++) {
                    		char sntCat = words.charAt(i);
                    		if (Character.isLetter(sntCat) && i != endOfLine) {
                      			word = true;
                      		} else if (!Character.isLetter(sntCat) && word) {
                      			a0.put(words, 1);
                      			word = false;
                      		} else if (Character.isLetter(sntCat) && i == endOfLine) {
                      			a0.put(words, 1);
                      		}
                    	}
                    }
                } else if (arT.equals("a1")) {
                	if (a1.containsKey(words)) {
                		a1.put(words, a1.get(words) + 1);
                	} else {
                    	boolean word = false;
                    	for (int i = 0; i < words.length(); i++) {
                    		char sntCat = words.charAt(i);
                    		if (Character.isLetter(sntCat) && i != endOfLine) {
                      			word = true;
                      		} else if (!Character.isLetter(sntCat) && word) {
                      			a1.put(words, 1);
                      			word = false;
                      		} else if (Character.isLetter(sntCat) && i == endOfLine) {
                      			a1.put(words, 1);
                      		}
                    	}
                    }
                } else if (arT.equals("a2")) {
                	if (a2.containsKey(words)) {
                		a2.put(words, a2.get(words) + 1);
                	} else {
                    	boolean word = false;
                    	for (int i = 0; i < words.length(); i++) {
                    		char sntCat = words.charAt(i);
                    		if (Character.isLetter(sntCat) && i != endOfLine) {
                      			word = true;
                      		} else if (!Character.isLetter(sntCat) && word) {
                      			a2.put(words, 1);
                      			word = false;
                      		} else if (Character.isLetter(sntCat) && i == endOfLine) {
                      			a2.put(words, 1);
                      		}
                    	}
                    }
                } else if (arT.equals("a3")) {
                	if (a3.containsKey(words)) {
                		a3.put(words, a3.get(words) + 1);
                	} else {
                    	boolean word = false;
                    	for (int i = 0; i < words.length(); i++) {
                    		char sntCat = words.charAt(i);
                    		if (Character.isLetter(sntCat) && i != endOfLine) {
                      			word = true;
                      		} else if (!Character.isLetter(sntCat) && word) {
                      			a3.put(words, 1);
                      			word = false;
                      		} else if (Character.isLetter(sntCat) && i == endOfLine) {
                      			a3.put(words, 1);
                      		}
                    	}
                    }
                } else if (arT.equals("a4")) {
                	if (a4.containsKey(words)) {
                		a4.put(words, a4.get(words) + 1);
                	} else {
                    	boolean word = false;
                    	for (int i = 0; i < words.length(); i++) {
                    		char sntCat = words.charAt(i);
                    		if (Character.isLetter(sntCat) && i != endOfLine) {
                      			word = true;
                      		} else if (!Character.isLetter(sntCat) && word) {
                      			a4.put(words, 1);
                      			word = false;
                      		} else if (Character.isLetter(sntCat) && i == endOfLine) {
                      			a4.put(words, 1);
                      		}
                    	}
                    }
                } else if (arT.equals("a5")) {
                	if (a5.containsKey(words)) {
                		a5.put(words, a5.get(words) + 1);
                	} else {
                    	boolean word = false;
                    	for (int i = 0; i < words.length(); i++) {
                    		char sntCat = words.charAt(i);
                    		if (Character.isLetter(sntCat) && i != endOfLine) {
                      			word = true;
                      		} else if (!Character.isLetter(sntCat) && word) {
                      			a5.put(words, 1);
                      			word = false;
                      		} else if (Character.isLetter(sntCat) && i == endOfLine) {
                      			a5.put(words, 1);
                      		}
                    	}
                    }
                } else if (arT.equals("a6")) {
                	if (a6.containsKey(words)) {
                		a6.put(words, a6.get(words) + 1);
                	} else {
                    	boolean word = false;
                    	for (int i = 0; i < words.length(); i++) {
                    		char sntCat = words.charAt(i);
                    		if (Character.isLetter(sntCat) && i != endOfLine) {
                      			word = true;
                      		} else if (!Character.isLetter(sntCat) && word) {
                      			a6.put(words, 1);
                      			word = false;
                      		} else if (Character.isLetter(sntCat) && i == endOfLine) {
                      			a6.put(words, 1);
                      		}
                    	}
                    }
                } else if (arT.equals("a7")) {
                	if (a7.containsKey(words)) {
                		a7.put(words, a7.get(words) + 1);
                	} else {
                    	boolean word = false;
                    	for (int i = 0; i < words.length(); i++) {
                    		char sntCat = words.charAt(i);
                    		if (Character.isLetter(sntCat) && i != endOfLine) {
                      			word = true;
                      		} else if (!Character.isLetter(sntCat) && word) {
                      			a7.put(words, 1);
                      			word = false;
                      		} else if (Character.isLetter(sntCat) && i == endOfLine) {
                      			a7.put(words, 1);
                      		}
                    	}
                    }
                } 
            }
    	}
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
    
    /*
     * @Convert List To String
     */
    public static String listToString(ArrayList<String> strings) {
    	String listString = null;
    	for (String s : strings)
        {
            listString += s;
        }
    	return listString;
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
     * @Pre-Count Total Existing Word
     */
    public static int countWords(String s){
    	int wordCount = 0;

    	boolean word = false;
    	int endOfLine = s.length() - 1;

    	for (int i = 0; i < s.length(); i++) {
    		if (Character.isLetter(s.charAt(i)) && i != endOfLine) {
    			word = true;
    		} else if (!Character.isLetter(s.charAt(i)) && word) {
    			wordCount++;
    			word = false;
    		} else if (Character.isLetter(s.charAt(i)) && i == endOfLine) {
    			wordCount++;
    		}
    	}
    	return wordCount;
    }
}
