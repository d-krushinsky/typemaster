package game.state;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import game.TypeMaster;
import game.resources.Fonts;
import game.resources.Images;
import game.state.listener.ModeSelectionListener;
import nightingale.state.NState;
import nightingale.ui.NActionListener;
import nightingale.ui.NButton;
import nightingale.ui.NLabel;
import nightingale.ui.NUIElement;
import nightingale.ui.NUIGroup;

public class ModeSelection implements NState{

	protected NActionListener listener = new ModeSelectionListener(this);
	
	public NUIGroup ui = new NUIGroup();
	public NUIGroup ssUI = new NUIGroup(); //Survive settings user interface
	
	public boolean survive = false;
	
	public static int speed = 1;
	
	public ModeSelection() {
		ui.addElement("TITLE", new NLabel("PLAY", 0, 40, 160, 40));
		ui.addElement("SURVIVE", new NButton("Survive", 0, 140, 110, 50));
		ui.addElement("TRAINING", new NButton("Training", 0, 200, 125, 50));
		ui.addElement("BACK", new NButton("Back", 0, 300, 70, 40));
		ssUI.addElement("Back_to_selection", new NButton("Back", 600, 340, 70, 40));
		ssUI.addElement("play", new NButton("Play", 450, 240, 80, 50));
		ssUI.addElement("speed", new NLabel("Speed: "+speed, 95, 100, 190, 25));
		ssUI.addElement("down_speed", new NButton("Easier", 95, 130, 90, 40));
		ssUI.addElement("up_speed", new NButton("Harder", 195, 130, 90, 40));
		
		ui.setActionListener(listener);
		ui.setCamera(TypeMaster.uiCamera);
		ssUI.setActionListener(listener);
		ssUI.setCamera(TypeMaster.uiCamera);
		
		ui.getElements().forEach( (element) -> { 
			if(element instanceof NButton) { 
				((NButton)element).setFont(Fonts.uiFont);
				((NButton)element).setImages(Images.pressedButton, Images.focusedButton, Images.calmButton);
			}else if(element instanceof NLabel) {
				((NLabel)element).setFont(Fonts.uiFont);
			}
		} );
		
		ssUI.getElements().forEach( (element) -> { 
			if(element instanceof NButton) { 
				((NButton)element).setFont(Fonts.uiFont);
				((NButton)element).setImages(Images.pressedButton, Images.focusedButton, Images.calmButton);
			}else if(element instanceof NLabel) {
				((NLabel)element).setFont(Fonts.uiFont);
			}
		} );
	}
	
	@Override
	public void install() {
		TypeMaster.in.typingOff();
		for(NUIElement element : ui.getElements()) {
			element.setX(TypeMaster.canvas.getWidth()/2-element.getWidth()/2);
		}
		for(NUIElement element : ssUI.getElements()) {
			element.setX(TypeMaster.canvas.getWidth()/2-element.getWidth()/2);
		}
		survive = false;
		ssUI.getElement("down_speed").setX(ssUI.getElement("speed").getX());
		ssUI.getElement("up_speed").setX(
			ssUI.getElement("speed").getX()+ssUI.getElement("speed").getWidth() - ssUI.getElement("up_speed").getWidth());
	}

	@Override
	public void update() {
		if(!survive)
			ui.perform(TypeMaster.in);
		else
			ssUI.perform(TypeMaster.in);
	}

	@Override
	public void draw(Graphics g, Graphics2D g2d, AffineTransform at) {
		if(!survive)
			ui.draw(g);
		else
			ssUI.draw(g);
	}
	
}