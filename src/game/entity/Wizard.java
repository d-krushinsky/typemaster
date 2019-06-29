package game.entity;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import game.resources.Images;
import nightingale.graph.animation.NAnimator;
import nightingale.graph.animation.NSprite;
import nightingale.util.NCamera;

public class Wizard extends Entity {
	protected NSprite sprite;
	protected NAnimator animator;
	
	public Wizard(WizardType type) {
		this.type = type.getType();
		this.setWidth(type.getWidth());
		this.setHeight(type.getHeight());
		this.sprite = type.getSprite();
	}
	
	@Override
	public void update() {
		if(animator != null)
			animator.update();
	}

	@Override
	public void draw(Graphics2D g2d, NCamera cam) {
		g2d.drawImage(Images.wizardSurvive, (int)getX(cam), (int)getY(cam), (int)getWidth(cam), (int)getHeight(cam), null);

		/*
		if(sprite != null) {
			animator.draw((int)this.getX(), (int)this.getY(), g2d, cam);
		}else {
			g2d.drawRect(
					(int)getX(cam),
					(int)getY(cam),
					(int)getWidth(cam),
					(int)getHeight(cam));
		}
		*/
	}

	@Override
	public void draw(Graphics2D g2d, NCamera cam, AffineTransform at) {}
	
}
