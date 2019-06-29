package game.entity;

import game.resources.Animations;
import nightingale.graph.animation.NAnimation;

public enum SpellType {
	Fireball (18, 30, Animations.fireball, 0.08f, false);
	
	private NAnimation anim;
	private float animSpeed;
	private int width, height;
	private boolean special;
	
	public NAnimation getAnimation() { return anim; }
	public float getAnimationSpeed() { return animSpeed; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public boolean isSpecial() { return special; }
	
	private SpellType(int width, int height, NAnimation anim, float animSpeed, boolean special) {
		this.width = width;
		this.height = height;
		this.anim = anim;
		this.animSpeed = animSpeed;
		this.special = special;
	}
}