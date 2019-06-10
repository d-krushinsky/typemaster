package game.entity;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import nightingale.game.NGameObject;
import nightingale.util.NCamera;

public abstract class Entity extends NGameObject{
	protected int type;
	protected int hp;
	
	public void setNObjectAtributes(int x, int y, int width, int height) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
	}
	
	protected void setCoordsByCenter(float cx, float cy) {
		setX(cx - getWidth()/2);
		setY(cy - getHeight()/2);
	}
	
	public abstract void update();
	public abstract void draw(Graphics2D g2d, NCamera cam);
	public abstract void draw(Graphics2D g2d, NCamera cam, AffineTransform at);
}
