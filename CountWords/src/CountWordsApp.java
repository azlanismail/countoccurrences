import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class CountWordsApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//set the path for input file
		String inputfile = "inputfile.txt";
	
		//prepare the lists
		ArrayList<String> inline = new ArrayList<String>();
		
		//prepare the hashmap
		HashMap<String, Integer> wordMap = new LinkedHashMap<String, Integer>();
		
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
		
		//split each line into word-by-word and count the occurrences
		for(int i=0; i < inline.size(); i++) {
			//to read line by line
			String input = inline.get(i);
			
			//to split the string on whitespace using regular expression "\\s+"
			String[] words = input.split("\\s+");
	
			//start counting the occurences
		    for (String word : words) {
		         wordMap.put(word,
		           (wordMap.get(word) == null ? 1 : (wordMap.get(word) + 1)));
		    }
		}
	    //display the result
		System.out.println("The results :");
		for (String name: wordMap.keySet()){
             String key =name.toString();
             String value = wordMap.get(name).toString();  
             System.out.println(key + " = " + value);  
       } 
	//	System.out.println(wordMap);		
	}
	
}
