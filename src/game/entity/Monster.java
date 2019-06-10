package game.entity;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import nightingale.graph.animation.NAnimator;
import nightingale.graph.animation.NSprite;
import nightingale.util.NCamera;

public class Monster extends Entity {
	protected NSprite sprite;
	protected NAnimator animator;
	
	private String name = "";
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Monster(MonsterType type, String name) {
		this.type = type.getType();
		this.name = name;
		setWidth(type.getWidth());
		setHeight(type.getHeight());
	}
	
	public void update() {
		if(animator != null)
			animator.update();
	}

	@Override
	public void draw(Graphics2D g2d, NCamera cam) {
		if(sprite != null) {
			animator.draw((int)this.getX(), (int)this.getY(), g2d, cam);
		}else {
			g2d.drawRect(
					(int)getX(cam),
					(int)getY(cam),
					(int)getWidth(cam),
					(int)getHeight(cam));
		}
	}
	
	@Override
	public void draw(Graphics2D g2d, AffineTransform at) {}
}
