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
	
	@Override
	public void update() {
		animator.update();
		//MOVE TO TARGET
	}
	
	public Whizzbang(Monster target, WhizzbangType type, int x, int y) {
		animation = type.getAnimation();
		setX(x);
		setY(y);
		this.target = target;
		animator = new NAnimator(animation, type.getAnimationSpeed());
		animator.start();
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
