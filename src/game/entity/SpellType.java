package game.entity;

import game.resources.Animations;
import nightingale.graph.animation.NAnimation;

public enum SpellType {
	Fireball (18, 30, Animations.fireball, 0.08f),
	MagicMissile (14, 30, Animations.magicmissile, 0.09f);
	
	private NAnimation anim;
	private float animSpeed;
	private int width, height;
	
	public NAnimation getAnimation() { return anim; }
	public float getAnimationSpeed() { return animSpeed; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	
	private SpellType(int width, int height, NAnimation anim, float animSpeed) {
		this.width = width;
		this.height = height;
		this.anim = anim;
		this.animSpeed = animSpeed;
	}
}