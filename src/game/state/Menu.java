package game.state;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import game.TypeMaster;
import game.state.listener.MenuListener;
import nightingale.state.NState;
import nightingale.ui.NActionListener;
import nightingale.ui.NButton;
import nightingale.ui.NLabel;
import nightingale.ui.NUIElement;
import nightingale.ui.NUIGroup;

public class Menu implements NState{
	protected NActionListener listener = new MenuListener();
	
	public NUIGroup ui = new NUIGroup();
	
	public Menu() {
		ui.addElement("TITLE", new NLabel("Type Master", 0, 30, 100, 30));
		ui.addElement("PLAY", new NButton("Play", 0, 100, 100, 40));
		ui.addElement("SETTINGS", new NButton("Options", 0, 200, 100, 40));
		ui.addElement("CREDITS", new NButton("Credits", 0, 300, 100, 40));
		ui.addElement("EXIT", new NButton("Quit", 0, 400, 100, 40));
		
		ui.setActionListener(listener);
	}
	
	@Override
	public void install() {
		System.out.println(TypeMaster.canvas.getWidth());
		for(NUIElement element : ui.getElements()) {
			element.setX(TypeMaster.canvas.getWidth()/2-element.getWidth()/2);
		}
	}

	@Override
	public void update() {
		ui.perform(TypeMaster.in);
	}

	@Override
	public void draw(Graphics g, Graphics2D g2d, AffineTransform at) {
		ui.draw(g, g2d, at);
	}
	
}
