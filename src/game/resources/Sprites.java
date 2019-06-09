package game.resources;

import nightingale.graph.animation.NAnimator;
import nightingale.graph.animation.NSprite;

public class Sprites {

	public static NSprite test = new NSprite(
		"/res/img/sprites/test.png",
		new int[]{ 2, 1 }, 
		16, 16);
	
	public static NAnimator testAnimator = new NAnimator(test, 0.3f);
	
}
