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
	
	public static float randomFloat(float start, float end) {
		float integer = randomInt((int)start, (int)end);
		float decimal = (randomInt(10)+0.0f)/10;
		if(integer+decimal < start) {
			return start;
		}else if(integer+decimal > end) {
			return end;
		}
		return integer+decimal;
	}
}
