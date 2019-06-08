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
import util.Ring;

public class Settings implements NState{
	// DEFAULTS
	public static final int DEFAULT_WIDTH  = 800;
	public static final int DEFAULT_HEIGHT = 600;
	
	//SETTINGS CASES
	public static final Ring<Dimension> resolutions = new Ring<Dimension>();
	static {
		resolutions.add(new Dimension(800, 600));
		resolutions.add(new Dimension(720, 480));
		resolutions.add(new Dimension(1024, 600));
		resolutions.add(new Dimension(1024, 768));
		resolutions.add(new Dimension(1280, 720));
	}
	
	// SETTINGS
	public static Dimension resolution;
	static {
		resolution = new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	
	// NState starts here
	protected NActionListener listener = new SettingsListener();
	public NUIGroup ui = new NUIGroup();
	
	public Settings() {	
		ui.addElement("RESOLUTION_LABEL",
				new NLabel("Resolution: "+resolution.width+"x"+resolution.height,
						40, 60, 200, 30));
		ui.addElement("PREV_RESO", new NButton("Prev", 250, 60, 70, 30));
		ui.addElement("NEXT_RESO", new NButton("Next", 330, 60, 70, 30));
		ui.addElement("BACK", new NButton("Back", 460, 500, 80, 50));
		
		ui.setActionListener(listener);
		ui.setCamera(TypeMaster.uiCamera);
	}
	
	@Override
	public void install() {}

	@Override
	public void update() {
		ui.perform(TypeMaster.in);
		//update labels
		((NLabel)ui.getElement("RESOLUTION_LABEL")).setText("Resolution: "+resolution.width+"x"+resolution.height);
	}

	@Override
	public void draw(Graphics g, Graphics2D g2d, AffineTransform at) {
		ui.draw(g, g2d, at);
	}
}
