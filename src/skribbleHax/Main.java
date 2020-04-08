package skribbleHax;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
	public static final boolean useUSDict = false;
	
	public static void main(String[] args) throws FileNotFoundException{
		setup();
		
		String userInput = "___s_t__p";
		
		ArrayList<String> answer = findAnswer(userInput);
		
		printArrayList(answer);
	}
	
	private static HashMap<Integer, ArrayList<String>> words;
	public static void setup() {
		if(words != null) return;
		
		if(useUSDict) setupWithUSDict();
		else setupWithLocalList();
	}
	
	public static ArrayList<String> findAnswer(String input){
		setup();
		
		ArrayList<String> wordsOfLength = words.get(input.length());
		if(wordsOfLength == null) return new ArrayList<String>();
		
		ArrayList<String> answer = new ArrayList<String>();
		for(int i = 0; i < wordsOfLength.size(); i++) {
			if(input.length() == wordsOfLength.get(i).length()) {
				String str = wordsOfLength.get(i);
				boolean same = true;
				int j = 0;
				while(j < input.length() && same) {
					if(input.charAt(j) != '_') {
						if(input.charAt(j) != str.charAt(j)) same = false;
					}
					j++;
				}
				
				if(same) answer.add(str);
			}
		}
		
		return answer;	
	}
	
	public static void printArrayList(ArrayList<String> list) {
		for(int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
	}
	
	private static void setupWithLocalList() {
		if(words == null) try {
			words = new HashMap<Integer, ArrayList<String>>();
			Scanner lstSc = new Scanner(new File("list.txt"));
			while(lstSc.hasNext()) {
				String word = lstSc.next();
				ArrayList<String> list = words.getOrDefault(word.length(), new ArrayList<String>());
				list.add(word);
				if(!words.containsKey(word.length())) words.put(word.length(), list);
			}
			lstSc.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			words = null;
		}
	}
	private static void setupWithUSDict() {
		ArrayList<String> dict = USDictionary.loadDictionary();
		if(dict == null) return;
		
		words = new HashMap<Integer, ArrayList<String>>();
		for(String word : dict) {
			ArrayList<String> list = words.getOrDefault(word.length(), new ArrayList<String>());
			list.add(word);
			if(!words.containsKey(word.length())) words.put(word.length(), list);
		}
	}
}
