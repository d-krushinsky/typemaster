package game.state;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import game.Input;
import game.TypeMaster;
import game.resources.Fonts;
import game.resources.Sprites;
import game.state.listener.ModeSelectionListener;
import nightingale.state.NState;
import nightingale.ui.NActionListener;
import nightingale.ui.NButton;
import nightingale.ui.NLabel;
import nightingale.ui.NUIElement;
import nightingale.ui.NUIGroup;

public class ModeSelection implements NState{

	protected NActionListener listener = new ModeSelectionListener();
	
	public NUIGroup ui = new NUIGroup();
	
	public ModeSelection() {
		ui.addElement("TITLE", new NLabel("PLAY", 0, 40, 160, 40));
		ui.addElement("SURVIVE", new NButton("Survive", 0, 140, 90, 50));
		ui.addElement("TRAINING", new NButton("Training", 0, 200, 105, 50));
		ui.addElement("BACK", new NButton("Back", 0, 300, 70, 40));
		
		ui.setActionListener(listener);
		ui.setCamera(TypeMaster.uiCamera);
		
		ui.getElements().forEach( (element) -> { 
			if(element instanceof NButton) { 
				((NButton)element).setFont(Fonts.uiFont);
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
		Sprites.testAnimator.start();
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