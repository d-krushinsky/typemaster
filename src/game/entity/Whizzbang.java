package game.entity;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import nightingale.graph.animation.NAnimation;
import nightingale.graph.animation.NAnimator;
import nightingale.util.NCamera;

public class Whizzbang extends Entity{

	private Monster target;
	private NAnimation animation;
	private NAnimator animator;
	
	// For moving
	private float angle = 0;
	private float speed = 3;
	
	@Override
	public void update() {
		if(animator != null) animator.update();
		//MOVE TO TARGET
	}
	
	public Whizzbang(Monster target, WhizzbangType type, int x, int y) {
		animation = type.getAnimation();
		setNObjectAtributes(x, y, type.getWidth(), type.getHeight());
		this.target = target;
		animator = new NAnimator(animation, type.getAnimationSpeed());
		if(animator != null) animator.start();
	}
	
	@Override
	public void draw(Graphics2D g2d, NCamera cam) {
		g2d.drawRect((int)getX(), (int)getY(), (int)getWidth(), (int)getHeight());
	}
	@Override
	public void draw(Graphics2D g2d, AffineTransform at) {
		animator.draw(g2d, at);
	}

}
