package game.entity;

import nightingale.graph.animation.NSprite;

public enum MonsterType {
	Doll(0, null, 40, 45),
	Goblin(1, null, 25, 38);
	
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