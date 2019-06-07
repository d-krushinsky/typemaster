package game.state;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import game.TypeMaster;
import game.state.listener.SettingsListener;
import nightingale.state.NState;
import nightingale.ui.NActionListener;
import nightingale.ui.NButton;
import nightingale.ui.NLabel;
import nightingale.ui.NUIGroup;

public class Settings implements NState{
	// DEFAULTS
	public static final int DEFAULT_WIDTH  = 800;
	public static final int DEFAULT_HEIGHT = 600;
	
	// SETTINGS
	public static Dimension resolution;
	{
		resolution = new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	protected NActionListener listener = new SettingsListener();
	public NUIGroup ui = new NUIGroup();
	
	public Settings() {		
		ui.addElement("RESOLUTION_LABEL",
				new NLabel("Resolution: "+resolution.width+"x"+resolution.height,
						40, 60, 200, 30));
		ui.addElement("PREV_RESO", new NButton("Prev", 250, 60, 100, 30));
		ui.addElement("NEXT_RESO", new NButton("Next", 360, 60, 100, 30));

		ui.addElement("BACK", new NButton("Back", 400, 400, 80, 50));
		
		ui.setActionListener(listener);
	}
	
	@Override
	public void install() {}

	@Override
	public void update() {
		ui.perform(TypeMaster.in);
	}

	@Override
	public void draw(Graphics g, Graphics2D g2d, AffineTransform at) {
		ui.draw(g, g2d, at);
	}
	
}
