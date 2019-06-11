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
	
	//DELETE!!!!!!!!
	private boolean survive = false;
	
	public Wizard(boolean survive) {
		this.survive = survive;
	}
	
	@Override
	public void update() {
		if(animator != null)
			animator.update();
	}

	@Override
	public void draw(Graphics2D g2d, NCamera cam) {
		if(survive) {
			g2d.drawImage(Images.wizardSurvive, (int)getX(cam), (int)getY(cam), (int)getWidth(cam), (int)getHeight(cam), null);
		}else {
			g2d.drawImage(Images.wizardTraining, (int)getX(cam), (int)getY(cam), (int)getWidth(cam), (int)getHeight(cam), null);
		}
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
