package game.state;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

import game.Input;
import game.TypeMaster;
import game.entity.Monster;
import game.entity.MonsterType;
import game.entity.Whizzbang;
import game.entity.WhizzbangType;
import game.entity.Wizard;
import game.resources.Fonts;
import game.resources.Images;
import game.resources.Words;
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
	
	List<Whizzbang> whizzbangs = new ArrayList<Whizzbang>();
	List<Whizzbang> hitted = new ArrayList<Whizzbang>();
	
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
	
	@Override
	public void update() {
		ui.perform(TypeMaster.in);
		
		synchronized(whizzbangs){
			for(Whizzbang whizzbang : whizzbangs) {
				whizzbang.update();
				if(whizzbang.check()) {
					doll.setName(Words.getRandomWord());
					hitted.add(whizzbang);
				}
			}
			whizzbangs.removeAll(hitted);
		}
		
		if(Input.ENTER_KEY.isClicked())
			if(TypeMaster.in.getTypedString().equals(doll.getName())) {
				whizzbangs.add(new Whizzbang(doll, WhizzbangType.Fireball, (int)wizard.getX(), (int)wizard.getY()));
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
		
		doll.draw(g2d, TypeMaster.gameCamera);
		Fonts.gameFont.draw(
				doll.getName(),
				(int)(doll.getCenterX()-(Fonts.gameFont.getStringWidth(doll.getName()))/2),
				(int)(doll.getY()-Fonts.gameFont.getHeight()), 
				g2d, TypeMaster.gameCamera);
		
		synchronized(whizzbangs){
			for(Whizzbang whizzbang : whizzbangs) {
				whizzbang.draw(g2d, TypeMaster.gameCamera, at);
			}
		}
		
		Fonts.extraFont.draw(TypeMaster.in.getCurrentString(), 300, 450, g2d, TypeMaster.gameCamera);
	}
	
}
