package game.state;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import nightingale.state.NState;
import nightingale.ui.NActionListener;
import nightingale.ui.NUIGroup;

public class Game implements NState{

	protected NActionListener listener;
	
	public NUIGroup ui = new NUIGroup();
	
	@Override
	public void install() {}

	@Override
	public void update() {
		
	}

	@Override
	public void draw(Graphics g, Graphics2D g2d, AffineTransform at) {
		ui.draw(g, g2d, at);
	}
	
}