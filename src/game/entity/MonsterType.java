package game.entity;

import game.resources.Sprites;
import nightingale.graph.animation.NSprite;

public enum MonsterType {
	Doll(0, Sprites.doll, 34, 43, 0),
	Goblin(1, Sprites.goblin, 40, 57, 0),
	GoblinBoss(2, Sprites.goblinBoss, 60, 85, -0.4f);
	
	private int type;
	private NSprite sprite;
	private int width, height;
	private float v;
	
	public     int   getType() { return   type; }
	public NSprite getSprite() { return sprite; }
	public     int  getWidth() { return  width; }
	public     int getHeight() { return height; }
	public   float         V() { return      v; }
	
	MonsterType(int type, NSprite sprite, int width, int height, float v) { 
		this.type = type;
		this.sprite = sprite;
		this.width = width;
		this.height = height;
		this.v = v;
	}
}