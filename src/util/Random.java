package util;

public class Random {

	private static java.util.Random random = new java.util.Random();
	
	public static int randomInt(int end) {
		return random.nextInt(end);
	}
	
	public static int randomInt(int start, int end) {
		return start+random.nextInt(end-start);
	}
	
	public static double randomDouble(int start, int end) {
		return start+random.nextInt(end-start)+random.nextDouble();
	}
	
}
