package game.entity;

import nightingale.graph.animation.NAnimation;

public enum WhizzbangType {
	Crystal (null, 0.0f);
	
	private NAnimation anim;
	private float animSpeed;
	
	public NAnimation getAnimation() { return anim; }
	public float getAnimationSpeed() { return animSpeed; }
	
	private WhizzbangType(NAnimation anim, float animSpeed) {
		this.anim = anim;
		this.animSpeed = animSpeed;
	}
	
}
