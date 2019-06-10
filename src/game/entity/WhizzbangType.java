package game.entity;

import game.resources.Animations;
import nightingale.graph.animation.NAnimation;

public enum WhizzbangType {
	Crystal (10, 16, Animations.test, 0.08f);
	
	private NAnimation anim;
	private float animSpeed;
	private int width, height;
	
	public NAnimation getAnimation() { return anim; }
	public float getAnimationSpeed() { return animSpeed; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	
	private WhizzbangType(int width, int height, NAnimation anim, float animSpeed) {
		this.width = width;
		this.height = height;
		this.anim = anim;
		this.animSpeed = animSpeed;
	}
	
}