package game.resources;

import nightingale.graph.animation.NAnimation;
import nightingale.graph.animation.NAnimator;

public class Animations {

	public static NAnimation test = new NAnimation("/res/img/anima/test.png", 16);
	static {
		System.out.println(test.getFramesCount());
	}
	
	public static NAnimator testAnimator = new NAnimator(test, 0.05f);
	
}
