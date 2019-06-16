package game.entity;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import game.resources.Images;
import nightingale.util.NCamera;

public class Castle extends Entity {
	
	protected BufferedImage image;
	
	public Castle() {
		this.image = Images.castle;
	}
	
	public void update() {
		
	}
	
	public int HP() { return this.hp; }
	public int HP(int hp) {
		this.hp = hp;
		return this.hp;
	}
	
	public void draw(Graphics2D g2d, NCamera cam) {
		if(image != null) {
			g2d.drawImage(image, (int)getX(cam), (int)getY(cam), (int)getWidth(cam), (int)getHeight(cam), null);
		}else {
			g2d.drawRect(
					(int)getX(cam),
					(int)getY(cam),
					(int)getWidth(cam),
					(int)getHeight(cam));
		}
	}
	
	@Override
	public void draw(Graphics2D g2d, NCamera cam, AffineTransform at) {}
	
}
