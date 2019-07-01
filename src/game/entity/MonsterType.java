package game.entity;

import game.resources.Sprites;
import nightingale.graph.animation.NSprite;

public enum MonsterType {
	Doll(0, Sprites.doll, 34, 43),
	Goblin(1, Sprites.goblin, 40, 57),
	GoblinBoss(2, Sprites.goblinBoss, 60, 85);
	
	private int type;
	private NSprite sprite;
	private int width, height;
	
	public     int   getType() { return   type; }
	public NSprite getSprite() { return sprite; }
	public     int  getWidth() { return  width; }
	public     int getHeight() { return height; }
	
	MonsterType(int type, NSprite sprite, int width, int height) { 
		this.type = type;
		this.sprite = sprite;
		this.width = width;
		this.height = height;
	}
}