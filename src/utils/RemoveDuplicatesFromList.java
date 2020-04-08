package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class RemoveDuplicatesFromList {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner file = new Scanner(new File("list.txt"));
		
		//use a hashmap so we can sort by length
		HashMap<Integer, ArrayList<String>> words = new HashMap<Integer, ArrayList<String>>();
		while(file.hasNext()) {
			String word = file.next();
			ArrayList<String> list = words.getOrDefault(word.length(), new ArrayList<String>());
			if(!list.contains(word)) list.add(word); //remove duplicates
			words.put(word.length(), list);
		}
		file.close();
		
		PrintStream output = new PrintStream(new File("list.txt")); 
		for(ArrayList<String> w : words.values()) {
			//sort, because why not
			w.sort(null);
			
			//print it to the file
			for(String str : w) output.println(str);
			output.println();
		}
	}
}
