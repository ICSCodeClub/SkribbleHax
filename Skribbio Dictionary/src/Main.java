import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws FileNotFoundException{
		File f = new File("list.txt");
		Scanner lstSc = new Scanner(f);
		
		ArrayList<String> words = new ArrayList<String>();
		
		while(lstSc.hasNext()) {
			words.add(lstSc.next());
		}
		
		String userInput = "___s_t__p";
		
		ArrayList<String> answer = findAnswer(userInput, words);
		
		printArrayList(answer);
	}
	
	public static ArrayList<String> findAnswer(String input, ArrayList<String> words){
		ArrayList<String> answer = new ArrayList<String>();
		for(int i = 0; i < words.size(); i++) {
			if(input.length() == words.get(i).length()) {
				String str = words.get(i);
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

}
