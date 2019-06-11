package game.state;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import game.TypeMaster;
import game.resources.Fonts;
import game.resources.Images;
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
		ui.addElement("SETTINGS", new NButton("Options", 0, 160, 100, 40));
		ui.addElement("CREDITS", new NButton("Credits", 0, 220, 100, 40));
		ui.addElement("EXIT", new NButton("Quit", 0, 280, 100, 40));
		
		ui.setCamera(TypeMaster.uiCamera);
		ui.setActionListener(listener);
		
		ui.getElements().forEach( (element) -> { 
			if(element instanceof NButton) { 
				((NButton)element).setFont(Fonts.uiFont);
				((NButton)element).setImages(Images.pressedButton, Images.focusedButton, Images.calmButton);
			}else if(element instanceof NLabel) {
				((NLabel)element).setFont(Fonts.extraFont);
			}
		} );
	}
	
	@Override
	public void install() {
		TypeMaster.in.typingOff();
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
		ui.draw(g);
	}
	
}