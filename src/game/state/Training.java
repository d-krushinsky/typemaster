package game.state;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import game.Input;
import game.TypeMaster;
import game.entity.Monster;
import game.entity.MonsterType;
import game.entity.Wizard;
import game.resources.Fonts;
import game.resources.Images;
import game.state.listener.TrainingListener;
import nightingale.state.NState;
import nightingale.ui.NActionListener;
import nightingale.ui.NButton;
import nightingale.ui.NLabel;
import nightingale.ui.NUIGroup;
import util.Random;

public class Training implements NState{
	protected NActionListener listener = new TrainingListener();
	public NUIGroup ui = new NUIGroup();
	
	Wizard wizard = new Wizard(false);
	Monster doll = new Monster(MonsterType.Doll, "doll");
	{
		wizard.setNObjectAtributes(240, 340, 30, 45);
		doll.setNObjectAtributes(615, 340, 40, 45);
		doll.setX(615); doll.setY(340);
	}
	
	public Training() {
		ui.addElement("TO_MENU", new NButton("To Menu", 10, 10, 90, 30));
		
		ui.setCamera(TypeMaster.uiCamera);
		ui.setActionListener(listener);
		
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
	public void install() {
		TypeMaster.in.typingOn();
		
		wizard = new Wizard(false);
		doll = new Monster(MonsterType.Doll, "doll");
		
		wizard.setX(240); wizard.setY(340);
		wizard.setWidth(30); wizard.setHeight(45);
		doll.setX(615); doll.setY(340);
	}

	// !REMOVE IT WHEN REFACTORING!
	public String[] words = new String[] {
		"biba", "jopa", "KAVO i SHO", "Glad", "Dima", "Vlad", "Krol'"
	};
	// !REMOVE IT WHEN REFACTORING!
	
	@Override
	public void update() {
		ui.perform(TypeMaster.in);
		if(Input.ENTER_KEY.isClicked())
			if(TypeMaster.in.getTypedString().equals(doll.getName())) {
				doll.setName(words[Random.randomInt(words.length)]);
			}else {
				TypeMaster.in.setCurrentString(TypeMaster.in.getTypedString());
				TypeMaster.in.restoreTypedString();
			}
	}

	@Override
	public void draw(Graphics g, Graphics2D g2d, AffineTransform at) {
		ui.draw(g);
		g.drawImage(Images.surviveBackground, 0, (int)TypeMaster.gameCamera.getY(385),
				(int)TypeMaster.uiCamera.scale(Images.surviveBackground.getWidth()),
				(int)TypeMaster.uiCamera.scale(Images.surviveBackground.getHeight()), null);
		
		g.setColor(Color.GREEN);
		wizard.draw(g2d, TypeMaster.gameCamera);
		
		g.setColor(Color.RED);
		g.drawString(doll.getName(), (int)doll.getX(TypeMaster.gameCamera),	(int)doll.getY(TypeMaster.gameCamera)-1);
		doll.draw(g2d, TypeMaster.gameCamera);
		
		Fonts.extraFont.draw(
				TypeMaster.in.getCurrentString(),
				(int)TypeMaster.gameCamera.getX(300), 
				(int)TypeMaster.gameCamera.getY(450),
				g2d, TypeMaster.gameCamera);
	}
	
}
