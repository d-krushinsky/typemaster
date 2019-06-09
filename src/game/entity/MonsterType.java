package game.entity;

import nightingale.graph.animation.NSprite;

public enum MonsterType {
	Doll(0, null),
	Goblin(1, null);
	
	private int type;
	private NSprite sprite;
	
	public     int   getType() { return   type; }
	public NSprite getSprite() { return sprite; }
	
	MonsterType(int type, NSprite sprite) { 
		this.type = type;
		this.sprite = sprite;
	}
}