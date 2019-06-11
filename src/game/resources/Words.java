package game.resources;

import util.Random;

public class Words {

	private static String[] words = new String[] {
			"Josh",
			"Bob",
			"Joshua",
			"Valera",
			"Dmitriy",
			"Gladiator",
			"Patau",
			"Girl",
			"Maslo",
			"Dick",
			"Serega"
	};
	
	public static String getRandomWord() {
		return words[Random.randomInt(words.length)];
	}
	
}
