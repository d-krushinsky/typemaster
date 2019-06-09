package game.entity;

import java.awt.Graphics2D;

import nightingale.game.NGameObject;
import nightingale.util.NCamera;

public abstract class Entity extends NGameObject{
	protected int type;
	protected int hp;
	
	public abstract void update();
	public abstract void draw(Graphics2D g2d, NCamera cam);
}
