package game.resources;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import util.Random;

public class Words {
	private static String[] words;
	
	static {
		FileReader fr;
		Scanner scan;
		try {
			fr = new FileReader("data/words.tmr");
			scan = new Scanner(fr);
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
