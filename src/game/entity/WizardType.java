package game.entity;

import nightingale.graph.animation.NSprite;

public enum WizardType {
	Test(0, null, 45, 64);
	
	private int type;
	private NSprite sprite;
	private int width, height;
	
	public     int   getType() { return   type; }
	public NSprite getSprite() { return sprite; }
	public     int  getWidth() { return  width; }
	public     int getHeight() { return height; }
	
	WizardType(int type, NSprite sprite, int width, int height) { 
		this.type = type;
		this.sprite = sprite;
		this.width = width;
		this.height = height;
	}
}
