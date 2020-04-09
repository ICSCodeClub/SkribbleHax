package main.src.main;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

public final class USDictionary {
	public static ArrayList<String> loadDictionary(){
		return loadDictionary(true);
	}
    public static ArrayList<String> loadDictionary(boolean tolerateErrors){
        ArrayList<String> words = new ArrayList<String>();
        try {
	        BufferedReader br = new BufferedReader(new FileReader(dictPath));
	        String line;
	        while((line = br.readLine()) != null) words.add(line);
	        br.close();
        } catch (FileNotFoundException e) {
            System.out.println("Dictionary not found, attempting to download");
            //if the file isn't found, we might need to download it
            downloadDictionary();
            //retry with no tolerance if it had tolerance
            if(tolerateErrors)
            	return loadDictionary(false);
            else
            	e.printStackTrace();
        } catch (IOException e) {
	        e.printStackTrace();
	        //on an IO exception, simply retry once
	        if(tolerateErrors)
	        	return loadDictionary(false);
        }
        return words;
    }
	
	
	private static String dictPath = System.getProperty("java.io.tmpdir")+File.separator+"americanWords.txt";
	private static void downloadDictionary() {
        try {
            InputStream is = new URL("https://raw.githubusercontent.com/dwyl/english-words/master/words.txt").openStream();
            Files.copy(is, Paths.get(dictPath),StandardCopyOption.REPLACE_EXISTING);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }        
    }
}
