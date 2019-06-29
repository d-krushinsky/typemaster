package game.entity;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import nightingale.game.NGameObject;
import nightingale.util.NCamera;

public abstract class Entity extends NGameObject{
	protected int type;
	protected int hp;
	protected boolean delete = false;
	
	public boolean shoudDelete() { return delete; }
	public void setDeletable(boolean deletable) { this.delete = deletable; }
	
	public void setNObjectAtributes(int x, int y, int width, int height) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
	}
	
	public abstract void update();
	public abstract void draw(Graphics2D g2d, NCamera cam);
	public abstract void draw(Graphics2D g2d, NCamera cam, AffineTransform at);
}
