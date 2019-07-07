package game.resources;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import util.Random;

public class Words {
	private static String[] words;
	
	static {
		Scanner scan;
		try {
			InputStream in = Words.class.getResourceAsStream("/data/words.tmr"); 
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			scan = new Scanner(reader);
			List<String> wordsList = new ArrayList<String>();
			while(scan.hasNextLine()) {
				String[] wr = scan.nextLine().split(",");
				for(String word : wr)
					wordsList.add(word.trim());
			}
			words = new String[wordsList.size()];
			for(int i=0;i<words.length;i++)
				words[i] = wordsList.get(i);
		}catch(Exception e){
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public static String getRandomWord() {
		return words[Random.randomInt(words.length)];
	}
}
