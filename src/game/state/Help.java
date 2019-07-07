package game.state;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import game.TypeMaster;
import game.resources.Fonts;
import game.resources.Images;
import game.state.listener.HelpListener;
import nightingale.state.NState;
import nightingale.ui.NActionListener;
import nightingale.ui.NButton;
import nightingale.ui.NLabel;
import nightingale.ui.NUIGroup;

public class Help implements NState{

	protected NActionListener listener = new HelpListener();
	
	public NUIGroup ui = new NUIGroup();
	
	public Help() {
		ui.addElement("BACK", new NButton("Back", 25, 500, 70, 40));
		
		ui.setActionListener(listener);
		ui.setCamera(TypeMaster.uiCamera);
		
		ui.getElements().forEach( (element) -> { 
			if(element instanceof NButton) { 
				((NButton)element).setFont(Fonts.uiFont);
				((NButton)element).setImages(Images.pressedButton, Images.focusedButton, Images.calmButton);
			}else if(element instanceof NLabel) {
				((NLabel)element).setFont(Fonts.uiFont);
			}
		} );
	}
	
	@Override
	public void install() {}

	@Override
	public void update() {
		ui.perform(TypeMaster.in);
	}

	@Override
	public void draw(Graphics g, Graphics2D g2d, AffineTransform at) {
		g.drawImage(Images.surviveBackground, 0, 0, TypeMaster.canvas.getWidth(), TypeMaster.canvas.getHeight(), null);
		Fonts.gameFont.draw("Main feature of a game is killing the monsters.", 14, 20, g2d, TypeMaster.gameCamera);
		Fonts.gameFont.draw("For this just type a name of a monster that on top", 14, 45, g2d, TypeMaster.gameCamera);
		Fonts.gameFont.draw("of a monster. Then your wizzard cast a powerfull", 14, 70, g2d, TypeMaster.gameCamera);
		Fonts.gameFont.draw("spell that kill the monster and you get a points!", 14, 95, g2d, TypeMaster.gameCamera);
		Fonts.gameFont.draw("If you need change settings, use 'Options' button in", 14, 130, g2d, TypeMaster.gameCamera);
		Fonts.gameFont.draw("main menu. ", 14, 155, g2d, TypeMaster.gameCamera);
		Fonts.gameFont.draw("You can just training in 'Training' mode. For start it", 14, 210, g2d, TypeMaster.gameCamera);
		Fonts.gameFont.draw("just use 'Training' button in mode selection menu.", 14, 235, g2d, TypeMaster.gameCamera);
		
		Fonts.gameFont.draw("I hope you having fun with this!", 14, 300, g2d, TypeMaster.gameCamera);
		Fonts.gameFont.draw("Dmitriy 'dyuz' Krushinskiy", 450, 430, g2d, TypeMaster.gameCamera);
		ui.draw(g);
	}
	
}
